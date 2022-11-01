// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package types ;
//import pgtree.*;

import Temp.Temp;

public class Variable extends MJType {
    private String name ;
    private MJType var_type ;
    private Temp tmp ;

    public Variable(String v_name, MJType v_type) {
        name = v_name ;
        var_type = v_type ;
        tmp = null ;
    }

    public Variable(String v_name, MJType v_type, Temp t) {
        name = v_name ;
        var_type = v_type ;
        tmp = t ;
    }

    public String GetName() {
        return name ;
    }

    public Temp GetTemp() {
        return tmp ;
    }

    public void Print() {
        String type_name = var_type.GetType();
        System.out.println("             " + name + "   Type: " + type_name);
    }

    public String GetType() {
        return ("Object" + name) ;
    }

    public MJType SearchType(String name) {
        return var_type ;
    }

    public boolean CheckType(MJType n_type) {
        if (!(n_type instanceof Variable)) return false ;
        else return true ;
    }

    public void SetType(MJType new_type) {
        var_type = new_type ;
    }


}
