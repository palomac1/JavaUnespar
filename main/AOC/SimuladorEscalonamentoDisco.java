package AOC;
import java.util.*;

public class SimuladorEscalonamentoDisco {

    // Classe para representar um pedido de cilindro
    static class Pedido {
        int cilindro;
        public Pedido(int cilindro) { // Construtor da classe Pedido para inicializar o pedido de cilindro
            this.cilindro = cilindro; // Inicializa o pedido de cilindro para a quantidade de cilindros informados
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================================================");
        System.out.println("ALGORITMO DE ESCALONAMENTO DE BRAÇO DE DISCO");
        System.out.println("Atividade 2 - AOC - CCOMP - UNESPAR");
        System.out.println("==================================================================================");

        System.out.println("\nInforme o tamanho do cilindro:");
        int tamanhoCilindro = scanner.nextInt();

        // Lê os pedidos de cilindro e armazena em uma lista
        List<Pedido> pedidos = lerPedidos(scanner, tamanhoCilindro);
        scanner.close();
    
        final int posicaoInicial = 0; // Posição inicial do braço do disco

        System.out.println("\n==================================================================================");
        System.out.println("Início FCFS");
        System.out.println("==================================================================================");
        FCFS(new ArrayList<>(pedidos), posicaoInicial, tamanhoCilindro); //Cria uma cópia da lista de pedidos para FCFS
        System.out.println("==================================================================================");
        System.out.println("Final FCFS");
        System.out.println("==================================================================================");
        
        // Cria um array para armazenar os pedidos de cilindro para SSF
        List<Pedido> pedidosSSF = new ArrayList<>(pedidos);
        System.out.println("\n==================================================================================");
        System.out.println("Início SSF");
        System.out.println("==================================================================================");
        SSF(new ArrayList<>(pedidosSSF), posicaoInicial, tamanhoCilindro); //Cria uma cópia da lista de pedidos para SSF
        System.out.println("==================================================================================");
        System.out.println("Final SSF");
        System.out.println("==================================================================================");
    }

    // Função para ler os pedidos de cilindro e armazenar em uma lista
    static List<Pedido> lerPedidos(Scanner scanner, int tamanhoCilindro) {
        List<Pedido> pedidos = new ArrayList<>();
        System.out.println("\nInforme a quantidade de pedidos de cilindros:");
        int qtdPedidos = scanner.nextInt();
        System.out.println();
        
        for (int i = 0; i < qtdPedidos; i++) { 
            System.out.print("Informe o " + (i + 1) + "º pedido de cilindro: ");
            int cilindro = scanner.nextInt(); // Lê o pedido de cilindro
            pedidos.add(new Pedido(cilindro)); // Adiciona o pedido de cilindro na lista
        }
        System.out.println("\n==========================================================");
        System.out.println("Representação inicial do Cilindro:");
        System.out.println("----------------------------------------------------------");
        System.out.print("|| ");

        // Exibe a representação do cilindro
        for (int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("%2d || ", i + 1); // Exibe o número do cilindro
        }
    
        char[] acessoCilindro = new char[tamanhoCilindro]; // Cria um array para armazenar o acesso ao cilindro
        Arrays.fill(acessoCilindro, '-'); // Preenche o array com '-'
    
        System.out.print("\n|| ");
        for (int i = 0; i < tamanhoCilindro; i++) { 
            System.out.printf("%2c || ", acessoCilindro[i]); // Exibe o acesso ao cilindro
        }
    
        System.out.println("\n==========================================================");
        System.out.println("\nVetor de Pedidos");
        System.out.println("==========================================================");
        System.out.print("||");
        for (Pedido pedido : pedidos) {
            System.out.printf(" %2d ||", pedido.cilindro); 
        }  
        return pedidos;
    }

    // Função para calcular FCFS - Ordem
    static void FCFS(List<Pedido> pedidos, int posicaoAtual, int tamanhoCilindro) {
        Set<Integer> marcados = new HashSet<>(); // Cria um conjunto para armazenar os cilindros marcados

        for (Pedido pedido : pedidos) {
            marcados.add(pedido.cilindro); // Adiciona o cilindro marcado   
            mostraRepresentacao(marcados, tamanhoCilindro); // Exibe a representação do cilindro e o acesso se tiver
        }
    }

    // Função para calcular SSF - Mais próximo
    static void SSF(List<Pedido> pedidos, int posicaoAtual, int tamanhoCilindro) {
        Set<Integer> marcados = new HashSet<>();
        int posicaoAtualFinal = posicaoAtual; // Posição atual final do braço do disco
    
        // Loop para percorrer os pedidos de cilindro
        while (!pedidos.isEmpty()) { // Enquanto houver pedidos de cilindro execute o loop
            Pedido proximo = null; // Define o próximo pedido como nulo para iniciar a busca
            int menorDistancia = Integer.MAX_VALUE; // Define a menor distância como o maior valor inteiro
    
            // Loop para percorrer os pedidos de cilindro e calcular a menor distância
            for (Pedido pedido : pedidos) {
                int distancia = Math.abs(pedido.cilindro - posicaoAtualFinal); // Calcula a distância entre o pedido e a posição atual final
                if (distancia < menorDistancia) { // Se a distância for menor que a menor distância, a menor distância recebe a distância
                    menorDistancia = distancia; 
                    proximo = pedido; // O próximo pedido recebe o pedido
                }
            }
    
            // Se o próximo pedido não for nulo, adiciona o cilindro marcado, remove o pedido e exibe a representação do cilindro
            if (proximo != null) {
                marcados.add(proximo.cilindro); // Adiciona o cilindro marcado para o conjunto dos que foram marcados
                pedidos.remove(proximo); // Remove o pedido para não ser processado novamente
                mostraRepresentacao(marcados, tamanhoCilindro); // Exibe a representação do cilindro e o acesso para o próximo pedido
                posicaoAtualFinal = proximo.cilindro;
            }
        }
    }

    // Função para mostrar a movimentação acumulada do braço do disco
    static void mostraRepresentacao(Set<Integer> marcados, int tamanhoCilindro) {
        
        System.out.println("\nRepresentação do Cilindro:");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < tamanhoCilindro; i++) {
            if(i < 10) {
                System.out.printf("|0%d|\t", i);
            } else {
                System.out.printf("|%d|\t", i);
            }
        }
        System.out.println();
        
        // Loop para exibir a representação do cilindro e o acesso marcando com '--' quando não acessado
        for(int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("|--|\t");
        }
        System.out.println();

        // Loop para exibir a representação do cilindro e o acesso marcando com 'XX' quando acessado
        for(int i = 0; i < tamanhoCilindro; i++) {
            if(marcados.contains(i)) {
                System.out.printf("|XX|\t");
            } else {
                System.out.printf("|  |\t");
            }
        }
        System.out.println("\n");
    }
}
