// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Temp;

public class CombineMap implements TempMap {
    TempMap tmap1, tmap2;
    public String tempMap(Temp t) {
        String s = tmap1.tempMap(t);
        if (s!=null) return s;
        return tmap2.tempMap(t);
    }

    public CombineMap(TempMap t1, TempMap t2) {
        tmap1=t1;
        tmap2=t2;
    }
}
