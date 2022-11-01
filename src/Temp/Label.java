// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Temp;
import Symbol.Symbol;

/**
 * A Label represents an address in assembly language.
 */

public class Label  {
   private String name;
   private static int count;

  /**
   * a printable representation of the label, for use in assembly 
   * language output.
   */
   public String toString() {return name;}

  /**
   * Makes a new label that prints as "name".
   * Warning: avoid repeated calls to <tt>new Label(s)</tt> with
   * the same name <tt>s</tt>.
   */
   public Label(String n) {
	name=n;
   }

  /**
   * Makes a new label with an arbitrary name.
   */
   public Label() {
	this("L" + count++);
   }
	
  /**
   * Makes a new label whose name is the same as a symbol.
   */
   public Label(Symbol s) {
	this(s.toString());
   }
}
