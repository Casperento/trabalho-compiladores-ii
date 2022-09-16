JFLAGS=-g -cp ./:../lib/java-cup-11b.jar

Parse/Main.class: Parse/*.java Parse/Yylex.java
	javac ${JFLAGS} Parse/*.java

Parse/Yylex.java: Parse/Tiger.lex
	java Parse.JLex.Main Parse/Tiger.lex
	sed -i 's/ErrorMsg\.ErrorMsg[^;]/ErrorMsg /g' Parse/Tiger.lex.java
	mv Parse/Tiger.lex.java Parse/Yylex.java

ErrorMsg/ErrorMsg.class:  ErrorMsg/*.java
	javac ${JFLAGS} ErrorMsg/*.java

clean:
	rm Parse/*.class ErrorMsg/*.class Parse/Yylex.java Parse/JLex/*.class


