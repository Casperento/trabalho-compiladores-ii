// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.

import Assem.AssemFlowGraph;
import Assem.InstrList;
import Mips.MipsFrame;
import Parser.MiniJavaParser;
import Parser.ParseException;
import Tree.Stm;
import syntaxtree.Node;
import types.MJClasses;
import types.MJType;
import visitor.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
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
            if (last==null) {first=last=i;}
            else {while (last.tail!=null) last=last.tail;
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
        Assem.InstrList instrs= codegen(f.frame,traced);

        // Register allocation by graph coloring
        AssemFlowGraph flowgraph = new AssemFlowGraph(instrs);
        debug.println("# Flowgraph: ");
        flowgraph.show(out);

        debug.println("# Instructions: ");
        for(Assem.InstrList p=instrs; p!=null; p=p.tail){
            debug.print(p.head.format(tempmap) + '\n');
            out.print(p.head.format(tempmap) + '\n');
        }

    }

    public static void main(String [] args) throws java.io.IOException{
        Node root;
        try {
            File inputFile = new File("tests/Factorial.java");
            InputStream stream = new FileInputStream(inputFile);
            root = (new MiniJavaParser(stream)).Goal();
        }
        catch (ParseException e){
            System.err.println (e.toString() );
            return;
        }

        //build a symbol table
        MJType my_classes = new MJClasses();
        root.accept(new GJFillTable(),my_classes);


        //this visitor is used to translate to IR
//        Translate translate=new Translate(new Mips.MipsFrame());
        Translate translate=new Translate(frame);
        root.accept(translate, my_classes);

        //the translate visitor has a getResults method which returns
        //an iterator of fragments. The following code illustrates how to
        //iterate through the fragments. I am printing out the IR Tree to
        //stdout here, but one could as well implement a backend to compile
        //the IR to assembly at this point.

        java.io.PrintStream out = new java.io.PrintStream(new java.io.FileOutputStream("foo.s"));
        for (Iterator<visitor.Frag> frags = translate.getResults(); frags.hasNext(); ) {

            //get next fragment
            Frag f = frags.next();

            //if the fragment is a ProcFrag i.e one which contains a procedure
            //then I get the map of temps associated with it and print it out.

            if (f instanceof ProcFrag){
                emitProc(out, (ProcFrag) f);
            } else if  (f instanceof DataFrag) {
                out.print(((DataFrag) f).data);
            }
        }

        out.close();
        debug.close();
    }
}
