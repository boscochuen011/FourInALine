import java.util.*;
public class FourInALine {
    public static final int ROW = 6; //***about the table ROW
    public static final int COLUMN = 7; //***about the table COLUMN
    public static int check_column;   //***Player input
    public static int[][] mTable = new int[ROW][COLUMN];// mTable = ROW x COLUMN
    public static boolean Game_control = true;  //***Game Looping
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        boolean GamePlayer = true;//***player num
        int num;
        drawGrid();//***output drawGrid
        while (Game_control) { //********Game controller****** //looping
            GamePlayer = !GamePlayer; //***Player control
            if (GamePlayer)
                num = 2;       //****Player 2 is num 2
             else
                num = 1;       //****Player 1 is num 1
            try { //*****try to do
                System.out.print("\n" + "Player " + num + " type a column (0-" + (COLUMN - 1) + ") or 9 to quit current game: ");
                check_column = kb.nextInt();    //***Player input
                if (check_column == 9) {          //type 9 to quit the game
                    System.out.print("Bye Bye!");//output  message
                    break;
                } else if (getError(check_column) != null) {  //***getError
                    GamePlayer = !GamePlayer;  //change back the player
                    System.out.print(getError(check_column)); //get and output error message
                    continue;
                }
                for (int r = 0; r < ROW; r++) {       //add the num into the grid
                    if (mTable[r][check_column] == 0) {
                        mTable[r][check_column] = num; //add the num
                        break;
                    }
                }
                drawGrid();             //output drawGrid
                CheckWin(num);         //go to checking who win
                if (checkFull()) {    //***Check Full***
                    System.out.println("\n" + "Draw!"); //output message
                    break;    //***Stop Game
                }
            } catch (InputMismatchException inputMismatchException) {  //if program find a problem (not Natural number)
                kb.nextLine();
                System.out.print("Incorrect input"); //***Output error message
                GamePlayer = !GamePlayer;
            }
        }
    }

    private static void CheckWin(int num) {     //***checking win
        int h = 0;//***check horizontal
        for (int r = mTable.length - 1; r >= 0; r--) {
            for (int c = 0; c < COLUMN; c++) {  //Checking from mTable[5][0]...mTable[5][1]...
                if (mTable[r][c] == num)
                    h += num;           //if there have num should add num in the (h)
                if (mTable[r][c] != num)
                    h = 0;
                CheckGetWin(h, num);
            }
            h = 0;
        }
        int v = 0;     // **********check vertical**********
        for (int c = COLUMN - 1; c >= 0; c--) {
            for (int r = 0; r <= mTable.length - 1; r++) { //Checking from mTable[0][6]...mTable[1][6]...
                if (mTable[r][c] == num)
                    v += num;           //if there have num should add num in the (v)
                if (mTable[r][c] != num)
                    v = 0;              //if not should be set (v) to 0
                 CheckGetWin(v, num);
            }
            v = 0;
        }
        for (int r = 0; r <= mTable.length - 1; r++) {       //*****Check the board
            for (int c = COLUMN - 1; c >= 0; c--) {  //***Check the board
                if (mTable[r][c] == num) {      //Check if having a num in the board
                    int RTL = num;              //Set first (RTL) is num
                    for (int z = 1; z <= 3; z++) { //Check other 3
                        if (r + z >= mTable.length || c - z < 0)   //r + z >= 6 || c - z < 0
                            break; //if over the mTable[r][c] should be stop checking e.g. r + z = 6 || c - z = -1
                        if (mTable[r + z][c - z] == num)   //***Check Right-To-Left
                            RTL += num;     //if there have num should add num in the (RTL)
                        if (mTable[r + z][c - z] != num)  //***Check Right-To-Left
                            RTL = 0;         //if next is not this num, it should be set (RTL) to 0
                        CheckGetWin(RTL, num);
                    }
                }
            }
        }
        for (int r = 0; r <= mTable.length - 1; r++) {          //***Check the board
            for (int c = COLUMN - 1; c >= 0; c--) {    //***Check the board
                if (mTable[r][c] == num) {   //Check if having a num in the board
                    int LTR = num;          //Set first (LTR) is num
                    for (int z = 1; z <= 3; z++) { //Check other 3
                        if (r + z >= mTable.length || c + z >= COLUMN) //r + z >= 6 || c + z >= 7
                            break;  //if over the mTable[r][c] should be stop checking e.g. r + z = 6 || c + z = 7
                        if (mTable[r + z][c + z] == num)  //***Check Left-To-Right
                            LTR += num;  //if there have num should add num in the (LTR)
                        if (mTable[r + z][c + z] != num)  //***Check Left-To-Right
                            LTR = 0;   //if next is not this num, it should be set (LTR) to 0
                        CheckGetWin(LTR, num);
                    }
                }
            }
        }
    }

    private static void drawGrid() {
        for (int r = ROW - 1; r >= 0; r--) {  //***Checkerboard
            System.out.print(r + " |"); //print [ 5 | ]...
            for (int c = 0; c <= COLUMN - 1; c++) {  //print [  0  0  0  0  0  0  0 ]
                System.out.print( "  " + mTable[r][c]);
            }
            System.out.println();
        }
        System.out.print("  +");
        for (int cl = 0; cl <= COLUMN - 1; cl++)
            System.out.print("---");     //print  ---------------------
        System.out.println();
        System.out.print("   ");
        for (int columnL = 0; columnL <= COLUMN - 1; columnL++)
            System.out.print("  " + columnL); //print [ 0  1  2  3  4  5  6 ]
    }

    private static String getError(int CheckNUM) {
        if (CheckNUM < 0 || CheckNUM > COLUMN - 1)        //***CheckError
             return ("Range of column should be 0 to " + (COLUMN - 1) + "!"); //return error message
        else if (mTable[ROW - 1][CheckNUM] != 0)   //***CheckError
             return ("Column " + CheckNUM + " is full!");  //return error message
        return null; //if no Problem
    }

    private static void CheckGetWin(int winWin, int w) {  //***Print winner
        if (winWin == w * 4) {  //if there have 4 num of continuous it should win
            Game_control = false; // stop the game
            System.out.print("\n" + "Player " + w + " win this game!");//output message
        }
    }

    private static boolean checkFull() {  //***Check Full
        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COLUMN; c++) {
                if (mTable[r][c] == 0) //there have 0 in the table
                    return false;      //if no problem
            }
        }
        return true;// is full
    }
}