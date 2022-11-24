package RegAlloc;

import Assem.AssemFlowGraph;
import Assem.InstrList;
import Frame.Frame;
import Mips.MipsFrame;
import Temp.Temp;
import Temp.TempMap;

public class RegAlloc implements TempMap {
    public InstrList instrs;
    private Color regalloc = null;
    @Override
    public String tempMap(Temp t) {
        return regalloc.tempMap(t);
    }
    public RegAlloc(Frame f, InstrList il) {
        instrs = il;
        AssemFlowGraph flowgraph = new AssemFlowGraph(instrs); // build flowgraph for liveness analysis
        Liveness liveness = new Liveness(flowgraph, (MipsFrame) f); // run liveness analysis (Liveness extends InterferenceGraph)
        regalloc = new Color(liveness, liveness.initial, Temp.tempVecToList(f.registers()), (MipsFrame) f);
    }
}
