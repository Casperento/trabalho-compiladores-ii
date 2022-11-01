//
// Generated by JTB GJ1.1.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> Identifier()
 * f1 -> "="
 * f2 -> Expression()
 * f3 -> ";"
 */
public class AssignmentStatement implements Node {
   public Identifier f0;
   public NodeToken f1;
   public Expression f2;
   public NodeToken f3;

   public AssignmentStatement(Identifier n0, NodeToken n1, Expression n2, NodeToken n3) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
      f3 = n3;
   }

   public AssignmentStatement(Identifier n0, Expression n1) {
      f0 = n0;
      f1 = new NodeToken("=");
      f2 = n1;
      f3 = new NodeToken(";");
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}

