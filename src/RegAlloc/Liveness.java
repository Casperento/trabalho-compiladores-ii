package RegAlloc;

import Assem.AssemFlowGraph;
import Graph.Node;
import Graph.NodeList;
import Mips.MipsFrame;
import Temp.Temp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Liveness extends InterferenceGraph {
    private Map<Integer, HashSet<Temp>> liveMap;
    protected AssemFlowGraph flowgraph;
    private final int K = 15;
    protected HashSet<Temp> initial;
    public Liveness(AssemFlowGraph flow, MipsFrame f) {
        flowgraph = flow;
        int totalNodes = Node.len(flow.nodes());

        Map<Integer, HashSet<Temp>> use = new HashMap<Integer, HashSet<Temp>>();
        Map<Integer, HashSet<Temp>> def = new HashMap<Integer, HashSet<Temp>>();
        Map<Integer, NodeList> succ = new HashMap<Integer, NodeList>();
        Map<Integer, HashSet<Temp>> in = new HashMap<Integer, HashSet<Temp>>();
        Map<Integer, HashSet<Temp>> inl = new HashMap<Integer, HashSet<Temp>>();
        Map<Integer, HashSet<Temp>> out = new HashMap<Integer, HashSet<Temp>>();
        Map<Integer, HashSet<Temp>> outl = new HashMap<Integer, HashSet<Temp>>();

        Node n;
        for (NodeList p = flow.nodes(); p != null; p = p.tail) {
            n = p.head;
            if (flow.use(n) != null)
                use.put(n.mykey, flow.use(n).toSet());
            else
                use.put(n.mykey, new HashSet<Temp>());

            if (flow.def(n) != null)
                def.put(n.mykey, flow.def(n).toSet());
            else
                def.put(n.mykey, new HashSet<Temp>());

            if (n.succ() != null)
                succ.put(n.mykey, n.succ());
            else
                succ.put(n.mykey, new NodeList(null, null));
        }

        // for each n
        for (int i = 0; i < totalNodes; i++) {
            in.put(i, new HashSet<Temp>());
            out.put(i, new HashSet<Temp>());
        }

        // repeat-until
        boolean until_eval = true;
        HashSet<Temp> t1;
        HashSet<Temp> t2;
        HashSet<Temp> t3;
        HashSet<Temp> t4;
        HashSet<Temp> outUnion;
        while (until_eval) {
            for (int nn = totalNodes - 1; nn >= 0; nn--) {
                inl.put(nn, new HashSet<Temp>(in.get(nn))); // inl_n = in_n
                outl.put(nn, new HashSet<Temp>(out.get(nn))); // outl_n = out_n

                // diff = out_n - def_n
                t1 = new HashSet<Temp>(out.get(nn));
                t2 = new HashSet<Temp>(def.get(nn));
                t1.removeAll(t2);

                // in_n = use_n | diff
                t3 = new HashSet<Temp>(use.get(nn));
                t3.addAll(t1);
                in.put(nn, t3);

                // out_n = U_s_succ in_s
                outUnion = new HashSet<Temp>();
                for (NodeList s = succ.get(nn); s != null; s = s.tail) {
                    if (s.head != null) {
                        t1 = new HashSet<Temp>(in.get(s.head.mykey));
                        outUnion.addAll(t1);
                    }
                }
                out.put(nn, outUnion);
            }

            until_eval = false;
            for (int r = 0; r < totalNodes; r++) {
                t1 = outl.get(r);
                t2 = inl.get(r);
                t3 = out.get(r);
                t4 = in.get(r);
                if (!(t1.containsAll(t3) && t1.size() == t3.size()) || !(t2.containsAll(t4) && t2.size() == t4.size())) {
                    until_eval = true;
                    break;
                }
            }
        }
        liveMap = out;

        // Creating nodes for each temp available at each basic block
        Node nd;
        HashSet<Temp> tmp;
        initial = new HashSet<Temp>();
        for (int i = 0; i < totalNodes; i++) {
            tmp = def.get(i);

            if (tmp.size() == 17)
                tmp = use.get(i);
            else
                tmp.addAll(use.get(i));

            for (Temp tp : tmp) {
                if (!initial.contains(tp)) {
                    nd = this.newNode();
                    tableNodeTemp.put(nd, tp);
                    tableTempNode.put(tp, nd);
                    initial.add(tp);
                }
            }
        }

        for (int i = 0; i < K; i++) {
            this.colorMap.put(i, f.getInitial().get(i));
        }
    }

    public HashSet<Temp> liveOut(int n) {
        return liveMap.get(n);
    }

    @Override
    public Node tnode(Temp temp) {
        return tableTempNode.get(temp);
    }

    @Override
    public Temp gtemp(Node node) {
        return tableNodeTemp.get(node);
    }

    @Override
    public HashMap<Integer,MoveList> moves() {
        return this.moveList;
    }
}
