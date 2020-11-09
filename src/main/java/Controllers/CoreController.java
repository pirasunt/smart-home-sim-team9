package Controllers;

import Enums.WallType;
import Models.EnvironmentModel;
import Models.Room;
import Models.Walls.OutsideWall;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Observers.RoomChangeObserver;
import Views.CoreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class CoreController implements RoomChangeObserver {

    private CoreView theView;
    private EnvironmentModel theModel;
    private ArrayList<Room> previousLightConfiguration;
    private final CoreController selfReference; //For inner classes


    public CoreController(CoreView v, EnvironmentModel m) {
        this.theModel = m;
        this.theView =v;
        this.selfReference= this;
        this.createWindowSectionComponents();
        this.createOutsideDoorComponents();
        this.createLightsSection();

        theView.addTurnOnAutoLightsListener(new AutoLightTurnOnListener());
        theView.addTurnOffAutoLightsListener(new AutoLightTurnOffListener());

    }

    //Creates the Window Section buttons and adds functionality to each button
    private void createWindowSectionComponents(){

        Room[] allRooms = theModel.getRooms();

        ArrayList<JLabel> windowLabels = new ArrayList<>();
        ArrayList<JRadioButton>  openButtons = new ArrayList<>();
        ArrayList<JRadioButton> closeButtons = new ArrayList<>();
        ArrayList<JCheckBox> obstructButtons = new ArrayList<>();


        for(int i =0; i < allRooms.length; i++) {
            String labelString = allRooms[i].getName();
            Wall[] allWalls = allRooms[i].getAllWalls();

            int windowNumber = 0;
            for (int j = 0; j < allWalls.length; j++) {
                if(allWalls[j] != null){
                if (allWalls[j].getType() == WallType.WINDOWS) {
                    windowNumber++;
                    WindowWall window = (WindowWall) allWalls[j];
                    windowLabels.add(new JLabel(labelString + " (#" + windowNumber + ")"));

                    JRadioButton openButton = new JRadioButton("Open");
                    JRadioButton closeButton = new JRadioButton("Close");
                    JCheckBox obstructButton = new JCheckBox("Obstruct");
                    ButtonGroup windowButtonGroup = new ButtonGroup();
                    windowButtonGroup.add(openButton);
                    windowButtonGroup.add(closeButton);

                    if (window.isWindowOpen()) {
                        openButton.setSelected(true);
                    } else {
                        closeButton.setSelected(true);
                    }

                    if (window.isWindowObstructed()) {
                        obstructButton.setSelected(true);
                    } else {
                        obstructButton.setSelected(false);
                    }


                    openButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(!window.isWindowOpen()) {
                                window.setWindowOpen(true);
                            }
                        }
                    });

                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(window.isWindowOpen()) {
                                window.setWindowOpen(false);
                            }
                        }
                    });

                    obstructButton.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {

                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                window.setWindowObstructed(true);
                            } else {
                                window.setWindowObstructed(false);
                            }
                        }
                    });
                    openButtons.add(openButton);
                    closeButtons.add(closeButton);
                    obstructButtons.add(obstructButton);
                }

            }
            }
        theView.displayWindowSection(windowLabels, openButtons, closeButtons, obstructButtons);
        }

    }


    private void createOutsideDoorComponents(){

        Room[] allRooms = theModel.getRooms();

        ArrayList<JLabel> doorLabels = new ArrayList<>();
        ArrayList<JRadioButton>  lockButtons = new ArrayList<>();
        ArrayList<JRadioButton> unlockButtons = new ArrayList<>();

        for(int i = 0; i < allRooms.length; i++){
            Wall[] allWalls = allRooms[i].getAllWalls();

            for(int j=0; j < allWalls.length; j++){
                if(allWalls[j] != null){
                    if(allWalls[j].getType() == WallType.OUTSIDE){
                        OutsideWall door =  (OutsideWall) allWalls[j];

                        doorLabels.add(new JLabel(allRooms[i].getName() + " door"));
                        JRadioButton lockButton = new JRadioButton("Lock");
                        JRadioButton unlockButton = new JRadioButton("Unlock");
                        ButtonGroup doorButtonGroup = new ButtonGroup();
                        doorButtonGroup.add(lockButton);
                        doorButtonGroup.add(unlockButton);

                        if(door.getDoorLocked()) {
                            lockButton.setSelected(true);
                        } else {
                            unlockButton.setSelected(true);
                        }

                        lockButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!door.getDoorLocked()) {
                                    door.setDoorLocked(true);
                                }
                            }
                        });

                        unlockButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(door.getDoorLocked()) {
                                    door.setDoorLocked(false);
                                }
                            }
                        });

                        lockButtons.add(lockButton);
                        unlockButtons.add(unlockButton);

                    }
                }
            }
        }

        theView.displayOutsideDoorsSection(doorLabels, lockButtons, unlockButtons);
    }


    private void createLightsSection(){

        Room[] allRooms = theModel.getRooms();

        ArrayList<JLabel> lightLabels = new ArrayList<>();
        ArrayList<JRadioButton>  onButtons = new ArrayList<>();
        ArrayList<JRadioButton> offButtons = new ArrayList<>();

        for(int i =0; i < allRooms.length; i++){

            lightLabels.add(new JLabel(allRooms[i].getName()));

            JRadioButton onButton = new JRadioButton("On");
            JRadioButton offButton = new JRadioButton("Off");
            ButtonGroup lightsBtnGroup = new ButtonGroup();
            lightsBtnGroup.add(onButton);
            lightsBtnGroup.add(offButton);

            if(allRooms[i].getLightsOn()){
                onButton.setSelected(true);
            } else {
                offButton.setSelected(true);
            }

            int currentRoomIndex = i;
            onButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!allRooms[currentRoomIndex].getLightsOn()) {
                        allRooms[currentRoomIndex].turnOnLights();
                    }
                }
            });

            offButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(allRooms[currentRoomIndex].getLightsOn()) {
                        allRooms[currentRoomIndex].turnOffLights();
                    }
                }
            });

            onButtons.add(onButton);
            offButtons.add(offButton);

        }

        theView.displayLightsSection(lightLabels, onButtons, offButtons, theModel.getAutomaticLights());
    }


    private class AutoLightTurnOnListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if(!theModel.getAutomaticLights()){
                theModel.setAutomaticLights(true);
                theView.setLightButtonStatus(false); //Disables manual radio buttons for each room

                //Save Previous Config
                previousLightConfiguration = new ArrayList<>();
                Room[] allRooms = theModel.getRooms();

                for(Room r: allRooms){
                    if(r.getLightsOn())
                        previousLightConfiguration.add(r);
                }

                //Initial Setup for Auto-Lighting
                for(Room r:allRooms){
                    if(r.getAllUsersInRoom(theModel).size() == 0){
                        r.setLightsOn(false);
                    } else {
                        r.setLightsOn(true);
                    }
                }

                EnvironmentModel.subscribe(selfReference);


            }
        }
    }

    private class AutoLightTurnOffListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            if(theModel.getAutomaticLights()){
                theModel.setAutomaticLights(false);
                theView.setLightButtonStatus(true); //Enables manual radio buttons for each room

                Room[] allRooms = theModel.getRooms();

                for(Room r: allRooms){
                    r.setLightsOn(false);
                }
                if(previousLightConfiguration != null) {
                    for (Room r : previousLightConfiguration) {
                        r.setLightsOn(true);
                    }
                }
                previousLightConfiguration = null;

                EnvironmentModel.unsubscribe(selfReference);

            }

        }
    }

    @Override
    public void update(int oldRoomID, int newRoomID) {
        if (oldRoomID > 0 && newRoomID > 0) {
            Room oldRoom = theModel.getRoomByID(oldRoomID);
            Room newRoom = theModel.getRoomByID(newRoomID);

            //Turn off light if there is no one remaining in the room
            if (oldRoom.getAllUsersInRoom(theModel).size() == 0) {
                oldRoom.setLightsOn(false);
            }

                newRoom.setLightsOn(true);

        }
    }
}
