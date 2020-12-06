package Views;

import Models.Room;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class EditRoomTemp extends JDialog {
    private JPanel contentPane;
    private JPanel tempChangeDiag;
    private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JLabel roomName;
    private JSpinner spinner1;
    private JButton saveButton;

    public EditRoomTemp(Room room) {

        this.roomName.setText(room.getName());

        this.spinner1.setValue(room.getTemperature());
        if(room.isTempOverriden()){
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

    public JPanel getTempChangeDiag(){
        return this.tempChangeDiag;
    }

    public JRadioButton getYesRadioButton(){
        return yesRadioButton;
    }

    public JRadioButton getNoRadioButton(){
        return noRadioButton;
    }

    public int getNewTemp(){
        return (Integer)spinner1.getValue();
    }

    public void addSaveListener(ActionListener al){
        this.saveButton.addActionListener(al);
    }

    public void addTurnOnOverrideListener(ActionListener al){
        this.yesRadioButton.addActionListener(al);
    }

    public void addTurnOffOverrideListener(ActionListener al){
        this.noRadioButton.addActionListener(al);
    }

}
