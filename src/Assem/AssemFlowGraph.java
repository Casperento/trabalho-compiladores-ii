package Assem;

import FlowGraph.FlowGraph;
import Graph.Node;
import Graph.NodeList;
import Temp.Label;
import Temp.Temp;
import Temp.TempList;

import java.util.Dictionary;
import java.util.Hashtable;

public class AssemFlowGraph extends FlowGraph {

    Dictionary tableNodeInstr = new Hashtable();
    Dictionary tableInstrNode = new Hashtable();

    public Instr instr(Node n) {
        return (Instr) tableNodeInstr.get(n);
    }

    public AssemFlowGraph(InstrList instrs) {
        Instr instr, jmpInstr = null;
        Node node;
        for (InstrList p = instrs; p != null; p = p.tail) {
            instr = p.head;
            node = this.newNode();
            tableNodeInstr.put(node, instr);
            tableInstrNode.put(instr, node);
        }
        for (NodeList nodes = this.nodes(); nodes != null; nodes = nodes.tail) {
            node = nodes.head;
            if (nodes.tail != null)
                node.setSuccs(nodes.tail.head);
        }
        for (InstrList p = instrs; p != null; p = p.tail) {
            instr = p.head;
            if (instr.jumps != null) {
                for (Label jmp : instr.jumps) {
                    for (InstrList q = instrs; q != null; q = q.tail) {
                        if (q.head.assem.equals(jmp.toString() + ":")) {
                            jmpInstr = q.head;
                            break;
                        }
                    }
                    this.addEdge((Node) tableInstrNode.get(instr), (Node) tableInstrNode.get(jmpInstr));
                }
            }
        }

        System.out.println("# Calculated use-defs for each instructions' blocks:\n");
        this.show(System.out);
    }

    @Override
    public TempList def(Node node) {
        Temp[] defVec = instr(node).def;
        return Temp.tempVecToList(defVec);
    }

    @Override
    public TempList use(Node node) {
        Temp[] useVec = instr(node).use;
        return Temp.tempVecToList(useVec);
    }

    @Override
    public boolean isMove(Node node) {
        return (instr(node).def == instr(node).use);
    }

}
