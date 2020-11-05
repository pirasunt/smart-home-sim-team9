package Views;

import Controllers.SecurityController;
import Models.SecurityModel;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class SecurityView {
  private JPanel shpWrap;
  private JPanel Tab1;
  private JSpinner spinner1;
  private JButton awayModeButton;

  public SecurityView() {

  }

  SecurityController sc = new SecurityController(new SecurityModel(),this);

  public void addAwayListener(ActionListener l) {
    this.awayModeButton.addActionListener(l);
  }
  public void addIntervalListener (ChangeListener l) {
    this.spinner1.addChangeListener(l);
  }
  public void changeAwayModeText(String s) {
    awayModeButton.setText(s);
  }
  public String getSpinner(){
    return (String) spinner1.getValue();
  }

}
