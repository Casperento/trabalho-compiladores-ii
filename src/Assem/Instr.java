// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Assem;
import Temp.Temp;
import Temp.TempMap;
import Temp.Label;
import java.util.List;

public abstract class Instr {
    public String assem;
    public Temp[] use;
    public Temp[] def;
    public List<Label> jumps;
    public void replaceUse(Temp olduse, Temp newuse) {
        if (use != null)
            for (int i = 0; i< use.length; i++)
                if (use[i] == olduse) use[i] = newuse;
    }
    public void replaceDef(Temp olddef, Temp newdef) {
        if (def != null)
            for (int i = 0; i< def.length; i++)
                if (def[i] == olddef) def[i] = newdef;
    };

    public String format(TempMap m) {
        StringBuffer s = new StringBuffer();
        int len = assem.length();
        for(int i=0; i<len; i++)
            if (assem.charAt(i)=='`')
                switch(assem.charAt(++i)) {
                case 's': {
                    int n = Character.digit(assem.charAt(++i),10);
                    s.append(m.tempMap(use[n]));
                    break;
                }
                case 'd': {
                    int n = Character.digit(assem.charAt(++i),10);
                    s.append(m.tempMap(def[n]));
                    break;
                }
                case 'j': {
                    int n = Character.digit(assem.charAt(++i),10);
                    s.append(jumps.get(n).toString());
                    break;
                }
                case '`':
                    s.append('`');
                    break;
                default:
                    throw new Error("bad Assem format:" + assem);
                } else s.append(assem.charAt(i));
        return s.toString();
    }
}
