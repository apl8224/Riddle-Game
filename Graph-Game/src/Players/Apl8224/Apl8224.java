package Players.Apl8224;
import java.util.Random;
import Interface.PlayerModulePart1;
import Interface.PlayerModulePart2;
import Interface.PlayerMove;
import Interface.Coordinate;

import java.util.*;

public class Apl8224 implements PlayerModulePart2, PlayerModulePart1 {

    /**
     * The board dimension assigned that determines the number of nodes there are for each player.
     * The full game board has dimensions, dim*2+1 X dim*2+1.
     */
    private int dim;

    /**
     * The player ID ( either 1 or 2 ) assigned to this class reference
     * at the beginning of the game
     */
    private int id;

    /**
     * Actual game board consisting of...
     * 1's: owned by player1, 2's: owned by player2, 0's: free to take by any player
     */
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
     * @param playerMove move made last
     */
    @Override
    public void lastMove(PlayerMove playerMove) {
        game[playerMove.getCoordinate().getRow()][playerMove.getCoordinate().getCol()] = playerMove.getPlayerId();
    }

    @Override
    public void otherPlayerInvalidated() {
    }

    /**Generates the next move for this player
     * @return PLayerMove of the next move
     */
    @Override
    public PlayerMove move() {
        ArrayList<PlayerMove> possible = (ArrayList<PlayerMove>) allLegalMoves();
        Random rand = new Random();
        int i = rand.nextInt(possible.size()-1);
        lastMove(possible.get(i));
        return possible.get(i);
    }

    /**Checks the actual game board for this player and adds all the coordinates that haven't
     * been claimed by either player1 or player2 to the list of moves that can be made.
     *
     * @return  the list of PlayerMove objects that are valid
     *          for this player to make. Returns empty list if there are no valid moves to make
     */
    @Override
    public List allLegalMoves() {
        ArrayList<PlayerMove> moves = new ArrayList<>();
        int value;
        for (int i = 1; i < dim*2; i++){
            for(int j = 1; j < dim*2; j++){
                value = game[i][j];
                if (value == 0){
                    moves.add(new PlayerMove(new Coordinate(i,j), id));
                }
            }
        }
        return moves;
    }

    /**Searches the game board for the given player to see how many moves are needed
     * for that player to win at the current board state. (Assuming the other player
     * doesn't make any more moves)
     *
     * @param i id of the player to get the fewest moves
     * @return integer of the least amount of moves needed for that player to win the game.
     */
    @Override
    public int fewestSegmentsToVictory(int i) {
        int tempCoord = 1;
        int moves = Integer.MAX_VALUE;
        Pair current;
        //create the reference board containing cost to get to each coordinate from a starting node
        int[][] fakeGame = new int[dim*2+1][dim*2+1];

        //populate the reference board with -1
        //during the search, if a ( -1 ) is found on the board it means that it is not
        //a valid coordinate to visit
        for (int b = 0; b < dim*2+1;b++){
            for (int z = 0; z < dim*2+1; z++){
                //essentially this is used to assign the edges to be untouched by the search
                fakeGame[b][z] = -1;
            }
        }
        Deque<Pair> touch = new LinkedList<>();

        if(i == 1){
            // do for player 1
            do{
                touch.add(new Pair(tempCoord, 0));
                fakeGame[tempCoord][0] = 0;
                tempCoord +=2;
            }while (tempCoord <= dim*2);

        }else{
            // do for player 2
            do{
                touch.add(new Pair(0, tempCoord));
                fakeGame[0][tempCoord] = 0;
                tempCoord += 2;
            }while (tempCoord <= dim*2);
        }
        while(!touch.isEmpty()){    //while there are still coordinates to check...
            current = touch.removeFirst();
            int weight = fakeGame[current.getRow()][current.getCol()];
            thirdHelper(fakeGame, touch, current.getRow()-1, current.getCol(), weight, i);//check North
            thirdHelper(fakeGame, touch, current.getRow()+1, current.getCol(), weight, i);//check South
            thirdHelper(fakeGame, touch, current.getRow(), current.getCol()-1, weight, i);//check East
            thirdHelper(fakeGame, touch, current.getRow(), current.getCol()+1, weight, i);//check West
        }

        if (i == 1){
            // do for player 1
            for (int b = 1; b < dim*2; b+=2){
                if (fakeGame[b][dim*2] < moves && fakeGame[b][dim*2] != -1){
                    moves = fakeGame[b][dim*2];
                }
            }
        }else{
            // do for player 2
            for (int b = 1; b < dim*2; b+=2){
                if (fakeGame[dim*2][b] < moves && fakeGame[dim*2][b] != -1){
                    moves = fakeGame[dim*2][b];
                }
            }
        }
        return moves;
    }


    /**Helps the fewestSegmentsToVictory method to check a given neighbor
     * coordinate to make sure that it is a valid coordinate to add to the queue to check.
     *
     *
     * @param fGame     2D matrix containing the least number of moves it takes to get to each coordinate
     *                  from a starting node.
     *
     * @param touch     Deque containing "Pair" objects for each coordinate to check in the search
     * @param row       row of the coordinate to check
     * @param col       column of the coordinate to check
     * @param weight    current weight that it took to get to the previous coordinate
     * @param i         id of the chosen player to check
     */
    private void thirdHelper(int[][] fGame, Deque<Pair> touch, int row, int col, int weight, int i){
        //bounds checking... for a given coordinate before it is added to the toVisit deque
        if (row < 0 || col < 0 || row > 2*dim || col > 2*dim){
            return;
        }
        if (fGame[row][col] != -1){
            return;
        }
        if (game[row][col] == 0){
            touch.addLast(new Pair(row, col));
            fGame[row][col] = weight+1;
        }
        if (game[row][col] == i){
            touch.addFirst(new Pair(row, col));
            fGame[row][col] = weight;
        }
    }
}
