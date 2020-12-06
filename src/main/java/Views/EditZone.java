package Views;

import Models.HeatingZone;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EditZone extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner morningTemp;
    private JSpinner afternoonTemp;
    private JSpinner nightTemp;
    private JLabel zoneName;

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

    public void addEditConfirmButtonListener(ActionListener al){
        this.buttonOK.addActionListener(al);
    }
    public void addEditCancelButtonListener(ActionListener al){
        this.buttonCancel.addActionListener(al);
    }

    public int getUpdatedMorningTemp(){
        return (Integer)this.morningTemp.getValue();
    }
    public int getUpdatedAfternoonTemp(){
        return (Integer)this.afternoonTemp.getValue();
    }
    public int getUpdatedNightTemp(){
        return (Integer)this.nightTemp.getValue();
    }
}
