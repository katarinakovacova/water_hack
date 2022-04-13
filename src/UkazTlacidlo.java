import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class UkazTlacidlo implements ActionListener {
    JLabel jlab;
    int waterAmount;
    UkazTlacidlo () {
        JFrame jfrm = new JFrame("Moj pitny rezim");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(300, 300);

        jfrm.getContentPane().setBackground(new Color(179, 205, 230));
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Path fileName = Path.of("voda.dat");
        try {
            String content  = "";
            content = Files.readString(fileName, StandardCharsets.UTF_8);
            waterAmount = Integer.parseInt(content.strip());
        } catch (IOException e) {
            waterAmount = 0;
        }

        JButton jbtnUp = new JButton("Pridaj 100 ml");
        JButton jbtnDown = new JButton("Odober 100 ml");

        jbtnUp.setActionCommand("waterUp");
        jbtnDown.setActionCommand("waterDown");

        jbtnUp.addActionListener(this);
        jbtnDown.addActionListener(this);

        jfrm.add(jbtnUp);
        jfrm.add(jbtnDown);

        jlab = new JLabel("<html><center>Stlac tlacidlo pre pridanie vody<br><br><h1>" + String.valueOf(waterAmount) + " ml</h1></center></html>", SwingConstants.CENTER);
        jfrm.add(jlab);
        jfrm.setVisible(true);
    }

    private void setVisible(boolean b) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("waterUp")) {
            waterAmount += 100;
            jlab.setText("<html><center>Bolo pridanych 100 ml<br><br><h1>" + String.valueOf(waterAmount) + " ml</h1></center></html>");
        } else {
            waterAmount = Math.max(0, waterAmount - 100);
            jlab.setText("<html><center>Bolo odobranych 100 ml<br><br><h1>" + String.valueOf(waterAmount) + " ml</h1></center></html>");
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("voda.dat", "UTF-8");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        writer.println(String.valueOf(waterAmount));
        writer.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UkazTlacidlo();
            }
        });
    }
}
