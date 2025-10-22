package fr.bebedlastreat.slkart.panel;

import fr.bebedlastreat.slkart.main.KartGame;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class KartPanel extends JPanel {

    private final KartGame kartGame;
    private final JFrame window;
    private int width;
    private int height;

    public KartPanel(KartGame kartGame, JFrame window) {
        super();

        this.kartGame = kartGame;
        this.window = window;

        this.width = window.getPreferredSize().width;
        this.height = window.getPreferredSize().height - kartGame.getBounds().y;

        this.setPreferredSize(window.getPreferredSize());
        this.setMinimumSize(window.getMinimumSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = window.getWidth();
        height = window.getHeight() - kartGame.getBounds().y;
    }
}
