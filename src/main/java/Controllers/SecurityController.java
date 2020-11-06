package Controllers;

import Models.EnvironmentModel;
import Models.SecurityModel;
import Views.CustomConsole;
import Views.SecurityView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityController {
  private final SecurityModel secModel;
  private final SecurityView secView;

  public SecurityController(SecurityModel secModel, SecurityView secView) {
    this.secModel = secModel;
    this.secView = secView;
    secView.addAwayListener(new AwayModeListener());
    secView.addIntervalListener(new IntervalSpinnerListener());
  }

  private class AwayModeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (secModel.isAwayOn()) {
        secModel.setAwayOn(false);
        secView.changeAwayModeText("Turn on Away Mode");
        // logic to turn off away mode
      } else {
        if(EnvironmentModel.getHouse().hasObstruction()){
          CustomConsole.print("A window is obstructed! Please correct this to enable Away Mode");
        }
        else{
          secModel.setAwayOn(true);
          secView.changeAwayModeText("Turn Away Mode off");
        }
        // logic to turn on away mode

      }
    }
  }

  private class IntervalSpinnerListener implements ChangeListener {
    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
      System.out.println(secView.getSpinner());
      secModel.setAlertInterval(secView.getSpinner());
    }
  }
}
