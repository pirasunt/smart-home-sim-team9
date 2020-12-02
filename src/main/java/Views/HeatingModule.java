package Views;


import Controllers.HeatingController;
import Models.HeatingModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HeatingModule extends JPanel {
    HeatingController hc;
    private JButton createNewHeatingZoneButton;
    private JPanel panel;

    public HeatingModule() {
        HeatingModel model = new HeatingModel();
        hc = new HeatingController(model, this);


    }

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

}
