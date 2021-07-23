package Players.zaj;
import Interface.PlayerModulePart1;
import Interface.PlayerMove;
import Interface.Coordinate;
import java.util.Arrays;

public class zaj implements PlayerModulePart1{

    private int dim;
    private int id;
    private int[][] game;

    /**
     * Tests if the player has met victory conditions
     * and returns a boolean based on the tests outcome.
     *
     * @param id the id of the player in question
     * @return Boolean, true or false for victory.
     */
    @Override
    public boolean hasWonGame(int id) {
        boolean[][] tracker =  new boolean[dim*2+1][dim*2+1];
        for (int i = 0; i < dim*2+1;i++){
            helper(tracker, i, 0, id);
            helper(tracker, 0, i, id);
        }

        for (int z = 0; z < dim*2+1; z++){
            if(tracker[z][dim*2]){
                return true;
            }else if(tracker[dim*2][z]){
                return true;
            }
        }
        return false;
    }

    /**
     * recursively fills in the 2D boolean array with true for all
     * nodes reachable from row col in the game array.
     *
     * @param tracker A boolean array to track visited "nodes"
     * @param row the row of the current "node" that we're at
     * @param col the column of the current "node" that we're at
     * @param id the id of the player in question
     */
    private void helper(boolean[][] tracker, int row, int col,int id){
        if (row < 0 || col < 0|| row >= dim*2+1|| col >= dim*2+1){
            return;
        }
        else if (game[row][col] != id){
            return;
        }else if(tracker[row][col]){
            return;
        }

        tracker[row][col] = true;
        helper(tracker, row-1, col, id);
        helper(tracker, row+1, col, id);
        helper(tracker, row, col-1, id);
        helper(tracker, row, col+1, id);

    }
    /**
     * Initializes the player, can be used to
     * reset them at the beginning of each game
     * or to create them to compete.
     * @param dim the dimensions
     * @param id player id
     */
    @Override
    public void initPlayer(int dim, int id) {
        this.dim = dim;
        this.id = id;
        this.game = new int[dim*2+1][dim*2+1];
        for (int row = 0; row < dim*2+1; row++){
            for (int col = 0; col < dim*2+1; col++){
                if (row%2 != 0 && col%2 == 0){
                    game[row][col] = 1;
                }else if(row%2 == 0 && col%2 != 0){
                    game[row][col] = 2;
                }
            }
        }
    }

    /**
     * Method called after every move of the game. Used to keep internal game state current.
     * Required task for Part 1. Note that the engine will only call this method after verifying
     * the validity of the current move.
     * Thus, you do not need to verify the move provided to this method.
     * It is guaranteed to be a valid move.
     * @param playerMove
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        game[playerMove.getCoordinate().getRow()][playerMove.getCoordinate().getCol()] = playerMove.getPlayerId();
    }

    @Override
    public void otherPlayerInvalidated() {

    }

    @Override
    public PlayerMove move() {
        return null;
    }
}
