package Views;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import Custom.NonExistantUserProfileException;
import Models.UserProfileModel;
import Models.Room;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

/**
 * The type Dash.
 */
public class Dash extends JFrame {
    /**
     * The Stop simulation button.
     */
    JButton stopSimulationButton;
    /**
     * The Edit action button.
     */
    JButton editActionButton;
    /**
     * The Tabbed pane 1.
     */
    JTabbedPane tabbedPane1;
    /**
     * The P 1.
     */
    JPanel p1;
    /**
     * The Sp 1.
     */
    JScrollPane sp1;
    /**
     * The Sp 2.
     */
    JScrollPane sp2;
    /**
     * The Tab 2.
     */
    JPanel Tab2;
    /**
     * The Tab 1.
     */
    JPanel Tab1;
    JComboBox userProfileDropDown; //Current User Profile
    JComboBox userRoomDropDown; //Current User Profile's Room
    JButton changeDate;
    JButton changeTime;
    JFormattedTextField dateField; //Date field
    JFormattedTextField timeField;//Time Field
    JSpinner tempSpinner; //temperature spinner
    JDatePickerImpl datePicker;
    JSpinner time_spinner;
    JButton timeConfirm;


    public Dash(int temp, String date, String time) {
        tempSpinner.setValue(temp);
        dateField.setValue(date);
        timeField.setValue(time);
        timeConfirm = new JButton("OK");



        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        editActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

    }


}
