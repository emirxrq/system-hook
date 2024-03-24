import java.util.Map.Entry;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.lang.annotation.Native;
import java.util.Timer;
import java.util.TimerTask;

import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;
public class Main implements MouseHook.MouseEventListener  {

    public static void main(String[] args) throws InterruptedException {
        MouseHook mouseHook = new MouseHook();
        KeyboardHook keyboardHook = new KeyboardHook();


        mouseHook.start();
        keyboardHook.start();

        mouseHook.setMouseEventListener(new Main());

        while (true) {
            if (keyboardHook.isKeyPressed(GlobalKeyEvent.VK_ESCAPE)) { 
                break;
            }

            for (int i = 0; i <= 0xff; i++) { 
                if (keyboardHook.isKeyPressed(i)) {
                    System.out.println("Klavyede tıklanan tuşun kodu " + i);
                }
            }

            for (int i = 0x0; i <= 1<<6; i++) { 
                if (mouseHook.isButtonPressed(i)) {
                    System.out.println("Farede tıklanan tuşun kodu " + i);
                    if (i == GlobalMouseEvent.BUTTON_X2)
                    {
                    	Robot robot;
        				try {
        					robot = new Robot();
        					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        		            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        		            System.out.println("Sol tıklanmış olması lazım");
        				} catch (AWTException e) {
        					e.printStackTrace();
        				}
                    }
                }
            }
            
            Thread.sleep(150); 
        }

        mouseHook.stop();
        keyboardHook.stop();
    }
    
    @Override
    public void onMouseReleased(int button)
    {
    	System.out.println("Fare tuşu bırakıldı: " + button);
    }
}
