// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.

import Assem.InstrList;
import Assem.LABEL;
import Mips.MipsFrame;
import Parser.MiniJavaParser;
import Parser.ParseException;
import RegAlloc.RegAlloc;
import Temp.Label;
import Tree.Stm;
import syntaxtree.Node;
import types.MJClasses;
import types.MJType;
import visitor.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    static PrintWriter debug = new PrintWriter(System.out);
    static Frame.Frame frame = new MipsFrame();

    static void prStmList(Tree.StmList stms) {
        for(Tree.StmList l = stms; l!=null; l=l.tail) {
            new Tree.Print(debug, l.head);
        }
    }

    static Assem.InstrList codegen(Frame.Frame f, Tree.StmList stms) {
        Assem.InstrList first=null, last=null;
        for(Tree.StmList s=stms; s!=null; s=s.tail) {
            ArrayList<Stm> code = new ArrayList<Stm>();
            code.add(s.head);
            Assem.InstrList i = InstrList.fromArrayList(f.codegen(code));
            if (last==null) {
                first=last=i;
            } else {
                while (last.tail!=null) last=last.tail;
                last=last.tail=i;
            }
        }
        return first;
    }

    static void emitProc(java.io.PrintStream out, ProcFrag f) {
        Temp.TempMap tempmap= new Temp.CombineMap(f.frame,new Temp.DefaultMap());
        debug.println("# Before canonicalization: ");
        new Tree.Print(debug, tempmap, f.body);
        debug.print("# After canonicalization: ");
        Tree.StmList stms = Canon.Canon.linearize(f.body);
        prStmList(stms);
        debug.println("# Basic Blocks: ");
        Canon.BasicBlocks b = new Canon.BasicBlocks(stms);
        for(Canon.StmListList l = b.blocks; l!=null; l=l.tail) {
            debug.println("#");
            prStmList(l.head);
        }
        new Tree.Print(debug, tempmap, new Tree.LABEL(b.done));
        debug.println("# Trace Scheduled: ");
        Tree.StmList traced = (new Canon.TraceSchedule(b)).stms;
        prStmList(traced);

        Assem.InstrList instrs = codegen(f.frame,traced);
        String frag_label = new String();

        if (f.frame.name.toString().contains("main"))
            frag_label = "main:";
        else
            frag_label = f.frame.name + ":";

        instrs = new InstrList(new LABEL(frag_label, new Label()), instrs);

        // Register allocation by graph coloring
        RegAlloc allocator = new RegAlloc(f.frame, instrs);

        debug.println("# Instructions: ");
        for(Assem.InstrList p=instrs; p!=null; p=p.tail) {
            debug.print(p.head.format(allocator) + '\n');
            out.print(p.head.format(allocator) + '\n');
        }

    }

    public static void main(String [] args) throws java.io.IOException {
        Node root = null;
        InputStream stream;
        MJType my_classes;
        Translate translate;
        PrintStream out;
        Frag f;
        FileOutputStream oStream;
        String newName;
        File dir = new File("tests/"), inputFile;
        File[] dirList = dir.listFiles();
        if (dirList != null) {
            for (int i = dirList.length-1; i >= 0; i--) {
                inputFile = dirList[i];
                try {
                    stream = new FileInputStream(inputFile);
                    try {
                        if (root == null)
                            new MiniJavaParser(stream);
                        else
                            MiniJavaParser.ReInit(stream);
                        root = MiniJavaParser.Goal();
                    } catch (Error | ParseException e) {
                        System.err.println("Catched exception when trying to parse " + inputFile + " source code: " + e);
                        continue;
                    }

                    my_classes = new MJClasses();
                    root.accept(new GJFillTable(), my_classes);

                    translate = new Translate(frame);
                    root.accept(translate, my_classes);

                    newName = "foo_" + inputFile.getName() + ".s";
                    oStream = new FileOutputStream(newName);
                    out = new PrintStream(oStream);
                    out.println("\t.data\n\t.text\n\t.globl main\n");
                    for (Iterator<Frag> frags = translate.getResults(); frags.hasNext(); ) {
                        f = frags.next();
                        if (f instanceof ProcFrag) {
                            emitProc(out, (ProcFrag) f);
                        } else if  (f instanceof DataFrag) {
                            out.print(((DataFrag) f).data);
                        }
                    }
                    oStream.close();
                    out.close();
                    stream.close();
                } catch (FileNotFoundException e) {
                    System.err.println("File not found exception: " + e);
                }
            }
        }
        debug.close();
    }
}
