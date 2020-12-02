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
    private JList<String> heatingZones;

    public HeatingModule() {
        HeatingModel model = new HeatingModel();
        hc = new HeatingController(model, this);
        roomsInHouse = Context.getHouse().getRooms().toArray(roomsInHouse);

        DefaultListModel<String> lm = new DefaultListModel<>();
        heatingZones = new JList<String>(lm);
    }

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

    public JList<String> getList() {
        return this.heatingZones;
    }

}
