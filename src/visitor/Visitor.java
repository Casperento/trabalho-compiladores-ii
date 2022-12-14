// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
//
// Generated by JTB GJ1.1.2
//

package visitor;

import syntaxtree.*;

/**
 * All void visitors must implement this interface.
 */

public interface Visitor {

    //
    // void Auto class visitors
    //

    public void visit(NodeList n);
    public void visit(NodeListOptional n);
    public void visit(NodeOptional n);
    public void visit(NodeSequence n);
    public void visit(NodeToken n);

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    public void visit(Goal n);

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> PrintStatement()
     * f15 -> "}"
     * f16 -> "}"
     */
    public void visit(MainClass n);

    /**
     * f0 -> ClassDeclaration()
     *       | ClassExtendsDeclaration()
     */
    public void visit(TypeDeclaration n);

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    public void visit(ClassDeclaration n);

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    public void visit(ClassExtendsDeclaration n);

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public void visit(VarDeclaration n);

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    public void visit(MethodDeclaration n);

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    public void visit(FormalParameterList n);

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    public void visit(FormalParameter n);

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    public void visit(FormalParameterRest n);

    /**
     * f0 -> ArrayType()
     *       | BooleanType()
     *       | IntegerType()
     *       | Identifier()
     */
    public void visit(Type n);

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    public void visit(ArrayType n);

    /**
     * f0 -> "boolean"
     */
    public void visit(BooleanType n);

    /**
     * f0 -> "int"
     */
    public void visit(IntegerType n);

    /**
     * f0 -> Block()
     *       | AssignmentStatement()
     *       | ArrayAssignmentStatement()
     *       | IfStatement()
     *       | WhileStatement()
     *       | PrintStatement()
     */
    public void visit(Statement n);

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    public void visit(Block n);

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    public void visit(AssignmentStatement n);

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    public void visit(ArrayAssignmentStatement n);

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    public void visit(IfStatement n);

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    public void visit(WhileStatement n);

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    public void visit(PrintStatement n);

    /**
     * f0 -> AndExpression()
     *       | CompareExpression()
     *       | PlusExpression()
     *       | MinusExpression()
     *       | TimesExpression()
     *       | ArrayLookup()
     *       | ArrayLength()
     *       | MessageSend()
     *       | PrimaryExpression()
     */
    public void visit(Expression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    public void visit(AndExpression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    public void visit(CompareExpression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    public void visit(PlusExpression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    public void visit(MinusExpression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    public void visit(TimesExpression n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    public void visit(ArrayLookup n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    public void visit(ArrayLength n);

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    public void visit(MessageSend n);

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    public void visit(ExpressionList n);

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    public void visit(ExpressionRest n);

    /**
     * f0 -> IntegerLiteral()
     *       | TrueLiteral()
     *       | FalseLiteral()
     *       | Identifier()
     *       | ThisExpression()
     *       | ArrayAllocationExpression()
     *       | AllocationExpression()
     *       | NotExpression()
     *       | BracketExpression()
     */
    public void visit(PrimaryExpression n);

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public void visit(IntegerLiteral n);

    /**
     * f0 -> "true"
     */
    public void visit(TrueLiteral n);

    /**
     * f0 -> "false"
     */
    public void visit(FalseLiteral n);

    /**
     * f0 -> <IDENTIFIER>
     */
    public void visit(Identifier n);

    /**
     * f0 -> "this"
     */
    public void visit(ThisExpression n);

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    public void visit(ArrayAllocationExpression n);

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    public void visit(AllocationExpression n);

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    public void visit(NotExpression n);

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    public void visit(BracketExpression n);

    void visit(And and);

    void visit(Call call);

    void visit(ClassDeclExtends classDeclExtends);

    void visit(ClassDeclSimple classDeclSimple);

    void visit(False aFalse);

    void visit(Formal formal);

    void visit(IdentifierExp identifierExp);

    void visit(LessThan lessThan);

    void visit(MethodDecl methodDecl);

    void visit(Minus minus);

    void visit(NewArray newArray);

    void visit(NewObject newObject);

    void visit(Not not);

    void visit(Plus plus);

    void visit(Program program);

    void visit(This aThis);

    void visit(Times times);

    void visit(True aTrue);

    void visit(VarDecl varDecl);
}

