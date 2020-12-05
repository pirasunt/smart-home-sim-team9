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

public class HeatZoneCreator extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel roomPanel;
    private JTextField heatZoneNameField;
    private JTextField zoneName;


    public HeatZoneCreator() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(400, 600);
    }

    public void addConfirmButtonListener(ActionListener al){
        this.buttonOK.addActionListener(al);
    }

    public void addCancelButtonListener(ActionListener al){
        this.buttonCancel.addActionListener(al);
    }

    public String getZoneName(){
        return this.heatZoneNameField.getText();
    }
}
