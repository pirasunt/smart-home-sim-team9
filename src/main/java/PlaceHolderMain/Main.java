package PlaceHolderMain;

import Custom.CustomXStream;
import Models.House;
import Views.HouseGraphic;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main{
    public static void main(String[] args) {
        CustomXStream stream = new CustomXStream();
        House house = (House)stream.fromXML(new File("House.xml"));

        java.awt.EventQueue.invokeLater(() -> {
            JScrollPane scrollPane = new JScrollPane(new HouseGraphic(house));
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