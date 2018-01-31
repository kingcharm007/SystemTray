/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author Chandan
 */
public class Ezi {
    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws Exception{
        try{
            System.out.println("setting look and feel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.println("Unable to set LookAndFeel");
        }
        if (SystemTray.isSupported()) {
            // Yes My System Support System Tray
            System.out.println("System Try Supported");
            // Create SystemTray and TrayIcon (TrayIcon : It is icon that
            // display in SystemTray)
            
            final SystemTray systemTray = SystemTray.getSystemTray();
            final TrayIcon trayIcon = new TrayIcon(getImage("resources/omt.gif"),
                    "omt is running",new ReadXMLFile().getMenus());

            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // System.out.println("omt click");
                    // This will display small popup message from System Tray
                    trayIcon.displayMessage("Omt TrayIcon Demo",
                            "This is an info message from TrayIcon omt demo",
                            TrayIcon.MessageType.INFO);
                }
            };
            trayIcon.setImageAutoSize(true);// Autosize icon base on space
                                            // available on
                                            // tray
           trayIcon.addMouseListener(mouseAdapter);
 
            try {
                systemTray.add(trayIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
 
        }
 
    }
 
    public static Image getImage(String path) {
        Image image = Toolkit.getDefaultToolkit().getImage(path);
        return image;
    }
    
}
