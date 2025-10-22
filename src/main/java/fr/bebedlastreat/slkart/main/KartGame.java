package fr.bebedlastreat.slkart.main;

import fr.bebedlastreat.slkart.content.KartImage;
import fr.bebedlastreat.slkart.content.Sound;
import fr.bebedlastreat.slkart.entity.Location;
import fr.bebedlastreat.slkart.kart.*;
import fr.bebedlastreat.slkart.map.Circuit;
import fr.bebedlastreat.slkart.map.Game;
import fr.bebedlastreat.slkart.panel.GamePanel;
import fr.bebedlastreat.slkart.panel.MainPanel;
import lombok.Data;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Data
public class KartGame {

    @Getter
    private static KartGame instance;

    private JFrame window;
    private Sound sound;
    private String credits;
    private KartImage kartImage;
    private Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    private List<Kart> karts;

    public static void main(String[] args) {
        KartGame kartGame = new KartGame();
        kartGame.init();
        //kartGame.mainMenu();
        kartGame.play();

    }


    public KartGame() {
        instance = this;
    }

    public void init() {
        sound = new Sound();
        kartImage = new KartImage();
        karts = Arrays.asList(new Mp2iKart(), new Mpsi1Kart(), new Mpsi4Kart(), new Pcsi1Kart(), new Pcsi4Kart(), new Bio1Kart(), new Bio2Kart(), new EcgKart());
        karts.forEach(Kart::load);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/credits.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (credits == null) {
                    credits = line;
                } else {
                    credits += "\n" + line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setPreferredSize(screenSize);
        window.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2));
        //Application.getApplication().setDockIconImage(kartImage.getImage(KartImage.LOGO));

        window.setIconImage(kartImage.getImage(KartImage.LOGO));
        window.setTitle("Saint-Louis Kart");
        window.setLocationRelativeTo(null);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void mainMenu() {

        MainPanel panel = new MainPanel(this, window);
        window.add(panel);

        window.pack();
        window.setVisible(true);

        sound.changeVolume(Sound.MENU_MUSIC, -18);
        sound.play(Sound.MENU_MUSIC);
        sound.loop(Sound.MENU_MUSIC);

        //panel.startGameThread();
    }

    public void play() {

        GamePanel panel = new GamePanel(this, window, new Game(new Circuit("/maps/circuit_3.png", "/maps/circuit_3_tiles.png", 5, 300,
                new Location[]{new Location(919, 661, -Math.PI/2)})));
        window.add(panel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // when a new panel is added to a JFrame it is not focused
        panel.requestFocusInWindow();
        panel.startGameThread();
    }
}
