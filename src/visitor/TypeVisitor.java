// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package visitor;

import syntaxtree.*;

public interface TypeVisitor {
  public Type visit(Program n);
  public Type visit(MainClass n);
  public Type visit(ClassDeclSimple n);
  public Type visit(ClassDeclExtends n);
  public Type visit(VarDecl n);
  public Type visit(MethodDecl n);
  public Type visit(Formal n);
  public Type visit(IntArrayType n);
  public Type visit(BooleanType n);
  public Type visit(IntegerType n);
  public Type visit(IdentifierType n);
  public Type visit(Block n);
  public Type visit(If n);
  public Type visit(While n);
  public Type visit(Print n);
  public Type visit(Assign n);
  public Type visit(ArrayAssign n);
  public Type visit(And n);
  public Type visit(LessThan n);
  public Type visit(Plus n);
  public Type visit(Minus n);
  public Type visit(Times n);
  public Type visit(ArrayLookup n);
  public Type visit(ArrayLength n);
  public Type visit(Call n);
  public Type visit(IntegerLiteral n);
  public Type visit(True n);
  public Type visit(False n);
  public Type visit(IdentifierExp n);
  public Type visit(This n);
  public Type visit(NewArray n);
  public Type visit(NewObject n);
  public Type visit(Not n);
  public Type visit(Identifier n);
}
