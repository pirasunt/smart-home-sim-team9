package Views;

import Enums.ProfileType;
import Models.UserProfileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * EnvironmentView is the visual representation of the entire system and is the module that
 * interacts directly with the user. Any operation requested by the user is then passed on to the
 * {@link Controllers.EnvironmentController}
 */
public class EnvironmentView extends JFrame {

    private final JButton userButton;
    private final JButton locationButton;
    private final JButton enterSimButton;
    private final JButton createUserButton;
    private JButton confirmUserCreateBtn;

    private JFrame createUser;
    private JTextField profileName;
    private JComboBox<ProfileType> profileType;


    /**
     * Initializes the Environment View
     */
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
        this.setSize(250, 250);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForConfirmUserCreate Listener for the button that confirms user creation
     */
    public void addConfirmUserCreateListener(ActionListener listenForConfirmUserCreate) {
        this.confirmUserCreateBtn.addActionListener(listenForConfirmUserCreate);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForCreateUser Listener for the button that starts the user creation process
     */
    public void addCreateUserListener(ActionListener listenForCreateUser) {
        this.createUserButton.addActionListener(listenForCreateUser);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForUser Listener for the button that selects the {@link UserProfileModel} before
     *                      starting the simulation
     */
    public void addUserListener(ActionListener listenForUser) {
        this.userButton.addActionListener(listenForUser);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForLocation Listener for the button that selects the selected {@link
     *                          UserProfileModel} location before starting the simulation
     */
    public void addLocationListener(ActionListener listenForLocation) {
        this.locationButton.addActionListener(listenForLocation);
    }

    /**
     * Method used to pass on the {@link JButton} listener responsibility to its caller.
     *
     * @param listenForSim Listener for the button that starts the simulation
     */
    public void addSimulatorListener(ActionListener listenForSim) {
        this.enterSimButton.addActionListener(listenForSim);
    }

    /**
     * Creates an interface that allows the Simulator user to create new profiles The user needs to
     * specify the new profile's name as well as the. The latter is displayed
     */
    public void userCreationWindow() {
        this.createUser = new JFrame("Create a new User");
        GridLayout grid = new GridLayout(4, 2, 2, 2);
        createUser.setLayout(grid);

        this.profileName = new JTextField();
        this.profileType = new JComboBox<ProfileType>(ProfileType.values());
        this.confirmUserCreateBtn = new JButton("Create");

        createUser.add(new JLabel("Profile Name: "));
        createUser.add(profileName);
        createUser.add(new JLabel("Profile Type"));
        createUser.add(profileType);
        createUser.add(new JLabel()); // Empty cell
        createUser.add(new JLabel()); // Empty cell
        createUser.add(new JLabel()); // Empty cell
        createUser.add(this.confirmUserCreateBtn);

        createUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createUser.pack();
        createUser.setLocationRelativeTo(null); // Center User Selection JFrame
        createUser.setVisible(true);
    }

    /**
     * Called after a user confirms the create of a user. This method creates a new {@link
     * UserProfileModel}* with the completed fields
     *
     * @return A {@link UserProfileModel} containing the attributes specified in {@link
     * #userCreationWindow()}
     */
    public UserProfileModel getNewlyCreatedUser() {
        return new UserProfileModel(
                (ProfileType) this.profileType.getSelectedItem(), this.profileName.getText(), 0);
    }

    /**
     * Used to clean up UI after the user is done creating a {@link UserProfileModel}
     */
    public void disposeCreateUser() {
        this.createUser.dispose();
    }

}
