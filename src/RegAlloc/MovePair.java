package RegAlloc;

import Graph.Node;

public class MovePair {
    private Node src;
    private Node dst;
    MovePair(Node s, Node d) {
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
