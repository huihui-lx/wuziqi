package per.lx.wuziqi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;


public class GameListener extends MouseAdapter implements ActionListener {

    // 设置一个画笔
    private final Graphics g;

    // 记录棋子的数量的计数器
    private int count = 0;

    //设置记录棋子横坐标和纵坐标的2个数组
    private final int[] setX = new int[300];
    private final int[] setY = new int[300];

    //创建一个JPane对象
    private final JPanel jp;

    //创建一个标志
    private boolean flag;

    //创建一个字符串
    private String s = "人人对战";

    private int x1 = 0, y1 = 0;

    /*
    保存棋盘上的棋子信息
    0: 无棋子
    1. 黑棋
    2. 白棋
     */
    private final int[][] array = new int[Pub_Var.ROWS][Pub_Var.COLS];

    //创建一个电脑AI对象
    private AI ca;

    //创建一个权值数组
    private int[][] weightArray;


    public GameListener(Graphics g, JPanel jp, boolean flag) {
        this.g = g;
        this.jp = jp;
        this.flag = flag;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (flag) {
            if (x >= Pub_Var.X0 && y >= Pub_Var.X0 && x <= Pub_Var.X0 + (Pub_Var.ROWS - 1) * Pub_Var.SIZE &&
                    y <= Pub_Var.Y0 + (Pub_Var.ROWS - 1) * Pub_Var.SIZE) {
                // 算出棋子应该放在的地方(+ Config.SIZE/2)来控制精度
                int row = (y - Pub_Var.Y0 + Pub_Var.SIZE / 2) / Pub_Var.SIZE;
                int col = (x - Pub_Var.X0 + Pub_Var.SIZE / 2) / Pub_Var.SIZE;

                if (s.equals("人人对战")) {
                    playerPk(row, col);
                } else if (s.equals("人机对战")) {
                    pkComputer(row, col);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String str = e.getActionCommand();
        switch (str) {
            case "开始":
                flag = true;
                break;
            case "悔棋":
                if (count > 0) {
                    int x = setX[count];
                    int y = setY[count];

                    array[x][y] = 0;
                    count--;
                    //jp.repaint(0); //刷新重绘
                    //jp.paintImmediately();
                    jp.paintImmediately(0,0,1050,1000);
//                    for (int[] ints : array) {
//                        for (int j = 0; j < array[0].length; j++) {
//                            System.out.print(ints[j]);
//                        }
//                        System.out.println();
//                    }
//                    System.out.println("----------------------");
                    for (int i = 0; i < array.length; i++) {
                        for (int j = 0; j < array[0].length; j++) {
                            if (array[i][j] == 1) {
                                g.setColor(Color.BLACK);
                                g.fillOval(Pub_Var.X0 + j * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                                       Pub_Var.Y0 + i * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                                      Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);
                            } else if (array[i][j] == 2) {
                                g.setColor(Color.WHITE);
                                g.fillOval(Pub_Var.X0 + j * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                                        Pub_Var.Y0 + i * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                                        Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);
                            }
                        }
                    }
                }
                break;
            case "重新开始":
                for (int[] ints : array) {
                    Arrays.fill(ints, 0);
                }
                //计数器归0
                count = 0;
                jp.paintImmediately(0,0,1050,1000);
                break;
            case "结束游戏":
                //将保存棋子的数组归0
                for (int[] ints : array) {
                    Arrays.fill(ints, 0);
                }

                //计数器归0
                count = 0;
                jp.paintImmediately(0,0,1050,1000);
                flag = false;
                break;
            case "人机对战":
                s = "人机对战";
                ca = new AI(array);
                weightArray = ca.getWeightArray();
                break;
        }
    }

    //判断输赢得一方
    private void gameWin(int row, int col) {
        GameLogic cw = new GameLogic(array);
        if (array[row][col] == 1) {
            if (cw.IsWin(row, col)) {
                //winJF("黑棋获胜");
                new GameUI().winJF("黑棋获胜");
                flag = false;
            }
        } else if (array[row][col] == 2) {
            if (cw.IsWin(row, col)) {
                //winJF("白棋获胜");
                new GameUI().winJF("白棋获胜");
                flag = false;
            }
        }
    }

    //确定电脑下棋的下一步的行和列数
    private void computerNextStep() {
        int[] max = new int[weightArray.length];    //存储行最大权值
        for (int i = 0; i < weightArray.length; i++) {    //求出x方向上面的最大值
            int maxX = weightArray[i][0];
            for (int j = 0; j < weightArray[i].length; j++) {
                if (maxX < weightArray[i][j]) {
                    maxX = weightArray[i][j];
                }
            }
            max[i] = maxX;
        }
        int m = max[0];
        for (int i = 1; i < max.length; i++) {
            if (m < max[i]) {
                m = max[i];
            }
        }
        int flag = 0;
        //System.out.println("max=" + m);

        for (int i = 0; i < weightArray.length; i++) {
            for (int j = 0; j < weightArray[i].length; j++) {
                if (weightArray[i][j] == m) {            //找出最大是的行和列标
                    x1 = i;
                    y1 = j;
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                break;
            }
        }
    }

    //实现人机对战
    private void pkComputer(int r, int c) {
        if (array[r][c] == 0) {

            g.setColor(Color.BLACK);
            g.fillOval(Pub_Var.X0 + c * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                    Pub_Var.Y0 + r * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                    Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);
            array[r][c] = 1;    // 1表示该位置为黑棋
            count++;        //计数器加1
            gameWin(r, c);  //判断黑棋是否获胜

            ca.playGame();            //更新权值数组
            computerNextStep();    //电脑下棋的下一步

            //记录画出一个棋子后的x和y坐标
            setX[count] = r;
            setY[count] = c;
        }
        if (array[x1][y1] == 0) {
            g.setColor(Color.WHITE);
            g.fillOval(Pub_Var.X0 + y1 * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                    Pub_Var.Y0 + x1 * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                    Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);

            array[x1][y1] = 2;    //2表示该位置为白棋
            count++;        //计数器加一
            setX[count] = x1;
            setY[count] = y1;
        }
        //判断白棋是否获胜
        gameWin(x1, y1);
        //将有棋子位置的权值归0
        updateWeight();
    }

    //玩家对战: 执黑先行
    private void playerPk(int r, int c) {

        if (array[r][c] == 0) {
            if (count % 2 == 0) {
                array[r][c] = 1;
                g.setColor(Color.BLACK);
                g.fillOval(Pub_Var.X0 + c * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                        Pub_Var.Y0 + r * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2, Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);
            } else if (count % 2 == 1) {
                array[r][c] = 2;
                g.setColor(Color.WHITE);
                g.fillOval(Pub_Var.X0 + c * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                        Pub_Var.Y0 + r * Pub_Var.SIZE - Pub_Var.CHESS_SIZE / 2,
                        Pub_Var.CHESS_SIZE, Pub_Var.CHESS_SIZE);
            }
        }
        // 计数器加1
        count++;
        //判断赢得一方
        gameWin(r, c);

        //记录画出一个棋子后的x和y坐标
        setX[count] = r;
        setY[count] = c;
    }

    //下棋之后将有棋子位置的权值改为0
    private void updateWeight() {
        for (int[] ints : weightArray) {
            Arrays.fill(ints, 0);
        }
    }
}

