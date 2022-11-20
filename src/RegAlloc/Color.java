package RegAlloc;

import Graph.Node;
import Graph.NodeList;
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

    public Color(InterferenceGraph ig, TempMap initial, TempList registers) {
        // TODO: build
        // TODO: makeWorkList

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

        if (!ig.spilledNodes.isEmpty()) {
            // TODO: rewriteProgram
            new Color(ig, initial, registers);
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

    boolean moveRelated(Node m) {
        // TODO: NodeMoves(n) != {}
        return false;
    }

    MoveList nodeMoves(InterferenceGraph ig, Node n) {
        // TODO: moveList[n] & (activeNodes | worklistMoves)
        return null;
    }
}
