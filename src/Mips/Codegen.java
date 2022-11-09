// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Mips;
import Temp.Temp;
import Temp.*;
import Temp.Label;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Arrays;
import Assem.Instr;
import Tree.ExpList;

public class Codegen implements Tree.CodeVisitor
{
    private MipsFrame frame;
    private ListIterator<Instr> insns;
    public Codegen(MipsFrame f, ListIterator<Instr> i)
    {
	frame = f;
	insns = i;
    }

    private void emit(Instr inst)
    {
	insns.add(inst);
    }

    static Assem.Instr OPER(String a, Temp[] d, Temp[] s, List<Label> j)
    {
	return new Assem.OPER("\t" + a, d, s, j);
    }
    static Assem.Instr OPER(String a, Temp[] d, Temp[] s)
    {
	return OPER(a, d, s, null);
    }
    static Instr MOVE(String a, Temp d, Temp s)
    {
	return new Assem.MOVE("\t" + a, d, s);
    }

    private static Tree.CONST CONST16(Tree.Exp e)
    {
        if (e instanceof Tree.CONST) {
	    Tree.CONST c = (Tree.CONST)e;
	    int value = c.value;
	    if (value == (short)value)
		return c;
	}
	return null;
    }

    private static boolean immediate(Tree.BINOP e) {
	Tree.CONST left = CONST16(e.left);
	Tree.CONST right = CONST16(e.right);
	if (left == null)
	    return right != null;
	switch (e.binop) {
	case Tree.BINOP.PLUS:
	case Tree.BINOP.MUL:
	case Tree.BINOP.AND:
	case Tree.BINOP.OR:
	case Tree.BINOP.XOR:
	    if (right == null) {
		e.left = e.right;
		e.right = left;
	    }
	    return true;
	}
	return false;
    }

    public void visit(Tree.MOVE s) {
	// MOVE(MEM, Exp)
	if (s.dst instanceof Tree.MEM) {
	    Tree.MEM mem = (Tree.MEM)s.dst;

            // MOVE(MEM(+ Exp CONST), Exp)
            if (mem.exp instanceof Tree.BINOP) {
		Tree.BINOP b = (Tree.BINOP)mem.exp;
		if (b.binop == Tree.BINOP.PLUS && immediate(b)) {
		    int right = ((Tree.CONST)b.right).value;
		    Temp left = (b.left instanceof Tree.TEMP) ?
			((Tree.TEMP)b.left).temp : b.left.accept(this);
		    String off = Integer.toString(right);
		    if (left == frame.FP) {
			left = frame.SP;
			off += "+" + frame.name + "_framesize";
		    }
		    emit(OPER("sw `s0, " + off + "(`s1)", null,
			      new Temp[]{s.src.accept(this), left}));
		    return;
		}
	    }

            // MOVE(MEM(CONST), Exp)
            Tree.CONST exp = CONST16(mem.exp);
	    if (exp != null) {
		emit(OPER("sw `s0, " + exp.value + "(`s1)", null,
			  new Temp[]{s.src.accept(this), frame.ZERO}));
		return;
	    }

	    // MOVE(MEM(TEMP), Exp)
	    if (mem.exp instanceof Tree.TEMP) {
		Temp temp = ((Tree.TEMP)mem.exp).temp;
		if (temp == frame.FP) {
		    emit(OPER("sw `s0, " + frame.name + "_framesize" + "(`s1)",
			      null,
			      new Temp[]{s.src.accept(this), frame.SP}));
		    return;
		}
	    }

            // MOVE(MEM(Exp), Exp)
            emit(OPER("sw `s0, (`s1)", null,
		      new Temp[]{s.src.accept(this), mem.exp.accept(this)}));
            return;
	}

        // From here on dst must be a TEMP
        Temp dst = ((Tree.TEMP)s.dst).temp;

	// MOVE(TEMP, MEM)
	if (s.src instanceof Tree.MEM) {
	    Tree.MEM mem = (Tree.MEM)s.src;

	    // MOVE(TEMP, MEM(+ Exp CONST))
	    if (mem.exp instanceof Tree.BINOP) {
		Tree.BINOP b = (Tree.BINOP)mem.exp;
		if (b.binop == Tree.BINOP.PLUS && immediate(b)) {
		    int right = ((Tree.CONST)b.right).value;
		    Temp left = (b.left instanceof Tree.TEMP) ?
			((Tree.TEMP)b.left).temp : b.left.accept(this);
		    String off = Integer.toString(right);
		    if (left == frame.FP) {
			left = frame.SP;
			off += "+" + frame.name + "_framesize";
		    }
		    emit(OPER("lw `d0, " + off + "(`s0)",
			      new Temp[]{dst}, new Temp[]{left}));
		    return;
		}
	    }

	    // MOVE(TEMP, MEM(CONST))
	    Tree.CONST exp = CONST16(mem.exp);
	    if (exp != null) {
		emit(OPER("lw `d0, " + exp.value + "(`s0)",
			  new Temp[]{dst}, new Temp[]{frame.ZERO}));
		return;
	    }

	    // MOVE(TEMP, MEM(TEMP))
	    if (mem.exp instanceof Tree.TEMP) {
		Temp temp = ((Tree.TEMP)mem.exp).temp;
		if (temp == frame.FP) {
		    emit(OPER("lw `d0, " + frame.name + "_framesize" + "(`s0)",
			      new Temp[]{dst}, new Temp[]{frame.SP}));
		    return;
		}
	    }

	    // MOVE(TEMP, MEM(Exp))
	    emit(OPER("lw `d0, (`s0)",
		      new Temp[]{dst}, new Temp[]{mem.exp.accept(this)}));
	    return;
	}

	// MOVE(TEMP, Exp)
        emit(MOVE("move `d0, `s0", dst, s.src.accept(this)));
    }

    public void visit(Tree.EXP s) { s.exp.accept(this); }

    public void visit(Tree.JUMP s) {
        if (s.exp instanceof Tree.NAME) {
	    Tree.NAME name = (Tree.NAME)s.exp;
	    // JUMP(Tree.NAME, List<Label>)
	    emit(OPER("j " + name.label.toString(), null, null, LabelList.listLabelToList(s.targets)));
	    return;
	}
	// JUMP(Exp, List<Label>)
	emit(OPER("jr `s0", null, new Temp[]{s.exp.accept(this)}, LabelList.listLabelToList(s.targets)));
	return;
    }

    private static boolean immediate(Tree.CJUMP s) {
        Tree.CONST left = CONST16(s.left);
	Tree.CONST right = CONST16(s.right);
	if (left == null)
	    return right != null;
	if (right == null) {
	    s.left = s.right;
	    s.right = left;
	    switch (s.relop) {
	    case Tree.CJUMP.EQ:
	    case Tree.CJUMP.NE:
		break;
	    case Tree.CJUMP.LT:
		s.relop = Tree.CJUMP.GT;
		break;
	    case Tree.CJUMP.GE:
		s.relop = Tree.CJUMP.LE;
		break;
	    case Tree.CJUMP.GT:
		s.relop = Tree.CJUMP.LT;
		break;
	    case Tree.CJUMP.LE:
		s.relop = Tree.CJUMP.GE;
		break;
	    case Tree.CJUMP.ULT:
		s.relop = Tree.CJUMP.UGT;
		break;
	    case Tree.CJUMP.UGE:
		s.relop = Tree.CJUMP.ULE;
		break;
	    case Tree.CJUMP.UGT:
		s.relop = Tree.CJUMP.ULT;
		break;
	    case Tree.CJUMP.ULE:
		s.relop = Tree.CJUMP.UGE;
		break;
	    default:
		throw new Error("bad relop in Codegen.immediate");
	    }
	}
	return true;
    }

    private static String[] CJUMP = new String[10];
    static {
	CJUMP[Tree.CJUMP.EQ ] = "beq";
	CJUMP[Tree.CJUMP.NE ] = "bne";
	CJUMP[Tree.CJUMP.LT ] = "blt";
	CJUMP[Tree.CJUMP.GT ] = "bgt";
	CJUMP[Tree.CJUMP.LE ] = "ble";
	CJUMP[Tree.CJUMP.GE ] = "bge";
	CJUMP[Tree.CJUMP.ULT] = "bltu";
	CJUMP[Tree.CJUMP.ULE] = "bleu";
	CJUMP[Tree.CJUMP.UGT] = "bgtu";
	CJUMP[Tree.CJUMP.UGE] = "bgeu";
    }

    public void visit(Tree.CJUMP s) {
        List<Label> targets = new LinkedList<Label>();
        targets.add(s.iftrue);
	targets.add(s.iffalse);
        if (immediate(s)) {
	    int right = ((Tree.CONST)s.right).value;
	    // CJUMP(op, Exp, CONST, Label, Label)
	    emit(OPER(CJUMP[s.relop] + " `s0, " + right + ", "
		      + s.iftrue.toString(),
		      null, new Temp[]{s.left.accept(this)}, targets));
	    return;
	}

        // CJUMP(op, Exp, Exp, Label, Label)
        emit(OPER(CJUMP[s.relop] + " `s0, `s1, " + s.iftrue.toString(),
		  null, new Temp[]{s.left.accept(this), s.right.accept(this)},
		  targets));
	return;
    }

    public void visit(Tree.LABEL l) {
        emit(new Assem.LABEL(l.label.toString() + ":", l.label));
	return;
    }

    public Temp visit(Tree.CONST e) {
	if (e.value == 0)
	    return frame.ZERO;
	Temp t = new Temp();
	emit(OPER("li `d0, " + e.value, new Temp[]{t}, null));
	return t;
    }

    public Temp visit(Tree.NAME e) {
	Temp t = new Temp();
	emit(OPER("la `d0, " + e.label.toString(), new Temp[]{t}, null));
	return t;
    }

    public Temp visit(Tree.TEMP e) {
	if (e.temp == frame.FP) {
	    Temp t = new Temp();
	    emit(OPER("addu `d0, `s0, " + frame.name + "_framesize",
		      new Temp[]{t}, new Temp[]{frame.SP}));
	    return t;
	}
	return e.temp;
    }

    private static String[] BINOP = new String[10];
    static {
	BINOP[Tree.BINOP.PLUS   ] = "add";
	BINOP[Tree.BINOP.MINUS  ] = "sub";
	BINOP[Tree.BINOP.MUL    ] = "mulo";
	BINOP[Tree.BINOP.DIV    ] = "div";
	BINOP[Tree.BINOP.AND    ] = "and";
	BINOP[Tree.BINOP.OR     ] = "or";
	BINOP[Tree.BINOP.LSHIFT ] = "sll";
	BINOP[Tree.BINOP.RSHIFT ] = "srl";
	BINOP[Tree.BINOP.ARSHIFT] = "sra";
	BINOP[Tree.BINOP.XOR    ] = "xor";
    }

    private static int shift(int i) {
	int shift = 0;
	if ((i >= 2) && ((i & (i - 1)) == 0)) {
	    while (i > 1) {
		shift += 1;
		i >>= 1;
	    }
	}
	return shift;
    }

    public Temp visit(Tree.BINOP e) {
        Temp t = new Temp();
        if (immediate(e)) {
	    int right = ((Tree.CONST)e.right).value;
	    switch (e.binop) {
	    case Tree.BINOP.PLUS:
		{
		    Temp left = (e.left instanceof Tree.TEMP) ?
			((Tree.TEMP)e.left).temp : e.left.accept(this);
		    String off = Integer.toString(right);
		    if (left == frame.FP) {
			left = frame.SP;
			off += "+" + frame.name + "_framesize";
		    }
		    emit(OPER("add `d0, `s0, " + off, new Temp[]{t},
			      new Temp[]{left}));
		    return t;
		}
	    case Tree.BINOP.MUL:
		{
		    int shift = shift(right);
		    if (shift != 0) {
			emit(OPER("sll `d0, `s0, " + shift, new Temp[]{t},
				  new Temp[]{e.left.accept(this)}));
			return t;
		    }
		    emit(OPER(BINOP[e.binop] + " `d0, `s0, " + right,
			      new Temp[]{t}, new Temp[]{e.left.accept(this)}));
		    return t;
		}
	    case Tree.BINOP.DIV:
		{
		    int shift = shift(right);
		    if (shift != 0) {
			emit(OPER("sra `d0, `s0, " + shift, new Temp[]{t},
				  new Temp[]{e.left.accept(this)}));
			return t;
		    }
		    emit(OPER(BINOP[e.binop] + " `d0, `s0, " + right,
			      new Temp[]{t}, new Temp[]{e.left.accept(this)}));
		    return t;
		}
	    default:
		emit(OPER(BINOP[e.binop] + " `d0, `s0, " + right,
			  new Temp[]{t}, new Temp[]{e.left.accept(this)}));
		return t;
	    }
	}
	emit(OPER(BINOP[e.binop] + " `d0, `s0, `s1", new Temp[]{t},
		  new Temp[]{e.left.accept(this), e.right.accept(this)}));
	return t;
    }

    public Temp visit(Tree.MEM e) {
        Temp t = new Temp();

	// MEM(+ Exp CONST)
	if (e.exp instanceof Tree.BINOP) {
	    Tree.BINOP b = (Tree.BINOP)e.exp;
	    if (b.binop == Tree.BINOP.PLUS && immediate(b)) {
		int right = ((Tree.CONST)b.right).value;
		Temp left = (b.left instanceof Tree.TEMP) ?
		    ((Tree.TEMP)b.left).temp : b.left.accept(this);
		String off = Integer.toString(right);
		if (left == frame.FP) {
		    left = frame.SP;
		    off += "+" + frame.name + "_framesize";
		}
		emit(OPER("lw `d0, " + off + "(`s0)", new Temp[]{t},
			  new Temp[]{left}));
		return t;
	    }
	}

	// MEM(CONST)
	Tree.CONST exp = CONST16(e.exp);
	if (exp != null) {
	    emit(OPER("lw `d0, " + exp.value + "(`s0)", new Temp[]{t},
		      new Temp[]{frame.ZERO}));
	    return t;
	}

	// MEM(TEMP)
	if (e.exp instanceof Tree.TEMP) {
	    Temp temp = ((Tree.TEMP)e.exp).temp;
	    if (temp == frame.FP) {
		emit(OPER("lw `d0, " + frame.name + "_framesize" + "(`s0)",
			  new Temp[]{t}, new Temp[]{frame.SP}));
		return t;
	    }
	}

	// MEM(Exp)
	emit(OPER("lw `d0, (`s0)", new Temp[]{t},
		  new Temp[]{e.exp.accept(this)}));
	return t;
    }

    public Temp visit(Tree.CALL s) {
	Iterator<Tree.Exp> args = ExpList.toArrayList(s.args).iterator();

	LinkedList<Temp> uses = new LinkedList<Temp>();
	
	Temp V0 = args.next().accept(this);
	if (V0 != frame.ZERO) {
	    emit(MOVE("move `d0, `s0", frame.V0, V0));
	    uses.add(frame.V0);
	}

	int offset = 0;

	if (args.hasNext()) {
	    offset += frame.wordSize();
	    emit(MOVE("move `d0, `s0", frame.A0, args.next().accept(this)));
	    uses.add(frame.A0);
	}
	if (args.hasNext()) {
	    offset += frame.wordSize();
	    emit(MOVE("move `d0, `s0", frame.A1, args.next().accept(this)));
	    uses.add(frame.A1);
	}
	if (args.hasNext()) {
	    offset += frame.wordSize();
	    emit(MOVE("move `d0, `s0", frame.A2, args.next().accept(this)));
	    uses.add(frame.A2);
	}
	if (args.hasNext()) {
	    offset += frame.wordSize();
	    emit(MOVE("move `d0, `s0", frame.A3, args.next().accept(this)));
	    uses.add(frame.A3);
	}
	while (args.hasNext()) {
	    offset += frame.wordSize();
	    emit(OPER("sw `s0 " + offset + "(`s1)", null,
		      new Temp[]{args.next().accept(this), frame.SP}));
	}

	if (offset > frame.maxArgOffset)
	    frame.maxArgOffset = offset;

        if (s.func instanceof Tree.NAME) {
	    emit(OPER("jal " + ((Tree.NAME)s.func).label.toString(),
		      frame.calldefs, uses.toArray(new Temp[]{})));
	    return frame.V0;
	}
        uses.addFirst(s.func.accept(this));
        emit(OPER("jal `s0", frame.calldefs, uses.toArray(new Temp[]{})));
	return frame.V0;
    }

    // Canonical trees shouldn't have these so throw an error
    public void visit(Tree.SEQ n) { throw new Error(); }
    public Temp visit(Tree.ESEQ n) { throw new Error(); }
}
