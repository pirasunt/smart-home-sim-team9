package Controllers;

import Models.SecurityModel;
import Views.SecurityView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SecurityController {
    private SecurityModel secModel;
    private SecurityView secView;

    public SecurityController(SecurityModel secModel, SecurityView secView) {
        this.secModel = secModel;
        this.secView = secView;
    }

    private class TempSpinnerListener implements ChangeListener {
        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
//            theModel.setTemperature(
//                    theView
//                            .getTemperatureFromSpinner()); // Any change on Temp Spinner will update Environment
//            // attribute
        }
    }

}
