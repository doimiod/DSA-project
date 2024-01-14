import java.util.*;

// The TernarySearchTree class defines the tree itself
public class TernarySearchTree {
    TSTNode root;
    private ArrayList<String> busStops = new ArrayList<String>(); // to store the bus stops whose name starts with the
                                                                  // given prefix.

    public TernarySearchTree() {
        this.root = null;
    }

    public void insert(String busstop) {
        root = insert(root, busstop.toCharArray(), 0);
    }

    /*
     * The insert() method inserts a given string into the tree recursively.
     */
    private TSTNode insert(TSTNode node, char[] busstop,
            int index) {
        if (node == null) {
            node = new TSTNode(busstop[index]);
        }

        if (busstop[index] < node.c) {
            node.left = insert(node.left, busstop, index);
        } else if (busstop[index] > node.c) {
            node.right = insert(node.right, busstop, index);
        } else {
            if (index + 1 < busstop.length) {
                node.middle = insert(node.middle, busstop, index + 1);
            } else {
                node.isLastChar = true;
            }
        }
        return node;
    }

    /*
     * this method will determine whether the tree has a prefix by using
     * searchPrefix() method.
     * if it has a prefix then it will start searching for all the possible bus stop
     * names in the tree by getAllChild().
     */
    public boolean foundPrefix(String prefix) {
        TSTNode node = searchPrefix(prefix);
        if (node == null) { // if node is null the tree does not have a prefix.
            System.out.println(
                    "No bus stops found with given inputs" + "\n");
            return false;
        }

        this.busStops = new ArrayList<>();

        if (node.isLastChar) { // if prefix itself is a bus stop name.
            this.busStops.add(prefix);
        }

        // start looking for all possble bus stop names in the tree.
        getAllChild(node.middle, new StringBuilder(prefix));
        return true;
    }

    /*
     * checking if the tree contains a given prefix.
     * To search, we compare the first character in the key with the character at
     * the root.
     * If it is less, we take the left link;
     * if it is greater, we take the right link; and
     * if it is equal, we take the middle link and move to the next search key
     * character.
     */
    private TSTNode searchPrefix(String prefix) {
        TSTNode node = root;
        int i = 0;
        while (node != null && i < prefix.length()) {
            if (prefix.charAt(i) < node.c) {
                node = node.left;
            } else if (prefix.charAt(i) > node.c) {
                node = node.right;
            } else {
                i++;
                if (i == prefix.length()) { // found!!
                    return node;
                }
                node = node.middle;
            }
        }
        return node;
    }

    /*
     * looking for all bus stops that start with the given prefix.
     * it will go through all values from the point where the prefix is found.
     * if it reaches the vertex - the final character, the bus stop name is added to
     * the arraylist.
     */
    private void getAllChild(TSTNode node,
            StringBuilder prefix) {
        if (node == null) {
            return;
        }

        getAllChild(node.left, prefix);

        if (node.isLastChar) {
            this.busStops.add(prefix.toString() + node.c);
        }

        getAllChild(node.middle, prefix.append(node.c));
        prefix.deleteCharAt(prefix.length() - 1);
        getAllChild(node.right, prefix);
    }

    public ArrayList<String> getBusStops() {
        return this.busStops;
    }
}