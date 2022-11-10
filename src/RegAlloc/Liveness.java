package RegAlloc;

import FlowGraph.FlowGraph;
import Graph.Node;
import Graph.NodeList;
import Temp.Temp;
import Temp.TempList;

import java.util.Dictionary;
import java.util.Hashtable;

public class Liveness extends InterferenceGraph {
    private Dictionary liveMap;
    public FlowGraph flowgraph;
    public Liveness(FlowGraph flow) {
        flowgraph = flow;
        int totalNodes = Node.len(flow.nodes());

        Dictionary use = new Hashtable();
        Dictionary def = new Hashtable();
        Dictionary succ = new Hashtable();
        Dictionary in = new Hashtable();
        Dictionary inl = new Hashtable();
        Dictionary out = new Hashtable();
        Dictionary outl = new Hashtable();

        Node n;
        for (NodeList p = flow.nodes(); p != null; p = p.tail) {
            n = p.head;
            if (flow.use(n) != null)
                use.put(n.mykey, flow.use(n));

            if (flow.def(n) != null)
                def.put(n.mykey, flow.def(n));

            if (n.succ() != null)
                succ.put(n.mykey, n.succ());
        }

        // for each n
        for (int i = 0; i < totalNodes; i++) {
            in.put(i, new TempList(null, null));
            out.put(i, new TempList(null, null));
        }

        // repeat-until
        int[] iterateds = new int[totalNodes];
        int sum = 0;
        TempList diff = new TempList(null, null), tmp, union, un;
        do {
            for (int nn = totalNodes - 1; nn >= 0; nn--) {
                inl.put(nn, in.get(nn)); // inl_n = in_n
                outl.put(nn, out.get(nn)); // outl_n = out_n

                // diff = out_n - def_n
                tmp = (TempList) def.get(nn);
                for (TempList on = (TempList) out.get(nn); on != null; on = on.tail) {
                    if (on.head != null && !TempList.inList(on.head, tmp)) {
                        diff = new TempList(on.head, diff);
                    }
                }

                // in_n = use_n | diff
                union = (TempList) use.get(nn);
                for (un = union; un != null; un = un.tail) { // get last (null tail) element reference
                    if (un.head != null && un.tail == null) {
                        un.tail = diff;
                        break;
                    }
                }

                if (union != null)
                    in.put(nn, union);

                // out_n = U_s_succ in_s
                for (NodeList g = (NodeList) succ.get(nn); g != null; g = g.tail) {
                    out.put(nn, in.get(g.head.mykey));
                }
            }

            for (int r = 0; r < totalNodes; r++) {
                if (outl.get(r) == out.get(r) && inl.get(r) == in.get(r)) {
                    iterateds[r] = 1;
                }
            }

            sum = 0;
            for (int r = 0; r < totalNodes; r++)
                sum += iterateds[r];

        } while (sum < totalNodes);
        liveMap = out;
    }

    public TempList liveOut(int n) {
        return (TempList) liveMap.get(n);
    }

    @Override
    public Node tnode(Temp temp) {
        return null; // TODO: impl
    }

    @Override
    public Temp gtemp(Node node) {
        return null; // TODO: impl
    }

    @Override
    public MoveList moves() {
        return null; // TODO: impl
    }
}
