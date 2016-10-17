package cave.fighter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class BitKeys implements KeyListener {

    private BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
    public boolean isEmpty() {
        return keyBits.isEmpty();
    }
}