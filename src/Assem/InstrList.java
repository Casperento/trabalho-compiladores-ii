package Assem;

import java.util.List;

public class InstrList {
  public Instr head;
  public InstrList tail;
  public InstrList(Instr h, InstrList t) {
    head=h; tail=t;
  }
  public static InstrList fromArrayList(List<Instr> exprs) {
    if (exprs.isEmpty()) {
      return null;
    }
    Instr first = exprs.remove(0);
    return new InstrList(first, fromArrayList(exprs));
  }
}
