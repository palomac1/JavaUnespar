package Física;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrearDesviar extends JFrame implements ActionListener {

    private JTextField distanciaField, massaField, velocidadeField, atritoEstaticoField, atritoCineticoField;
    private JTextArea resultadoArea;
    private JButton calcularButton, resetarButton;

    public FrearDesviar() {

        // Configurações do JFrame e do painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Layout do JFrame
        setLayout(new BorderLayout());

        // Titulo e descrição do exercicio selecionado para o programa
        String text = "--- Trabalho de Física - 1ºBim - C.C UNESPAR --- \n\n"
                + "Aluno: Paloma de Castro Leite - 2º Ano\n\n"
                + "Programa referente ao exercicio 58, capítulo 6 - Força e Movimento II. Disponível no livro: Fundamentos de Física Vol. 1_Mecânica, Halliday\n\n" 
                +"Enunciado: Temos a vista superior de um carro que se aproxima de um muro. Suponha que o  motorista começa a frear quando a distância entre o carro e o muro é d, que a massa do carro é m, que a velocidade inicial é v0 e que o coeficiente de atrito estático é µ."                 
                + "Suponha também que o peso do carro está distribuído igualmente pelas quatro rodas, mesmo durante a frenagem.";

        // Adicionar texto ao JTextArea
        JTextArea textAreal = new JTextArea(text);
        textAreal.setPreferredSize(new Dimension(300, 150));
        textAreal.setLineWrap(true); // Quebra de linha automática
        textAreal.setWrapStyleWord(true); // Quebra de linha entre palavras

        // Adiciona barra de rolagem ao JTextArea
        JScrollPane scrollPane = new JScrollPane(textAreal, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Painel de Entrada e campos de texto
        inputPanel.add(new JLabel("Digite a distância (d) em metros:"));
        distanciaField = new JTextField();
        inputPanel.add(distanciaField);

        inputPanel.add(new JLabel("Digite a massa do veículo (m) em kg:"));
        massaField = new JTextField();
        inputPanel.add(massaField);

        inputPanel.add(new JLabel("Digite a velocidade inicial (v0) em m/s:"));
        velocidadeField = new JTextField();
        inputPanel.add(velocidadeField);

        inputPanel.add(new JLabel("Digite o coeficiente de atrito estático (µ) (Ex: 0.40)"));
        atritoEstaticoField = new JTextField();
        inputPanel.add(atritoEstaticoField);

        inputPanel.add(new JLabel("Digite o coeficiente de atrito cinético (µk) (Ex: 0.30)"));
        atritoCineticoField = new JTextField();
        inputPanel.add(atritoCineticoField);

        calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(this);
        inputPanel.add(calcularButton);

        resetarButton = new JButton("Reset");
        resetarButton.addActionListener(this);
        inputPanel.add(resetarButton);

        // Area que mostra os resultados dos cálculos
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane resultadoScrollPane = new JScrollPane(resultadoArea);
        add(resultadoScrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH); // Adiciona o painel de entrada ao JFrame
        add(scrollPane, BorderLayout.SOUTH); // Adiciona o painel de texto ao JFrame
    }

    @Override
    // Método para ação dos botões
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularButton) {
            calcular();
        } else if (e.getSource() == resetarButton) {
            resetar();
        }
    }

    // Método para executar os cálculos
    private void calcular() {
        try {
            double d = Double.parseDouble(distanciaField.getText());
            double m = Double.parseDouble(massaField.getText());
            double v0 = Double.parseDouble(velocidadeField.getText());
            double coefAtritoEstatico = Double.parseDouble(atritoEstaticoField.getText());
            double coefAtritoCinetico = Double.parseDouble(atritoCineticoField.getText());

            final double g = 9.81; // Aceleração da gravidade, valor fixo

            double minAtritoEstatico = (m * v0 * v0) / (2 * d); // Cálculo do atrito estático mínimo
            double maxAtritoEstatico = coefAtritoEstatico * m * g; // Cálculo do atrito estático máximo
            double atritoCinetico = coefAtritoCinetico * m * g; // Cálculo do atrito cinético
            double vFinalRaiz = v0 * v0 - (2 * atritoCinetico * d) / m; // Cálculo da velocidade final ao chocar com o muro
            double velFinalChoque = Math.sqrt(Math.max(0, vFinalRaiz)); // Velocidade final ao chocar com o muro
            double forcaAtritoCurva = (m * v0 * v0) / d; // Cálculo da força de atrito necessária para realizar a curva

            StringBuilder resultado = new StringBuilder(); // Para armazenar os resultados
            resultado.append("Resultados:\n");
            resultado.append(String.format("\nValor mínimo do atrito estático para o veículo frear: %.2f N%n", minAtritoEstatico));
            resultado.append(String.format("\nValor máximo do atrito estático: %.2f N%n", maxAtritoEstatico));
            resultado.append(String.format("\nVelocidade com que o veículo se choca contra o muro: %.2f m/s%n", velFinalChoque));
            resultado.append(String.format("\nMódulo da força necessária para realizar a curva: %.2f N%n%n", forcaAtritoCurva));
            if (forcaAtritoCurva < maxAtritoEstatico) {
                resultado.append(String.format("\nA força de atrito necessária para realizar a curva é menor que %.2f N, evitando o choque.%n", maxAtritoEstatico));
            } else {
                resultado.append(String.format("A força de atrito necessária para realizar a curva é maior que %.2f N, o que não evitaria o choque.%n%n", maxAtritoEstatico));
            }

            resultadoArea.setText(resultado.toString()); // Exibe os resultados na JTextArea
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método para resetar os campos de texto
    private void resetar() {
        distanciaField.setText("");
        massaField.setText("");
        velocidadeField.setText("");
        atritoEstaticoField.setText("");
        atritoCineticoField.setText("");
        resultadoArea.setText("");
    }

    // Método para iniciar o programa
    public static void main(String[] args) {
        // Cria e exibe o JFrame
        SwingUtilities.invokeLater(new Runnable() { 
            @Override
            // Método para execução do JFrame
            public void run() {
                new FrearDesviar().setVisible(true);
            }
        });
    }
}
