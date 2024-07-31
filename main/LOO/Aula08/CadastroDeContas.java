package LOO.Aula08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CadastroDeContas extends JFrame {
    private JLabel label1, label2, label3, label4;
    private JButton btConsultar, btGravar, btLimpar;
    private JTextField tfNumeroConta, tfTitular, tfSaldo;
    private JRadioButton rbContaCorrente, rbPoupanca;
    private ButtonGroup buttonGroup;
    private Contas conta;

    public static void main(String[] args) {
        JFrame frame = new CadastroDeContas();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public CadastroDeContas() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setTitle("Cadastro de Contas");
        setBounds(250, 50, 800, 400);
        setBackground(new Color(150, 150, 150));
        label1 = new JLabel("Número da conta");
        label2 = new JLabel("Titular");
        label3 = new JLabel("Saldo");
        label4 = new JLabel("Tipo de conta: ");
        btConsultar = new JButton("Consultar");
        btGravar = new JButton("Gravar");
        btLimpar = new JButton("Limpar");
        tfNumeroConta = new JTextField();
        tfSaldo = new JTextField();
        tfTitular = new JTextField();
        label4 = new JLabel("Selecione o tipo de conta:");
        rbContaCorrente = new JRadioButton("Conta Corrente");
        rbPoupanca = new JRadioButton("Poupança");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rbContaCorrente);
        buttonGroup.add(rbPoupanca);
        setLayout(null);
        label1.setBounds(10, 15, 120, 20);
        label2.setBounds(10, 40, 60, 20);
        label3.setBounds(10, 65, 60, 20);
        btConsultar.setBounds(10, 150, 90, 30);
        btGravar.setBounds(105, 150, 75, 30);
        btLimpar.setBounds(185, 150, 75, 30);
        tfNumeroConta.setBounds(120, 15, 255, 20);
        tfSaldo.setBounds(120, 65, 255, 20);
        tfTitular.setBounds(120, 40, 255, 20);
        rbContaCorrente.setBounds(10, 100, 140, 30);
        label4.setBounds(10, 80, 180, 40);
        rbPoupanca.setBounds(10, 120, 140, 40);
        rbContaCorrente.setSelected(true);
        add(label1);
        add(label2);
        add(label3);
        add(label4);
        add(btConsultar);
        add(btGravar);
        add(btLimpar);
        add(tfNumeroConta);
        add(tfSaldo);
        add(tfTitular);
        add(rbContaCorrente);
        add(rbPoupanca);

        conta = new Contas();
    }

    public void definirEventos() {
        btLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tfNumeroConta.setText("");
                tfSaldo.setText("");
                tfTitular.setText("");
                rbContaCorrente.setSelected(true);
            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tfNumeroConta.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Número da conta não pode estar vazio");
                    tfNumeroConta.requestFocus();
                } else if (tfTitular.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Titular não pode estar vazio");
                    tfTitular.requestFocus();
                } else if (tfSaldo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Saldo não pode estar vazio");
                    tfSaldo.requestFocus();
                } else {
                    conta.NumeroConta = tfNumeroConta.getText().trim();
                    conta.Titular = tfTitular.getText().trim();
                    conta.Saldo = tfSaldo.getText().trim();
                    if (rbContaCorrente.isSelected()) {
                        conta.TipoConta = "Conta Corrente";
                    } else if (rbPoupanca.isSelected()) {
                        conta.TipoConta = "Poupança";
                    }
                    JOptionPane.showMessageDialog(null, conta.gravar("c:/temp"));
                }
            }
        });

        btConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conta.NumeroConta = JOptionPane.showInputDialog(null, "Digite o número da conta:");
                if (conta.NumeroConta != null && !conta.NumeroConta.trim().isEmpty()) {
                    conta = conta.ler("c:/temp");
                    if (conta != null) {
                        tfNumeroConta.setText(conta.NumeroConta);
                        tfTitular.setText(conta.Titular);
                        tfSaldo.setText(conta.Saldo);
                        if (conta.TipoConta.equals("Conta Corrente")) {
                            rbContaCorrente.setSelected(true);
                        } else if (conta.TipoConta.equals("Poupança")) {
                            rbPoupanca.setSelected(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Conta não encontrada");
                    }
                }
            }
        });
    }
}
