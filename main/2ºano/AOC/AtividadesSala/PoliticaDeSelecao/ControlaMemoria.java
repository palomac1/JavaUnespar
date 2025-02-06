package AOC.AtividadesSala.PoliticaDeSelecao;

import java.util.ArrayList;
import java.util.List;

public class ControlaMemoria {
    List<Memoria> memorias; 

    public ControlaMemoria(int tamanhoInicial) {
        memorias = new ArrayList<>();    
        memorias.add(new Memoria(tamanhoInicial, true));
    }

    public void primeiroAjuste(int tamanho) {
        for (Memoria memoria : memorias) {
            if (memoria.livre && memoria.tamanho >= tamanho) {
                dividirBloco(memoria, tamanho); 
                return;
            }
        }
        System.out.println("Memoria insuficiente para " + tamanho + " KB");
    }

    public void melhorAjuste(int tamanho) {
        Memoria melhor = null;
        for (Memoria memoria : memorias) {
            if (memoria.livre && memoria.tamanho >= tamanho) {
                if (melhor == null || memoria.tamanho < melhor.tamanho) {
                    melhor = memoria; 
                }
            }
        }
        if (melhor != null) {
            dividirBloco(melhor, tamanho);
        } else {
            System.out.println("Memoria insuficiente para " + tamanho + " KB");
        }
    }

    public void piorAjuste(int tamanho) {
        Memoria pior = null;
        for (Memoria memoria : memorias) {
            if (memoria.livre && memoria.tamanho >= tamanho) {
                if (pior == null || memoria.tamanho > pior.tamanho) {
                    pior = memoria; 
                }
            }
        }
        if (pior != null) {
            dividirBloco(pior, tamanho);
        } else {
            System.out.println("Memoria insuficiente para " + tamanho + " KB");
        }
    }

    private void dividirBloco(Memoria memoria, int tamanho) {
        if (memoria.tamanho > tamanho) {
            memorias.add(memorias.indexOf(memoria) + 1, new Memoria(memoria.tamanho - tamanho, true)); 
        }
        memoria.tamanho = tamanho;
        memoria.livre = false; 
    }

    public void liberarMemoria(int tamanho) {
        for (Memoria memoria : memorias) {
            if (!memoria.livre && memoria.tamanho == tamanho) {
                memoria.livre = true; 
                fundirBlocosLivres(); 
                return;
            }
        }
        System.out.println("Bloco de " + tamanho + " KB nao encontrado.");
    }

    private void fundirBlocosLivres() {
        for (int i = 0; i < memorias.size() - 1; i++) {
            Memoria atual = memorias.get(i);
            Memoria proximo = memorias.get(i + 1);
            if (atual.livre && proximo.livre) {
                atual.tamanho += proximo.tamanho; 
                memorias.remove(i + 1); 
                i--; 
            }
        }
    }

    public void exibirMemoria() {
        for (Memoria memoria : memorias) {
            System.out.print(memoria + " "); 
        }
        System.out.println();
    }
}
