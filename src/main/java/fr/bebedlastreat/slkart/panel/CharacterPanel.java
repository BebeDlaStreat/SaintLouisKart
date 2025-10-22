package fr.bebedlastreat.slkart.panel;

import fr.bebedlastreat.slkart.content.KartImage;
import fr.bebedlastreat.slkart.kart.Kart;
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
public class CharacterPanel extends KartPanel {

    private List<Runnable> updatable = new ArrayList<>();

    public CharacterPanel(KartGame kartGame, JFrame window) {
        super(kartGame, window);

        this.setPreferredSize(window.getPreferredSize());
        this.setMinimumSize(window.getMinimumSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = getWidth()/getKartGame().getKarts().size();
        int x = 0;
        for (Kart kart : getKartGame().getKarts()) {
            g.setColor(Color.white);
            g.drawImage(kart.getIcon(), x, 0, size, size, null);
            Font font = new Font("TimesRoman", Font.PLAIN, Math.round((float) size/kart.getName().length()));
            drawText(kart.getName(), new Rectangle(x, size, size, size/2), font, g);

            font = font.deriveFont((float) size /"Vitesse".length());
            drawText("Vitesse", new Rectangle(x, (int) (size*1.5f), size, size/2), font, g);
            font = font.deriveFont((float) size /"Accélération".length());
            drawText("Accélération", new Rectangle(x, (int) (size*2.25f), size, size/2), font, g);
            font = font.deriveFont((float) size /"Maniabilité".length());
            drawText("Maniabilité", new Rectangle(x, (int) (size*3f), size, size/2), font, g);
            g.fillRect(x + Math.round(size/20f), (int) (size * 2f), Math.round(size/100f * kart.getSpeed() * 0.9f), size/10);
            g.fillRect(x + Math.round(size/20f), (int) (size * 2.75f), Math.round(size/100f * kart.getAcceleration() * 0.9f), size/10);
            g.fillRect(x + Math.round(size/20f), (int) (size * 3.5f), Math.round(size/100f * kart.getRotationSpeed() * 0.9f), size/10);
            x += size;
        }
    }

    private void drawText(String text, Rectangle rect, Font font, Graphics g) {
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int textX = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int textY = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, textX, textY);
    }
}
