package Players.zaj;
import java.util.*;
public class GameGraph implements Graph<Node> {
    /**
     *local field graph to be used by main programs
     */
    public  Map<String, Node> graph = new HashMap<>();

    /**
     * Get all the nodes currently in this graph.
     *
     * @return an {@link Collection} over all the graph's nodes.
     */
    public Set<Node> getNodes() {
        return (Set<Node>) graph.values();
    }

    /**
     * Get all of the names of the entries in the graph
     *
     * @return Collection of keys from the graph
     */
    Set<String> getNames(){
        return graph.keySet();
    }

    /**
     * Check if a given String node is in the graph.
     *
     * @param nodeName name of a node
     * @return true iff the graph contains a node with that name
     */
    public boolean hasNode(String nodeName) {
        return graph.containsKey(nodeName);
    }

    /**
     * Look up a node in the graph by its name.
     *
     * @param nodeName the sought node's name
     * @return the Node object named nodeName or null if doesn't exist
     * @rit.pre hasNode(nodeName)
     */
    public Node getNode(String nodeName) {
        return graph.get(nodeName);
    }

    /**
     * Look up the name of a Node.
     *
     * @param o the Node to look up
     * @return the Node's name
     */
    public String getNodeCoords(Node o) {
        return o.getCoordinate();
    }

    /**
     * Adds a single Node to the neighbors adjacency list
     * of the o Node
     *
     * @param o Node to add a neighbor node to
     * @param neighbor the Node that is getting added to o's adjacency list
     */
    public void addNeighbor(Node o, Node neighbor) {
        o.addNeighbor(neighbor);
    }

    /**Adds multiple neighbors to the neighbors list
     * of a Node
     *
     * @param o Node to add neighbors to
     * @param aList node adjacency list containing the neighboring nodes
     */
    public void addNeighbors(Object o, ArrayList<Node> aList) {
        ((Node) o).addNeighbors(aList);
    }

    /**
     * Removes a neighbor from the selected Node
     *
     * @param o Node to use
     * @param neighbor Node to remove from adjacency list
     */
    public void removeNeighbor(Node o, Node neighbor) {
        o.removeNeighbor(neighbor);
    }

    /**
     * Returns the adjacency list of that Node's neighbors
     *
     * @param o the Node whose neighbors are sought
     * @return an arraylist of {@link Node}s where for each node v in the set,
     * addNeighbor had previously been called with node as the
     * first argument and v as the second
     */
    public ArrayList<Node> getNeighbors(Node o) {
        return o.getNeighbors();
    }

    /**
     * Create a new Node for the graph.
     *
     * @param coord the name of the new node
     * @return the new Node object created
     * @rit.post the returned Node has no neighbors.
     */
    public void addNewNode(String coord, int id) {
        Node temp = new Node(coord, id, new ArrayList<>());
        graph.put(coord, temp);
    }
}
