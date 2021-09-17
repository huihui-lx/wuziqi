package per.lx.wuziqi;


import javax.swing.*;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JPanel implements Pub_Var {


    //游戏开始表示
    boolean flag = false;

    //初始化
    public void inIt() {

        //创建一个窗体,并给其设置属性
        JFrame jFrame = new JFrame("五子棋");
        jFrame.setVisible(true);
        jFrame.setBounds(300, 20, 1300, 1000);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);


        //面板1: 棋盘
        this.setBounds(0, 0, 1050, 1000);
        jFrame.add(this);

        //面板2: 按键
        JPanel jp = new JPanel(){
            @Override
            public void paint(Graphics g) {
                ImageIcon chessBoardImageIcon = new ImageIcon(this.getClass().getResource("/static/img_2.png"));
                g.drawImage(chessBoardImageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        jp.setBounds(1050, 0, 240, 1000);
        jFrame.add(jp);



        //添加按钮
        JButton startB = new JButton("开始");
        createButton(jp, startB, 45, 120);

        JButton repentB = new JButton("悔棋");
        createButton(jp, repentB, 45, 240);


        JButton restartB = new JButton("重新开始");
        createButton(jp, restartB, 45, 360);

        JButton endgameB = new JButton("结束游戏");
        createButton(jp, endgameB, 45, 480);

        JButton computerPk = new JButton("人机对战");
        createButton(jp, computerPk, 45, 600);

        JButton computerExit = new JButton("退出游戏");
        computerExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               jFrame.dispose();
            }
        });
        createButton(jp, computerExit, 45, 720);

        //创建一支画笔
        Graphics g = this.getGraphics();

        //创建一个监听器
        GameListener gL = new GameListener(g, this, flag);

        this.addMouseListener(gL);


        //给按钮添加动作监听器
        startB.addActionListener(gL);
        repentB.addActionListener(gL);
        restartB.addActionListener(gL);
        endgameB.addActionListener(gL);
        computerPk.addActionListener(gL);
        computerExit.addActionListener(gL);

    }

    @Override
    public void paint(Graphics g) {
        //画出五子棋的棋盘
        //drawChessTable(g);
        Graphics2D g2 = (Graphics2D) g;  //g是Graphics对象
        g2.setStroke(new BasicStroke(2.0f));

        ImageIcon chessBoardImageIcon = new ImageIcon(this.getClass().getResource("/static/img.png"));
        g2.drawImage(chessBoardImageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

        for (int i = 0; i < Pub_Var.ROWS; i++) {
            g.drawLine(Pub_Var.X0, Pub_Var.Y0 + Pub_Var.SIZE * i, Pub_Var.X0 + (Pub_Var.ROWS - 1) * Pub_Var.SIZE, Pub_Var.Y0 + Pub_Var.SIZE * i);
            g.drawLine(Pub_Var.X0 + Pub_Var.SIZE * i, Pub_Var.Y0, Pub_Var.X0 + Pub_Var.SIZE * i, Pub_Var.Y0 + (Pub_Var.ROWS - 1) * Pub_Var.SIZE);
        }
    }

    //赢棋的时候弹出的提示框
    protected void winJF(String str) {
        //创建一个新的提示框体
        JFrame jf = new JFrame();
        //设置其属性
        jf.setSize(300, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        //使用空布局
        jf.setLayout(null);
        //创建一个按钮，并设置其大小
        JButton jbu = new JButton(str);
        jbu.setBounds(80, 50, 120, 40);
        jf.add(jbu);

        jbu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
    }

    //创建按钮
    public void createButton(JPanel jpa, JButton jb, int x, int y) {
        jpa.setLayout(null);
        jb.setFont(new Font("宋体", Font.BOLD, 16));
        jb.setBackground(new Color(23, 151, 219));
        jb.setBounds(x, y, 150, 60);
        jpa.add(jb);
    }

}