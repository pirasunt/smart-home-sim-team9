package Views;

import javax.swing.*;
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
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton changeButton1;
    private JButton changeButton2;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JSpinner spinner1;
    private JDatePickerImpl datePicker;
    private JSpinner time_spinner;
    private Boolean action1 = false;
    private Boolean action2 = false;

    /**
     * Instantiates a new Dash.
     */
    public Dash() {
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
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
            final String datePattern = "yyyy-MM-dd";
            final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

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
        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);

        time_spinner = new JSpinner(sm);

        JSpinner.DateEditor te = new JSpinner.DateEditor(time_spinner, "HH:mm a");
        time_spinner.setEditor(te);

        add(time_spinner, gbc);

        JButton btn = new JButton("OK");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = time_spinner.getValue();
                if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
                    String time = format.format(date);
                    formattedTextField2.setValue(time);
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
