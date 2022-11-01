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

public class MOVE extends Instr {
    public MOVE(String a, Temp d, Temp s) {
	assem = a;
	use = new Temp[]{s};
	def = new Temp[]{d};
	jumps = null;
    }
    public Temp dst() { return def[0]; }
    public Temp src() { return use[0]; }

    public String format(TempMap m) {
	if (m.tempMap(src()) == m.tempMap(dst()))
	    return "#" + super.format(m);
	return super.format(m);
    }
}
