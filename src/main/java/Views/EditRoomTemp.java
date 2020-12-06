package Views;

import Models.Room;

import javax.swing.*;
import java.awt.event.ActionListener;

/** The type Edit room temp. */
public class EditRoomTemp extends JDialog {
  private JPanel contentPane;
  private JPanel tempChangeDiag;
  private JRadioButton yesRadioButton;
  private JRadioButton noRadioButton;
  private JLabel roomName;
  private JSpinner spinner1;
  private JButton saveButton;

  /**
   * Instantiates a new Edit room temp.
   *
   * @param room the room
   */
  public EditRoomTemp(Room room) {

    this.roomName.setText(room.getName());

    this.spinner1.setValue(room.getTemperature());
    if (room.isTempOverriden()) {
      yesRadioButton.setSelected(true);
      this.tempChangeDiag.setVisible(true);
    } else {
      noRadioButton.setSelected(true);
      this.tempChangeDiag.setVisible(false);
    }
    setContentPane(contentPane);
    setModal(true);
    setSize(400, 200);
  }

  /**
   * Get temp change diag j panel.
   *
   * @return the j panel
   */
  public JPanel getTempChangeDiag() {
    return this.tempChangeDiag;
  }

  /**
   * Get yes radio button j radio button.
   *
   * @return the j radio button
   */
  public JRadioButton getYesRadioButton() {
    return yesRadioButton;
  }

  /**
   * Get no radio button j radio button.
   *
   * @return the j radio button
   */
  public JRadioButton getNoRadioButton() {
    return noRadioButton;
  }

  /**
   * Get new temp int.
   *
   * @return the int
   */
  public int getNewTemp() {
    return (Integer) spinner1.getValue();
  }

  /**
   * Add save listener.
   *
   * @param al the al
   */
  public void addSaveListener(ActionListener al) {
    this.saveButton.addActionListener(al);
  }

  /**
   * Add turn on override listener.
   *
   * @param al the al
   */
  public void addTurnOnOverrideListener(ActionListener al) {
    this.yesRadioButton.addActionListener(al);
  }

  /**
   * Add turn off override listener.
   *
   * @param al the al
   */
  public void addTurnOffOverrideListener(ActionListener al) {
    this.noRadioButton.addActionListener(al);
  }
}
