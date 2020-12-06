package Views;

import Controllers.HeatingController;
import Models.Context;
import Models.HeatingModel;
import Models.Room;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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


    public HeatZoneCreator() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(400, 600);
    }

    public void addAvailableRoomsToUI(ArrayList<JLabel> allLabels, ArrayList<JCheckBox> checkBoxes) {
        this.roomPanel.setLayout(new GridLayout(0,2,10,5));

        for(int i = 0; i < allLabels.size(); i++) {
            this.roomPanel.add(allLabels.get(i));
            this.roomPanel.add(checkBoxes.get(i));
        }

    }

    public void addConfirmButtonListener(ActionListener al) {
        this.buttonOK.addActionListener(al);
    }

    public void addCancelButtonListener(ActionListener al) {
        this.buttonCancel.addActionListener(al);
    }

    public String getZoneName() {
        return this.heatZoneNameField.getText();
    }

    public int getMorningTemp(){
        return (Integer)this.morningTemp.getValue();
    }
    public int getAfternoonTemp(){
        return (Integer)this.afternoonTemp.getValue();
    }
    public int getNightTemp(){
        return (Integer)this.nightTemp.getValue();
    }




}
