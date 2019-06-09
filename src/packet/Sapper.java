package packet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sapper implements ActionListener {

    JFrame frame;

    public Sapper() {
        frame = new JFrame("Sapper");

        frame.getContentPane().setLayout(null);
        frame.setSize(515, 539);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //создание поля
        MinedField minedField = new MinedField(10, frame, this);
        minedField.getOpenField();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Sapper::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell cell = (Cell) e.getSource();
        cell.openCell();
        cell.setText(cell.getView());
        if (cell.isExploded){
            JOptionPane.showMessageDialog(frame, "You lose", "The end", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (Cell.minedField.isWin()){
            JOptionPane.showMessageDialog(frame, "You WIN", "The end", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
