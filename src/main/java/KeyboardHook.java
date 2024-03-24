import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.util.HashMap;
import java.util.Map;

public class KeyboardHook {
    private GlobalKeyboardHook keyboardHook;
    private boolean isRunning = false;
    private Map<Integer, Boolean> keyStates = new HashMap<>();

    public KeyboardHook() {
        for (int i = 0x0; i <= 0xff; i++) {
            keyStates.put(i, false);
        }
    }

    public void start() {
        if (!isRunning) {
            keyboardHook = new GlobalKeyboardHook();
            keyboardHook.addKeyListener(new GlobalKeyListener() {
                @Override
                public void keyPressed(GlobalKeyEvent event) {
                    int keyCode = event.getVirtualKeyCode();
                    if (keyStates.containsKey(keyCode)) {
                        keyStates.put(keyCode, true);
                    }
                }

                @Override
                public void keyReleased(GlobalKeyEvent event) {
                    int keyCode = event.getVirtualKeyCode();
                    if (keyStates.containsKey(keyCode)) {
                        keyStates.put(keyCode, false);
                    }
                }
            });
            isRunning = true;
            System.out.println("Keyboard hook başladı.");
        }
    }

    public void stop() {
        if (isRunning) {
            keyboardHook.shutdownHook();
            isRunning = false;
            System.out.println("Keyboard hook durdu.");
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyStates.containsKey(keyCode)) {
            return keyStates.get(keyCode);
        }
        return false;
    }
}
