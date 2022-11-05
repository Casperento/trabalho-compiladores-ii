// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package FlowGraph;
import Temp.TempList;
import Temp.Temp;
import Graph.NodeList;
import Graph.Node;

/**
 * A control flow graph is a directed graph in which each edge
 * indicates a possible flow of control.  Also, each node in
 * the graph defines a set of temporaries; each node uses a set of
 * temporaries; and each node is, or is not, a <strong>move</strong>
 * instruction.
 *
 * @see AssemFlowGraph
 */

public class FlowGraph extends Graph.Graph {
    /**
     * The set of temporaries defined by this instruction or block
     */
    public TempList def(Node node) {
        return null;
    }

    /**
     * The set of temporaries used by this instruction or block
     */
    public TempList use(Node node) {
        return null;
    }

    /**
     * True if this node represents a <strong>move</strong> instruction,
     * i.e. one that can be deleted if def=use.
     */
    public boolean isMove(Node node) {
        return false;
    }

    /**
     * Print a human-readable dump for debugging.
     */
    public void show(java.io.PrintStream out) {
        for (NodeList p=nodes(); p!=null; p=p.tail) {
            Node n = p.head;
            out.print(n.toString());
            out.print(": ");
            for(TempList q=def(n); q!=null; q=q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.print(isMove(n) ? "<= " : "<- ");
            for(TempList q=use(n); q!=null; q=q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.print("; goto ");
            for(NodeList q=n.succ(); q!=null; q=q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println();
        }
    }

}

