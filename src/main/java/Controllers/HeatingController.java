package Controllers;

import Models.*;
import Observers.AwayChangeObserver;
import Views.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

enum Season {
    WINTER,
    SUMMER,
}

public class HeatingController implements AwayChangeObserver {

    private HeatingModel heatingModel;
    private static HeatingModel sHeatingModel;
    private HeatingModule heatingView;
    private HeatZoneCreator heatZoneDialog;
    private ArrayList<Room> selectedRoomsMemory;

    public HeatingController(HeatingModel m, HeatingModule v) {
        this.heatingModel = m;
        this.heatingView = v;
        sHeatingModel = m;
        SecurityModel.subscribe(this);
        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
        heatingView.createSummerChangeListener(new SummerChangeListener());
        heatingView.createWinterChangeListener(new WinterChangeListener());

        createHeatingComponents();

    }
    public static HeatingModel getStaticHeatingModel() {
        return sHeatingModel;
    }
    public static void updateTimePeriodTemps(){
        for(HeatingZone hz: sHeatingModel.getHeatingZones()){
            hz.updateTimePeriodTemp();
        }
    }

    private void createHeatingComponents(){

         ArrayList<Room> rooms = Context.getHouse().getRooms();

         ArrayList<JLabel> roomNames = new ArrayList<>();
         ArrayList<JLabel> roomTemps = new ArrayList<>();
         ArrayList<JButton> editRoomTempBtns = new ArrayList<>();

         for(int i =0; i < rooms.size(); i++){
             roomNames.add(new JLabel(rooms.get(i).getName()));
             roomTemps.add(rooms.get(i).getRoomTempLabel());

             JButton editRoomTempBtn = new JButton("Edit");

             int index = i;
             editRoomTempBtn.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     Room r = rooms.get(index);
                     EditRoomTemp ert = new EditRoomTemp(r);

                     ert.addSaveListener(new ActionListener() {
                         @Override
                         public void actionPerformed(ActionEvent e) {

                             if(ert.getYesRadioButton().isSelected()){
                                 if(!r.isTempOverriden()) {
                                     r.setRoomTempSetting(true);
                                     r.getRoomTempLabel().setText(r.getRoomTempLabel().getText() + " [OVERRIDDEN]");
                                 }
                                 r.manualSetTemperature(ert.getNewTemp());
                             } else {
                                 int zoneTemp = r.getTemperature();
                                 if(r.isTempOverriden()) {
                                     r.setRoomTempSetting(false);

                                     for (HeatingZone hz : sHeatingModel.getHeatingZones()) {
                                         for (Room room : hz.getRooms()) {
                                             if (r.getId() == room.getId())
                                                 zoneTemp = hz.getTemperature();
                                         }
                                     }
                                 }
                                 r.manualSetTemperature(zoneTemp); //return room to its zoneTemp.
                             }

                             ert.dispose();
                         }
                     });

                     ert.addTurnOffOverrideListener(new ActionListener() {
                         @Override
                         public void actionPerformed(ActionEvent e) {
                             ert.getTempChangeDiag().setVisible(false);
                         }
                     });

                     ert.addTurnOnOverrideListener(new ActionListener() {
                         @Override
                         public void actionPerformed(ActionEvent e) {
                            ert.getTempChangeDiag().setVisible(true);

                         }
                     });

                     ert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                     ert.setLocationRelativeTo(null);
                     ert.setVisible(true);
                 }
             });


             editRoomTempBtns.add(editRoomTempBtn);
         }

        heatingView.initializeView(heatingModel.getSummerStart(), heatingModel.getWinterStart(), heatingModel.getAwayTempSpinner(), heatingModel.getDangerTempSpinner(), roomNames, roomTemps, editRoomTempBtns);
    }


    public ArrayList<HeatingZone> getHeatingZones() {
        return this.heatingModel.getHeatingZones();
    }

    private void refreshHeatZones(){
        ArrayList<HeatingZone> allZones = this.heatingModel.getHeatingZones();

        ArrayList<JLabel> zoneNames = new ArrayList<>();
        ArrayList<JLabel> zoneTemps = new ArrayList<>();
        ArrayList<JButton> editZoneButtons = new ArrayList<>();

        for(int i = 0; i < allZones.size(); i++){
            zoneNames.add(new JLabel(allZones.get(i).getName()));
            zoneTemps.add(allZones.get(i).getTempLabel());

            JButton editBtn = new JButton("Edit");

            int index = i;
            editBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EditZone editZoneDiag = new EditZone(allZones.get(index));
                    editZoneDiag.addEditCancelButtonListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            editZoneDiag.dispose();
                        }
                    });

                    editZoneDiag.addEditConfirmButtonListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            allZones.get(index).setMorningTemp(editZoneDiag.getUpdatedMorningTemp());
                            allZones.get(index).setAfternoonTemp(editZoneDiag.getUpdatedAfternoonTemp());
                            allZones.get(index).setNightTemp(editZoneDiag.getUpdatedNightTemp());

                            editZoneDiag.dispose();
                        }
                    });

                    editZoneDiag.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    editZoneDiag.setLocationRelativeTo(null);
                    editZoneDiag.setVisible(true);

                }
            });

            editZoneButtons.add(editBtn);
        }
        heatingView.refreshCurrentHeatZones(zoneNames, zoneTemps, editZoneButtons);
    }

    private Room[] getAvailableRooms() {
        ArrayList<Room> allRooms = Context.getHouse().getRooms();
        ArrayList<Room> result = new ArrayList<>();

        for (Room room : allRooms) {
            if (isRoomAvailable(room)) {
                result.add(room);
            }
        }

        Room[] resultArr = new Room[result.size()];
        for(int i =0; i < resultArr.length; i++)
            resultArr[i] = result.get(i);
        return resultArr;
    }

    private boolean isRoomAvailable(Room room) {
        for (HeatingZone zone : getHeatingZones()) {
            if (zone.getRooms().contains(room)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void update(boolean awayIsOn) {
        // TODO remove prints
        // triggered when heating mode changes
        if (awayIsOn) {
            System.out.println("HeatingController says awayIsOn");
            heatingModel.setAwayModeTemp(true);
        } else {
            System.out.println("HeatingController says not awayIsOn");
            heatingModel.setAwayModeTemp(false);

        }
    }


    private class HeatingZoneCreationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            heatZoneDialog = new HeatZoneCreator();
            heatZoneDialog.addCancelButtonListener(new ZoneCancelButtonListener());
            heatZoneDialog.addConfirmButtonListener(new ZoneConfirmButtonListener());

            createAvailableRoomUIComponents();

            heatZoneDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            heatZoneDialog.setLocationRelativeTo(null);
            heatZoneDialog.setVisible(true);
        }

        private void createAvailableRoomUIComponents(){

            Room[] availRoom = getAvailableRooms();
            ArrayList<JLabel> roomLabels = new ArrayList<>();
            ArrayList<JCheckBox> roomCheckboxes = new ArrayList<>();
            selectedRoomsMemory = new ArrayList<>();

            for(Room r: availRoom){
                roomLabels.add(new JLabel(r.getName()));
                JCheckBox roomCheck = new JCheckBox();

                roomCheck.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.SELECTED){
                            selectedRoomsMemory.add(r);
                        } else{
                            selectedRoomsMemory.remove(r);
                        }
                    }
                });

                roomCheckboxes.add(roomCheck);
            }

            heatZoneDialog.addAvailableRoomsToUI(roomLabels, roomCheckboxes);
        }

        private class ZoneCancelButtonListener implements ActionListener {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                heatZoneDialog.dispose();
            }
        }

        private class ZoneConfirmButtonListener implements ActionListener {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (heatZoneDialog.getZoneName().length()>0 && selectedRoomsMemory.size() > 0) {

                    Room[] selectedRooms = new Room[selectedRoomsMemory.size()];
                    for(int i =0; i < selectedRooms.length; i++)
                        selectedRooms[i] = selectedRoomsMemory.get(i);

                    heatingModel.createHeatingZone(selectedRooms,heatZoneDialog.getZoneName(),
                            heatZoneDialog.getMorningTemp(),
                            heatZoneDialog.getAfternoonTemp(),
                            heatZoneDialog.getNightTemp());

                    refreshHeatZones();

                    heatZoneDialog.dispose();
                } else
                    CustomConsole.print("Make sure to name the heating zone you are creating, as well as select at least 1 room.");
            }
        }
    }

    private class SummerChangeListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            heatingView.changeDate(new SeasonDateFormatter(Season.SUMMER));

        }
    }

    private class WinterChangeListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            heatingView.changeDate(new SeasonDateFormatter(Season.WINTER));
        }
    }

    private class SeasonDateFormatter extends JFormattedTextField.AbstractFormatter {


        private final String datePattern = "MMMM dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        private Season seasonType;

        public SeasonDateFormatter(Season type){
            this.seasonType=type;
        }

        /**
         * Parses <code>text</code> returning an arbitrary Object. Some
         * formatters may return null.
         *
         * @param text String to convert
         * @return Object representation of text
         * @throws ParseException if there is an error in the conversion
         */
        @Override
        public Object stringToValue(String text) throws ParseException {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) dateFormatter.parseObject(text));
            return cal;
        }

        /**
         * Returns the string value to display for <code>value</code>.
         *
         * @param value Value to convert
         * @return String representation of value
         * @throws ParseException if there is an error in the conversion
         */
        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                if(seasonType == Season.SUMMER) {
                    if(heatingModel.getWinterStart().getMonth() == cal.get(Calendar.MONTH) && heatingModel.getWinterStart().getDate() == cal.get(Calendar.DAY_OF_MONTH))
                        CustomConsole.print("ERROR: Winter and Summer Start Dates can not be the same. Please choose a different Date");
                    else {
                        heatingView.updateSummerStartLabel(dateFormatter.format(cal.getTime()));
                        heatingModel.updateSummerStart(cal.getTime());
                    }

                } else if(seasonType == Season.WINTER){
                    if(heatingModel.getSummerStart().getMonth() == cal.get(Calendar.MONTH) && heatingModel.getSummerStart().getDate() == cal.get(Calendar.DAY_OF_MONTH))
                        CustomConsole.print("ERROR: Winter and Summer Start Dates can not be the same. Please choose a different Date");
                    else {
                        heatingView.updateWinterStartLabel(dateFormatter.format(cal.getTime()));
                        heatingModel.updateWinterStart(cal.getTime());
                    }
                }
                refreshHeatZones();

                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
