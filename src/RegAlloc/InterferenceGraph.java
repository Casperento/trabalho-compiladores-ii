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
import Temp.Temp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

abstract public class InterferenceGraph extends Graph {
    protected Integer[][] interferenceMatrix;
    protected LinkedList<MovePair> coalescedMoves;
    protected LinkedList<MovePair> constrainedMoves;
    protected LinkedList<MovePair> frozenMoves;
    protected LinkedList<MovePair> worklistMoves;
    protected LinkedList<MovePair> activeMoves;
    protected HashMap<Integer, Integer> alias;
    protected LinkedList<EdgePair> adjSet;
    protected LinkedList<NodeList> adjList;
    protected LinkedList<Node> simplifyWorklist;
    protected LinkedList<Node> freezeWorklist;
    protected LinkedList<Node> spillWorklist;
    protected LinkedList<Node> preColored;
    protected LinkedList<Node> spilledNodes;
    protected LinkedList<Node> coalescedNodes;
    protected LinkedList<Node> coloredNodes;
    protected Stack<Node> selectStack;
    protected HashMap<Integer,MoveList> moveList;
    protected Integer[] degree;
    protected Integer[] color;
    protected Map<Node, Temp> tableNodeTemp;
    protected Map<Temp, Node> tableTempNode;
    protected HashMap<Integer, Temp> colorMap;

    InterferenceGraph() {
        this.coalescedMoves = new LinkedList<MovePair>(); // MovePair has src and dst
        this.constrainedMoves = new LinkedList<MovePair>();
        this.frozenMoves = new LinkedList<MovePair>();
        this.worklistMoves = new LinkedList<MovePair>();
        this.activeMoves = new LinkedList<MovePair>();
        this.alias = new HashMap<Integer, Integer>();
        this.adjSet = new LinkedList<EdgePair>(); // EdgePair has src and dst
        this.preColored = new LinkedList<Node>();
        this.simplifyWorklist = new LinkedList<Node>();
        this.selectStack = new Stack<Node>();
        this.freezeWorklist = new LinkedList<Node>();
        this.spillWorklist = new LinkedList<Node>();
        this.spilledNodes = new LinkedList<Node>();
        this.coalescedNodes = new LinkedList<Node>();
        this.coloredNodes = new LinkedList<Node>();
        this.moveList = new HashMap<Integer, MoveList>();
        this.adjList = new LinkedList<NodeList>();
        this.tableNodeTemp = new HashMap<Node, Temp>();
        this.tableTempNode = new HashMap<Temp, Node>();
        this.colorMap = new HashMap<Integer, Temp>();
    }

    public void initInterferenceMatrix() {  // init bit matrix after all nodes been created
        this.interferenceMatrix = new Integer[this.nodecount][this.nodecount];
    }

    public boolean isAdjacent(int x, int y) {
        return this.interferenceMatrix[x][y].equals(1) && this.interferenceMatrix[y][x].equals(1);
    }

    public void setEdgeBits(int x, int y) {
        this.interferenceMatrix[x][y] = 1;
        this.interferenceMatrix[y][x] = 1;
    }

    public abstract Node tnode(Temp temp);

    public abstract Temp gtemp(Node node);

    public abstract HashMap<Integer,MoveList> moves();

    public int spillCost(Node node) {
        return 1;
    }

    public NodeList GetAllAdjacentNodesFrom(int n) {
        return adjList.get(n).tail;
    }

    public void initAdjList(InterferenceGraph ig) {
        for (NodeList p = ig.nodes(); p != null; p = p.tail)
            adjList.add(new NodeList(null, null));
    }
}
