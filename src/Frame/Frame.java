// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Frame;
import java.util.List;

public abstract class Frame implements Temp.TempMap {
    public Temp.Label name;
    public List<Access> formals;
    public abstract Frame newFrame(Symbol.Symbol name, List<Boolean> formals);
    public abstract Access allocLocal(boolean escape);
    public abstract Temp.Temp FP();
    public abstract int wordSize();
    public abstract Tree.Exp externalCall(String func, List<Tree.Exp> args);
    public abstract Temp.Temp RV();
    public abstract String string(Temp.Label label, String value);
    public abstract Temp.Label badPtr();
    public abstract Temp.Label badSub();
    public abstract String tempMap(Temp.Temp temp);
    public abstract List<Assem.Instr> codegen(List<Tree.Stm> stms);
    public abstract void procEntryExit1(List<Tree.Stm> body);
    public abstract void procEntryExit2(List<Assem.Instr> body);
    public abstract void procEntryExit3(List<Assem.Instr> body);
    public abstract Temp.Temp[] registers();
    public abstract void spill(List<Assem.Instr> insns, Temp.Temp[] spills);
    public abstract String programTail(); //append to end of target code
}
