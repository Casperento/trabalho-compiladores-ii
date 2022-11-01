// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Graph;

public class Node {

    Graph mygraph;
    private Node() {}
    int mykey;
    public Node(Graph g) {
        mygraph=g;
        mykey= g.nodecount++;
        NodeList p = new NodeList(this, null);
        if (g.mylast==null)
            g.mynodes=g.mylast=p;
        else g.mylast = g.mylast.tail = p;
    }

    NodeList succs;
    NodeList preds;
    public NodeList succ() {
        return succs;
    }
    public NodeList pred() {
        return preds;
    }
    NodeList cat(NodeList a, NodeList b) {
        if (a==null) return b;
        else return new NodeList(a.head, cat(a.tail,b));
    }
    public NodeList adj() {
        return cat(succ(), pred());
    }

    int len(NodeList l) {
        int i=0;
        for(NodeList p=l; p!=null; p=p.tail) i++;
        return i;
    }

    public int inDegree() {
        return len(pred());
    }
    public int outDegree() {
        return len(succ());
    }
    public int degree() {
        return inDegree()+outDegree();
    }

    public boolean goesTo(Node n) {
        return Graph.inList(n, succ());
    }

    public boolean comesFrom(Node n) {
        return Graph.inList(n, pred());
    }

    public boolean adj(Node n) {
        return goesTo(n) || comesFrom(n);
    }

    public String toString() {
        return String.valueOf(mykey);
    }

}
