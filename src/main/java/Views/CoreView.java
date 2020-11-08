package Views;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class CoreView extends JFrame{
    private JPanel windowPanels;
    private JPanel topLevelPane;
    private JScrollPane scrollPane;

    public CoreView(){
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(18); //Helps scroll faster
    }

    public void displayWindowSection(ArrayList<JLabel> labels, ArrayList<JRadioButton> openBtns, ArrayList<JRadioButton> closeBtns, ArrayList<JCheckBox> obstructBtns){

        this.windowPanels.setLayout(new GridLayout(0, 4, 20, 20));

        for(int i =0; i < labels.size(); i++){
            this.windowPanels.add(labels.get(i));
            this.windowPanels.add(openBtns.get(i));
            this.windowPanels.add(closeBtns.get(i));
            this.windowPanels.add(obstructBtns.get(i));
        }



    }
}
