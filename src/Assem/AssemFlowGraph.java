package Assem;

import FlowGraph.FlowGraph;
import Graph.Node;
import Graph.NodeList;
import Temp.Label;
import Temp.Temp;
import Temp.TempList;

import java.util.HashMap;
import java.util.Map;

public class AssemFlowGraph extends FlowGraph {

    Map<Node, Instr> tableNodeInstr = new HashMap<Node, Instr>();
    Map<Instr, Node> tableInstrNode = new HashMap<Instr, Node>();
    Map<Node, Temp[]> tableNodeTemp = new HashMap<Node, Temp[]>();
    Map<Temp[], Node> tableTempNode = new HashMap<Temp[], Node>();

    public Map<Node, Temp[]> getTableNodeTemp() {
        return tableNodeTemp;
    }

    public Map<Temp[], Node> getTableTempNode() {
        return tableTempNode;
    }

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

            if (instr.def == null || instr.use == null)
                continue;

            Temp[] temps = new Temp[instr.def.length + instr.use.length];
            for (int t = 0; t < instr.def.length; t++)
                temps[t] = instr.def[t];
            for (int t = instr.def.length; t < instr.use.length; t++)
                temps[t] = instr.use[t];
            tableNodeTemp.put(node, temps);
            tableTempNode.put(temps, node);
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
        if (defVec == null)
            return new TempList(null, null);
        return Temp.tempVecToList(defVec);
    }

    @Override
    public TempList use(Node node) {
        Temp[] useVec = instr(node).use;
        if (useVec == null)
            return new TempList(null, null);
        return Temp.tempVecToList(useVec);
    }

    @Override
    public boolean isMove(Node node) {
        return (instr(node) instanceof MOVE);
    }

}
