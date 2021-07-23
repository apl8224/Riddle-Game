package Players.zaj;

import java.util.ArrayList;
/**
 * Movie node class that is a specific design
 * that is to be used for movies and actors
 * @author Zeb Hollinger zah1276
 */
public class Node{
    /**
     * List of the connected actors / movies
     * that the node is connected to
     */
    private ArrayList<Node> adjacencyList;

    /**
     * Name of the movie / actor node
     */
    private String coord;

    private int id;

    /**
     * Constructor to create a new Node
     *
     * @param name String to be the name of the node
     * @param neighbors list of Nodes that the new node should be connected with
     */
    Node(String coord, int id, ArrayList<Node> neighbors){
        this.adjacencyList = neighbors;
        this.coord = coord;
        this.id = id;
    }

    /**
     * Adds a Node to the current node's adjacency list
     * @param neighbor node to add to the current adjacency list
     */
    void addNeighbor(Node neighbor){
        adjacencyList.add(neighbor);
    }

    /**
     * Adds multiple Node neighbors from a list to the current neighbors list
     * @param aList list of new neighbors to add
     */
    void addNeighbors(ArrayList<Node> aList){
        adjacencyList.addAll(aList);
    }

    /**
     * removes a Node from the current adjacency list based
     * on the given string
     * @param neighbor Node to remove
     */
    void removeNeighbor(Node neighbor){adjacencyList.remove(neighbor);}

    /**Gets the name of the current Node
     * @return String name of the Node
     */
    String getCoordinate(){
        return coord;
    }

    /**Gets the Node's adjacency list of Nodes
     * @return current adjacency list
     */
    ArrayList<Node> getNeighbors(){
        return adjacencyList;
    }

    /**Gets the player that owns that coordinate node
     * @return id of the player that the node is assigned to
     */
    public int getId(){
        return id;
    }
}
