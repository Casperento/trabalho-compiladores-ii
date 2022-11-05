// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package RegAlloc;

import Graph.Graph;
import Graph.Node;
import Graph.NodeList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InterferenceGraph extends Graph {
    private Integer[][] interferenceMatrix;
    private LinkedList<MovePair> coalescedMoves;
    private LinkedList<MovePair> constrainedMoves;
    private LinkedList<MovePair> frozenMoves;
    private LinkedList<MovePair> worklistMoves;
    private LinkedList<MovePair> activeMoves;
    private HashMap<Integer, Integer> alias;
    private LinkedList<EdgePair> adjSet;
    private LinkedList<NodeList> adjList;
    private LinkedList<Node> simplifyWorklist;
    private LinkedList<Node> freezeWorklist;
    private LinkedList<Node> spillWorklist;
    private LinkedList<Node> preColored;
    private LinkedList<Node> initial;
    private LinkedList<Node> spilledNodes;
    private LinkedList<Node> coalescedNodes;
    private LinkedList<Node> coloredNodes;
    private Stack<Node> selectStack;

    InterferenceGraph() {
        this.coalescedMoves = new LinkedList<MovePair>(); // MovePair has src and dst
        this.constrainedMoves = new LinkedList<MovePair>();
        this.frozenMoves = new LinkedList<MovePair>();
        this.worklistMoves = new LinkedList<MovePair>();
        this.activeMoves = new LinkedList<MovePair>();
        this.alias = new HashMap<Integer, Integer>();
        this.adjSet = new LinkedList<EdgePair>(); // EdgePair has src and dst
        this.preColored = new LinkedList<Node>();
        this.initial = new LinkedList<Node>();
        this.simplifyWorklist = new LinkedList<Node>();
        this.selectStack = new Stack<Node>();
        this.freezeWorklist = new LinkedList<Node>();
        this.spillWorklist = new LinkedList<Node>();
        this.spilledNodes = new LinkedList<Node>();
        this.coalescedNodes = new LinkedList<Node>();
        this.coloredNodes = new LinkedList<Node>();
    }

    public void initInterferenceMatrix() {  // init bit matrix after all nodes been created
        this.interferenceMatrix = new Integer[this.nodecount][this.nodecount];
    }

    private boolean isAdjacent(int x, int y) {
        if (this.interferenceMatrix[x][y].equals(1) && this.interferenceMatrix[y][x].equals(1)) {
            return true;
        }
        return false;
    }

    public Node tnode(Temp.Temp temp) {
        return null;
    }

    public Temp.Temp gtemp(Node node) {
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
