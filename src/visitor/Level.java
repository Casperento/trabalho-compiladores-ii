// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;
import Symbol.Symbol;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class Level {
    public Level parent;
    Frame.Frame frame;		// not public!
    public List<Access> formals = new LinkedList<Access>();
    public Level(Level p, Symbol name, List<Boolean> escapes) {
        parent = p;
        frame = parent.frame.newFrame(name, escapes);
        for (Iterator<Frame.Access> f = frame.formals.iterator(); f.hasNext();)
            formals.add(new Access(this, f.next()));
    }

    Level(Frame.Frame f) {
        frame = f;
    }

    public Temp.Label name() {
        return frame.name;
    }

    public Access allocLocal(boolean escape) {
        return new Access(this, frame.allocLocal(escape));
    }
}


