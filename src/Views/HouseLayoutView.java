package Views;

import java.awt.*;
import javax.swing.*;

class HouseLayoutView extends JFrame {
    JPanel panel;
    JLabel label;

    // constructor
    HouseLayoutView() {
        JScrollPane scrollPane = new JScrollPane(new JPanel());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setPreferredSize(new Dimension(400, 400));
        this.add(scrollPane);

        this.getContentPane().add(scrollPane);

        //TODO: Close window don't exit app lol
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}

class InsidePane extends JPanel {
    private static final int SPA_WIDTH = 600;
    private static final int SPA_HEIGHT = 600;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.drawRect(0,0,50,50);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SPA_WIDTH, SPA_HEIGHT);
    }
}

