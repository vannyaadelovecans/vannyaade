import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class UI {

    GameManager gm;
    JLabel background;
    JFrame window;
    public JTextArea messageText;

    public UI(GameManager gm){
        this.gm = gm;

        createMainField();

        window.setVisible(true);
    }

    public void createMainField(){
            window = new JFrame();
            window.setSize(1920, 1200);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            background = new JLabel(new ImageIcon("D:\\Vannya\\coba coba\\pokemongo\\background main.png"));
            background.setLayout(new BorderLayout());
        
            messageText = new JTextArea(" START");
            messageText.setBackground(Color.WHITE);
            messageText.setForeground(Color.BLACK);
            messageText.setEditable(false);
            messageText.setLineWrap(true);
            messageText.setWrapStyleWord(true);
            messageText.setFont(new Font("Arial", Font.PLAIN, 26));
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
    
            background.add(messageText, gbc);
    
    }
}
