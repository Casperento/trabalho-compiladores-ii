package RegAlloc;

import Temp.Temp;
import Temp.TempList;
import Temp.TempMap;

public class Color implements TempMap {

    public TempList spills() {
        return null;
    }

    public String tempMap(Temp t) {
        return null;
    }

    public Color(InterferenceGraph ig, TempMap initial, TempList registers) {
        // Example to get flowgraph nodes (instructions)
//        Liveness ref = (Liveness) ig;
//        NodeList n = ref.flowgraph.nodes();

        // TODO: build
        // TODO: makeWorkList
        // TODO: simplify
        // TODO: coalesce
        // TODO: spill
        // TODO: assignColors
        // TODO: rewriteProgram

    }
}
