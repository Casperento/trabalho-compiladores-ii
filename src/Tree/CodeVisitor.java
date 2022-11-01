// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Tree;
import Temp.Temp;

public interface CodeVisitor {
    public void visit(SEQ n);
    public void visit(LABEL n);
    public void visit(JUMP n);
    public void visit(CJUMP n);
    public void visit(MOVE n);
    public void visit(EXP n);

    public Temp visit(BINOP n);
    public Temp visit(MEM n);
    public Temp visit(TEMP n);
    public Temp visit(ESEQ n);
    public Temp visit(NAME n);
    public Temp visit(CONST n);
    public Temp visit(CALL n);
}
