import java.util.Stack;

public class BuscaCegaProfundidade {

    // Classe que representa um nó da árvore 
    public static class Nodo {
        int id; // Identificador único do nó
        Nodo filhoEsquerdo;
        Nodo filhoDireito;

        public Nodo(int id) {
            this.id = id;
            this.filhoEsquerdo = null;
            this.filhoDireito = null;
        }
    }

    public static void explorarEmProfundidade(Nodo raiz) {
        if (raiz == null) {
            System.out.println("A árvore está vazia. Nada para explorar!");
            return;
        }

        // Pilha para armazenar os nós a serem explorados que ainda não foram visitados
        Stack<Nodo> pilhaExploracao = new Stack<>();
        pilhaExploracao.push(raiz);

        // Enquanto houver nós a serem explorados então continua a exploraçã, senão termina
        while (!pilhaExploracao.isEmpty()) {
            Nodo nodoAtual = pilhaExploracao.pop();
            System.out.println("Explorando nó: " + nodoAtual.id);

            // Adiciona os filhos à pilha (direito primeiro, depois esquerdo)
            if (nodoAtual.filhoDireito != null) {
                pilhaExploracao.push(nodoAtual.filhoDireito);
            }
            if (nodoAtual.filhoEsquerdo != null) {
                pilhaExploracao.push(nodoAtual.filhoEsquerdo);
            }
        }
        System.out.println("Exploração concluída!");
    }

    public static void main(String[] args) {
        Nodo raiz = new Nodo(1);
        raiz.filhoEsquerdo = new Nodo(2);
        raiz.filhoDireito = new Nodo(3);
        raiz.filhoEsquerdo.filhoEsquerdo = new Nodo(4);
        raiz.filhoEsquerdo.filhoDireito = new Nodo(5);
        raiz.filhoDireito.filhoDireito = new Nodo(6); 

        System.out.println("=== Busca em Profundidade ===");
        explorarEmProfundidade(raiz);
    }
}