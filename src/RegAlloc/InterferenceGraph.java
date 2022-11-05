// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package RegAlloc;
import Graph.Node;
import Graph.Graph;
import Graph.NodeList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InterferenceGraph extends Graph {

    private LinkedList<NodeList> adjList;
    private LinkedList<Node> simplifyWorklist;
    private LinkedList<Node> worklistMoves;
    private LinkedList<Node> freezeWorklist;
    private LinkedList<Node> spillWorklist;
    private LinkedList<Node> preColored;
    private LinkedList<Node> initial;
    private LinkedList<Node> spilledNodes;
    private LinkedList<Node> coalescedNodes;
    private LinkedList<Node> coloredNodes;
    private Stack<Node> selectStack;

    public InterferenceGraph() {
        preColored = new LinkedList<Node>();
        initial = new LinkedList<Node>();
        simplifyWorklist = new LinkedList<Node>();
        selectStack = new Stack<Node>();
        worklistMoves = new LinkedList<Node>();
        freezeWorklist = new LinkedList<Node>();
        spillWorklist = new LinkedList<Node>();
        spilledNodes = new LinkedList<Node>();
        coalescedNodes = new LinkedList<Node>();
        coloredNodes = new LinkedList<Node>();
    }

    public Node tnode(Temp.Temp temp){
        return null;
    }
    
    public Temp.Temp gtemp(Node node){
        return null;
    }

    public MoveList moves() {
        return null;
    }

    public int spillCost(Node node) {
        return 1;
    }


    private NodeList GetAllAdjacentNodesFrom(int n) {
        return adjList.get(n).tail;
    }

    private void initAdjList() {
        for(NodeList p=nodes(); p!=null; p=p.tail) {
            Node n = p.head;
            NodeList list = new NodeList(n, n.adj());
            adjList.add(list);
        }
    }
    
}
