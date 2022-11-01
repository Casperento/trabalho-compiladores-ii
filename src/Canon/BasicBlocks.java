// Cabeçalho a ser colocado em todo arquivo fonte produzido pelo grupo que desenvolvou o trabalho

// Nome(s) dos Discente(s): Rafael Alvarenga e Yuri Gandra
// Matrícula:               0053108          e 0049466
// Data: 09-10-2022


// Declaro que sou (somos) o(s) único(s) autor(es) e responsável (éis) por este programa. Todas as partes do programa, exceto as que foram fornecidas
// pelo professor ou copiadas das bibliotecas ou do livro Modern Compiler Implementation, foram desenvolvidas por mim. Declaro também que
// sou (somos) o(s) responsável (éis) por todas as eventuais cópias deste programa e que não distribui (mos) nem facilitei (amos) a distribuição de cópias.
package Canon;

public class BasicBlocks {
    public StmListList blocks;
    public Temp.Label done;

    private StmListList lastBlock;
    private Tree.StmList lastStm;

    private void addStm(Tree.Stm s) {
        lastStm = lastStm.tail = new Tree.StmList(s,null);
    }

    private void doStms(Tree.StmList l) {
        if (l==null)
            doStms(new Tree.StmList(new Tree.JUMP(done), null));
        else if (l.head instanceof Tree.JUMP
                 || l.head instanceof Tree.CJUMP) {
            addStm(l.head);
            mkBlocks(l.tail);
        } else if (l.head instanceof Tree.LABEL)
            doStms(new Tree.StmList(new Tree.JUMP(((Tree.LABEL)l.head).label),
                                    l));
        else {
            addStm(l.head);
            doStms(l.tail);
        }
    }

    void mkBlocks(Tree.StmList l) {
        if (l==null) return;
        else if (l.head instanceof Tree.LABEL) {
            lastStm = new Tree.StmList(l.head,null);
            if (lastBlock==null)
                lastBlock= blocks= new StmListList(lastStm,null);
            else
                lastBlock = lastBlock.tail = new StmListList(lastStm,null);
            doStms(l.tail);
        } else mkBlocks(new Tree.StmList(new Tree.LABEL(new Temp.Label()), l));
    }


    public BasicBlocks(Tree.StmList stms) {
        done = new Temp.Label();
        mkBlocks(stms);
    }
}
