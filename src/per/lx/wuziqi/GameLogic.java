package per.lx.wuziqi;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;

public class GameLogic {
    //定义一个存储棋子的数组
    int[][] array;

    public GameLogic(int[][] array) {
        this.array = array;
    }

    // 判断是否五子相连
    public boolean IsWin(int r, int c) {

        boolean flag = false;
        if (countX(r, c) >= 5 || countY(r, c) >= 5 || countXy(r, c) >= 5 || countYx(r, c) >= 5) {
            flag = true;
        }
        return flag;
    }


    //  判断水平方向上相连的棋子数

    private int countX(int r, int c) {
        int count = 1;
        for (int i = c + 1; i < array[r].length; i++) {    //向右检查
            if (array[r][i] == array[r][c]) {
                count++;

            } else {
                break;
            }
        }
        for (int i = c - 1; i >= 0; i--) {    //向左检查
            if (array[r][i] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    // 判断竖直方向上相连的棋子数
    private int countY(int r, int c) {
        int count = 1;

        //向上统计
        for (int i = r - 1; i >= 0; i--) {
            if (array[i][c] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        //向下统计
        for (int i = r + 1; i < array.length; i++) {
            if (array[i][c] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    //判断45度方向上相连的棋子
    private int countXy(int r, int c) {
        int count = 1;

        //向右上角统计
        for (int i = c + 1, a = r - 1; i < array.length && a >= 0; i++, a--) {
            if (array[a][i] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        //左下角统计
        for (int j = r + 1, b = c - 1; j < array.length && b >= 0; j++, b--) {
            if (array[j][b] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    //判断135度方向上相连的棋子数

    private int countYx(int r, int c) {
        int count = 1;

        //左上角统计
        for (int i = c - 1, a = r - 1; i >= 0 && a >= 0; i--, a--) {
            if (array[a][i] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        //右下角统计
        for (int j = c + 1, b = r + 1; j < array.length && b < array.length; j++, b++) {
            if (array[b][j] == array[r][c]) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

}