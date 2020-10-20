package Views;

import Enums.ProfileType;
import Models.Room;
import Models.UserProfileModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EnvironmentView extends JFrame {

    private JButton userButton;
    private JButton locationButton;
    private JButton enterSimButton;
    private JButton createUserButton;
    private JButton confirmUserCreateBtn;

    private JFrame createUser;
    private JTextField profileName;
    private JComboBox<ProfileType> profileType;

    private Dash dashboard;

    public EnvironmentView() {
        userButton = new JButton("1. Select User Profile");
        locationButton = new JButton("2. Select Location");
        enterSimButton = new JButton("3. Enter Simulation");
        Box box1 = Box.createVerticalBox();
        box1.add(userButton);
        box1.add(locationButton);
        box1.add(enterSimButton);
        box1.add(new JSeparator());
        box1.add(new JLabel("Configuration"));

        this.createUserButton = new JButton("Create User");
        box1.add(this.createUserButton);

        this.add(box1);
        setLayout(new GridLayout(1, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(250,250);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addConfirmUserCreateListener(ActionListener listenForConfirmUserCreate){
        this.confirmUserCreateBtn.addActionListener(listenForConfirmUserCreate);
    }

    public void addCreateUserListener(ActionListener listenForCreateUser){
        this.createUserButton.addActionListener(listenForCreateUser);
    }

    public void addUserListener(ActionListener listenForUser) {
        this.userButton.addActionListener(listenForUser);
    }

    public void addLocationListener(ActionListener listenForLocation) {
        this.locationButton.addActionListener(listenForLocation);
    }

    public void addSimulatorListener(ActionListener listenForSim) {
        this.enterSimButton.addActionListener(listenForSim);
    }

    public void addUserDropDownListener(ActionListener listenForUserDropDown){
        this.dashboard.userProfileDropDown.addActionListener(listenForUserDropDown);
    }

    public void addUserRoomDropDownListener(ActionListener listenForUserRoomDropDown) {
        this.dashboard.userRoomDropDown.addActionListener(listenForUserRoomDropDown);
    }

    public void addTempSpinnerListener(ChangeListener listenForTempSpinner){
        this.dashboard.tempSpinner.addChangeListener(listenForTempSpinner);
    }

    public void addChangeDateListener(ActionListener listenForDateChange) {
        this.dashboard.changeDate.addActionListener(listenForDateChange);
    }

    public void addChangeTimeListener(ActionListener listenForTimeChange) {
        this.dashboard.changeTime.addActionListener(listenForTimeChange);
    }

    public void addConfirmTimeListener(ActionListener listenForTimeConfirm) {
        this.dashboard.timeConfirm.addActionListener(listenForTimeConfirm);
    }

    public void addSimulationToggleListener(ActionListener listenForSimulationStart){
        this.dashboard.toggleSimulationButton.addActionListener(listenForSimulationStart);
    }

    public void createDash(int temp, String date, String time){
        JFrame frame = new JFrame("Dashboard");
        this.dashboard = new Dash(temp, date, time);
        JPanel jp = this.dashboard.p1;
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void addProfileToDropDown(UserProfileModel profile, boolean isCurrentUser){
        dashboard.userProfileDropDown.addItem(profile);
        if(isCurrentUser)
            dashboard.userProfileDropDown.setSelectedItem(profile);
    }

    public void addRoomToDropDown(Room room, boolean isCurrentRoom){
        dashboard.userRoomDropDown.addItem(room);
        if(isCurrentRoom)
            dashboard.userRoomDropDown.setSelectedItem(room);
    }

    public void setRoomDropDownIndex(int index){
        this.dashboard.userRoomDropDown.setSelectedIndex(index);
    }

    public void setRoomDropDownItem(Room room){
        this.dashboard.userRoomDropDown.setSelectedItem(room);
    }

    public void ChangeTime(Date currentDate) {
        this.dashboard.setLayout(new GridLayout(2,1, 3,3));

        SpinnerDateModel sm = new SpinnerDateModel(currentDate, null, null, Calendar.HOUR_OF_DAY);

        dashboard.time_spinner = new JSpinner(sm);

        JSpinner.DateEditor te = new JSpinner.DateEditor(dashboard.time_spinner, "hh:mm:ss a");
        dashboard.time_spinner.setEditor(te);


        this.dashboard.add(dashboard.time_spinner);


        this.dashboard.add(dashboard.timeConfirm);
        this.dashboard.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.dashboard.pack();
        this.dashboard.setVisible(true);
    }

    /**
     * Change date.
     */
    public void ChangeDate(JFormattedTextField.AbstractFormatter formatter) {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        dashboard.datePicker = new JDatePickerImpl(panel, formatter);
        this.dashboard.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
        this.dashboard.add(dashboard.datePicker);
        this.dashboard.pack();
        this.dashboard.setVisible(true);
    }


    public Object getTimeSpinnerVal() {
        return dashboard.time_spinner.getValue();
    }

    public void removeDateComponentPicker(){
        this.dashboard.remove(dashboard.datePicker);
    }

    public void removeTimeComponentPicker(){
        this.dashboard.remove(dashboard.time_spinner);
        this.dashboard.remove(dashboard.timeConfirm);
    }

    public JFormattedTextField getTimeField(){
        return dashboard.timeField;
    }

    public void disposeDash(){
        this.dashboard.dispose();
    }

    public void setDateField(String date){
        this.dashboard.dateField.setValue(date);
    }

    public int getTemperatureFromSpinner(){
        return (int)dashboard.tempSpinner.getValue();
    }

    public void userCreationWindow(){
        this.createUser = new JFrame("Create a new User");
        GridLayout grid = new GridLayout(4,2, 2,2);
        createUser.setLayout(grid);

        this.profileName = new JTextField();
        this.profileType = new JComboBox<ProfileType>(ProfileType.values());
        this.confirmUserCreateBtn= new JButton("Create");

        createUser.add(new JLabel("Profile Name: "));
        createUser.add(profileName);
        createUser.add(new JLabel("Profile Type"));
        createUser.add(profileType);
        createUser.add(new JLabel());//Empty cell
        createUser.add(new JLabel());//Empty cell
        createUser.add(new JLabel());//Empty cell
        createUser.add(this.confirmUserCreateBtn);


        createUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createUser.pack();
        createUser.setLocationRelativeTo(null); //Center User Selection JFrame
        createUser.setVisible(true);
    }

    public void disposeCreateUser(){
        this.createUser.dispose();
    }

    public UserProfileModel getNewlyCreatedUser() {
        return new UserProfileModel((ProfileType) this.profileType.getSelectedItem(), this.profileName.getText(), -1);
    }

    public void changeSimulationToggleText(String test){
        dashboard.toggleSimulationButton.setText(test);
    }
}
