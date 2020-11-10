package Controllers;

import Models.Context;
import Models.EnvironmentModel;
import Models.Room;
import Models.SecurityModel;
import Observers.RoomChangeObserver;
import Views.CustomConsole;
import Views.SecurityView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** The type Security controller. */
public class SecurityController implements RoomChangeObserver {
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
    this.createLightsSection();
  }

  private void createLightsSection() {

    ArrayList allRooms = Context.getHouse().getRooms();

    ArrayList<JLabel> lightLabels = new ArrayList<>();
    ArrayList<JRadioButton> onButtons = new ArrayList<>();
    ArrayList<JRadioButton> offButtons = new ArrayList<>();

    for (int i = 0; i < allRooms.size(); i++) {
      Room r = (Room) allRooms.get(i);

      lightLabels.add(new JLabel(r.getName()));

      JRadioButton onButton = new JRadioButton("On");
      JRadioButton offButton = new JRadioButton("Off");
      ButtonGroup lightsBtnGroup = new ButtonGroup();
      lightsBtnGroup.add(onButton);
      lightsBtnGroup.add(offButton);

      if (r.getLightsOn()) {
        onButton.setSelected(true);
      } else {
        offButton.setSelected(true);
      }

      int currentRoomIndex = i;
      onButton.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              secModel.addToLightList(r);
            }
          });

      offButton.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              secModel.removeFromLightList(r);
            }
          });

      onButtons.add(onButton);
      offButtons.add(offButton);
    }

    secView.displayLightsSection(lightLabels, onButtons, offButtons);
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
        secView.toggleRadios(true);
        // logic to turn off away mode
      } else {
        boolean exceptionFound = false;
        if (EnvironmentModel.getSimulationRunning() == false) {
          CustomConsole.print(
              "Simulation is not running, Away Mode will not be enabled. Please start the simulation");
          exceptionFound = true;
        }
        if (Context.getHouse().hasObstruction()) {
          CustomConsole.print("A window is obstructed! Please correct this to enable Away Mode");
          exceptionFound = true;
        }
        if (!Context.houseIsEmpty()) {
          CustomConsole.print(
              "Someone is in the house, please remove them before enabling Away Mode.");
          exceptionFound = true;
        }
        if (!exceptionFound) {
          secModel.setAwayOn(true);
          secView.toggleSpinners(false);
          secView.toggleRadios(false);
          secView.changeAwayModeText("Turn Away Mode off");
        }
        // logic to turn on away mode

      }
    }
  }
}
