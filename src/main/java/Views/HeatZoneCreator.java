package Views;

import Controllers.HeatingController;
import Models.Context;
import Models.HeatingModel;
import Models.Room;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HeatZoneCreator extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField zoneName;
    private JLabel zoneNameLabel;

    private HeatingController controller;
    private static HeatingController sController;
    private Room[] availableRooms; //The rooms available to be selected from (not already in a heating zone) Only display these as options

    public HeatZoneCreator(HeatingController controller) {

        this.controller = controller;
        sController = controller;

        availableRooms = controller.getAvailableRooms();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(400, 600);
    }

    private void onOK() {
        if (!zoneName.getText().equals("") /*&& some rooms are selected*/) {

            //call controller.createHeatingZone(zoneName.getText(), rooms);

            //refresh the SHH to display a list of all existing zones
            //can maybe use an observer (?), not necessary though
            //controller.getHeatingZones() should return all existing zones

            //code to test without UI, dont forget to remove
            Room[] testRooms = {Context.getHouse().getRooms().get(1), Context.getHouse().getRooms().get(2)};
            controller.createHeatingZone(zoneName.getText(), testRooms);

            System.out.println(controller.getHeatingZones().get(0).getName());

            dispose();
        } else
            CustomConsole.print("Make sure to name the heating zone you are creating, as well as select at least 1 room.");
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        HeatZoneCreator dialog = new HeatZoneCreator(sController);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
