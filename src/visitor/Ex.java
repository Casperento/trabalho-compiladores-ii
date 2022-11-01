// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
//package Translate;
package visitor;
import Temp.Label;

public class Ex extends Exp {
    Tree.Exp exp;
    Ex(Tree.Exp e) { exp = e; }
    Tree.Exp unEx() { return exp; }
    Tree.Stm unNx() { return new Tree.EXP(exp); }
    Tree.Stm unCx(Label t, Label f) {
	// if the exp is a constant, emit JUMP statement.
	if (exp instanceof Tree.CONST) {
	    Tree.CONST c = (Tree.CONST)exp;
	    if (c.value == 0)
		return new Tree.JUMP(f);
	    else
		return new Tree.JUMP(t);
	}
	return new Tree.CJUMP(Tree.CJUMP.NE, exp, new Tree.CONST(0), t, f);
    }
}
