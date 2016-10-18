package cave.fighter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

public class BitKeys implements KeyListener {

    private BitSet keyBits;
    private BitSet moveBits;
    private boolean moveKeysPressed;

    public BitKeys(){
    	keyBits = new BitSet(256);
    	moveBits = new BitSet(256);
    	moveKeysPressed = false;
    	moveBits.set('A');
    	moveBits.set('W');
    	moveBits.set('S');
    	moveBits.set('D');
    }
    
    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
        moveKeysPressed = keyBits.intersects(moveBits);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
        moveKeysPressed = keyBits.intersects(moveBits);
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
    
    public boolean areMoveKeysPressed(){
    	return moveKeysPressed;
    }
}