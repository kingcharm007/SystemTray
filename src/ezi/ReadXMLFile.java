/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezi;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Chandan
 */
public class ReadXMLFile {
    
    public PopupMenu getMenus(){
    PopupMenu trayPopupMenu = new PopupMenu();
    try {
//        File fXmlFile =  new File("C:\\Users\\Chandan\\Documents\\NetBeansProjects\\Ezi\\src\\resources\\Container.xml");;
        File fXmlFile =  new File("resources\\Container.xml");;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        //credential data 
         HashMap<String, HashMap<String, String>> finalArray = new HashMap<String, HashMap<String, String>>();
         NodeList ndList = doc.getElementsByTagName("credential");
         for (int temp = 0; temp < ndList.getLength(); temp++) {
              Node nNode = ndList.item(temp);
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (!finalArray.containsKey(eElement.getAttribute("name"))) {
                        finalArray.put(eElement.getAttribute("name"), new HashMap<String, String>());
                        finalArray.get(eElement.getAttribute("name")).put("username", eElement.getElementsByTagName("username")
                                    .item(temp).getTextContent());
                        finalArray.get(eElement.getAttribute("name")).put("password", eElement.getElementsByTagName("password")
                                    .item(temp).getTextContent());
                      }
               }
         }
        //Menu data
        NodeList nList = doc.getElementsByTagName("container");
        //adding nested menu elements from xml
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Menu menu=new Menu(eElement.getAttribute("name"));
                 for (int tmp = 0; tmp < eElement.getElementsByTagName("subcontainer").getLength(); tmp++) {
                    MenuItem action = new MenuItem(eElement.getElementsByTagName("subcontainer").item(tmp).getTextContent());
                    Element category = (Element)eElement.getElementsByTagName("subcontainer").item(tmp);
                    
                    action.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Finding oprating system to open browser
                            String os = System.getProperty("os.name").toLowerCase();
                            Runtime rt = Runtime.getRuntime();
                            String credential = category.getAttribute("credential");
                            String url = category.getAttribute("url");
                            url +="?username="+finalArray.get(credential).get("username")+"&password="+finalArray.get(credential).get("password");
//                            String url = category.getAttribute("url");
                                                       
                            if(os.indexOf("win") >= 0){
                                try {             
                                    rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
                                } catch (IOException ex) {
                                    Logger.getLogger(ReadXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else if(os.indexOf("mac") >= 0){
                                try {
                                    rt.exec("open " + url);
                                } catch (IOException ex) {
                                    Logger.getLogger(ReadXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else if(os.indexOf("nix") >=0 || os.indexOf("nux") >=0){
                                String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror",
                                     "netscape", "opera", "links", "lynx" };

                                StringBuffer cmd = new StringBuffer();
                                for (int i = 0; i < browsers.length; i++)
                                    if(i == 0)
                                        cmd.append(String.format("%s \"%s\"", browsers[i], url));
                                    else
                                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url)); 
                                    // If the first didn't work, try the next browser and so on
                                try {
                                    rt.exec(new String[] { "sh", "-c", cmd.toString() });    
                                } catch (IOException ex) {
                                    Logger.getLogger(ReadXMLFile.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });
                    menu.add(action);
                 }
                 trayPopupMenu.add(menu);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }  
    
        MenuItem close = new MenuItem("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);             
            }
        });
        trayPopupMenu.add(close);
      return trayPopupMenu;
    }
  
    
    public static void main(String argv[]) {
    try {
        File fXmlFile = new File("C:\\Users\\Chandan\\Documents\\NetBeansProjects\\Ezi\\src\\resources\\Container.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        //credential test
         HashMap<String, HashMap<String, String>> finalArray = new HashMap<String, HashMap<String, String>>();
         NodeList ndList = doc.getElementsByTagName("credential");
         for (int temp = 0; temp < ndList.getLength(); temp++) {
              Node nNode = ndList.item(temp);
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Container name : " + eElement.getAttribute("name"));
                    if (!finalArray.containsKey(eElement.getAttribute("name"))) {
                        finalArray.put(eElement.getAttribute("name"), new HashMap<String, String>());
                        finalArray.get(eElement.getAttribute("name")).put("username", eElement.getElementsByTagName("username")
                                    .item(temp).getTextContent());
                        finalArray.get(eElement.getAttribute("name")).put("password", eElement.getElementsByTagName("username")
                                    .item(temp).getTextContent());
                      }
//                    System.out.println("user Credential : " + eElement.getElementsByTagName("username")
//                                    .item(temp).getTextContent());
//                    System.out.println("user Credential : " + eElement.getElementsByTagName("password")
//                                    .item(temp).getTextContent());
//                    System.out.println(finalArray.get("user1").get("username"));

               }
         }
        //reading top element
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("container");
        System.out.println("----------------------------");
        //reading container inner content
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Container name : " + eElement.getAttribute("name"));
                //Reading Container
                for (int tmp = 0; tmp < eElement.getElementsByTagName("subcontainer").getLength(); tmp++) {
                  System.out.println("First Name : " + eElement.getElementsByTagName("subcontainer")
                                    .item(tmp).getTextContent());
                  Element category = (Element)eElement.getElementsByTagName("subcontainer")
                                    .item(tmp);
                  System.out.println("C ---- "+category.getAttribute("url"));
                  String crd= category.getAttribute("credential");
                  System.out.println(crd);
                  System.out.println(finalArray.get(category.getAttribute("credential")).get("username"));
//                  System.out.println(finalArray.get(crd).get("password"));
                } 
            }
        }
          
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

}
//reference for getting nested traverse in xml
/*DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(acpFile);
        Element docEle = doc.getDocumentElement();
        NodeList series = docEle.getElementsByTagName("dod:recordSeries");

        if(series != null && series.getLength()>0) 
        {
            for(int i=0; i<series.getLength(); i++) 
            {
                Element serie = (Element)series.item(i);
                System.out.println("S -- "+serie.getAttribute("view:childName"));
                NodeList categories = serie.getElementsByTagName("dod:recordCategory");

                for(int j=0; j<categories.getLength(); j++)
                {
                    Element category = (Element)categories.item(j);
                    System.out.println("C ---- "+category.getAttribute("view:childName"));
                    NodeList recordFolders = category.getElementsByTagName("rma:recordFolder");

                    for(int k=0; k<recordFolders.getLength(); k++)
                    {
                        Element folder = (Element)recordFolders.item(k);
                        System.out.println("F ------ "+folder.getAttribute("view:childName"));
                    }
                }
            }
        }
 */

//open browse references

/*
You can also use the Runtime to create a cross platform solution:

import java.awt.Desktop;
import java.net.URI;

public class App {

    public static void main(String[] args) throws Exception {
        String url = "http://stackoverflow.com";

        if (Desktop.isDesktopSupported()) {
            // Windows
            Desktop.getDesktop().browse(new URI(url));
        } else {
            // Ubuntu
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("/usr/bin/firefox -new-window " + url);
        }
    }
}

Its very simple just write below code:

String s = "http://www.google.com";
Desktop desktop = Desktop.getDesktop();
desktop.browse(URI.create(s));

or if you don't want to load URL then just write your browser name into string values like,

String s = "chrome";
Desktop desktop = Desktop.getDesktop();
desktop.browse(URI.create(s));

it will open browser automatically with empty URL after executing a program
*/

