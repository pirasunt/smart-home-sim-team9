package Views;

import Controllers.SecurityController;
import Models.SecurityModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** The type Security view. */
public class SecurityView {
  private final ArrayList<JRadioButton> lightsRadioButtons = new ArrayList<>();
  /** The model. */
  SecurityModel sModel = new SecurityModel();

  /** The controller. */
  SecurityController sc = new SecurityController(sModel, this);

  private JPanel shpWrap;
  private JPanel Tab1;
  private JSpinner intervalSpinner;
  private JButton awayModeButton;
  private JSpinner startTime;
  private JSpinner endTime;
  private JLabel endLabel;
  private JLabel alertAuthoritiesAfterThisLabel;
  private JPanel jpWrap;
  private JPanel lightPanel;

  /** Instantiates a new Security view. */
  public SecurityView() {
    startTime.setModel(sModel.getStartModel());
    JSpinner.DateEditor de = new JSpinner.DateEditor(startTime, "hh:mm a");
    de.getTextField().setEditable(false);
    startTime.setEditor(de);

    endTime.setModel(sModel.getEndModel());
    JSpinner.DateEditor de1 = new JSpinner.DateEditor(endTime, "hh:mm a");
    de1.getTextField().setEditable(false);
    endTime.setEditor(de1);

    intervalSpinner.setModel(sModel.getIntervalModel());
  }

  /**
   * Add away listener.
   *
   * @param l the l
   */
  public void addAwayListener(ActionListener l) {
    this.awayModeButton.addActionListener(l);
  }

  /**
   * Change away mode text.
   *
   * @param s the s
   */
  public void changeAwayModeText(String s) {
    awayModeButton.setText(s);
  }

  /**
   * Gets start time.
   *
   * @return the start time
   */
  public JSpinner getStartTime() {
    return startTime;
  }

  /**
   * Gets end time.
   *
   * @return the end time
   */
  public JSpinner getEndTime() {
    return endTime;
  }

  /**
   * Toggle spinners.
   *
   * @param shouldAllowEdit boolean indicating if editable
   */
  public void toggleSpinners(boolean shouldAllowEdit) {
    startTime.setEnabled(shouldAllowEdit);
    endTime.setEnabled(shouldAllowEdit);
    intervalSpinner.setEnabled(shouldAllowEdit);
  }

  /**
   * Gets wrapper.
   *
   * @return the wrapper
   */
  public JPanel getWrapper() {
    return this.shpWrap;
  }

  /**
   * Display lights section.
   *
   * @param lightLabels the light labels
   * @param onButtons the on buttons
   * @param offButtons the off buttons
   */
  public void displayLightsSection(
      ArrayList<JLabel> lightLabels,
      ArrayList<JRadioButton> onButtons,
      ArrayList<JRadioButton> offButtons) {
    this.lightPanel.setLayout(new GridLayout(0, 3, 1, 20));
    this.lightPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    // labels ArrayList same length as buttons ArrayList
    for (int i = 0; i < lightLabels.size(); i++) {
      this.lightPanel.add(lightLabels.get(i));
      this.lightPanel.add(onButtons.get(i));
      this.lightPanel.add(offButtons.get(i));
    }

    this.lightsRadioButtons.addAll(onButtons);
    this.lightsRadioButtons.addAll(offButtons);
  }

  /**
   * Toggle radios.
   *
   * @param shouldEnable the should enable boolean
   */
  public void toggleRadios(boolean shouldEnable) {
    for (JRadioButton jr : lightsRadioButtons) {
      jr.setEnabled(shouldEnable);
    }
  }
}
