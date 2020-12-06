package Views;

import Models.HeatingZone;

import javax.swing.*;
import java.awt.event.ActionListener;

/** The type Edit zone. */
public class EditZone extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JSpinner morningTemp;
  private JSpinner afternoonTemp;
  private JSpinner nightTemp;
  private JLabel zoneName;

  /**
   * Instantiates a new Edit zone.
   *
   * @param hz the hz
   */
  public EditZone(HeatingZone hz) {
    this.zoneName.setText(this.zoneName.getText() + " " + hz.getName());

    this.morningTemp.setValue(hz.getMorningTemp());
    this.afternoonTemp.setValue(hz.getAfternoonTemp());
    this.nightTemp.setValue(hz.getNightTemp());

    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);
    setSize(300, 400);
  }

  /**
   * Add edit confirm button listener.
   *
   * @param al the al
   */
  public void addEditConfirmButtonListener(ActionListener al) {
    this.buttonOK.addActionListener(al);
  }

  /**
   * Add edit cancel button listener.
   *
   * @param al the al
   */
  public void addEditCancelButtonListener(ActionListener al) {
    this.buttonCancel.addActionListener(al);
  }

  /**
   * Get updated morning temp int.
   *
   * @return the int
   */
  public int getUpdatedMorningTemp() {
    return (Integer) this.morningTemp.getValue();
  }

  /**
   * Get updated afternoon temp int.
   *
   * @return the int
   */
  public int getUpdatedAfternoonTemp() {
    return (Integer) this.afternoonTemp.getValue();
  }

  /**
   * Get updated night temp int.
   *
   * @return the int
   */
  public int getUpdatedNightTemp() {
    return (Integer) this.nightTemp.getValue();
  }
}
