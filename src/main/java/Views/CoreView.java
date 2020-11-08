package Views;



import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class CoreView extends JFrame{
    private JPanel windowPanels;
    private JPanel topLevelPane; //Needs to be bound to a field for the Form Builder to build CoreView JFrame
    private JScrollPane scrollPane;
    private JPanel outsideDoorPanel;
    private JPanel lightsPanel;

    public CoreView(){
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(18); //Helps scroll faster
    }

    public void displayWindowSection(ArrayList<JLabel> labels, ArrayList<JRadioButton> openBtns, ArrayList<JRadioButton> closeBtns, ArrayList<JCheckBox> obstructBtns){

        this.windowPanels.setLayout(new GridLayout(0, 4, 1, 20));
        this.windowPanels.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for(int i =0; i < labels.size(); i++){
            this.windowPanels.add(labels.get(i));
            this.windowPanels.add(openBtns.get(i));
            this.windowPanels.add(closeBtns.get(i));
            this.windowPanels.add(obstructBtns.get(i));
        }
    }

    public void displayOutsideDoorsSection(ArrayList<JLabel> labels, ArrayList<JRadioButton> lockBtns, ArrayList<JRadioButton> unlockBtns){

        this.outsideDoorPanel.setLayout(new GridLayout(0, 3, 1, 20));
        this.outsideDoorPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for(int i =0; i < labels.size(); i++){
            this.outsideDoorPanel.add(labels.get(i));
            this.outsideDoorPanel.add(lockBtns.get(i));
            this.outsideDoorPanel.add(unlockBtns.get(i));
        }
    }


    public void displayLightsSection(ArrayList<JLabel> labels, ArrayList<JRadioButton> onBtns, ArrayList<JRadioButton> offBtns){

        this.lightsPanel.setLayout(new GridLayout(0, 3, 1, 20));
        this.lightsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        for(int i=0; i<labels.size(); i++){
            this.lightsPanel.add(labels.get(i));
            this.lightsPanel.add(onBtns.get(i));
            this.lightsPanel.add(offBtns.get(i));
        }
    }

}
