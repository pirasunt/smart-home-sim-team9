package Controllers;

import Models.*;
import Views.CustomConsole;
import Views.HeatZoneCreator;
import Views.HeatingModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

enum Season {
    WINTER,
    SUMMER,
}

public class HeatingController {

    private HeatingModel heatingModel;
    private HeatingModule heatingView;

    public HeatingController(HeatingModel m, HeatingModule v) {
        this.heatingModel = m;
        this.heatingView = v;

        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
        heatingView.initializeView(heatingModel.getSummerStart(), heatingModel.getWinterStart());
        heatingView.createSummerChangeListener(new SummerChangeListener());
        heatingView.createWinterChangeListener(new WinterChangeListener());
    }

    public void createHeatingZone(String zoneName, Room[] rooms) {
        heatingModel.createHeatingZone(rooms, zoneName);
    }

    public ArrayList<HeatingZone> getHeatingZones() {
        return this.heatingModel.getHeatingZones();
    }

    public Room[] getAvailableRooms() {
        ArrayList<Room> allRooms = Context.getHouse().getRooms();
        ArrayList<Room> result = new ArrayList<>();

        for (Room room : allRooms) {
            if (isRoomAvailable(room)) {
                result.add(room);
            }
        }

        Room[] resultArr = new Room[result.size()];
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


    private class HeatingZoneCreationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            HeatZoneCreator heatZoneDialog = new HeatZoneCreator(new HeatingController(heatingModel, heatingView));
            heatZoneDialog.setLocationRelativeTo(null);
            heatZoneDialog.setVisible(true);
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
                heatingView.disposeDatePicker();

                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}
