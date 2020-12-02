package Views;


import Controllers.HeatingController;
import Models.Context;
import Models.HeatingModel;
import Models.Room;

import javax.swing.*;
import java.awt.event.ActionListener;

public class HeatingModule extends JPanel {
    HeatingController hc;
    Room[] roomsInHouse = new Room[Context.getHouse().getRooms().size()];

    private JButton createNewHeatingZoneButton;
    private JPanel panel;
    private JLabel heatingZonesLabel;
    private JList heatingZones;

    public HeatingModule() {
        HeatingModel model = new HeatingModel();
        hc = new HeatingController(model, this);
        roomsInHouse = Context.getHouse().getRooms().toArray(roomsInHouse);
    }

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

    public JList getList() {
        return this.heatingZones;
    }

}
