package LFA.MaquinaTuring;

public class UsarMT {
    public static void main(String[] args) {

        MT mt = new ControlaMT();

        mt.entradaUsuario();

        mt.tabelaTransicaoDescricao();
        
        mt.testarCadeias();
    }
}