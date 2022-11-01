// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Tree;
import Temp.Temp;

public interface ResultVisitor<R> {
    public R visit(SEQ n);
    public R visit(LABEL n);
    public R visit(JUMP n);
    public R visit(CJUMP n);
    public R visit(MOVE n);
    public R visit(EXP n);
    public R visit(BINOP n);
    public R visit(MEM n);
    public R visit(TEMP n);
    public R visit(ESEQ n);
    public R visit(NAME n);
    public R visit(CONST n);
    public R visit(CALL n);
}
