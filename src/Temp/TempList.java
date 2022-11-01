// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Temp;

public class TempList {

    public Temp head;
    public TempList tail;
    public TempList(Temp h, TempList t) {
        head=h;
        tail=t;
    }

    public static TempList toTempList(Temp[] t) {
        TempList[] tailList = new TempList[t.length];
        for (int i = 0; i < t.length; i++)
        {
            tailList[i] = new TempList(t[i],null);
        }
        /*
        *
        * [1,2,3] => { [1] , [2] , [3] }
        *
        * */
        TempList tempList = new TempList(t[0],null);
        for (int i = t.length-2; i != 0; i--)
        {
            tempList.tail = tailList[i+1];
        }
        /*
         *
         * { [1] , [2] , [3] } => [1,[2,[3]]]
         *
         * */

        return tempList;
    }

    public static Temp[] toTempArray(TempList list) {
        Temp[] result = new Temp[1024];
        Temp h = list.head;
        int i = 0;
        while (h != null) {
            result[i] = h;
            list.head = list.tail.head;
            list.tail = list.tail.tail;
            h = list.head;
        }
        return result;
    }

}

