package per.lx.wuziqi;

import java.util.HashMap;

public class AI implements Pub_Var {

    //记录棋子的数组
    private final int[][] chessArray;

    //各个位置权值的数组
    private final int[][] weightArray = new int[Pub_Var.ROWS][Pub_Var.ROWS];

    //权值表
    private final HashMap<String, Integer> weightMap = new HashMap<>();

    //记录棋子颜色变量
    private int chess = 0;
    private String str = "";


    public AI(int[][] chessArray) {
        super();
        this.chessArray = chessArray;
    }

    //自动博弈
    public void playGame() {
        //设置权值表
        putMap();
        //扫扫描整个数组
        for (int i = 0; i < chessArray.length; i++) {
            for (int j = 0; j < chessArray[i].length; j++) {
                if (chessArray[i][j] == 0) {
                    //向左扫描，并得到权值
                    scanXLeft(i, j);
                    getWeight(str, i, j);
                    //向右扫描，并得到权值
                    scanXRight(i, j);
                    getWeight(str, i, j);
                    //向上扫描并得到权值
                    scanYUp(i, j);
                    getWeight(str, i, j);
                    //向下扫描并的到权值
                    scanYDown(i, j);
                    getWeight(str, i, j);
                    //向右上角扫面并的到权值
                    scanXyUp(i, j);
                    getWeight(str, i, j);
                    //左下角扫描并的到权值
                    scanXyDown(i, j);
                    getWeight(str, i, j);
                    //向左上角扫描并的到权值
                    scanYxUp(i, j);
                    getWeight(str, i, j);
                    //向右下角扫面
                    scanYxDown(i, j);
                    getWeight(str, i, j);
                }
            }
        }
    }

    //返回该位置的权值
    private void getWeight(String str, int i, int j) {
        //根据棋局字符串去权值表找权值
        Integer weight = weightMap.get(str);
        if (weight == null) {
            weight = 0;
        }
        weightArray[i][j] += weight;
    }

    //将权值数组传递出去
    public int[][] getWeightArray() {
        return weightArray;
    }



    //设置哈希表的权值
    private void putMap() {

        //黑棋活四连
        weightMap.put("11110", 1000000);
        //黑棋死四连
        weightMap.put("11112", 1000000);
        //黑棋活三连
        weightMap.put("1110", 10000);
        //黑棋死三连
        weightMap.put("1112", 5000);
        //黑棋活两连
        weightMap.put("110", 400);
        //黑棋死两连
        weightMap.put("112", 20);
        //黑棋单子
        weightMap.put("10", 3);
        //白棋活四连
        weightMap.put("22220", 1000000);
        //白棋死四连
        weightMap.put("22221", 1000000);
        //白棋活三连
        weightMap.put("2220", 10000);
        //白棋死三连
        weightMap.put("2221", 5000);
        //白棋活两连
        weightMap.put("220", 400);
        //白棋死两连
        weightMap.put("221", 20);
        //白棋单子
        weightMap.put("20", 3);
        //扫描周围的时候，黑棋单子
        weightMap.put("1", 3);
    }

    //向左扫描记录棋局

    private void scanXLeft(int i, int j) {
        chess = 0;
        //StringBuilder str = new StringBuilder();
        String str = "";
        for (int k = j - 1; k >= 0; k--) {    //向左扫描
            if (chessArray[i][k] == 0 && k == j - 1) {        //旁边是空位
                break;
            } else if (chess == 0) {
                chess = chessArray[i][k];    //记录颜色
                str += chessArray[i][k];
                //str.append(chessArray[i][k]);    //记录棋局
            } else if (chess == chessArray[i][k]) {    //如果旁边的棋子颜色一样
                str += chessArray[i][k];
                //str.append(chessArray[i][k]);
            } else if (chess != chessArray[i][k]) {    //如果旁边的棋子的颜色不一样
                str += chessArray[i][k];
                //str.append(chessArray[i][k]);
                break;
            }
        }
    }

    //向右扫描记录棋局

    private void scanXRight(int i, int j) {
        chess = 0;
        str = "";
        for (int a = j + 1; a < chessArray.length; a++) {    //向右
            if (chessArray[i][a] == 0 && a == i + 1) {    //旁边是空位
                break;
            } else if (chess == 0) {
                chess = chessArray[i][a];    //记录旁边棋子的颜色
                str += chessArray[i][a];    //记录棋局
            } else if (chess == chessArray[i][a]) {    //旁边棋子的颜色相同
                str += chessArray[i][a];
            } else if (chess != chessArray[i][a]) {    //旁边的棋子颜色不同
                str += chessArray[i][a];
                break;
            }
        }
    }

    //扫描竖直向上记录棋局
    private void scanYDown(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int a = i - 1; a >= 0; a--) {    //向上扫描
            if (chessArray[a][j] == 0 && a == i - 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[a][j];
                str += chessArray[a][j];
            } else if (chess == chessArray[a][j]) {
                str += chessArray[a][j];
            } else if (chess != chessArray[a][j]) {
                str += chessArray[a][j];
                break;
            }
        }
    }

    //扫描竖直向下记录棋局
    private void scanYUp(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int b = i + 1; b < chessArray.length; b++) {    //向下扫描
            if (chessArray[b][j] == 0 && b == i + 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[b][j];
                str += chessArray[b][j];
            } else if (chess == chessArray[b][j]) {
                str += chessArray[b][j];
            } else if (chess != chessArray[b][j]) {
                str += chessArray[b][j];
                break;
            }
        }
    }

    //扫描45度向上记录棋局
    private void scanXyUp(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int a = i - 1, b = j + 1; a >= 0 && b < chessArray.length; a--, b++) {    //像右上角扫描
            if (chessArray[a][b] == 0 && a == i - 1 && b == j + 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[a][b];
                str += chessArray[a][b];
            } else if (chess == chessArray[a][b]) {
                str += chessArray[a][b];
            } else if (chess != chessArray[a][b]) {
                str += chessArray[a][b];
                break;
            }
        }
    }

    //扫描45度向下方向记录棋局
    private void scanXyDown(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int m = i + 1, n = j - 1; m < chessArray.length && n >= 0; m++, n--) {    //向左下角扫描
            if (chessArray[m][n] == 0 && m == i + 1 && n == j - 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[m][n];
                str += chessArray[m][n];
            } else if (chess == chessArray[m][n]) {
                str += chessArray[m][n];
            } else if (chess != chessArray[m][n]) {
                str += chessArray[m][n];
                break;
            }
        }
    }

    //扫描135度向上记录棋局


    private void scanYxUp(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int a = i - 1, b = j - 1; a >= 0 && b >= 0; a--, b--) {    //像左上角上角扫描
            if (chessArray[a][b] == 0 && a == i - 1 && b == j - 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[a][b];
                str += chessArray[a][b];
            } else if (chess == chessArray[a][b]) {
                str += chessArray[a][b];
            } else if (chess != chessArray[a][b]) {
                str += chessArray[a][b];
                break;
            }
        }
    }

    //扫描135度向下记录棋局

    private void scanYxDown(int i, int j) {
        chess = 0;    //记录旁边棋子颜色
        str = "";
        for (int m = i + 1, n = j + 1; m < chessArray.length && n < chessArray.length; m++, n++) {    //向左下角扫描
            if (chessArray[m][n] == 0 && m == i + 1 && n == j + 1) {
                break;
            } else if (chess == 0) {
                chess = chessArray[m][n];
                str += chessArray[m][n];
            } else if (chess == chessArray[m][n]) {
                str += chessArray[m][n];
            } else if (chess != chessArray[m][n]) {
                str += chessArray[m][n];
                break;
            }
        }
    }


}
