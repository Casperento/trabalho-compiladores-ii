// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
//
// Generated by JTB GJ1.1.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> "while"
 * f1 -> "("
 * f2 -> Expression()
 * f3 -> ")"
 * f4 -> Statement()
 */
public class WhileStatement implements Node {
    public NodeToken f0;
    public NodeToken f1;
    public Expression f2;
    public NodeToken f3;
    public Statement f4;

    public WhileStatement(NodeToken n0, NodeToken n1, Expression n2, NodeToken n3, Statement n4) {
        f0 = n0;
        f1 = n1;
        f2 = n2;
        f3 = n3;
        f4 = n4;
    }

    public WhileStatement(Expression n0, Statement n1) {
        f0 = new NodeToken("while");
        f1 = new NodeToken("(");
        f2 = n0;
        f3 = new NodeToken(")");
        f4 = n1;
    }

    public void accept(visitor.Visitor v) {
        v.visit(this);
    }
    public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
        return v.visit(this,argu);
    }
    public <R> R accept(visitor.GJNoArguVisitor<R> v) {
        return v.visit(this);
    }
    public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
        v.visit(this,argu);
    }
}

