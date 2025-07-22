import java.util.*;

public class Dijkstr {

    // Classe que representa um vértice no grafo
    static class Vertice {
        String nome;
        double distanciaMinima = Double.POSITIVE_INFINITY; // Distância mínima inicialmente infinita para todos os vértices
        Vertice anterior; // Vértice anterior no caminho mínimo para este vértice
        List<Aresta> vizinhos = new ArrayList<>(); // Lista de arestas para os vizinhos do vértice

        Vertice(String nome) {
            this.nome = nome;
        }

        void adicionarVizinho(Vertice destino, double custo) {
            vizinhos.add(new Aresta(destino, custo));
        }
    }

    // Classe que representa uma aresta no grafo
    static class Aresta {
        Vertice destino;
        double custo;

        Aresta(Vertice destino, double custo) {
            this.destino = destino;
            this.custo = custo;
        }
    }

    // Função que executa o algoritmo de Dijkstra a partir de um vértice de origem dado como parâmetro 
    public static void processaCaminho(Vertice origem) {
        origem.distanciaMinima = 0; // A distância mínima do vértice de origem para ele mesmo é 0
        PriorityQueue<Vertice> fila = new PriorityQueue<>(Comparator.comparingDouble(v -> v.distanciaMinima)); // Fila de prioridade para armazenar os vértices a serem explorados
        fila.add(origem); // Adiciona o vértice de origem à fila para iniciar a exploração

        // Enquanto houver vértices então continua a exploração, senão termina
        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();

            // Percorre os vizinhos do vértice atual para atualizar as distâncias mínimas
            for (Aresta aresta : atual.vizinhos) {
                Vertice vizinho = aresta.destino;
                double novaDistancia = atual.distanciaMinima + aresta.custo;

                // Se encontrou um caminho mais curto, atualiza o vizinho e adiciona à fila
                if (novaDistancia < vizinho.distanciaMinima) {
                    vizinho.distanciaMinima = novaDistancia;
                    vizinho.anterior = atual;
                    fila.add(vizinho);
                }
            }
        }
    }

    // Função que recupera o menor caminho a partir do vértice de destino para o vértice de origem
    public static List<Vertice> getMenorCaminho(Vertice destino) {
        List<Vertice> caminho = new ArrayList<>();
        for (Vertice v = destino; v != null; v = v.anterior) { // Percorre os vértices do destino até o origem
            caminho.add(v);
        }
        Collections.reverse(caminho); // Inverte a ordem dos vértices para obter o caminho correto
        return caminho;
    }

    public static void main(String[] args) {
        // Cria os vértices do grafo para o exemplo
        Vertice a = new Vertice("A");
        Vertice b = new Vertice("B");
        Vertice c = new Vertice("C");
        Vertice d = new Vertice("D");

        // Adiciona arestas para conectar os vértices
        a.adicionarVizinho(b, 1);
        a.adicionarVizinho(c, 4);
        b.adicionarVizinho(c, 2);
        b.adicionarVizinho(d, 5);
        c.adicionarVizinho(d, 1);

        processaCaminho(a);

        // Recupera o menor caminho até o vértice D
        List<Vertice> caminho = getMenorCaminho(d);

        System.out.println("Menor caminho de A até D: ");
        for (Vertice v : caminho) {
            System.out.print(v.nome + " ");
        }
        System.out.println("\nDistância mínima: " + d.distanciaMinima);
    }
}