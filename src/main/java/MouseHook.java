import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;

import java.util.HashMap;
import java.util.Map;

public class MouseHook {

    private GlobalMouseHook mouseHook;
    private boolean isRunning = false;
    private Map<Integer, Boolean> buttonStates = new HashMap<>();
    private MouseEventListener listener;

    public MouseHook() {
        for (int i = 0x0; i <= 1<<6; i++) {
            buttonStates.put(i, false);
        }
    }

    public void start() {
        if (!isRunning) {
            mouseHook = new GlobalMouseHook();
            mouseHook.addMouseListener(new GlobalMouseAdapter() {
                @Override
                public void mousePressed(GlobalMouseEvent event) {
                    int button = event.getButton();
                    if (buttonStates.containsKey(button)) {
                        buttonStates.put(button, true);
                    }
                }

                @Override
                public void mouseReleased(GlobalMouseEvent event) {
                    int button = event.getButton();
                    if (buttonStates.containsKey(button)) {
                        buttonStates.put(button, false);
                        if (listener != null)
                        {
                        	listener.onMouseReleased(button);
                        }
                    }
                }
            });
            isRunning = true;
            System.out.println("Mouse hook başladı.");
        }
    }

    public void stop() {
        if (isRunning) {
            mouseHook.shutdownHook();
            isRunning = false;
            System.out.println("Mouse hook durdu.");
        }
    }

    public boolean isButtonPressed(int button) {
        if (buttonStates.containsKey(button)) {
            return buttonStates.get(button);
        }
        return false;
    }
    
    public void setMouseEventListener(MouseEventListener listener)
    {
    	this.listener = listener;
    }
    
    public interface MouseEventListener {
    	void onMouseReleased(int button);
    }
}
