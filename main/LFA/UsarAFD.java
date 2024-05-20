package LFA;

public class UsarAFD {
    public static void main(String[] args) {

        // Cria uma nova instância da classe ControlaAFD e a associa à variável afd do tipo AFD
        AFD afd = new ControlaAFD();

        // Le entradas para configurar o AFD
        afd.entradaUsuario();

        // Exibir a tabela de transição e a descrição formal
        afd.tabelaTransicaoDescricao();

        // Testar caracteres 
        afd.testarCadeias();
    }
}

