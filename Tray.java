import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Tray{

//start of main method
public static void main(String []args){
    final TrayIcon trayIcon;

    if (SystemTray.isSupported()) {

    SystemTray tray = SystemTray.getSystemTray();
    Image image = Toolkit.getDefaultToolkit().getImage("test.png");

    MouseListener mouseListener = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
        System.out.println("Tray Icon - Mouse clicked!");                 
        }

        public void mouseEntered(MouseEvent e) {
        System.out.println("Tray Icon - Mouse entered!");                 
        }

        public void mouseExited(MouseEvent e) {
        System.out.println("Tray Icon - Mouse exited!");                 
        }

        public void mousePressed(MouseEvent e) {
        System.out.println("Tray Icon - Mouse pressed!");                 
        }

        public void mouseReleased(MouseEvent e) {
        System.out.println("Tray Icon - Mouse released!");                 
        }
    
    };

    ActionListener exitListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
    System.out.println("Exiting...");
    System.exit(0);
    }
    };

    PopupMenu popup = new PopupMenu();
    MenuItem defaultItem = new MenuItem("Exit");
    defaultItem.addActionListener(exitListener);
    popup.add(defaultItem);

    trayIcon = new TrayIcon(image, "Tray Demo", popup);

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            trayIcon.displayMessage("Action Event", 
                "An Action Event Has Been Performed!",
                TrayIcon.MessageType.INFO);
        }
    };

    trayIcon.setImageAutoSize(true);
    trayIcon.addActionListener(actionListener);
    trayIcon.addMouseListener(mouseListener);

    try {
    tray.add(trayIcon);
    } catch (AWTException e) {
    System.err.println("TrayIcon could not be added.");
    }

    } else {

    //  System Tray is not supported

    }
}//end of main

}//end of class

// This is a new change
