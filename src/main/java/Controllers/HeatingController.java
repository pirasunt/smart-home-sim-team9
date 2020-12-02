package Controllers;

import Models.EnvironmentModel;
import Views.HeatZoneCreator;
import Views.HeatingModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeatingController {

    private EnvironmentModel env;
    private HeatingModule heatingView;

    public HeatingController(EnvironmentModel m, HeatingModule v) {
        this.env = m;
        this.heatingView = v;

        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
    }

    private class HeatingZoneCreationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            HeatZoneCreator heatZoneDialog = new HeatZoneCreator();

        }
    }

}
