package Controllers;

import Models.EnvironmentModel;
import Models.HeatingModel;
import Models.HeatingZone;
import Views.HeatZoneCreator;
import Views.HeatingModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class HeatingController {

    private HeatingModel heatingModel;
    private HeatingModule heatingView;

    public HeatingController(HeatingModel m, HeatingModule v) {
        this.heatingModel = m;
        this.heatingView = v;

        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
    }

    public void createZoneList() {
        HeatingZone[] zones = new HeatingZone[heatingModel.getHeatingZones().size()];
        zones = heatingModel.getHeatingZones().toArray(zones);

        for (HeatingZone zone : zones) {
            heatingView.getList().add(new JLabel(zone.getName()));
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
