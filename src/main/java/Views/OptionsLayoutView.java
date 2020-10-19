package Views;

import Context.Environment;
import Context.UserProfile;
import Enums.profileType;
import Models.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Option frame.
 */
class OptionFrame extends JFrame {
    /**
     * The Panel.
     */
    OptionPanel panel;
    private static Environment env;
    // constructor
    OptionFrame(Environment environment) {
        env = environment;
        panel = new OptionPanel(env);
        this.add(panel);
        //TODO: Close window don't exit app lol
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        // add the label to the JFrame
    }
}

/**
 * The type Option panel.
 */
class OptionPanel extends JPanel {

    private static Environment env;
    public OptionPanel(Environment environment) {
        env = environment;
        JButton jrb1 = new JButton("1. Select User Profile");
        JButton jrb2 = new JButton("2. Select Location");
        JButton jrb3 = new JButton("3. Enter Simulation");
        Box box1 = Box.createVerticalBox();
        box1.add(jrb1);
        box1.add(jrb2);
        box1.add(jrb3);
        jrb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(env.isCurrentUserSet()) {
                    if(env.getCurrentUser().getRoomID() != -1) {
                        JFrame frame = new JFrame("Dash");
                        Dash d = new Dash(env);
                        JPanel jp = d.p1;
                        frame.setContentPane(jp);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);

                        // Little example on how to programmatically add tabs.
                        d.tabbedPane1.addTab("Example added tab", new JLabel("foo"));
                    } else {
                        Console.print("ERROR: Please set location for selected user: '" + env.getCurrentUser().getName() + "'");
                    }
                } else {
                    Console.print("ERROR: Please Select a User Profile before Entering the Simulation");
                }
            }
        });

        //Get all users from environment and display so that user can choose.
        jrb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Console.print("Selecting user profile...");
                UserProfile[] allProfiles =  env.getAllUserProfiles();

                JLabel adultLabel = new JLabel("Adult", SwingConstants.CENTER);
                JLabel childLabel = new JLabel("Child", SwingConstants.CENTER);
                JLabel guestLabel = new JLabel("Guest", SwingConstants.CENTER);
                JLabel strangerLabel = new JLabel("Stranger", SwingConstants.CENTER);

                GridLayout userSelectionGrid = new GridLayout(0, 4, 20,20);

                JFrame frame = new JFrame("User Selection");
                frame.setLayout(userSelectionGrid);

                //Labels
                frame.add(adultLabel);
                frame.add(childLabel);
                frame.add(guestLabel);
                frame.add(strangerLabel);

                UserProfile[][] organisedProfiles = {env.getProfilesByCategory(profileType.ADULT), env.getProfilesByCategory(profileType.CHILD), env.getProfilesByCategory(profileType.GUEST), env.getProfilesByCategory(profileType.STRANGER)};
                for(int i = 0; i< allProfiles.length*4; i++) {
                    int currentCol = i%4;
                    int currentRow = i/4;

                    switch(currentCol){
                        case 0: //adult column
                            if(organisedProfiles[0].length <= currentRow) {
                                frame.add(new JLabel());
                            } else {
                                JButton btn = new JButton(organisedProfiles[0][currentRow].getName());
                                btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        env.setCurrentUser(organisedProfiles[0][currentRow]);
                                        frame.dispose();
                                    }
                                });
                                frame.add(btn);
                            }
                            break;
                        case 1: //child column
                            if(organisedProfiles[1].length <=  currentRow) {
                                frame.add(new JLabel());
                            } else {
                                JButton btn = new JButton(organisedProfiles[1][currentRow].getName());
                                btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        env.setCurrentUser(organisedProfiles[1][currentRow]);
                                        frame.dispose();
                                    }
                                });
                                frame.add(btn);
                            }
                            break;
                        case 2: //guest column
                            if(organisedProfiles[2].length <= currentRow) {
                                frame.add(new JLabel());
                            } else {
                                JButton btn = new JButton(organisedProfiles[2][currentRow].getName());
                                btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        env.setCurrentUser(organisedProfiles[2][currentRow]);
                                        frame.dispose();
                                    }
                                });
                                frame.add(btn);
                            }
                            break;
                        case 3://stranger column
                            if(organisedProfiles[3].length <= currentRow) {
                                frame.add(new JLabel());
                            } else {
                                JButton btn = new JButton(organisedProfiles[3][currentRow].getName());
                                btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        env.setCurrentUser(organisedProfiles[3][currentRow]);
                                        frame.dispose();
                                    }
                                });
                                frame.add(btn);
                            }
                            break;
                    }

                }

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); //Center User Selection JFrame
                frame.setVisible(true);
            }
        });

        jrb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(env.isCurrentUserSet()) {

                Console.print("Selecting location for "  + env.getCurrentUser().getName() + "...");
                Room[] roomList = env.getRooms();


                GridLayout userSelectionGrid = new GridLayout(0, 3, 20,20);
                JFrame frame = new JFrame("Select Location for " + env.getCurrentUser().getName());
                frame.setLayout(userSelectionGrid);

                for(int i = 0; i< roomList.length; i++) {
                    Room currentRoom = roomList[i];
                    JButton btn = new JButton(currentRoom.getName());
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            env.modifyProfileLocation(env.getCurrentUser(),currentRoom);
                            frame.dispose();
                        }
                    });
                    frame.add(btn);
                }


                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); //Center User Selection JFrame
                frame.setVisible(true);

            } else {
                Console.print("ERROR: Please select a User first before setting a Location");
            }


            }
        });

        setLayout(new GridLayout(1, 1));
        add(box1);
    }
}
