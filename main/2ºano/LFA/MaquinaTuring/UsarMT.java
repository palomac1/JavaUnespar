package LFA.MaquinaTuring;

// Classe que implementa a interface MT para usar a máquina de Turing, ela contém o método main para executar o programa e testar cadeias
public class UsarMT {
    public static void main(String[] args) {

        MT mt = new ControlaMT(); // Cria uma nova instância da classe ControlaMT e a associa à variável mt do tipo MT

        mt.entradaUsuario(); // Le entradas para configurar a MT
        
        mt.testarCadeias(); // Testa as cadeias na MT
    }
}
