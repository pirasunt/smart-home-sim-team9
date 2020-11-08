package Controllers;

import Enums.WallType;
import Models.EnvironmentModel;
import Models.Room;
import Models.Walls.OutsideWall;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CoreView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CoreController {

    private CoreView theView;
    private EnvironmentModel theModel;

    public CoreController(CoreView v, EnvironmentModel m) {
        this.theModel = m;
        this.theView =v;

        this.createWindowSectionComponents();
        this.createOutsideDoorComponents();
        this.createLightsSection();
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
                            window.openWindow();
                        }
                    });

                    closeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            window.closeWindow();
                        }
                    });

                    obstructButton.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {

                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                window.obstructWindow();
                            } else {
                                window.unobstructWindow();
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
                                door.lockDoor();
                            }
                        });

                        unlockButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                door.unlockDoor();
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
                    allRooms[currentRoomIndex].turnOnLights();
                }
            });

            offButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    allRooms[currentRoomIndex].turnOffLights();
                }
            });

            onButtons.add(onButton);
            offButtons.add(offButton);

        }

        theView.displayLightsSection(lightLabels, onButtons, offButtons);
    }



}
