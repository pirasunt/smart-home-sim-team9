package Main;

import com.thoughtworks.xstream.XStream;

import java.awt.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main{
    public static void main(String[] args) {

        XStream stream = new XStream();
        XStream.setupDefaultSecurity(stream);
        stream.allowTypes(new Class[]{House.class, Room.class, Wall.class, WindowWall.class,RoomWall.class, OutsideWall.class});
        House house = (House)stream.fromXML(new File("Main.House.xml"));

        java.awt.EventQueue.invokeLater(() -> {
            JScrollPane scrollPane = new JScrollPane(new HouseDrawer(house));
            scrollPane.getViewport().setPreferredSize(new Dimension(800, 800));
            JFrame frame = new JFrame("SOEN 343");
            frame.getContentPane().add(scrollPane);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    	});
    }
}