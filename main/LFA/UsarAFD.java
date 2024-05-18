package LFA;

public class UsarAFD {
    public static void main(String[] args) {
        AFD afd = new ControlaAFD();

        afd.leEntradas();
        afd.tabelaTransicaoDescricao();
        afd.testaCadeia();
    }
}
