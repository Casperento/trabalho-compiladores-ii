//// Generated by JTB GJ1.1.2//package syntaxtree;/** * The interface which NodeList, NodeListOptional, and NodeSequence * implement. */public interface NodeListInterface extends Node {   public void addNode(Node n);   public Node elementAt(int i);   public gj.util.Enumeration<Node> elements();   public int size();   public void accept(visitor.Visitor v);   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu);   public <R> R accept(visitor.GJNoArguVisitor<R> v);   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu);}