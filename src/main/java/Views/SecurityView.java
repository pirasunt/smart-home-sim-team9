package Views;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SecurityView {
  private JPanel shpWrap;
  private JPanel Tab1;
  private JRadioButton onRadioButton;
  private JRadioButton offRadioButton;
  private JSpinner spinner1;

  public SecurityView() {

    onRadioButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {}
        });
  }

  public void addOnRadioListener(ActionListener aE) {
    this.onRadioButton.addActionListener(aE);
  }

  public void addOffRadioListener(ActionListener aE) {
    this.offRadioButton.addActionListener(aE);
  }

  public void addTempSpinnerListener(ChangeListener listenForTempSpinner) {
    spinner1.addChangeListener(listenForTempSpinner);
  }
}
