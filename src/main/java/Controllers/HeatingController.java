package Controllers;

import Models.*;
import Observers.AwayChangeObserver;
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

public class HeatingController implements AwayChangeObserver {

  private static HeatingModel sHeatingModel;
  private final HeatingModel heatingModel;
  private final HeatingModule heatingView;
  private HeatZoneCreator heatZoneDialog;

  public HeatingController(HeatingModel m, HeatingModule v) {
    this.heatingModel = m;
    this.heatingView = v;
    sHeatingModel = m;
    SecurityModel.subscribe(this);
    heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
    heatingView.initializeView(
        heatingModel.getSummerStart(),
        heatingModel.getWinterStart(),
        heatingModel.getAwayTempSpinner(),
        heatingModel.getDangerTempSpinner());
    heatingView.createSummerChangeListener(new SummerChangeListener());
    heatingView.createWinterChangeListener(new WinterChangeListener());
  }

  public static HeatingModel getStaticHeatingModel() {
    return sHeatingModel;
  }

  public void createHeatingZone(String zoneName, Room[] rooms) {
    heatingModel.createHeatingZone(rooms, zoneName);
  }

  private boolean isRoomAvailable(Room room) {
    for (HeatingZone zone : getHeatingZones()) {
      if (zone.getRooms().contains(room)) {
        return false;
      }
    }

    return true;
  }

  public ArrayList<HeatingZone> getHeatingZones() {
    return this.heatingModel.getHeatingZones();
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

      heatZoneDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      heatZoneDialog.setLocationRelativeTo(null);
      heatZoneDialog.setVisible(true);
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
        if (heatZoneDialog.getZoneName().length() > 0 /*&& some rooms are selected*/) {

          // call controller.createHeatingZone(zoneName.getText(), rooms);

          // refresh the SHH to display a list of all existing zones
          // can maybe use an observer (?), not necessary though
          // controller.getHeatingZones() should return all existing zones

          // code to test without UI, dont forget to remove
          Room[] testRooms = {
            Context.getHouse().getRooms().get(1), Context.getHouse().getRooms().get(2)
          };
          createHeatingZone(heatZoneDialog.getZoneName(), testRooms);

          System.out.println(getHeatingZones().get(0).getName());

          heatZoneDialog.dispose();
        } else
          CustomConsole.print(
              "Make sure to name the heating zone you are creating, as well as select at least 1 room.");
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
    private final Season seasonType;

    public SeasonDateFormatter(Season type) {
      this.seasonType = type;
    }

    /**
     * Parses <code>text</code> returning an arbitrary Object. Some formatters may return null.
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
        if (seasonType == Season.SUMMER) {
          if (heatingModel.getWinterStart().getMonth() == cal.get(Calendar.MONTH)
              && heatingModel.getWinterStart().getDate() == cal.get(Calendar.DAY_OF_MONTH))
            CustomConsole.print(
                "ERROR: Winter and Summer Start Dates can not be the same. Please choose a different Date");
          else {
            heatingView.updateSummerStartLabel(dateFormatter.format(cal.getTime()));
            heatingModel.updateSummerStart(cal.getTime());
          }

        } else if (seasonType == Season.WINTER) {
          if (heatingModel.getSummerStart().getMonth() == cal.get(Calendar.MONTH)
              && heatingModel.getSummerStart().getDate() == cal.get(Calendar.DAY_OF_MONTH))
            CustomConsole.print(
                "ERROR: Winter and Summer Start Dates can not be the same. Please choose a different Date");
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
