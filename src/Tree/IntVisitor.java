// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Tree;
import Temp.Temp;

public interface IntVisitor {
    public void visit(SEQ n, int d);
    public void visit(LABEL n, int d);
    public void visit(JUMP n, int d);
    public void visit(CJUMP n, int d);
    public void visit(MOVE n, int d);
    public void visit(EXP n, int d);
    public void visit(BINOP n, int d);
    public void visit(MEM n, int d);
    public void visit(TEMP n, int d);
    public void visit(ESEQ n, int d);
    public void visit(NAME n, int d);
    public void visit(CONST n, int d);
    public void visit(CALL n, int d);
}
