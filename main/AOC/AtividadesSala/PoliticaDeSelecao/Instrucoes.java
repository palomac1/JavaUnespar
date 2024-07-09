package AOC.AtividadesSala.PoliticaDeSelecao;

public class Instrucoes {
    private int tamanho;

    public Instrucoes(int tamanho){
        this.tamanho = tamanho;
    }

    public int getTamanho(){
        return tamanho;
    }

    @Override
    public String toString(){
        return "Objeto {tamanho = " + tamanho + "}";
    }
}
