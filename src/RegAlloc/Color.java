package RegAlloc;

import Graph.Node;
import Graph.NodeList;
import Mips.MipsFrame;
import Temp.Temp;
import Temp.TempList;
import Temp.TempMap;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Color implements TempMap {

    private final int K = 15;

    public TempList spills() {
        return null;
    }

    private HashMap<Temp, Temp> tableTempColor = new HashMap<>();

    private MipsFrame frame;

    public String tempMap(Temp t) {
        return frame.tempMap(tableTempColor.get(t));
    }

    public Color(InterferenceGraph ig, HashSet<Temp> initial, TempList registers, MipsFrame f) {
        frame = f;
        // Adding precolored nodes to graph
        Node nd;
        Temp tp;
        for (TempList l = registers; l != null; l = l.tail) {
            tp = l.head;
            nd = ig.tnode(tp);
            if (tp != null && nd == null) {
                nd = ig.newNode();
                ig.tableNodeTemp.put(nd, tp);
                ig.tableTempNode.put(tp, nd);
            }
        }

        build(ig, registers);
        makeWorkLists(initial, ig);

        boolean until_eval = true;
        while (until_eval) {
            if (!ig.simplifyWorklist.isEmpty()) {
                simplify(ig);
            } /*else if (!ig.worklistMoves.isEmpty()) {
                // TODO: coalesce
            } else if (!ig.freezeWorklist.isEmpty()) {
                // TODO: freeze
            } else if (!ig.spillWorklist.isEmpty()) {
                // TODO: selectSpill
            }*/
            until_eval = !ig.simplifyWorklist.isEmpty() /* ||
                         !ig.worklistMoves.isEmpty() ||
                         !ig.freezeWorklist.isEmpty() ||
                         !ig.spillWorklist.isEmpty()*/;
        }

        assignCollors(ig);

//        if (!ig.spilledNodes.isEmpty()) {
//            // TODO: rewriteProgram
//            new Color(ig, initial, registers);
//        }
        Liveness ref = (Liveness) ig;
        for (Temp i: ref.initial) {
            if (!TempList.inList(i, registers)) {
                tableTempColor.put(i, ref.colorMap.get(ref.color[ref.tnode(i).mykey]));
            } else {
                tableTempColor.put(i, i);
            }

        }
    }

    private void build(InterferenceGraph ig, TempList registers) {
        Liveness ref = (Liveness) ig;
        NodeList n = ref.flowgraph.nodes();
        Node t = null;
        int totalNodes = Node.len(ref.nodes());

        ref.degree = new Integer[totalNodes];
        ref.color = new Integer[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            ref.moveList.put(i, null);

            for (NodeList p = ref.nodes(); p != null; p = p.tail){
                if (p.head.mykey == i) {
                    t = p.head;
                    break;
                }
            }

            // Setting degree and color up
            if (t != null && !TempList.inList(ref.gtemp(t), registers)) {
                ref.degree[i] = 0;
                ref.color[i] = 0;
            } else {
                ref.degree[i] = totalNodes + K;
                ref.color[i] = i;
            }

            // Setting preColored up
            if (TempList.inList(ref.gtemp(t), registers)) {
                ref.preColored.add(t);
            }

            t = null;
        }

        ref.initAdjList(ig);
        ref.initInterferenceMatrix();
        for(NodeList p=n; p!=null; p=p.tail){
            HashSet<Temp> live = new HashSet<Temp>(ref.liveOut(p.head.mykey));
            if(ref.flowgraph.isMove(p.head)){
                diff(live, ref.flowgraph.use(p.head).toSet());
                HashSet<Temp> defUse = new HashSet<Temp>(union(ref.flowgraph.def(p.head).toSet(), ref.flowgraph.use(p.head).toSet()));

                for (Temp i: defUse) {
                    if (ref.tnode(i) != null){
//                      ref.moveList.get(ref.tnode(i).mykey).tail = new MoveList(p.head, p.head, ref.moveList.get(ref.tnode(i).mykey).tail);
                        ref.moveList.put(ref.tnode(i).mykey, new MoveList(ref.tnode(ref.flowgraph.use(p.head).head), ref.tnode(ref.flowgraph.def(p.head).head), ref.moveList.get(ref.tnode(i).mykey)));
                    }
                }
                ref.worklistMoves.add(new MovePair(ref.tnode(ref.flowgraph.use(p.head).head), ref.tnode(ref.flowgraph.def(p.head).head)));
            }
            live.addAll(ref.flowgraph.def(p.head).toSet());
            for (Temp i: ref.flowgraph.def(p.head).toSet()) {
                for (Temp l: live)
                    if (l != null && i != null)
                        addEdge(l, i, ref);
            }
            live.removeAll(ref.flowgraph.def(p.head).toSet());
            live.addAll(ref.flowgraph.use(p.head).toSet());
        }
    }

    private void makeWorkLists(HashSet<Temp> initial, InterferenceGraph ig) {
        Liveness ref = (Liveness) ig;
        HashSet<Temp> initialCopy = new HashSet<Temp>(initial);
        for (Temp i: initial) {
            if (i == null)
                continue;

            if (initialCopy.remove(i)) {
                if (ref.tnode(i) == null)
                    continue;

                if (ref.degree[ref.tnode(i).mykey] >= K) {
//                    ref.spillWorklist.add(ref.tnode(i));
                    System.out.println();
                } else if (moveRelated(ig, ref.tnode(i))) {
//                    ref.freezeWorklist.add(ref.tnode(i));
                    System.out.println();
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
        int count = 0;

        while (!ref.selectStack.empty()) {
            Node n = ref.selectStack.pop();
            okColors.clear();
            for (int i = 0; i < K; i++) {
                okColors.add(i);
            }

//            for (NodeList p = ref.adjList.get(n.mykey); p!=null; p=p.tail) {
//                if (ref.preColored.contains(getAlias(ref, p.head)) || ref.coalescedNodes.contains(getAlias(ref, p.head))) {
//                    okColors.remove(ref.color[getAlias(ref, p.head).mykey]);
//                }
//            }

//            if (okColors.size() == 0) {
//                ref.spilledNodes.add(n);
//            } else {
            if (count == K) {
                count = 0;
            }
            ref.coloredNodes.add(n);
            c = okColors.get(count);
            ref.color[n.mykey] = c;
            count++;
//            }
        }

//        for (Node x: ref.coalescedNodes) {
//            ref.color[x.mykey] = getAlias(ref, x).mykey;
//        }
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
                if (!Node.inList(ref.tnode(i), ref.adjList.get(ref.tnode(l).mykey))) {
                    ref.adjList.get(ref.tnode(l).mykey).tail = new NodeList(ref.tnode(i), ref.adjList.get(ref.tnode(l).mykey).tail);
                    ref.degree[ref.tnode(l).mykey] += 1;
                }
            }
            if (!ref.preColored.contains(ref.tnode(i))) {
                if (!Node.inList(ref.tnode(l), ref.adjList.get(ref.tnode(i).mykey))) {
                    ref.adjList.get(ref.tnode(i).mykey).tail = new NodeList(ref.tnode(l), ref.adjList.get(ref.tnode(i).mykey).tail);
                    ref.degree[ref.tnode(i).mykey] += 1;
                }
            }
            // Set corresponding bits on the interference matrix
            ref.setEdgeBits(ref.tnode(l).mykey, ref.tnode(i).mykey);
        }
    }

    void simplify(InterferenceGraph ig) {
        Node node = ig.simplifyWorklist.remove(0);
        if (node != null) {
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
        if (m != null) {
            ig.degree[m.mykey] -= 1;
            if (ig.degree[m.mykey] == K) {
                adjacent(ig, m);

                NodeList union = ig.adjList.get(m.mykey);
                if (!Node.inList(m, union))
                    union = new NodeList(m, union);

                enableMoves(ig, union);
                if (ig.spillWorklist.remove(m)) {
                    if (moveRelated(ig, m)) {
                        ig.freezeWorklist.add(m);
                    } else {
                        ig.simplifyWorklist.add(m);
                    }
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
        if (n != null) {
            LinkedList<MovePair> union = new LinkedList<MovePair>(ig.activeMoves);
            for (MovePair w: ig.worklistMoves)
                if (!union.contains(w))
                    union.add(w);

            MoveList intersect = null;
            Node s, d;
            for (MoveList mv = ig.moveList.get(n.mykey); mv != null; mv = mv.tail) {
                s = mv.src;
                d = mv.dst;
                if (s != null || d != null) {
                    for (MovePair m: union)
                        if (m.getDest() != null && s.mykey == m.getSource().mykey && d.mykey == m.getDest().mykey)
                            intersect = new MoveList(s, d, intersect);
                }
            }

            return intersect;
        }
        return null;
    }
}
