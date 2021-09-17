package per.lx.wuziqi;

import javax.swing.*;
import java.awt.*;

public class Draw {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setBounds(100,100,500,500);
        jFrame.add(new MyPanel());

    }
}

class MyPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        g.drawOval(20,40,120,120);
    }
}
