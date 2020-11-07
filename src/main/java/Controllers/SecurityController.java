package Controllers;

import Models.EnvironmentModel;
import Models.SecurityModel;
import Views.CustomConsole;
import Views.SecurityView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityController {
  private final SecurityModel secModel;
  private final SecurityView secView;

  public SecurityController(SecurityModel secModel, SecurityView secView) {
    this.secModel = secModel;
    this.secView = secView;
    secView.addAwayListener(new AwayModeListener());
  }

  public void initStartSpinner() {}

  private class AwayModeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (secModel.isAwayOn()) {
        secModel.setAwayOn(false);
        secView.changeAwayModeText("Turn on Away Mode");
        // logic to turn off away mode
      } else {
        if (EnvironmentModel.getHouse().hasObstruction()) {
          CustomConsole.print("A window is obstructed! Please correct this to enable Away Mode");
        } else {
          secModel.setAwayOn(true);
          secView.changeAwayModeText("Turn Away Mode off");
        }
        // logic to turn on away mode

      }
    }
  }
}
