// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
//package Translate;
package visitor;
import Temp.Temp;
import Temp.Label;

public abstract class Cx extends Exp {
    Tree.Exp unEx() {
        Temp r = new Temp();
        Label t = new Label();
        Label f = new Label();

        return new Tree.ESEQ
               (new Tree.SEQ
                (new Tree.MOVE(new Tree.TEMP(r), new Tree.CONST(1)),
                 new Tree.SEQ(unCx(t, f),
                              new Tree.SEQ
                              (new Tree.LABEL(f),
                               new Tree.SEQ
                               (new Tree.MOVE(new Tree.TEMP(r),
                                              new Tree.CONST(0)),
                                new Tree.LABEL(t))))),
                new Tree.TEMP(r));
    }

    abstract Tree.Stm unCx(Label t, Label f);

    Tree.Stm unNx() {
        Label join = new Label();

        return new Tree.SEQ(unCx(join, join),
                            new Tree.LABEL(join));
    }
}



