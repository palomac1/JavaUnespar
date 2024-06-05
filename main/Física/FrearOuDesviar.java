package Física;
import java.util.Scanner;

public class FrearOuDesviar {

    // Códigos ANSI para cores
    public static final String RESET = "\033[0m";
    public static final String PURPLE = "\033[0;35m";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(PURPLE + "--- Trabalho de Física - 1ºBim - C.C UNESPAR ---" + RESET);
        System.out.println(PURPLE + "Aluno: Paloma de Castro Leite - 2º Ano \r\n" + RESET);
        System.out.println(PURPLE + "Programa referente ao exercicio 58, capítulo 6 - Força e Movimento II. Disponível no livro: Fundamentos de Física Vol. 1_Mecânica, Halliday\r\n" + RESET);
        System.out.println("Enunciado: Temos a vista superior de um carro que se aproxima de um muro. Suponha que o  motorista começa a frear quando a distância entre o carro e o muro é d, que a massa do carro é m, que a velocidade inicial é v0 e que o coeficiente de atrito estático é µ." + //
                        " Suponha também que o peso do carro está distribuído igualmente pelas quatro rodas, mesmo durante a frenagem.\n");

        System.out.println("O programa a seguir irá calcular o valor mínimo do módulo do atrito estático para o veículo frear, o valor máximo possível do atrito estático, a velocidade com que o veículo se choca com o muro, o módulo da força de atrito necessária para fazer a curva e a comparação da força calculada no item (d) com Fsmáx para ver se o veículo irá bater ou não. \n");


        System.out.print("Digite a distância (d) em metros: ");
        double d = scanner.nextDouble();
        
        System.out.print("Digite a massa do veículo (m) em kg: ");
        double m = scanner.nextDouble();
        
        System.out.print("Digite a velocidade inicial (v0) em m/s: ");
        double v0 = scanner.nextDouble();
        
        System.out.print("Digite o coeficiente de atrito estático (µ): ");
        double coefAtritoEstatico = scanner.nextDouble();
        
        System.out.print("Digite o coeficiente de atrito cinético (µk): ");
        double coefAtritoCinetico = scanner.nextDouble();

        // Constante da gravidade
        final double g = 9.81;

        // (a) Valor mínimo do módulo do atrito estático
        double minAtritoEstatico = (m * v0 * v0) / (2 * d);
        System.out.printf("Valor mínimo do atrito estático para o veículo frear: %.2f N%n", minAtritoEstatico);

        // (b) Valor máximo possível do atrito estático (Fsmáx)
        double maxAtritoEstatico = coefAtritoEstatico * m * g;
        System.out.printf("Valor máximo do atrito estático: %.2f N%n", maxAtritoEstatico);

        // (c) Velocidade com que o veiculo se choca com o muro
        double atritoCinetico = coefAtritoCinetico * m * g;
        double vFinalRaiz = v0 * v0 - (2 * atritoCinetico * d) / m;
        double velFinalChoque = Math.sqrt(Math.max(0, vFinalRaiz));
        System.out.printf("Velocidade com que o veículo se choca contra o muro: %.2f m/s%n", velFinalChoque);

        // (d) Módulo da força de atrito necessária para fazer a curva
        double forcaAtritoCurva = (m * v0 * v0) / d;
        System.out.printf("Módulo da força necessária para realizar a curva: %.2f N%n", forcaAtritoCurva);

        // (e) Comparação da força calculada no item (d) com Fsmáx para ver se o veiculo irá bater ou não
        if (forcaAtritoCurva < maxAtritoEstatico) {
            System.out.printf("A força de atrito necessária para realizar a curva é menor que %.2f N, evitando o choque.%n", maxAtritoEstatico);
        } else {
            System.out.printf("A força de atrito necessária para realizar a curva é maior que %.2f N, o que não evitaria o choque.%n", maxAtritoEstatico);
        }

        scanner.close();
    }
}
