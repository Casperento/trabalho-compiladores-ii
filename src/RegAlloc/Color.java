package RegAlloc;

import Graph.Node;
import Graph.NodeList;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

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
        // TODO: build
        build(ig);
        // TODO: makeWorkList
        makeWorkLists(initial, ig);

        // TODO: repeat-until
        if (!ig.simplifyWorklist.isEmpty()) { // TODO: simplify
            simplify(ig);
        } else if (!ig.worklistMoves.isEmpty()) {
            // TODO: coalesce
        } else if (!ig.freezeWorklist.isEmpty()) {
            // TODO: freeze
        } else if (!ig.spillWorklist.isEmpty()) {
            // TODO: selectSpill
        }
        // TODO: assignColors
        assignCollors(ig);

        if (!ig.spilledNodes.isEmpty()) {
            // TODO: rewriteProgram
            new Color(ig, initial, registers);
        }

    }

    private void build(InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;
        NodeList n = ref.flowgraph.nodes();

        for (int i = 0; i < Node.len(ref.flowgraph.nodes()); i++) {
            ref.moveList.add(new MoveList(null, null, null));
        }

        for(NodeList p=n; p!=null; p=p.tail){
            HashSet<Temp> live = new HashSet<Temp>(ref.liveOut(p.head.mykey));
            if(ref.flowgraph.isMove(p.head)){
                diff(live, ref.flowgraph.use(p.head).toSet());
                HashSet<Temp> defUse = new HashSet<Temp>(union(ref.flowgraph.def(p.head).toSet(), ref.flowgraph.use(p.head).toSet()));

                for (Temp i: defUse) {
                    ref.moveList.get(ref.tnode(i).mykey).tail = new MoveList(p.head, p.head, ref.moveList.get(ref.tnode(i).mykey).tail); // TODO: inicializar movelist / avaliar instruções move
                }
                ref.worklistMoves.add(new MovePair(p.head, p.head)); // TODO: Dest
            }
            live.addAll(ref.flowgraph.def(p.head).toSet());
            ref.initAdjList();
            ref.initInterferenceMatrix();
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
                } else if (moveRelated(ref.tnode(i))) { // TODO: implementar essa função
                    ref.freezeWorklist.add(ref.tnode(i));
                }
                else {
                    ref.simplifyWorklist.add(ref.tnode(i));
                }
            }
        }
    }

    private void assignCollors(InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;
        List<Integer> okColors = new ArrayList<>();
        Integer c = 0;

        Dictionary regIndce = new Hashtable();
        for (int i = 0; i < K; i++) {
            regIndce.put(i, ref.initial.get(i));
        }

        while (!ref.selectStack.empty()) {
            Node n = ref.selectStack.pop();
            okColors.clear();
            for (int i = 0; i < K; i++) {
                okColors.add(i);
            }

            for (NodeList p = ref.adjList.get(n.mykey); p!=null; p=p.tail) {
                if (ref.preColored.contains(getAlias(ref, p.head)) || ref.coalescedNodes.contains(getAlias(ref, p.head))) {
                    okColors.remove(ref.color[getAlias(ref, p.head).mykey]);
                }
            }

            if (okColors.size() == 0) {
                ref.spilledNodes.add(n);
            } else {
                ref.coloredNodes.add(n);
                c = okColors.get(0);
                ref.color[n.mykey] = c;
            }
        }

        for (Node x: ref.coalescedNodes) {
            ref.color[x.mykey] = getAlias(ref, x).mykey;
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

    // TODO: change adjList search Node approach
    void simplify(InterferenceGraph ig) {
        for (Node n : ig.simplifyWorklist) {
            ig.selectStack.push(n);
            adjacent(ig, n);
            for (NodeList m = ig.adjList.get(ig.adjList.indexOf(n)); m != null; m = m.tail)
                decrementDegree(ig, m.head);
            ig.simplifyWorklist.removeFirstOccurrence(n);
        }
    }

    void adjacent(InterferenceGraph ig, Node n) {
        NodeList adj = ig.adjList.get(ig.adjList.indexOf(n));

        // TODO: selectStack | coalescedNodes
        NodeList union = new NodeList(null, null);

        for (Node c : ig.coalescedNodes)
            union = new NodeList(c, union);

        for (Node node : ig.selectStack)
            union = new NodeList(node, union); // TODO: do not add existant Node

        for (NodeList u = union; u != null; u = u.tail)
            adj = new NodeList(u.head, adj);

        ig.adjList.set(ig.adjList.indexOf(n), adj);
    }

    void decrementDegree(InterferenceGraph ig, Node m) {
        int d = 0;
        // TODO: degree[m]
        // int d = degree[m.mykey];
        if (d == K) {
            adjacent(ig, m);
            enableMoves(ig, ig.adjList.get(ig.adjList.indexOf(m)));
            if (ig.spillWorklist.remove(m)) {
                if (moveRelated(m)) {
                    ig.freezeWorklist.add(m);
                } else {
                    ig.simplifyWorklist.add(m);
                }
            }
        }
    }

    void enableMoves(InterferenceGraph ig, NodeList nodes) {
        MovePair tmp;
        for (NodeList n = nodes; n != null; n = n.tail) {
            for (MoveList m = nodeMoves(ig, n.head); m != null; m = m.tail) {
                tmp = new MovePair(m.src, m.dst); // TODO: change MovePair object to following remove and add methods work
                if (ig.activeMoves.contains(tmp)) {
                    ig.activeMoves.remove(tmp);
                    ig.worklistMoves.add(tmp);
                }
            }
        }
    }

    private Node getAlias(Liveness ref, Node n) {
        if (ref.coalescedNodes.contains(n)) {
            return getAlias(ref, ref.adjList.get(ref.alias.get(n.mykey)).head);
        } else {
            return n;
        }
    }

    boolean moveRelated(Node m) {
        // TODO: NodeMoves(n) != {}
        return false;
    }

    MoveList nodeMoves(InterferenceGraph ig, Node n) {
        // TODO: moveList[n] & (activeNodes | worklistMoves)
        return null;
    }
}
