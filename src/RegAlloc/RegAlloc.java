package RegAlloc;

import Assem.AssemFlowGraph;
import Assem.InstrList;
import Mips.MipsFrame;
import Temp.Temp;
import Temp.TempMap;
import Frame.Frame;

public class RegAlloc implements TempMap {
    public InstrList instrs;
    @Override
    public String tempMap(Temp t) {
        return null;
    }
    public RegAlloc(Frame f, InstrList il) {
        instrs = il;
        AssemFlowGraph flowgraph = new AssemFlowGraph(instrs); // build flowgraph for liveness analysis
        Liveness liveness = new Liveness(flowgraph); // run liveness analysis (Liveness extends InterferenceGraph)
        Color regalloc = new Color(liveness, (MipsFrame) f, Temp.tempVecToList(f.registers())); // TODO: test and fix initial and registers params
    }
}
