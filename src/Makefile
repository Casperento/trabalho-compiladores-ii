JFLAGS=-cp .


MainBuild: ParserPkg MipsPkg FramePkg AssemPkg TypesPkg TreePkg SyntaxtreePkg VisitorPkg TempPkg SymbolPkg GjPkg
	javac ${JFLAGS}:Parser Main.java


ParserPkg: TempPkg SyntaxtreePkg VisitorPkg Parser/*.java
	javac ${JFLAGS} Parser/*.java


MipsPkg: TempPkg TreePkg FramePkg SymbolPkg AssemPkg Mips/*.java
	javac ${JFLAGS} Mips/*.java


FramePkg: TempPkg AssemPkg TreePkg Frame/*.java
	javac ${JFLAGS} Frame/*.java


AssemPkg: TempPkg Assem/*.java
	javac ${JFLAGS} Assem/*.java


TypesPkg: TempPkg types/*.java
	javac ${JFLAGS} types/*.java


TreePkg: TempPkg Tree/*.java
	javac ${JFLAGS} Tree/*.java


SyntaxtreePkg: TempPkg VisitorPkg syntaxtree/*.java
	javac ${JFLAGS} syntaxtree/*.java


VisitorPkg: GjPkg
	javac ${JFLAGS} visitor/*.java


TempPkg: Temp/*.java
	javac ${JFLAGS} Temp/*.java


SymbolPkg: Symbol/*.java
	javac ${JFLAGS} Symbol/*.java


GjPkg: gj/lang/reflect/Array.java gj/util/*.java
	javac ${JFLAGS} gj/lang/reflect/Array.java
	javac ${JFLAGS} gj/util/*.java


clean:
	find . -iname '*.class' | xargs rm
