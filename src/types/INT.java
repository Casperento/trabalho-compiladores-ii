// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package types;

public class INT extends MJType {
    private int value ;

    public INT(int v) {
        value = v ;
    }
    public INT( ) {
        value = 0 ;
    }

    public String GetType() {
        return "int" ;
    }

    public int GetVal() {
        return value ;
    }

    public MJType SearchType(String name) {
        return this ;
    }

    public boolean CheckType(MJType n_type) {
        if (!(n_type instanceof INT)) return false ;
        else return true ;
    }
}
