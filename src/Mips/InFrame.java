package Mips;import Tree.Exp1;public class InFrame extends Frame.Access {    int offset;    InFrame(int o) {        offset = o;    }    public Exp1 exp(Exp1 fp) {        return new Tree.MEM               (new Tree.BINOP(Tree.BINOP.PLUS, fp, new Tree.CONST(offset)));    }    public String toString() {        Integer offset = new Integer(this.offset);        return offset.toString();    }}