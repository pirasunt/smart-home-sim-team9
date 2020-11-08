package Views;

import Controllers.SecurityController;
import Models.SecurityModel;
import javax.swing.*;
import java.awt.event.ActionListener;

public class SecurityView {
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
}
