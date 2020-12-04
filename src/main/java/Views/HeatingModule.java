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
import java.awt.event.ActionListener;

public class HeatingModule extends JPanel {
    HeatingController hc;
    Room[] roomsInHouse = new Room[Context.getHouse().getRooms().size()];

    private JButton createNewHeatingZoneButton;
    private JPanel panel;
    private JPanel mainHeatWrap;
    private JPanel heatingZonePanel;
    private JPanel awayModeTempWrap;
    private JSpinner awayTempSpinner;
    private JSpinner dangerTempSpinner;
    private JPanel seasonPanel;
    private JPanel summerPanel;
    private JPanel winterPanel;
    private JLabel summerStartLabel;
    private JLabel winterStartLabel;
    private JButton summerChangeBtn;
    private JButton winterChangeBtn;
    private JList<String> heatingZones;

    public HeatingModule() {
        HeatingModel model = new HeatingModel();
        hc = new HeatingController(model, this);
        roomsInHouse = Context.getHouse().getRooms().toArray(roomsInHouse);

        DefaultListModel<String> lm = new DefaultListModel<>();
        heatingZones = new JList<String>(lm);
        awayTempSpinner.setModel(model.getAwayTempSpinner());
        dangerTempSpinner.setModel(model.getDangerTempSpinner());

    }

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

    public JList<String> getList() {
        return this.heatingZones;
    }


}
