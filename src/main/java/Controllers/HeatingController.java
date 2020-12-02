package Controllers;

import Models.*;
import Views.HeatZoneCreator;
import Views.HeatingModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeatingController {

    private HeatingModel heatingModel;
    private HeatingModule heatingView;

    public HeatingController(HeatingModel m, HeatingModule v) {
        this.heatingModel = m;
        this.heatingView = v;

        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
    }

    public void createZoneList() {
        Room[] test = {Context.getHouse().getRooms().get(1), Context.getHouse().getRooms().get(2)};
        heatingModel.createHeatingZone(test, "test");

        if (heatingModel.getHeatingZones() == null) {
            return;
        }

        HeatingZone[] zones = new HeatingZone[heatingModel.getHeatingZones().size()];
        zones = heatingModel.getHeatingZones().toArray(zones);

        for (HeatingZone zone : zones) {
            heatingView.getList().add(new JLabel(zone.getName()));
            heatingView.getList().repaint();
        }
    }

    private class HeatingZoneCreationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            HeatZoneCreator heatZoneDialog = new HeatZoneCreator(new HeatingController(heatingModel, heatingView));
            heatZoneDialog.setLocationRelativeTo(null);
            heatZoneDialog.setVisible(true);
        }
    }

}
