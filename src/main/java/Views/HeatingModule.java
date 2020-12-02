package Views;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HeatingModule extends JPanel {
    private JButton createNewHeatingZoneButton;

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

}
