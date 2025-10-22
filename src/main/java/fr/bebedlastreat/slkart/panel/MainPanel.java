package fr.bebedlastreat.slkart.panel;

import fr.bebedlastreat.slkart.content.KartImage;
import fr.bebedlastreat.slkart.main.KartGame;
import fr.bebedlastreat.slkart.main.StyledButtonUI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainPanel extends KartPanel {

    private List<Runnable> updatable = new ArrayList<>();

    public MainPanel(KartGame kartGame, JFrame window) {
        super(kartGame, window);

        this.setPreferredSize(window.getPreferredSize());
        this.setMinimumSize(window.getMinimumSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);

        addButton(0, "Jouer", e -> {
            kartGame.getSound().stopAll();
            window.remove(this);
            kartGame.play();
        });
        addButton(1, "Pramètres", e -> {
            System.out.println(e.getActionCommand());
        });
        addButton(2, "Crédits", e -> {
            window.remove(this);

            CharacterPanel panel = new CharacterPanel(kartGame, window);
            window.add(panel);
            window.pack();

            //JOptionPane.showMessageDialog(window, kartGame.getCredits(), "Crédits", JOptionPane.INFORMATION_MESSAGE);
        });
        addButton(3, "Quitter", e -> {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(getKartGame().getKartImage().getImage(KartImage.MAIN_BG), 0, 0, getWidth(), getHeight(), null);
        drawLogo(g);

        for (Runnable runnable : updatable) {
            runnable.run();
        }
        repaint();
    }

    private void drawLogo(Graphics g) {
        int size = Math.min(getWidth()/2, getHeight()/4);
        g.drawImage(getKartGame().getKartImage().getImage(KartImage.LOGO_ROUND),
                getWidth()/2 - size/2, getHeight()/24, size, size, null);
    }

    private void addButton(int index, String text, ActionListener action) {
        JButton button = new JButton(text);
        if (action != null) {
            button.addActionListener(action);
        }
        button.setFont(new Font("Calibri", Font.PLAIN, 14));
        button.setBackground(new Color(0x2dce98));
        button.setForeground(Color.white);
        button.setUI(new StyledButtonUI());
        add(button);

        updatable.add(() -> {
            int y = getHeight()/3 + getHeight()/48 + (getHeight()/6) * index;
            int sizeX = getWidth()/4;
            int sizeY = getHeight()/8;
            button.setBounds(getWidth()/2 - sizeX/2, y, sizeX, sizeY);
        });
    }
}
