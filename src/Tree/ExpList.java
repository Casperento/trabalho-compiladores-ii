// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public  class ExpList extends Exp  {
    public Exp head;
    public ExpList tail;
    private LinkedList<Exp> list;
    public ExpList(Exp h, ExpList t) {
        head = h;
        tail = t;
    }
    public LinkedList<Exp> getList() {
        return list;
    }
    public ExpList kids() {
        return null;
    }
    public Exp build(ExpList kids) {
        return null;
    }
    public void accept(IntVisitor v, int d) {}
    public <R> R accept(ResultVisitor<R> v) {
        return null;
    }
    public Temp.Temp accept(CodeVisitor v) {
        return null;
    }

    public static ExpList fromArrayList(List<Exp> exprs) {
        if (exprs.isEmpty()) {
            return null;
        }
        Exp first = exprs.remove(0);
        return new ExpList(first, fromArrayList(exprs));
    }

    public static List<Exp> toArrayList(ExpList labelList) {
        return toArrayList(new ArrayList<Exp>(), labelList);
    }

    private static List<Exp> toArrayList(List<Exp> list, ExpList labelList) {
        if (labelList == null || labelList.head == null) {
            return list;
        }
        list.add(labelList.head);
        return toArrayList(list, labelList.tail);
    }

}

