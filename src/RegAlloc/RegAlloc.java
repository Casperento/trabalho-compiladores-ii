package RegAlloc;

import Assem.AssemFlowGraph;
import Assem.InstrList;
import Frame.Frame;
import Temp.Temp;
import Temp.TempMap;

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
        Color regalloc = new Color(liveness, liveness.initial, Temp.tempVecToList(f.registers())); // TODO: test and fix initial and registers params
    }
}
