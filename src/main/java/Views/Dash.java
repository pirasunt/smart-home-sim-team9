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

import Context.Environment;
import Context.NonExistantUserProfileException;
import Context.UserProfile;
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
    private JComboBox comboBox1; //Current User Profile
    private JComboBox comboBox2; //Current User Profile's Room
    private JButton changeButton1;
    private JButton changeButton2;
    private JFormattedTextField formattedTextField1; //Date field
    private JFormattedTextField formattedTextField2;//Time Field
    private JSpinner spinner1; //temperature spinner
    private JDatePickerImpl datePicker;
    private JSpinner time_spinner;
    private Boolean action1 = false;
    private Boolean action2= false;
    private static Environment env;

    public Dash(Environment environment) {
        env = environment; //DI
        spinner1.setValue(env.getOutsideTemp());
        formattedTextField1.setValue(env.getDateString());
        formattedTextField2.setValue(env.getTimeString());

        UserProfile[] allProfiles = env.getAllUserProfiles();
        int indexProfile = -1;
        for(int i = 0; i < allProfiles.length; i++) {
            comboBox1.addItem(allProfiles[i]); //addItem uses toString() method of the passed in Object to display dropdown item.
            if(allProfiles[i].getProfileID() == env.getCurrentUser().getProfileID()) {
                indexProfile = i;
            }
        }
        comboBox1.setSelectedItem(allProfiles[indexProfile]); //For setSelectedItem to work the passed in object has to have the same reference as the object that was used in addItem()

        Room[] allRooms = env.getRooms();
        int indexRoom = -1;
        for(int i = 0; i < allRooms.length; i++) {
            comboBox2.addItem(allRooms[i]);//addItem uses toString() method of the passed in Object to display dropdown item.
            if(allRooms[i].getId() == env.getCurrentUser().getRoomID()) {
                indexRoom = i;
            }
        }
        comboBox2.setSelectedItem(allRooms[indexRoom]); //For setSelectedItem to work the passed in object has to have the same reference as the object that was used in addItem()

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource(); //Newly Selected item
                UUID newCurrentUserID = ((UserProfile)cb.getSelectedItem()).getProfileID();
                try {
                    env.setCurrentUser(env.getUserByID(newCurrentUserID));
                } catch (NonExistantUserProfileException nonExistantUserProfileException) {
                    nonExistantUserProfileException.printStackTrace();
                }

                if(env.getCurrentUser().getRoomID() == -1) {
                    comboBox2.setSelectedIndex(-1);
                } else {
                    for(int i=0; i < allRooms.length; i++) {
                        if(allRooms[i].getId() == env.getCurrentUser().getRoomID()) {
                            comboBox2.setSelectedItem(allRooms[i]);
                            break;
                        }
                    }
                }
            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource(); //Newly Selected item
                if(cb.getSelectedIndex() == -1) {
                    Console.print("NO LOCATION HAS BEEN SET FOR: " + env.getCurrentUser().getName());
                } else {
                    Room newRoom = (Room) cb.getSelectedItem();
                    if(newRoom.getId() != env.getCurrentUser().getRoomID()) {
                        env.modifyProfileLocation(env.getCurrentUser(), newRoom);
                    }
                  }
                }
        });

        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                env.setTemperature((int)spinner1.getValue()); //Any change on Temp Spinner will update Environment attribute
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

        changeButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (action2 == false) {
                    action1 = true;
                    ChangeDate();
                }
            }
        });
        changeButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (action1 == false) {
                    action2 = true;
                    ChangeTime();
                }
            }
        });
    }

    /**
     * Change date.
     */
    public void ChangeDate() {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter(){
            String datePattern = "MMM dd, yyyy";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
            @Override
            public Object stringToValue(String text) throws ParseException {
                Calendar cal = Calendar.getInstance();
                cal.setTime((Date) dateFormatter.parseObject(text));
                return cal;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    formattedTextField1.setValue(dateFormatter.format(cal.getTime()));
                    env.setDate(cal.getTime());//Update Environment date
                    remove(datePicker);
                    action1 = false;
                    dispose();
                    return dateFormatter.format(cal.getTime());
                }
                return "";
            }
        });
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.add(datePicker);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Change time.
     */
    public void ChangeTime() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        SpinnerDateModel sm = new SpinnerDateModel(env.getDateObject(), null, null, Calendar.HOUR_OF_DAY);

        time_spinner = new JSpinner(sm);

        JSpinner.DateEditor te = new JSpinner.DateEditor(time_spinner, "hh:mm a");
        time_spinner.setEditor(te);


        add(time_spinner, gbc);

        JButton btn = new JButton("OK");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = time_spinner.getValue();
                if (value instanceof Date) {
                    Date date = (Date)value;
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                    String time = formatter.format(date);
                    formattedTextField2.setValue(time);
                    env.setTime(date); //Update Environment time
                    remove(time_spinner);
                    remove(btn);
                    action2 = false;
                    dispose();
                }
            }
        });
        this.add(btn, gbc);
        this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
