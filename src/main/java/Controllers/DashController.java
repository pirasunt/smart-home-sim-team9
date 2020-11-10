package Controllers;

import Custom.NonExistantUserProfileException;
import Models.*;
import Views.CoreView;
import Views.CustomConsole;
import Views.Dash;
import Views.EditSimulationView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * The type Dash controller. Dashboard controller
 */
public class DashController {

    private EnvironmentModel theModel;
    private Dash theView;

    /**
     * Instantiates a new Dash controller.
     *
     * @param m the m
     * @param v the v
     */
    public DashController(EnvironmentModel m, Dash v){
        this.theModel = m;
        this.theView = v;

        initDash();
    }

    private void initDash(){
        UserProfileModel[] allProfiles = theModel.getAllUserProfiles();
        Room[] allRooms;


        for (int i = 0; i < allProfiles.length; i++) {
            boolean isCurrentUser = false;
            if (Context.getCurrentUser().getProfileID() == allProfiles[i].getProfileID())
                isCurrentUser = true;

            theView.addProfileToDropDown(allProfiles[i], isCurrentUser);
        }

        allRooms = theModel.getRooms();
    for (int i = allRooms.length - 1; i >= 0; i--) {
            boolean isCurrentRoom = false;
            if (Context.getCurrentUser().getRoomID() == allRooms[i].getId()) isCurrentRoom = true;

            theView.addRoomToDropDown(allRooms[i], isCurrentRoom);
        }

        theView.addUserDropDownListener(new UserDropDownListener());
        theView.addUserRoomDropDownListener(new UserRoomDropDownListener());
        theView.addSimulationToggleListener(new SimulationToggleListener());
        theView.addEditSimulationListener(new EditSimulationListener());
    }


    private class UserDropDownListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Room[] allRooms = theModel.getRooms();
            JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
            UUID newCurrentUserID = ((UserProfileModel) cb.getSelectedItem()).getProfileID();
            try {
                theModel.setCurrentUser(theModel.getUserByID(newCurrentUserID));
            } catch (NonExistantUserProfileException nonExistantUserProfileException) {
                nonExistantUserProfileException.printStackTrace();
            }

            if (Context.getCurrentUser().getRoomID() == 0) {
                theView.setRoomDropDownIndex(0);
            } else {
                for (int i = 0; i < allRooms.length; i++) {
                    if (allRooms[i].getId() == Context.getCurrentUser().getRoomID()) {
                        theView.setRoomDropDownItem(allRooms[i]);
                        break;
                    }
                }
            }

            theView.refreshDash();
        }
    }

    private class UserRoomDropDownListener implements ActionListener {
        /**
         * Invoked when the Room dropdown is selected and modified.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
            if (cb.getSelectedIndex() == -1) {
                CustomConsole.print("NO LOCATION HAS BEEN SET FOR: " + Context.getCurrentUser().getName());
            } else {
                Room newRoom = (Room) cb.getSelectedItem();
                if (newRoom.getId() != Context.getCurrentUser().getRoomID()) {
                    theModel.modifyProfileLocation(Context.getCurrentUser(), newRoom);
                }
            }
        }
    }

    private class SimulationToggleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EnvironmentModel.getSimulationRunning() == true) {
                SecurityModel.cancelAllTimers();
                theModel.stopSimulation();
                theView.changeSimulationToggleText("Start Simulation");
                Context.stopTimer();
            } else if (EnvironmentModel.getSimulationRunning() == false) {
                if (SecurityModel.isAwayOn()) {
                    SecurityModel.startAwayTimer();
                }
                theModel.startSimulation();
                theView.changeSimulationToggleText("Stop Simulation");
                Context.restartTimer();
            }

            CoreController.updateWindowSection();
        }
    }

    private class EditSimulationListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Pass on responsibility of editing the simulation to its own controller and view.
            // The Environment Model contains all the environment data that will be needed.


            EditSimulationView editSimView =
                    new EditSimulationView(theModel.getOutsideTemp(), Context.getDelay());
            new EditSimulationController(editSimView, theModel, EnvironmentModel.getSimulationRunning());

            editSimView.addWindowListener(new EditSimulationWindowListener());

        }

        private class EditSimulationWindowListener implements WindowListener {
            /**
             * Invoked the first time a window is made visible.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowOpened(WindowEvent e) {}

            /**
             * Invoked when the user attempts to close the window from the window's system menu.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {}

            /**
             * Invoked when a window has been closed as the result of calling dispose on the window.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosed(WindowEvent e) {
                theView.refreshDash(
                        Context.getDateString(),
                        Context.getTimeString(),
                        theModel.getOutsideTemp(),
                        Context.getDelay());
            }

            /**
             * Invoked when a window is changed from a normal to a minimized state. For many platforms, a
             * minimized window is displayed as the icon specified in the window's iconImage property.
             *
             * @param e the event to be processed
             * @see Frame#setIconImage
             */
            @Override
            public void windowIconified(WindowEvent e) {}

            /**
             * Invoked when a window is changed from a minimized to a normal state.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowDeiconified(WindowEvent e) {}

            /**
             * Invoked when the Window is set to be the active Window. Only a Frame or a Dialog can be the
             * active Window. The native windowing system may denote the active Window or its children
             * with special decorations, such as a highlighted title bar. The active Window is always
             * either the focused Window, or the first Frame or Dialog that is an owner of the focused
             * Window.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowActivated(WindowEvent e) {}

            /**
             * Invoked when a Window is no longer the active Window. Only a Frame or a Dialog can be the
             * active Window. The native windowing system may denote the active Window or its children
             * with special decorations, such as a highlighted title bar. The active Window is always
             * either the focused Window, or the first Frame or Dialog that is an owner of the focused
             * Window.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowDeactivated(WindowEvent e) {}
        }
    }

}
