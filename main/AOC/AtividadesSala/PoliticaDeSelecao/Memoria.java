package AOC.AtividadesSala.PoliticaDeSelecao;

public class Memoria {

    int tamanho; 
    boolean livre; 

    public Memoria(int tamanho, boolean livre) {
        this.tamanho = tamanho;
        this.livre = livre;
    }

    @Override
    public String toString() {
        return "(" + tamanho + "," + livre + ")";
    }
}
