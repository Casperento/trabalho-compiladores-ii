package RegAlloc;

import Graph.Node;
import Graph.NodeList;
import Mips.MipsFrame;
import Temp.Temp;
import Temp.TempList;
import Temp.TempMap;

import java.util.*;

public class Color implements TempMap {

    private final int K = 15;

    public TempList spills() {
        return null;
    }

    public String tempMap(Temp t) {
        return null;
    }

    public Color(InterferenceGraph ig, MipsFrame initial, TempList registers) {
        build(ig);
        makeWorkLists(initial, ig);

        boolean until_eval = true;
        while (until_eval) {
            if (!ig.simplifyWorklist.isEmpty()) {
                simplify(ig);
            } else if (!ig.worklistMoves.isEmpty()) {
                // TODO: coalesce
            } else if (!ig.freezeWorklist.isEmpty()) {
                // TODO: freeze
            } else if (!ig.spillWorklist.isEmpty()) {
                // TODO: selectSpill
            }
            until_eval = !ig.simplifyWorklist.isEmpty() ||
                         !ig.worklistMoves.isEmpty() ||
                         !ig.freezeWorklist.isEmpty() ||
                         !ig.spillWorklist.isEmpty();
        }

        assignCollors(ig);

        if (!ig.spilledNodes.isEmpty()) {
            // TODO: rewriteProgram
            new Color(ig, initial, registers);
        }

    }

    private void build(InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;
        NodeList n = ref.flowgraph.nodes();
        int totalNodes = Node.len(ref.nodes());

        ref.degree = new Integer[totalNodes];
        ref.color = new Integer[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            ref.moveList.add(new MoveList(null, null, null));
            ref.degree[i] = 0;
            ref.color[i] = 0;
        }

        ref.initAdjList(ig);
        ref.initInterferenceMatrix();

        for(NodeList p=n; p!=null; p=p.tail){
            HashSet<Temp> live = new HashSet<Temp>(ref.liveOut(p.head.mykey));
            if(ref.flowgraph.isMove(p.head)){
                diff(live, ref.flowgraph.use(p.head).toSet());
                HashSet<Temp> defUse = new HashSet<Temp>(union(ref.flowgraph.def(p.head).toSet(), ref.flowgraph.use(p.head).toSet()));

                for (Temp i: defUse) {
                    if (ref.tnode(i) != null)
                        ref.moveList.get(ref.tnode(i).mykey).tail = new MoveList(p.head, p.head, ref.moveList.get(ref.tnode(i).mykey).tail); // TODO: inicializar movelist / avaliar instruções move
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

                if (ref.tnode(i) == null)
                    continue;

                if (ref.degree[ref.tnode(i).mykey] >= K) {
                    ref.spillWorklist.add(ref.tnode(i));
                } else if (moveRelated(ig, ref.tnode(i))) {
                    ref.freezeWorklist.add(ref.tnode(i));
                } else {
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

        if (ref.tnode(i) == null || ref.tnode(l) == null)
            return;

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
            // Set corresponding bits on the interference matrix
            ref.setEdgeBits(ref.tnode(l).mykey, ref.tnode(i).mykey);
        }
    }

    void simplify(InterferenceGraph ig) {
        Node node = null;
        for (int i = 0; i < ig.simplifyWorklist.size(); i++) {
            if (ig.simplifyWorklist.get(i).degree() < K) {
                node = ig.simplifyWorklist.get(i);
                break;
            }
        }

        if (node != null) {
            ig.simplifyWorklist.removeFirstOccurrence(node);
            ig.selectStack.push(node);
            adjacent(ig, node);
            for (NodeList m = ig.adjList.get(node.mykey); m != null; m = m.tail)
                decrementDegree(ig, m.head);
        }
    }

    void adjacent(InterferenceGraph ig, Node n) {
        NodeList adj = ig.adjList.get(n.mykey), union = new NodeList(null, null);

        for (Node c : ig.coalescedNodes)
            union = new NodeList(c, union);

        for (Node node : ig.selectStack)
            if (!Node.inList(node, union))
                union = new NodeList(node, union);

        for (NodeList u = union; u != null; u = u.tail)
            if (!Node.inList(u.head, adj))
                adj = new NodeList(u.head, adj);

        ig.adjList.set(n.mykey, adj);
    }

    void decrementDegree(InterferenceGraph ig, Node m) {
        int d = ig.degree[m.mykey];
        if (d == K) {
            adjacent(ig, m);
            enableMoves(ig, ig.adjList.get(m.mykey));
            if (ig.spillWorklist.remove(m)) {
                if (moveRelated(ig, m)) {
                    ig.freezeWorklist.add(m);
                } else {
                    ig.simplifyWorklist.add(m);
                }
            }
        }
    }

    void enableMoves(InterferenceGraph ig, NodeList nodes) {
        Node s, d;
        MovePair el = null;
        for (NodeList n = nodes; n != null; n = n.tail) {
            for (MoveList m = nodeMoves(ig, n.head); m != null; m = m.tail) {
                s = m.src;
                d = m.dst;
                for (MovePair mv: ig.activeMoves) {
                    if (s.mykey == mv.getSource().mykey && d.mykey == mv.getDest().mykey) {
                        el = mv;
                        break;
                    }
                }
                if (el != null) {
                    ig.activeMoves.remove(el);
                    ig.worklistMoves.add(el); // TODO: test and fix possible issue on not finding el MovePair
                    el = null;
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
    boolean moveRelated(InterferenceGraph ig, Node m) {
        return (MoveList.len(nodeMoves(ig, m)) != 0);
    }

    MoveList nodeMoves(InterferenceGraph ig, Node n) {
        LinkedList<MovePair> union = new LinkedList<MovePair>(ig.activeMoves);
        for (MovePair w: ig.worklistMoves)
            if (!union.contains(w))
                union.add(w);

        MoveList intersect = new MoveList(null, null, null);
        Node s, d;
        for (MoveList mv = ig.moveList.get(n.mykey); mv != null; mv = mv.tail) {
            s = mv.src;
            d = mv.dst;
            for (MovePair m: union)
                if (s.mykey == m.getSource().mykey && d.mykey == m.getDest().mykey)
                    intersect = new MoveList(s, d, intersect);
        }

        return intersect;
    }
}
