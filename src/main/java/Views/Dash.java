package Views;

import Enums.ProfileType;
import Models.Context;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Observers.TimeChangeObserver;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The type Dash.
 */
public class Dash extends JFrame implements TimeChangeObserver {
    /**
     * The Stop simulation button.
     */
    private JButton toggleSimulationButton;
    /**
     * The Edit action button.
     */
    private JButton editSimulationButton;
    /**
     * The Tabbed pane 1.
     */
    private JTabbedPane tabbedPane1;
    /**
     * The P 1.
     */
    private JPanel smartModulesPanel;
    /**
     * The Sp 1.
     */
    private JScrollPane sp1;
    /**
     * The Sp 2.
     */
    private JScrollPane sp2;
    /**
     * The Tab 1.
     */
    private JPanel wrapperPanel;
    /**
     * The User profile drop down.
     */
    private JComboBox userProfileDropDown; // Current User Profile

    /**
     * The User room drop down.
     */
    private JComboBox userRoomDropDown; // Current User Profile's Room

    /**
     * The Date field.
     */
    private JLabel dateField; // Date field

    /**
     * The Time field.
     */
    private JLabel timeField; // Time Field

    /**
     * The Temp spinner.
     */
    private JLabel teampLabel; // temperature spinner

    /**
     * The Time speed.
     */
    private JLabel timeSpeed;
    private SecurityView shp;
    private CoreView coreView;
    private JPanel shpTab;
    private JPanel SHHTab;
    private HeatingModule SHH;


    /**
     * Instantiates teampLabel new Dashboard frame.
     *
     * @param temp  the temp
     * @param date  the date
     * @param time  the time
     * @param delay the delay
     */
    public Dash(int temp, String date, String time, int delay) {
        teampLabel.setText(temp + "°C");
        dateField.setText(date);
        timeField.setText(time);
        timeSpeed.setText(1000 / delay + " X");

        Context.subscribe(this);


        JPanel jp = this.smartModulesPanel;
        this.setContentPane(jp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

        this.setSize((int) rect.getWidth() / 2, (int) rect.getHeight());
        this.setLocation(0, 0);
        this.setVisible(true);

        tabbedPane1.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                    }
                });

        setSHPVisibility();
        setSHCVisibility();
    }

    /**
     * Since CoreView is initialized by The IntelliJ Swing Builder, we need to use that instance.
     *
     * @return returns an instance of CoreView (SHC)
     */
    public CoreView getSHC() {
        return this.coreView;
    }

    @Override
    public void repaint() {
        setSHPVisibility();
        setSHCVisibility();

        super.repaint();
    }

    /**
     * Adds the specified {@link UserProfileModel} to the Dashboard User Dropdown Menu
     *
     * @param profile       The {@link UserProfileModel} to add to the User Dropdown
     * @param isCurrentUser boolean indicating whether teampLabel current user is set in the {@link
     *                      EnvironmentModel}
     */
    public void addProfileToDropDown(UserProfileModel profile, boolean isCurrentUser) {
        this.userProfileDropDown.addItem(profile);
        if (isCurrentUser) this.userProfileDropDown.setSelectedItem(profile);
    }

    /**
     * Adds the specified {@link Room} to the Room Selection Dropdown
     *
     * @param room          The {@link Room} to add to the Room Dropdown
     * @param isCurrentRoom boolean indicating whether the currently selected {@link UserProfileModel}
     *                      is in the passed in {@link Room}
     */
    public void addRoomToDropDown(Room room, boolean isCurrentRoom) {
        this.userRoomDropDown.addItem(room);
        if (isCurrentRoom) this.userRoomDropDown.setSelectedItem(room);
    }

    /**
     * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
     *
     * @param listenForUserDropDown Listener for the dropdown button that selects between different
     *                              {@link UserProfileModel}
     */
    public void addUserDropDownListener(ActionListener listenForUserDropDown) {
        this.userProfileDropDown.addActionListener(listenForUserDropDown);
    }

    /**
     * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
     *
     * @param listenForUserRoomDropDown Listener for the dropdown button that between different {@link
     *                                  Room} for the currently selected {@link UserProfileModel}
     */
    public void addUserRoomDropDownListener(ActionListener listenForUserRoomDropDown) {
        this.userRoomDropDown.addActionListener(listenForUserRoomDropDown);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForSimulationStart the listen for simulation start
     */
    public void addSimulationToggleListener(ActionListener listenForSimulationStart) {
        this.toggleSimulationButton.addActionListener(listenForSimulationStart);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForEditSimulation the listen for window obstruction
     */
    public void addEditSimulationListener(ActionListener listenForEditSimulation) {
        this.editSimulationButton.addActionListener(listenForEditSimulation);
    }

    /**
     * Sets Room Dropdown menu to the {@link Room} specified by the passed in index value
     *
     * @param index integer value representing the index position of the {@link Room} within the
     *              Dropdown
     */
    public void setRoomDropDownIndex(int index) {
        this.userRoomDropDown.setSelectedIndex(index);
    }

    /**
     * Sets Room Dropdown menu to the specified {@link Room} NOTE: This only works if the passed in
     * {@link Room} object has the same reference as the {@link Room} object that is contained within
     * the Dropdown menu
     *
     * @param room {@link Room} object to set the Dropdown menu
     */
    public void setRoomDropDownItem(Room room) {
        this.userRoomDropDown.setSelectedItem(room);
    }

    public void refreshDash() {
        this.repaint();
    }

    public void refreshDash(String dateString, String timeString, int temp, int timerDelay) {
        this.dateField.setText(dateString);
        this.timeField.setText(timeString);
        this.teampLabel.setText(temp + "°C");
        this.timeSpeed.setText(1000 / timerDelay + " X");

        // This will trigger the userProfileDropDown ActionListener and will update the room of the
        // current user on the DASH
        this.userProfileDropDown.setSelectedIndex(
                this.userProfileDropDown.getSelectedIndex());
    }

    /**
     * Toggles the simulation text.
     *
     * @param text the text
     */
    public void changeSimulationToggleText(String text) {
        this.toggleSimulationButton.setText(text);
    }

    public void setTimeField(String time) {
        this.timeField.setText(time);
    }

    private void setSHCVisibility() {
        if (Context.getCurrentUser().getProfileType() == ProfileType.STRANGER) {
            tabbedPane1.setEnabledAt(0, false);
            coreView.getWrapper().setVisible(false);
        } else {
            tabbedPane1.setEnabledAt(0, true);
            coreView.getWrapper().setVisible(true);
        }
    }

    private void setSHPVisibility() {
        if (Context.getCurrentUser().getProfileType() != ProfileType.ADULT) {
            tabbedPane1.setEnabledAt(1, false);
            shp.getWrapper().setVisible(false);
        } else
            tabbedPane1.setEnabledAt(1, true);
        shp.getWrapper().setVisible(true);
    }

    /**
     * Updates the time of the observer
     *
     * @param newTime String representation of the new Time Value
     */
    @Override
    public void update(String newTime) {
        this.timeField.setText(newTime);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        smartModulesPanel = new JPanel();
        smartModulesPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        smartModulesPanel.setPreferredSize(new Dimension(1000, 1000));
        sp1 = new JScrollPane();
        smartModulesPanel.add(sp1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 4, new Insets(0, 0, 0, 0), -1, -1));
        sp1.setViewportView(panel1);
        toggleSimulationButton = new JButton();
        toggleSimulationButton.setText("Start Simulation");
        panel1.add(toggleSimulationButton, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editSimulationButton = new JButton();
        editSimulationButton.setText("Edit Simulation");
        panel1.add(editSimulationButton, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userProfileDropDown = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        userProfileDropDown.setModel(defaultComboBoxModel1);
        panel1.add(userProfileDropDown, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userRoomDropDown = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        userRoomDropDown.setModel(defaultComboBoxModel2);
        panel1.add(userRoomDropDown, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateField = new JLabel();
        Font dateFieldFont = this.$$$getFont$$$(null, Font.BOLD, 16, dateField.getFont());
        if (dateFieldFont != null) dateField.setFont(dateFieldFont);
        dateField.setText("");
        panel1.add(dateField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        timeField = new JLabel();
        Font timeFieldFont = this.$$$getFont$$$(null, Font.BOLD, 16, timeField.getFont());
        if (timeFieldFont != null) timeField.setFont(timeFieldFont);
        timeField.setText("");
        panel1.add(timeField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Date");
        panel1.add(label1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Time");
        panel1.add(label2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        teampLabel = new JLabel();
        Font teampLabelFont = this.$$$getFont$$$(null, Font.BOLD, 16, teampLabel.getFont());
        if (teampLabelFont != null) teampLabel.setFont(teampLabelFont);
        teampLabel.setText("Label");
        panel1.add(teampLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Time Speed");
        panel1.add(label3, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(2);
        label4.setText("Outside \nTemperature");
        panel1.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeSpeed = new JLabel();
        Font timeSpeedFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 16, timeSpeed.getFont());
        if (timeSpeedFont != null) timeSpeed.setFont(timeSpeedFont);
        timeSpeed.setText("Label");
        panel1.add(timeSpeed, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sp2 = new JScrollPane();
        smartModulesPanel.add(sp2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        wrapperPanel.setVisible(true);
        sp2.setViewportView(wrapperPanel);
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setVisible(true);
        wrapperPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setVerifyInputWhenFocusTarget(true);
        panel2.setVisible(false);
        tabbedPane1.addTab("SHC", panel2);
        coreView = new CoreView();
        panel2.add(coreView.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        shpTab = new JPanel();
        shpTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        shpTab.setVisible(false);
        tabbedPane1.addTab("SHP", shpTab);
        shp = new SecurityView();
        shpTab.add(shp.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SHHTab = new JPanel();
        SHHTab.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("SHH", SHHTab);
        SHH = new HeatingModule();
        SHHTab.add(SHH.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        SHHTab.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return smartModulesPanel;
    }
}
