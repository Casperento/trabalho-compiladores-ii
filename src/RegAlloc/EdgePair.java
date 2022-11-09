package RegAlloc;

import Graph.Node;

public class EdgePair {
    private Node src;
    private Node dst;
    EdgePair(Node s, Node d) {
        this.src = s;
        this.dst = d;
    }

    public Node getSource() {
        return src;
    }

    public Node getDest() {
        return dst;
    }
}
