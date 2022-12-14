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
 * f0 -> AndExpression()
 *       | CompareExpression()
 *       | PlusExpression()
 *       | MinusExpression()
 *       | TimesExpression()
 *       | ArrayLookup()
 *       | ArrayLength()
 *       | MessageSend()
 *       | PrimaryExpression()
 */
public class Expression implements Node {
    public NodeChoice f0;

    public Expression(NodeChoice n0) {
        f0 = n0;
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

