package othello;

/**
 * File name: OthelloModel.java 
 * Author: Hoang Tung Nguyen, 040977322 Manh Nghia Duong, 040971252
 * Course: CST8221 - JAP, Lab Section: 302 Assignment: 1-2
 * Date: November 15, 2020 
 * Professor: Daniel Cormier 
 * Purpose: Contains the model that the game program will be using for drawing the board
 * Class list: Othello
 */
public class OthelloModel {

    private int[][] board = new int[8][8];

    public static final int NORMAL = 0;
    public static final int CORNER_TEST = 1;
    public static final int OUTER_TEST = 2;
    public static final int TEST_CAPTURE = 3;
    public static final int TEST_CAPTURE2 = 4;
    public static final int UNWINNABLE = 5;
    public static final int INNER_TEST = 6;

    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    private int flipCounter = 0;

    //flag: 0 for check valid or 1 for flipChips
    /**
     * This method initialises the new board state
     *
     * @param mode the state code for mode
     */
    public void initialize(int mode) {
        switch (mode) {
            case CORNER_TEST:
                board = new int[][]{
                    {2, 0, 0, 0, 0, 0, 0, 1},
                    {0, 1, 0, 0, 0, 0, 2, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 1, 0},
                    {2, 0, 0, 0, 0, 0, 0, 2}};

                break;
            case OUTER_TEST:
                board = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 2, 2, 2, 2, 2, 2, 0},
                    {0, 2, 1, 1, 1, 1, 2, 0},
                    {0, 2, 1, 0, 0, 1, 2, 0},
                    {0, 2, 1, 0, 0, 1, 2, 0},
                    {0, 2, 1, 1, 1, 1, 2, 0},
                    {0, 2, 2, 2, 2, 2, 2, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case INNER_TEST:
                board = new int[][]{
                    {2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 0, 0, 0, 0, 0, 0, 2},
                    {2, 0, 2, 2, 2, 2, 0, 2},
                    {2, 0, 2, 1, 1, 2, 0, 2},
                    {2, 0, 2, 1, 1, 2, 0, 2},
                    {2, 0, 2, 2, 2, 2, 0, 2},
                    {2, 0, 0, 0, 0, 0, 0, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2}};
                break;
            case UNWINNABLE:
                board = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}};
                break;
            case TEST_CAPTURE:
                board = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 1, 1, 1, 0},
                    {0, 1, 1, 1, 1, 1, 1, 0},
                    {0, 1, 2, 2, 2, 1, 1, 0},
                    {0, 1, 2, 0, 2, 1, 1, 0},
                    {0, 1, 2, 2, 2, 1, 1, 0},
                    {0, 1, 1, 1, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}};
                break;

            case TEST_CAPTURE2:
                board = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 2, 2, 2, 1, 2, 1, 1},
                    {1, 2, 2, 2, 2, 2, 1, 1},
                    {1, 2, 2, 0, 2, 2, 1, 1},
                    {1, 2, 2, 2, 2, 1, 1, 1},
                    {1, 2, 1, 2, 2, 2, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1}};
                break;
            default:
                board = new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 2, 1, 0, 0, 0},
                    {0, 0, 0, 1, 2, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}};

        }
    }

    /**
     * this method return the current mode board
     *
     * @return returns the board 0 empty, 1 black, 2 white
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * This method read the board and return the content
     *
     * @param x given x-Axis position
     * @param y given y-Axis position
     * @return returns the content of the given square 0 empty, 1 black, 2 white
     */
    public int getBoard(int x, int y) {
        return board[x][y];
    }

    /**
     * this method helps get direction from the current move to the current
     * player's chip(s) if there are any
     *
     * @param x given x-Axis position
     * @param y given y-Axis position
     * @param xOffSetValue the opposite x-Axis direction to sandwich the
     * opponent's chips
     * @param yOffSetValue the opposite y-Axis direction to sandwich the
     * opponent's chips
     * @param player the current player
     * @param opponent the opponent player
     * @param flag true - gets direction from the current move to the current
     * player's chip(s) based on x and yOffsetValue false - flips all the
     * chip(s) based on the direction above
     * false - flip the captured chips
     * @return true - if there is direction(s) for the player to move 
     * false - if there is no directions for the player to move
     */
    public boolean getDirection(int x, int y, int xOffSetValue, int yOffSetValue, int player, int opponent, boolean flag) {
        int curX = x + xOffSetValue * 2, curY = y + yOffSetValue * 2; //increment the offset to move forward on the board
        FLAG:
        while (curX >= 0 && curX < 8 && curY >= 0 && curY < 8) { //check if current position is not outside the board
            if (board[curX][curY] == player) {
                if (flag == false) {
                    break FLAG;
                }
                return true;
            } else if (board[curX][curY] == EMPTY) { //if encounter an empty board, invalid move
                return false;
            }
            curX = curX + xOffSetValue;
            curY = curY + yOffSetValue;
        }
        if (flag == false) {
            while (curX != x || curY != y) { //step backwards until meet the origin tile
                if (board[curX][curY] == opponent) {
                    board[curX][curY] = player;
                    flipCounter++;
                }
                curX -= xOffSetValue;
                curY -= yOffSetValue;
            }
        }
        return false;
    }

    /**
     * this method tells if the move of the player is valid or not
     *
     * @param x current x-Axis position
     * @param y current y-Axis position
     * @param player the current player in the game (1 - black, 2 - white)
     * @param flag true - get direction from the current move false - flip the
     * coin based on the direction from the current move
     * @return
     */
    public boolean isValid(int x, int y, int player, boolean flag) {
        int offSetX = 0, offSetY = 0;
        int opponent;
        if (player == BLACK) {
            opponent = WHITE;
        } else {
            opponent = BLACK;
        }
        for (int i = x - 1; i <= x + 1; i++) {      //check the surrounding (adjacent) tiles of selected position
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 1 || j < 1 || i > 6 || j > 6) {
                    continue;
                }
                if (board[i][j] == opponent && (i != x || j != y)) {
                    offSetX = i - x;
                    offSetY = j - y;
                    if (getDirection(x, y, offSetX, offSetY, player, opponent, flag)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * this method makes the move for the player if it is valid
     *
     * @param x given x-Axis position
     * @param y given y-Axis position
     * @param player the current player in the game
     * @return 0 - 0 chips flipped if there are no moves flipCounter - numbers
     * of chips flipped if there are moves
     */
    public int move(int x, int y, int player) {
        if (flipCounter != 0) {
            flipCounter = 0;
        }
        if (isValid(x, y, player, true)) {
            board[x][y] = player;
            isValid(x, y, player, false);
            return flipCounter;
        }
        return 0;
    }

    /**
     * this method tells if the player can move or not
     *
     * @param player - the current player in the game
     * @return true - if the player has 1 or more move(s) to play false - if the
     * player has no more move to play
     */
    public boolean canMove(int player) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isValid(i, j, player, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method returns the current amount of chips
     *
     * @param player - the current player in the game, 1 - black, 2 - white
     * @return returns the amount of chips of the given player
     */
    public int getChips(int player) {
        int chips = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == player) {
                    chips++;
                }
            }
        }
        return chips;
    }

} // end of class
