package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** The type Heat zone creator. */
public class HeatZoneCreator extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JPanel roomPanel;
  private JTextField heatZoneNameField;
  private JSpinner morningTemp;
  private JSpinner afternoonTemp;
  private JSpinner nightTemp;
  private JTextField zoneName;

  /** Instantiates a new Heat zone creator. */
  public HeatZoneCreator() {

    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    setSize(400, 600);
  }

  /**
   * Add available rooms to ui.
   *
   * @param allLabels the all labels
   * @param checkBoxes the check boxes
   */
  public void addAvailableRoomsToUI(ArrayList<JLabel> allLabels, ArrayList<JCheckBox> checkBoxes) {
    this.roomPanel.setLayout(new GridLayout(0, 2, 10, 5));

    for (int i = 0; i < allLabels.size(); i++) {
      this.roomPanel.add(allLabels.get(i));
      this.roomPanel.add(checkBoxes.get(i));
    }
  }

  /**
   * Add confirm button listener.
   *
   * @param al the al
   */
  public void addConfirmButtonListener(ActionListener al) {
    this.buttonOK.addActionListener(al);
  }

  /**
   * Add cancel button listener.
   *
   * @param al the al
   */
  public void addCancelButtonListener(ActionListener al) {
    this.buttonCancel.addActionListener(al);
  }

  /**
   * Gets zone name.
   *
   * @return the zone name
   */
  public String getZoneName() {
    return this.heatZoneNameField.getText();
  }

  /**
   * Get morning temp int.
   *
   * @return the int
   */
  public int getMorningTemp() {
    return (Integer) this.morningTemp.getValue();
  }

  /**
   * Get afternoon temp int.
   *
   * @return the int
   */
  public int getAfternoonTemp() {
    return (Integer) this.afternoonTemp.getValue();
  }

  /**
   * Get night temp int.
   *
   * @return the int
   */
  public int getNightTemp() {
    return (Integer) this.nightTemp.getValue();
  }
}
