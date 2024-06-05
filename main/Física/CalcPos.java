package Física;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcPos implements ActionListener {

    JFrame frame;
    JButton addBtn, resetBtn;
    JTextField initPosXInput, initPosYInput, initPosZInput, deslPosXInput,
            deslPosYInput, deslPosZInput, desl, distTotalPer, finalPosText;
    JTextField[] vetoresText = new JTextField[20];
    JPanel panel;

    int cont = 1;
    double[] initialPos = new double[3];
    double[] finalPos = new double[3];
    double distanciaTotalPercorrida;

    CalcPos() {
        frame = new JFrame("Calcular Posição Final");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(null);

        JLabel initPosText = createLabel(
                "Posição inicial:", 40, 25, 100, 30);
        JLabel initPosXText = createLabel(

                "x:", 150, 25, 20, 30);
        initPosXInput = createTextField(
                "0", 170, 25, 70, 30, true, true);

        JLabel initPosYText = createLabel("y:", 250, 25, 20, 30);
        initPosYInput = createTextField(
                "0", 270, 25, 70, 30, true, true);

        JLabel initPosZText = createLabel(
                "z:", 350, 25, 20, 30);
        initPosZInput = createTextField(
                "0", 370, 25, 70, 30, true, true);

        JLabel deslPosText = createLabel("Deslocamento:", 40, 75, 100, 30);
        JLabel deslPosXText = createLabel("x:", 150, 75, 20, 30);
        deslPosXInput = createTextField("0", 170, 75, 70, 30, true, true);

        JLabel deslPosYText = createLabel("y:", 250, 75, 20, 30);
        deslPosYInput = createTextField("0", 270, 75, 70, 30, true, true);

        JLabel deslPosZText = createLabel("z:", 350, 75, 20, 30);
        deslPosZInput = createTextField("0", 370, 75, 70, 30, true, true);

        addBtn = new JButton("+");
        addBtn.setBounds(460, 75, 50, 30);
        addBtn.addActionListener(this);
        addBtn.setFocusable(false);

        panel = new JPanel();
        panel.setBounds(40, 120, 470, 200);
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        vetoresText[0] = new JTextField("P0 :  (0, 0, 0)");
        vetoresText[0].setEditable(false);
        vetoresText[0].setFocusable(false);
        panel.add(vetoresText[0]);

        for (int i = 1; i < vetoresText.length; i++) {
            vetoresText[i] = new JTextField("d" + String.valueOf(i) + " :  ");
            vetoresText[i].setEditable(false);
            vetoresText[i].setFocusable(false);
            panel.add(vetoresText[i]);
        }

        resetBtn = new JButton("Reset");
        resetBtn.setBounds(430, 340, 80, 30);
        resetBtn.addActionListener(this);
        resetBtn.setFocusable(false);

        finalPosText = createTextField("Posição Final: (0, 0, 0)", 40, 330, 300, 30, false, false);
        distTotalPer = createTextField("Distância total percorrida: 0", 40, 360, 300, 30, false, false);
        desl = createTextField("Deslocamento: 0", 40, 390, 300, 30, false, false);

        frame.add(initPosText);
        frame.add(initPosXText);
        frame.add(initPosXInput);
        frame.add(initPosYText);
        frame.add(initPosYInput);
        frame.add(initPosZText);
        frame.add(initPosZInput);

        frame.add(deslPosText);
        frame.add(deslPosXText);
        frame.add(deslPosXInput);
        frame.add(deslPosYText);
        frame.add(deslPosYInput);
        frame.add(deslPosZText);
        frame.add(deslPosZInput);
        frame.add(addBtn);
        frame.add(panel);
        frame.add(resetBtn);
        frame.add(finalPosText);
        frame.add(distTotalPer);
        frame.add(desl);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CalcPos calcPos = new CalcPos();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            initialPos[0] = Double.parseDouble(initPosXInput.getText());
            initialPos[1] = Double.parseDouble(initPosYInput.getText());
            initialPos[2] = Double.parseDouble(initPosZInput.getText());

            String initPosString = createVetorString("P0: ", initialPos[0], initialPos[1], initialPos[2]);
            vetoresText[0].setText(initPosString);

            if (cont == 1) {
                finalPos[0] = initialPos[0];
                finalPos[1] = initialPos[1];
                finalPos[2] = initialPos[2];
                String finalPosString = createVetorString("Posição Final: ", finalPos[0], finalPos[1], finalPos[2]);
                finalPosText.setText(finalPosString);
            }

            initPosXInput.setEditable(false);
            initPosXInput.setFocusable(false);
            initPosYInput.setEditable(false);
            initPosYInput.setFocusable(false);
            initPosZInput.setEditable(false);
            initPosZInput.setFocusable(false);

            double dx = Double.parseDouble(deslPosXInput.getText());
            double dy = Double.parseDouble(deslPosYInput.getText());
            double dz = Double.parseDouble(deslPosZInput.getText());

            if (dx != 0 || dy != 0 || dz != 0) {
                String deslVetorString = createVetorString("d" + String.valueOf(cont) + ": ", dx,
                        dy, dz);
                vetoresText[cont].setText(deslVetorString);

                calculateFinalPosition(dx, dy, dz);
                calculateDistanciaTotalPercorrida(dx, dy, dz);
                calculateDeslocamento();

                cont++;
            }

        }
        if (e.getSource() == resetBtn) {
            initialPos[0] = 0;
            initialPos[1] = 0;
            initialPos[2] = 0;
            finalPos[0] = 0;
            finalPos[1] = 0;
            finalPos[2] = 0;
            distanciaTotalPercorrida = 0;
            cont = 1;

            initPosXInput.setText(String.valueOf(0));
            initPosYInput.setText(String.valueOf(0));
            initPosZInput.setText(String.valueOf(0));
            deslPosXInput.setText(String.valueOf(0));
            deslPosYInput.setText(String.valueOf(0));
            deslPosZInput.setText(String.valueOf(0));

            String initPosString = createVetorString("P0: ", initialPos[0], initialPos[1], initialPos[2]);
            vetoresText[0].setText(initPosString);
            for (int i = 1; i < vetoresText.length; i++) {
                vetoresText[i].setText("d" + i + ": ");
            }

            initPosXInput.setEditable(true);
            initPosXInput.setFocusable(true);
            initPosYInput.setEditable(true);
            initPosYInput.setFocusable(true);
            initPosZInput.setEditable(true);
            initPosZInput.setFocusable(true);

            finalPosText.setText("Posição Final: (0, 0, 0)");
            distTotalPer.setText("Distância total percorrida: 0");
            desl.setText("Deslocamento: 0");
        }
    }

    private JTextField createTextField(String label, int x, int y, int w, int h, boolean edit, boolean focus) {
        JTextField textField = new JTextField(label);
        textField.setBounds(x, y, w, h);
        textField.setEditable(edit);
        textField.setFocusable(focus);

        return textField;
    }

    private JLabel createLabel(String label, int x, int y, int w, int h) {
        JLabel labelText = new JLabel(label);
        labelText.setBounds(x, y, w, h);

        return labelText;
    }

    private String createVetorString(String index, Double x, Double y, Double z) {
        String vector = index + "(" + String.valueOf(x) + ", " + String.valueOf(y) + ", " + String.valueOf(z)
                + ")";
        return vector;
    }

    private void calculateFinalPosition(Double dx, Double dy, Double dz) {
        finalPos[0] += dx;
        finalPos[1] += dy;
        finalPos[2] += dz;
        String finalPosString = createVetorString("Posição Final: ", finalPos[0], finalPos[1], finalPos[2]);
        finalPosText.setText(finalPosString);
    }

    private void calculateDistanciaTotalPercorrida(Double dx, Double dy, Double dz) {

        double temp = Math.sqrt(dx * dx + dy * dy + dz * dz);
        distanciaTotalPercorrida += temp;

        distTotalPer.setText("Distância total percorrida: " + distanciaTotalPercorrida);
    }

    private void calculateDeslocamento() {
        double x2 = Math.pow((initialPos[0] - finalPos[0]), 2);
        double y2 = Math.pow((initialPos[1] - finalPos[1]), 2);
        double z2 = Math.pow((initialPos[2] - finalPos[2]), 2);
        double temp = Math.sqrt(x2 + y2 + z2);
        desl.setText("Deslocamento: " + temp);
    }
}