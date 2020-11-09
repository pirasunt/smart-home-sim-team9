package Views;

import Controllers.SecurityController;
import Models.SecurityModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SecurityView {
  private final ArrayList<JRadioButton> lightsRadioButtons = new ArrayList<>();
  SecurityModel sModel = new SecurityModel();
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

  public void addAwayListener(ActionListener l) {
    this.awayModeButton.addActionListener(l);
  }

  public void changeAwayModeText(String s) {
    awayModeButton.setText(s);
  }

  public JSpinner getStartTime() {
    return startTime;
  }

  public JSpinner getEndTime() {
    return endTime;
  }

  public void toggleSpinners(boolean shouldAllowEdit) {
    startTime.setEnabled(shouldAllowEdit);
    endTime.setEnabled(shouldAllowEdit);
    intervalSpinner.setEnabled(shouldAllowEdit);
  }

  public JPanel getWrapper() {
    return this.shpWrap;
  }

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

  public void toggleRadios(boolean shouldEnable){
    for(JRadioButton jr: lightsRadioButtons){
      jr.setEnabled(shouldEnable);
    }
  }
}
