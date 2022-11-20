package RegAlloc;

import java.util.HashSet;
import java.util.LinkedList;

import Graph.Node;
import Graph.NodeList;
import Mips.MipsFrame;
import Temp.Temp;
import Temp.TempList;
import Temp.TempMap;

public class Color implements TempMap {

    private int K = 15;

    public TempList spills() {
        return null;
    }

    public String tempMap(Temp t) {
        return null;
    }

    public Color(InterferenceGraph ig, MipsFrame initial, TempList registers) {
        // Example to get flowgraph nodes (instructions)
//        Liveness ref = (Liveness) ig;
//        NodeList n = ref.flowgraph.nodes();

        // TODO: build y
        build(ig);
        // TODO: makeWorkList y
        makeWorkLists(initial, ig);
        // TODO: simplify r
        //  TODO: coalesce y
        //  TODO: spill r
        // TODO: assignColors y
        //  TODO: rewriteProgram r

    }

    private void build(InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;
        NodeList n = ref.flowgraph.nodes();

        for(NodeList p=n; p!=null; p=p.tail){
            HashSet<Temp> live = new HashSet<Temp>(ref.liveOut(p.head.mykey));
            if(ref.flowgraph.isMove(p.head)){
                diff(live, ref.flowgraph.use(p.head).toSet());
                HashSet<Temp> defUse = new HashSet<Temp>(union(ref.flowgraph.def(p.head).toSet(), ref.flowgraph.use(p.head).toSet()));

                for (Temp i: defUse) {
                    for(NodeList j=p.head.succ(); j!=null; j=j.tail){
                        ref.moveList.get(ref.tnode(i).mykey).tail = new MoveList(p.head, j.head, ref.moveList.get(ref.tnode(i).mykey).tail); // TODO: inicializar movelist / avaliar instruções move
                    }
                }
                ref.worklistMoves.add(new MovePair(p.head, p.head)); // TODO: Dest
            }
            live.addAll(ref.flowgraph.def(p.head).toSet());
            for (Temp i: ref.flowgraph.def(p.head).toSet()) {
                for (Temp l: live) {
                    addEdge(l, i, ref);
                }
            }
            live.removeAll(ref.flowgraph.def(p.head).toSet());
            live.addAll(ref.flowgraph.use(p.head).toSet());
        }
    }

    private void makeWorkLists(MipsFrame initial, InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;

        LinkedList<Temp> initialCopy = new LinkedList<Temp>(initial.getInitial());
        LinkedList<Temp> tmp;
        for (Temp i: initialCopy) {
            tmp = initial.getInitial();
            if (tmp.remove(i)) {
                initial.setInitial(tmp);
                if (ref.degree[ref.tnode(i).mykey] >= K) {
                    ref.spillWorklist.add(ref.tnode(i));
//                } else if (moveRelated(ref.tnode(i))) { // TODO: implementar essa função
//                    ref.freezeWorklist.add(ref.tnode(i));
                }
                else {
                    ref.simplifyWorklist.add(ref.tnode(i));
                }
            }
        }
    }

    private void diff(HashSet<Temp> live, HashSet<Temp> useI) {
        live.removeAll(useI);
    }

    private HashSet<Temp> union(HashSet<Temp> def, HashSet<Temp> use) {
        def.addAll(use);
        return def;
    }

    private void addEdge(Temp l, Temp i, Liveness ref) {
        EdgePair aux = new EdgePair(ref.tnode(l), ref.tnode(i));
        EdgePair aux2 = new EdgePair(ref.tnode(i), ref.tnode(l));
        if (!ref.adjSet.contains(aux) && (ref.tnode(l).mykey != ref.tnode(i).mykey)) {
            ref.adjSet.add(aux);
            ref.adjSet.add(aux2);
            if (!ref.preColored.contains(ref.tnode(l))) {
                ref.adjList.get(ref.tnode(l).mykey).tail = new NodeList(ref.tnode(i), ref.adjList.get(ref.tnode(l).mykey).tail);
                ref.degree[ref.tnode(l).mykey] += 1;
            }
            if (!ref.preColored.contains(ref.tnode(i))) {
                ref.adjList.get(ref.tnode(i).mykey).tail = new NodeList(ref.tnode(l), ref.adjList.get(ref.tnode(i).mykey).tail);
                ref.degree[ref.tnode(i).mykey] += 1;
            }
        }
    }

}
