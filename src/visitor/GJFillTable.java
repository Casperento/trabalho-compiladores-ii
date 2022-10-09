//// Generated by JTB GJ1.1//package visitor;import syntaxtree.*;import gj.util.*;import types.* ;/** * Provides default methods which visit each node in the tree in depth-first * order.  Your visitors may extend this class. */public class GJFillTable extends GJDepthFirst<MJType,MJType> {   boolean local = false ;   //   // Auto class visitors--probably don't need to be overridden.   //   public MJType visit(NodeList n, MJType argu) {      MJType _ret=null;      int _count=0;      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {         e.nextElement().accept(this,argu);         _count++;      }      return _ret;   }   public MJType visit(NodeListOptional n, MJType argu) {      if ( n.present() ) {         MJType _ret=null;         int _count=0;         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {            e.nextElement().accept(this,argu);            _count++;         }         return _ret;      }      else         return null;   }   public MJType visit(NodeOptional n, MJType argu) {      if ( n.present() )         return n.node.accept(this,argu);      else         return null;   }   public MJType visit(NodeSequence n, MJType argu) {      MJType _ret=null;      int _count=0;      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {         e.nextElement().accept(this,argu);         _count++;      }      return _ret;   }   public MJType visit(NodeToken n, MJType argu) { return null; }   //   // User-generated visitor methods below   //   /**    * f0 -> MainClass()    * f1 -> ( TypeDeclaration() )*    * f2 -> <EOF>    */   public MJType visit(Goal n, MJType argu) {      MJType _ret=null;      String error_msg01 ;      String error_msg02 ;      String error_msg03 ;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      error_msg01 = ((MJClasses)argu).SetExtensions();      if (error_msg01 != null) System.out.println("Error: " + error_msg01);      error_msg02 = ((MJClasses)argu).SetFields();      if (error_msg02 != null) System.out.println("Error: " + error_msg02);      error_msg03 = ((MJClasses)argu).SetMetArgs();      if (error_msg03 != null) System.out.println("Error: " + error_msg03);      ((MJClasses)argu).SetNames();      //((MJClasses)argu).PrintNames();      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> "class"    * f1 -> Identifier()    * f2 -> "{"    * f3 -> "public"    * f4 -> "static"    * f5 -> "void"    * f6 -> "main"    * f7 -> "("    * f8 -> "String"    * f9 -> "["    * f10 -> "]"    * f11 -> Identifier()    * f12 -> ")"    * f13 -> "{"    * f14 -> PrintStatement()    * f15 -> "}"    * f16 -> "}"    */   public MJType visit(MainClass n, MJType argu) {      MJType _ret=null;      MJMethod n_method ;      MJObj m_obj ;      String class_name ;      String error_msg ;      n.f0.accept(this, argu);      class_name = ((Name)n.f1.accept(this, argu)).GetName();      m_obj = new MJObj(class_name,(MJClasses)argu);      error_msg = ((MJClasses)argu).InsertClass(m_obj);      if (error_msg != null) System.out.println("Error: " + error_msg);      n_method = new MJMethod("main",null,m_obj);      m_obj.InsertMethod(n_method);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      n.f5.accept(this, argu);      n.f6.accept(this, argu);      n.f7.accept(this, argu);      n.f8.accept(this, argu);      n.f9.accept(this, argu);      n.f10.accept(this, argu);      n.f11.accept(this, argu);      n.f12.accept(this, argu);      n.f13.accept(this, argu);      n.f14.accept(this, n_method);      n.f15.accept(this, argu);      n.f16.accept(this, argu);      return _ret;   }   /**    * f0 -> ClassDeclaration()    *       | ClassExtendsDeclaration()    */   public MJType visit(TypeDeclaration n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> "class"    * f1 -> Identifier()    * f2 -> "{"    * f3 -> ( VarDeclaration() )*    * f4 -> ( MethodDeclaration() )*    * f5 -> "}"    */   public MJType visit(ClassDeclaration n, MJType argu) {      MJType _ret=null;      MJObj m_obj ;      String class_name ;      String error_msg ;      n.f0.accept(this, argu);      class_name = ((Name)n.f1.accept(this, argu)).GetName();      m_obj = new MJObj(class_name,(MJClasses)argu);      n.f2.accept(this, argu);      local = false ;      n.f3.accept(this, m_obj);      n.f4.accept(this, m_obj);      n.f5.accept(this, argu);      error_msg = ((MJClasses)argu).InsertClass(m_obj);      if (error_msg != null) System.out.println("Error: " + error_msg);      return _ret;   }   /**    * f0 -> "class"    * f1 -> Identifier()    * f2 -> "extends"    * f3 -> Identifier()    * f4 -> "{"    * f5 -> ( VarDeclaration() )*    * f6 -> ( MethodDeclaration() )*    * f7 -> "}"    */   public MJType visit(ClassExtendsDeclaration n, MJType argu) {      MJType _ret=null;      MJObj m_obj ;      String class_name ;      String extend_name ;      String error_msg ;      n.f0.accept(this, argu);      class_name = ((Name)n.f1.accept(this, argu)).GetName();      n.f2.accept(this, argu);      extend_name = ((Name)n.f3.accept(this, argu)).GetName();      m_obj = new MJObj(class_name,extend_name,(MJClasses)argu);      n.f4.accept(this, argu);      n.f5.accept(this, m_obj);      n.f6.accept(this, m_obj);      n.f7.accept(this, argu);      error_msg = ((MJClasses)argu).InsertClass(m_obj);      if (error_msg != null) System.out.println("Error: " + error_msg);      return _ret;   }   /**    * f0 -> Type()    * f1 -> Identifier()    * f2 -> ";"    */   public MJType visit(VarDeclaration n, MJType argu) {      MJType _ret=null;      MJType field_type ;      String f_name  ;      String error_msg = " " ;      field_type = n.f0.accept(this, argu);      f_name = ((Name)n.f1.accept(this, argu)).GetName();       //still have to determine the type      if (local)	  error_msg = ((MJMethod)argu).InsertLocal(f_name,field_type);      else 	  error_msg = ((MJObj)argu).InsertField(f_name,field_type);      if (error_msg != null) System.out.println("Error: " + error_msg);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> "public"    * f1 -> Type()    * f2 -> Identifier()    * f3 -> "("    * f4 -> ( FormalParameterList() )?    * f5 -> ")"    * f6 -> "{"    * f7 -> ( VarDeclaration() )*    * f8 -> ( Statement() )*    * f9 -> "return"    * f10 -> Expression()    * f11 -> ";"    * f12 -> "}"    */   public MJType visit(MethodDeclaration n, MJType argu) {      MJType _ret=null;      MJType method_ret_type ;      MJMethod n_method ;      String m_name  ;      String error_msg = " " ;      n.f0.accept(this, argu);      method_ret_type = n.f1.accept(this, argu);      m_name = ((Name)n.f2.accept(this, argu)).GetName();      n_method = new MJMethod(m_name,method_ret_type,(MJObj)argu);      n.f3.accept(this, argu);      n.f4.accept(this, n_method);      n.f5.accept(this, argu);      n.f6.accept(this, argu);      local = true ;      n.f7.accept(this, n_method);      local = false ;      n.f8.accept(this, argu);      n.f9.accept(this, argu);      n.f10.accept(this, argu);      n.f11.accept(this, argu);      error_msg = ((MJObj)argu).InsertMethod(n_method);      if (error_msg != null) System.out.println("Error: " + error_msg);      return _ret;   }   /**    * f0 -> FormalParameter()    * f1 -> ( FormalParameterRest() )*    */   public MJType visit(FormalParameterList n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      return _ret;   }   /**    * f0 -> Type()    * f1 -> Identifier()    */   public MJType visit(FormalParameter n, MJType argu) {      MJType _ret=null;      MJType param_type ;      String param_name ;      String  error_msg ;      param_type = n.f0.accept(this, argu);      param_name = ((Name)n.f1.accept(this, argu)).GetName();      error_msg = (((MJMethod)argu).InsertParam(param_name,param_type)) ;      if (error_msg != null) System.out.println("Error: " + error_msg);      return _ret;   }   /**    * f0 -> ","    * f1 -> FormalParameter()    */   public MJType visit(FormalParameterRest n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      return _ret;   }   /**    * f0 -> ArrayType()    *       | BooleanType()    *       | IntegerType()    *       | Identifier()    */   public MJType visit(Type n, MJType argu) {      MJType aux = n.f0.accept(this, argu);      return aux;    }   /**    * f0 -> "int"    * f1 -> "["    * f2 -> "]"    */   public MJType visit(ArrayType n, MJType argu) {      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return new ARRAYINT();   }   /**    * f0 -> "boolean"    */   public MJType visit(BooleanType n, MJType argu) {      n.f0.accept(this, argu);      return (new BOOLEAN());   }   /**    * f0 -> "int"    */   public MJType visit(IntegerType n, MJType argu) {      n.f0.accept(this, argu);      return new INT();   }   /**    * f0 -> Block()    *       | AssignmentStatement()    *       | ArrayAssignmentStatement()    *       | IfStatement()    *       | WhileStatement()    *       | PrintStatement()    */   public MJType visit(Statement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> "{"    * f1 -> ( Statement() )*    * f2 -> "}"    */   public MJType visit(Block n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> Identifier()    * f1 -> "="    * f2 -> Expression()    * f3 -> ";"    */   public MJType visit(AssignmentStatement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      return _ret;   }   /**    * f0 -> Identifier()    * f1 -> "["    * f2 -> Expression()    * f3 -> "]"    * f4 -> "="    * f5 -> Expression()    * f6 -> ";"    */   public MJType visit(ArrayAssignmentStatement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      n.f5.accept(this, argu);      n.f6.accept(this, argu);      return _ret;   }   /**    * f0 -> "if"    * f1 -> "("    * f2 -> Expression()    * f3 -> ")"    * f4 -> Statement()    * f5 -> "else"    * f6 -> Statement()    */   public MJType visit(IfStatement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      n.f5.accept(this, argu);      n.f6.accept(this, argu);      return _ret;   }   /**    * f0 -> "while"    * f1 -> "("    * f2 -> Expression()    * f3 -> ")"    * f4 -> Statement()    */   public MJType visit(WhileStatement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      return _ret;   }   /**    * f0 -> "System.out.println"    * f1 -> "("    * f2 -> Expression()    * f3 -> ")"    * f4 -> ";"    */   public MJType visit(PrintStatement n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      return _ret;   }   /**    * f0 -> AndExpression()    *       | CompareExpression()    *       | PlusExpression()    *       | MinusExpression()    *       | TimesExpression()    *       | ArrayLookup()    *       | ArrayLength()    *       | MessageSend()    *       | PrimaryExpression()    */   public MJType visit(Expression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "&"    * f2 -> PrimaryExpression()    */   public MJType visit(AndExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "<"    * f2 -> PrimaryExpression()    */   public MJType visit(CompareExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "+"    * f2 -> PrimaryExpression()    */   public MJType visit(PlusExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "-"    * f2 -> PrimaryExpression()    */   public MJType visit(MinusExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "*"    * f2 -> PrimaryExpression()    */   public MJType visit(TimesExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "["    * f2 -> PrimaryExpression()    * f3 -> "]"    */   public MJType visit(ArrayLookup n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "."    * f2 -> "length"    */   public MJType visit(ArrayLength n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }   /**    * f0 -> PrimaryExpression()    * f1 -> "."    * f2 -> Identifier()    * f3 -> "("    * f4 -> ( ExpressionList() )?    * f5 -> ")"    */   public MJType visit(MessageSend n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      n.f5.accept(this, argu);      return _ret;   }   /**    * f0 -> Expression()    * f1 -> ( ExpressionRest() )*    */   public MJType visit(ExpressionList n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      return _ret;   }   /**    * f0 -> ","    * f1 -> Expression()    */   public MJType visit(ExpressionRest n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      return _ret;   }   /**    * f0 -> IntegerLiteral()    *       | TrueLiteral()    *       | FalseLiteral()    *       | Identifier()    *       | ThisExpression()    *       | ArrayAllocationExpression()    *       | AllocationExpression()    *       | NotExpression()    *       | BracketExpression()    */   public MJType visit(PrimaryExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> <INTEGER_LITERAL>    */   public MJType visit(IntegerLiteral n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> "true"    */   public MJType visit(TrueLiteral n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> "false"    */   public MJType visit(FalseLiteral n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> <IDENTIFIER>    */   public MJType visit(Identifier n, MJType argu) {      MJType _ret = new Name(n.f0.toString());      return _ret;   }   /**    * f0 -> "this"    */   public MJType visit(ThisExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      return _ret;   }   /**    * f0 -> "new"    * f1 -> "int"    * f2 -> "["    * f3 -> Expression()    * f4 -> "]"    */   public MJType visit(ArrayAllocationExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      n.f4.accept(this, argu);      return _ret;   }   /**    * f0 -> "new"    * f1 -> Identifier()    * f2 -> "("    * f3 -> ")"    */   public MJType visit(AllocationExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      n.f3.accept(this, argu);      return _ret;   }   /**    * f0 -> "!"    * f1 -> Expression()    */   public MJType visit(NotExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      return _ret;   }   /**    * f0 -> "("    * f1 -> Expression()    * f2 -> ")"    */   public MJType visit(BracketExpression n, MJType argu) {      MJType _ret=null;      n.f0.accept(this, argu);      n.f1.accept(this, argu);      n.f2.accept(this, argu);      return _ret;   }}