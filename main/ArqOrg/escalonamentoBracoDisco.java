package ArqOrg;
import java.util.*;

public class escalonamentoBracoDisco {

    static class Pedido {
      int cilindro;
        public Pedido(int cilindro) {
            this.cilindro = cilindro;
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
    
        List<Pedido> pedidos = lerPedidos(scanner);
        scanner.close();
    
        final int posicaoInicial = 0;

        System.out.println("==================================================================================");
        System.out.println("Início FCFS");
        System.out.println("==================================================================================");
        FCFS(new ArrayList<>(pedidos), posicaoInicial, tamanhoCilindro);
        System.out.println("==================================================================================");
        System.out.println("Final FCFS");
        System.out.println("==================================================================================");
        
        List<Pedido> pedidosSSF = new ArrayList<>(pedidos);
        System.out.println("\n=================================================================================");
        System.out.println("Início SSF");
        System.out.println("==================================================================================");
        SSF(new ArrayList<>(pedidosSSF), posicaoInicial, tamanhoCilindro);
        System.out.println("==================================================================================");
        System.out.println("Final SSF");
        System.out.println("==================================================================================");
    }

    static List<Pedido> lerPedidos(Scanner scanner) {
        List<Pedido> pedidos = new ArrayList<>();
        System.out.println("\nInforme a quantidade de pedidos de cilindros:");
        int qtdPedidos = scanner.nextInt();
        System.out.println();
        
        for (int i = 0; i < qtdPedidos; i++) {
            System.out.print("Informe o " + (i + 1) + "º pedido de cilindro: ");
            int cilindro = scanner.nextInt();
            pedidos.add(new Pedido(cilindro));
        }
        return pedidos;

       /* System.out.println("\n==========================================================");
        System.out.println("Representação inicial do Cilindro:");
        System.out.println("----------------------------------------------------------");
        System.out.print("|| ");
        for (int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("%2d || ", i + 1);
        }

        char[] acessoCilindro = new char[tamanhoCilindro];
        Arrays.fill(acessoCilindro, '-');

        System.out.print("\n|| ");
        for (int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("%2c || ", acessoCilindro[i]);
        }

        System.out.println("\n==========================================================");
        System.out.println("\nVetor de Pedidos");
        System.out.println("==========================================================");
        System.out.print("||");
        for (int i = 0; i < qntdPedidos; i++) {
            System.out.printf(" %2d ||", vetorPedidos[i]);
        }  */
    }

    // Função para calcular FCFS - Ordem
    static void FCFS(List<Pedido> pedidos, int posicaoAtual, int tamanhoCilindro) {
        Set<Integer> marcados = new HashSet<>();

        for (Pedido pedido : pedidos) {
            marcados.add(pedido.cilindro);
            mostraRepresentacao(marcados, tamanhoCilindro);
        }
    }

    // Função para calcular SSF - Mais próximo
    static void SSF(List<Pedido> pedidos, int posicaoAtual, int tamanhoCilindro) {
        Set<Integer> marcados = new HashSet<>();
        int posicaoAtualFinal = posicaoAtual;
    
        while (!pedidos.isEmpty()) {
            Pedido proximo = null;
            int menorDistancia = Integer.MAX_VALUE;
    
            for (Pedido pedido : pedidos) {
                int distancia = Math.abs(pedido.cilindro - posicaoAtualFinal);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    proximo = pedido;
                }
            }
    
            if (proximo != null) {
                marcados.add(proximo.cilindro);
                pedidos.remove(proximo);
                mostraRepresentacao(marcados, tamanhoCilindro);
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
        
        for(int i = 0; i < tamanhoCilindro; i++) {
            System.out.printf("|--|\t");
        }
        System.out.println();

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
