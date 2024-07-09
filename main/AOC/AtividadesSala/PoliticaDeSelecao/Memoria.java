package AOC.AtividadesSala.PoliticaDeSelecao;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
    private List<BlocoDeMemoria> blocos;

    public Memoria(int tamanho) {
        blocos = new ArrayList<>();
        blocos.add(new BlocoDeMemoria(tamanho));
    }

    public boolean alocarPrimeiroAjuste(Instrucoes objeto) {
        return alocar(objeto, new PrimeiroAjuste());
    }

    public boolean alocarMelhorAjuste(Instrucoes objeto) {
        return alocar(objeto, new MelhorAjuste());
    }

    public boolean alocarPiorAjuste(Instrucoes objeto) {
        return alocar(objeto, new PiorAjuste());
    }

    private boolean alocar(Instrucoes objeto, PoliticaDeSelecao politica) {
        BlocoDeMemoria blocoSelecionado = politica.selecionarBloco(blocos, objeto);
        if (blocoSelecionado != null) {
            if (blocoSelecionado.getTamanho() > objeto.getTamanho()) {
                blocos.add(blocos.indexOf(blocoSelecionado) + 1, new BlocoDeMemoria(blocoSelecionado.getTamanho() - objeto.getTamanho()));
            }
            blocoSelecionado.ocupar(objeto);
            return true;
        }
        return false;
    }

    public boolean desalocarPorTamanho(int tamanho) {
        for (BlocoDeMemoria bloco : blocos) {
            if (bloco.isOcupado() && bloco.getTamanho() == tamanho) {
                bloco.liberar();
                return true;
            }
        }
        return false;
    }

    public void mostrarMemoria() {
        for (BlocoDeMemoria bloco : blocos) {
            System.out.print(bloco + " ");
        }
        System.out.println();
    }

    private static interface PoliticaDeSelecao {
        BlocoDeMemoria selecionarBloco(List<BlocoDeMemoria> blocos, Instrucoes objeto);
    }

    private static class PrimeiroAjuste implements PoliticaDeSelecao {
        public BlocoDeMemoria selecionarBloco(List<BlocoDeMemoria> blocos, Instrucoes objeto) {
            for (BlocoDeMemoria bloco : blocos) {
                if (!bloco.isOcupado() && bloco.getTamanho() >= objeto.getTamanho()) {
                    return bloco;
                }
            }
            return null;
        }
    }

    private static class MelhorAjuste implements PoliticaDeSelecao {
        public BlocoDeMemoria selecionarBloco(List<BlocoDeMemoria> blocos, Instrucoes objeto) {
            BlocoDeMemoria melhorBloco = null;
            for (BlocoDeMemoria bloco : blocos) {
                if (!bloco.isOcupado() && bloco.getTamanho() >= objeto.getTamanho()) {
                    if (melhorBloco == null || bloco.getTamanho() < melhorBloco.getTamanho()) {
                        melhorBloco = bloco;
                    }
                }
            }
            return melhorBloco;
        }
    }

    private static class PiorAjuste implements PoliticaDeSelecao {
        public BlocoDeMemoria selecionarBloco(List<BlocoDeMemoria> blocos, Instrucoes objeto) {
            BlocoDeMemoria piorBloco = null;
            for (BlocoDeMemoria bloco : blocos) {
                if (!bloco.isOcupado() && bloco.getTamanho() >= objeto.getTamanho()) {
                    if (piorBloco == null || bloco.getTamanho() > piorBloco.getTamanho()) {
                        piorBloco = bloco;
                    }
                }
            }
            return piorBloco;
        }
    }

    private static class BlocoDeMemoria {
        private int tamanho;
        private boolean ocupado;
        private Instrucoes objeto;

        public BlocoDeMemoria(int tamanho) {
            this.tamanho = tamanho;
            this.ocupado = false;
            this.objeto = null;
        }

        public int getTamanho() {
            return tamanho;
        }

        public boolean isOcupado() {
            return ocupado;
        }

        public void ocupar(Instrucoes objeto) {
            this.ocupado = true;
            this.objeto = objeto;
            this.tamanho = objeto.getTamanho();
        }

        public void liberar() {
            this.ocupado = false;
            this.objeto = null;
        }

        @Override
        public String toString() {
            return "(" + tamanho + "," + ocupado + ")";
        }
    }
}
