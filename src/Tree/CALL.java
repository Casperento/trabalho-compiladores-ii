// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Tree;

import Temp.Temp;
public class CALL extends Exp {
    public Exp func;
    public ExpList args;
    public CALL(Exp f, ExpList a) {
        func=f;
        args=a;
    }
    public ExpList kids() {
        return new ExpList(func, args);
    }
    public Exp build(ExpList kids) {
        return new CALL(kids.head, kids.tail);
    }
    public void accept(IntVisitor v, int d) {
        v.visit(this, d);
    }
    public Temp accept(CodeVisitor v) {
        return v.visit(this);
    }
    public <R> R accept(ResultVisitor<R> v) {
        return v.visit(this);
    }
}
