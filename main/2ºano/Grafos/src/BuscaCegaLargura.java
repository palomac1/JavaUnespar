import java.util.*;

public class BuscaCegaLargura {

    static class No {
        int valor;
        No esquerdo, direito;

        No(int valor) {
            this.valor = valor;
            this.esquerdo = this.direito = null;
        }
    }

    static boolean buscaLargura(No raiz, int alvo) {
        if (raiz == null) return false; // Se a árvore estiver vazia, retorna falso

        // Fila para armazenar os nós a serem explorados que ainda não foram visitados 
        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        // Enquanto houver nós a serem explorados então continua a exploração, senão termina
        while (!fila.isEmpty()) {
            No atual = fila.poll();
            System.out.println("Visitando nó: " + atual.valor);

            // Se o nó atual for o alvo, então retorna verdadeiro e encerra a busca
            if (atual.valor == alvo) {
                System.out.println("Nó " + alvo + " encontrado!");
                return true;
            }

            // Adiciona os filhos à fila (esquerdo primeiro, depois direito)
            if (atual.esquerdo != null) fila.add(atual.esquerdo);
            if (atual.direito != null) fila.add(atual.direito);
        }

        System.out.println("Nó " + alvo + " não encontrado.");
        return false;
    }

    public static void main(String[] args) {
        No raiz = new No(1);
        raiz.esquerdo = new No(2);
        raiz.direito = new No(3);
        raiz.esquerdo.esquerdo = new No(4);
        raiz.esquerdo.direito = new No(5);

        System.out.println("=== Busca em Largura ===");
        buscaLargura(raiz, 5);
    }
}