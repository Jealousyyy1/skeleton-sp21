package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public static int numberNonZero(int[] column) {
        int number = 0;
        for (int element : column) {
            if (element != 0) {
                number = number + 1;
            }
        }
        return number;
    }

    public static int[] sameAdjPairs(int[] column) {
        int pairs = 0;
        int len = column.length;
        int pointer = len - 1;
        int elements = numberNonZero(column);
        int[] newArr = new int[len];
        for (int block : column) {
            if (block != 0) {
                newArr[len - elements] = block;
                elements = elements - 1;
            }
        }
        int[] pairs_idx = new int[len];
        while (pointer >= len - numberNonZero(column) && pointer > 0) {
            if (newArr[pointer] == newArr[pointer - 1]) {
                if (pairs > 0 && pairs_idx[pairs - 1] == pointer + 2){
                    pairs_idx[pairs] = pointer + 1;
                    pairs = pairs + 1;
                    pointer = pointer - 2;
                } else {
                    pairs_idx[pairs] = pointer;
                    pairs = pairs + 1;
                    pointer = pointer - 2;
                }

            } else {
                pointer = pointer - 1;
            }
        }
        return pairs_idx;
    }

    public boolean tilt(Side side) {
        boolean changed;
        changed = false;
        int size = board.size();
        board.setViewingPerspective(side);  // up only
        for (int col = 0; col < size; col++) {
            int[] colVal = new int[size];
            for (int row = 0; row < size; row++) {
                if (board.tile(col, row) == null) {
                    colVal[row] = 0;
                } else {
                    colVal[row] = board.tile(col, row).value();
                }
            }
            int left_ele =  numberNonZero(colVal) - numberNonZero(sameAdjPairs(colVal));
            int pair = 0;
            int left = size - 1;
            int right = size - 1;
            int[] adjPairs = sameAdjPairs(colVal);
            while (right >= size - left_ele) {
                if (colVal[right] == 0) {
                    if (adjPairs[pair] == right) {
                        int i = 0;
                        while (i < 2) {
                            if (board.tile(col, left) != null) {
                                Tile t = board.tile(col, left);
                                board.move(col, right, t);
                                colVal[left] = 0;
                                changed = true;
                                left = left - 1;
                                i = i + 1;
                                this.score = this.score + t.value();
                            } else {
                                left = left - 1;
                            }
                        }
                        pair = pair + 1;
                        right = right - 1;
                    } else {
                        int i = 0;
                        while (i < 1 && left >= 0) {
                            if (colVal[left] != 0) {
                                Tile t = board.tile(col, left);
                                board.move(col, right, t);
                                colVal[left] = 0;
                                changed = true;
                                i = i + 1;
                                left = left - 1;
                            } else {
                                left = left - 1;
                            }
                        }
                        right = right - 1;
                    }
                } else {
                    if (adjPairs[pair] == right) {
                        int i = 0;
                        left = left - 1;   // left = 2
                        while (i < 1 && left >= 0) {
                            if (colVal[left] != 0) {
                                Tile t = board.tile(col, left);
                                board.move(col, right, t);
                                colVal[left] = 0;  // col[2] == 0
                                changed = true;
                                i = i + 1;
                                left = left - 1; // left = 1
                                pair = pair + 1;
                                this.score = this.score + t.value() * 2;
                            } else {
                                left = left - 1;
                            }
                        }
                    } else {
                        left = left - 1;
                    }
                    right = right - 1;
                }
            }
        }
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int col = 0; col < b.size(); col++) {
            for (int row = 0; row < b.size(); row++) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int col = 0; col < b.size(); col++) {
            for (int row = 0; row < b.size(); row++) {
                if (b.tile(col, row) != null) {
                    if (b.tile(col, row).value() == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean twoAdjacentSame(Board b) {
        int n = b.size();
        for (int col = 0; col < n; col++) {
            for (int row = 0; row < n; row++) {
                if (b.tile(col, row) == null) {
                    continue;
                }
                int curVal = b.tile(col, row).value();
                if (col + 1 < n) {
                    if (b.tile(col + 1, row) != null && curVal == b.tile(col + 1, row).value()) {
                        return true;
                    }
                }
                if (row + 1 < n) {
                    if (b.tile(col, row + 1) != null && curVal == b.tile(col, row + 1).value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        } else return twoAdjacentSame(b);
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }

}
