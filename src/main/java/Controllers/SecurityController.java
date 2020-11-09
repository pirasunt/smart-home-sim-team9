package Controllers;

import Models.EnvironmentModel;
import Models.Room;
import Models.SecurityModel;
import Views.CustomConsole;
import Views.SecurityView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** The type Security controller. */
public class SecurityController implements Observer {
  private final SecurityModel secModel;
  private final SecurityView secView;

  /**
   * Instantiates a new Security controller.
   *
   * @param secModel the sec model
   * @param secView the sec view
   */
  public SecurityController(SecurityModel secModel, SecurityView secView) {
    this.secModel = secModel;
    this.secView = secView;
    secView.addAwayListener(new AwayModeListener());
    EnvironmentModel.subscribe(this);
  }

  @Override
  public void update(int oldRoomID, int newRoomID) {
    if (SecurityModel.isAwayOn()) {
      secModel.notifyAuthorities();
    }
  }

  private class AwayModeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      if (EnvironmentModel.getSimulationRunning() == false) {
        CustomConsole.print("The simulation must be running in order to change the Away Status...");
      } else if (SecurityModel.isAwayOn() && EnvironmentModel.getSimulationRunning() == true) {
        secModel.setAwayOn(false);
        secView.changeAwayModeText("Turn on Away Mode");
        secView.toggleSpinners(true);
        // logic to turn off away mode
      } else {
        boolean exceptionFound = false;
        if (EnvironmentModel.getSimulationRunning() == false) {
          CustomConsole.print(
              "Simulation is not running, Away Mode will not be enabled. Please start the simulation");
          exceptionFound = true;
        }
        if (EnvironmentModel.getHouse().hasObstruction()) {
          CustomConsole.print("A window is obstructed! Please correct this to enable Away Mode");
          exceptionFound = true;
        }
        if (!EnvironmentModel.houseIsEmpty()) {
          CustomConsole.print(
              "Someone is in the house, please remove them before enabling Away Mode.");
          exceptionFound = true;
        }
        if (!exceptionFound) {
          secModel.setAwayOn(true);
          secView.toggleSpinners(false);
          secView.changeAwayModeText("Turn Away Mode off");
        }
        // logic to turn on away mode

      }
    }
  }
}
