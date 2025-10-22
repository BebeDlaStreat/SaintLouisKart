package fr.bebedlastreat.slkart.main;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Getter
@Setter
public class KeyHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_Z: {
                upPressed = true;
                break;
            }
            case KeyEvent.VK_S: {
                downPressed = true;
                break;
            }
            case KeyEvent.VK_Q: {
                leftPressed = true;
                break;
            }
            case KeyEvent.VK_D: {
                rightPressed = true;
                break;
            }
            case KeyEvent.VK_SPACE: {
                spacePressed = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_Z: {
                upPressed = false;
                break;
            }
            case KeyEvent.VK_S: {
                downPressed = false;
                break;
            }
            case KeyEvent.VK_Q: {
                leftPressed = false;
                break;
            }
            case KeyEvent.VK_D: {
                rightPressed = false;
                break;
            }
            case KeyEvent.VK_SPACE: {
                spacePressed = false;
                break;
            }
        }
    }
}
