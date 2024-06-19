package ArqOrg;
import java.util.*;

public class escalonamentoBracoDisco {

    // Classe que representa os pedidos
    static class Pedido {
        int cilindro;
        public Pedido(int cilindro) {
            this.cilindro = cilindro;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Informe o tamanho do cilindro:");
        int tamanhoCilindro = scanner.nextInt();
    
        List<Pedido> pedidos = lerPedidos(scanner);
        scanner.close();
    
        final int posicaoInicial = 0;

        System.out.println("Representação do cilindro:");
           for(int i = 0; i < tamanhoCilindro; i++) {
               if(i < 10) {
                   System.out.printf("|0%d|\t", i);
               }   
            }
            //System.out.println("\n");
            for(int i = 0; i < tamanhoCilindro; i++) {
                System.out.printf("|--|\t");
            }
        
        System.out.println("\nDeslocamento total com FCFS: " + fcfs(pedidos, posicaoInicial));
        
        List<Pedido> pedidosSSF = new ArrayList<>(pedidos);
        System.out.println("Deslocamento total com SSF: " + ssf(pedidosSSF, posicaoInicial));
    }

    static List<Pedido> lerPedidos(Scanner scanner) {
        List<Pedido> pedidos = new ArrayList<>();
        System.out.println("Informe a quantidade de pedidos de cilindros:");
        int quantidadePedidos = scanner.nextInt();
        
        System.out.println("Informe os pedidos de cilindros:");
        for (int i = 0; i < quantidadePedidos; i++) {
            int cilindro = scanner.nextInt();
            pedidos.add(new Pedido(cilindro));
        }

        return pedidos;
    }



    // Função para calcular FCFS - Ordem
    static int fcfs(List<Pedido> pedidos, int posicaoAtual) {
        int deslocamentoTotal = 0;

        for (Pedido pedido : pedidos) {
            deslocamentoTotal += Math.abs(pedido.cilindro - posicaoAtual);
            posicaoAtual = pedido.cilindro;
        }

        return deslocamentoTotal;
    }

    // Função para calcular SSF - Mais próximo
    static int ssf(List<Pedido> pedidos, int posicaoAtual) {
        int deslocamentoTotal = 0;
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
                deslocamentoTotal += menorDistancia;
                posicaoAtualFinal = proximo.cilindro;
                pedidos.remove(proximo);
            }
        }
    
        return deslocamentoTotal;
    }
}
