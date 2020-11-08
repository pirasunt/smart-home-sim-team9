package Controllers;

import Enums.WallType;
import Models.EnvironmentModel;
import Models.Room;
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



}
