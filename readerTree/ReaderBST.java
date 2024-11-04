package readerTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class ReaderBST {

    private static ReaderNode root;
    
    public static ReaderNode getRoot() {
        return root;
    }

    // Load data from file using Files.lines
    public void loadFromFile(String filename) {
        Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] readerData = line.split(", ");
                if (readerData.length == 3) {
                    Reader reader = new Reader(
                            readerData[0], readerData[1], Integer.parseInt(readerData[2]));
                    add(reader);
                }
            });
            System.out.println("Data loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    public static void addReader() {
        // Input and validate rcode
        System.out.print("Enter reader code (rcode): ");
        String rcode = Validate.checkInputString();
        if (validateRcode(root, rcode)) {
            System.out.println("Rcode already exists. Cannot add duplicate rcode.");
            return;
        }

        // Input reader name
        System.out.print("Enter reader name: ");
        String name = Validate.checkInputString();

        // Input birth year
        System.out.print("Enter birth year: ");
        int byear = Validate.inputByear();

        // Create new Reader and add to tree
        Reader newReader = new Reader(rcode, name, byear);
        root = insert(root, newReader);
        System.out.println("Reader added successfully.");
    }

    // Method to validate if rcode already exists in the tree
    public static boolean validateRcode(ReaderNode node, String rcode) {
        if (node == null) {
            return false;
        }
        if (rcode.equals(node.data.rcode)) {
            return true;
        }
        if (rcode.compareTo(node.data.rcode) < 0) {
            return validateRcode(node.left, rcode);
        } else {
            return validateRcode(node.right, rcode);
        }
    }

    // Insert a new Reader into the BST
    private static ReaderNode insert(ReaderNode rnode, Reader reader) {
        if (rnode == null) {
            return new ReaderNode(reader);
        }
        if (reader.rcode.compareTo(rnode.data.rcode) < 0) {
            rnode.left = insert(rnode.left, reader);
        } else if (reader.rcode.compareTo(rnode.data.rcode) > 0) {
            rnode.right = insert(rnode.right, reader);
        }
        return rnode;
    }

    public static void add(Reader reader) {
        root = insert(root, reader);
    }

    public static void displayPreOrder() {
        System.out.println("Displaying all readers (Pre-order Traversal):");
        preOrderTraversal(root);
    }

    private static void preOrderTraversal(ReaderNode node) {
        if (node != null) {
            // Visit the root node
            System.out.println(node.data);
            // Recursively visit the left subtree
            preOrderTraversal(node.left);
            // Recursively visit the right subtree
            preOrderTraversal(node.right);
        }
    }

    public static void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            preOrderSave(root, writer);
            System.out.println("Reader tree saved to " + filename + " successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reader tree to file: " + e.getMessage());
        }
    }

// Pre-order traversal to save each node in "rcode,name,byear" format
    private static void preOrderSave(ReaderNode node, BufferedWriter writer) throws IOException {
        if (node != null) {
            // Write data in the required format
            writer.write(node.data.rcode + "," + node.data.name + "," + node.data.byear);
            writer.newLine();

            // Traverse left and right children
            preOrderSave(node.left, writer);
            preOrderSave(node.right, writer);
        }
    }

    public static Reader searchByRcode(String rcode) {
        return searchRec(root, rcode);
    }

    // Recursive helper method to search the tree
    private static Reader searchRec(ReaderNode node, String rcode) {
        // Base case: If the node is null or the rcode matches, return the node's data
        if (node == null) {
            System.out.println("No such rcode found");
            return null;  // Not found
        }
        if (node.data.rcode.equalsIgnoreCase(rcode)) {
            System.out.println("Found " + node.data);
            return node.data; // Found
        }

        // Recur down the tree
        if (rcode.compareToIgnoreCase(node.data.rcode) < 0) {
            return searchRec(node.left, rcode);  // Search left subtree
        } else {
            return searchRec(node.right, rcode);  // Search right subtree
        }
    }

    public static void deleteByRcode(String rcode) {
        root = deleteByCopying(root, rcode);
    }

    private static ReaderNode deleteByCopying(ReaderNode node, String rcode) {
        if (node == null) {
            return null; // Base case: not found
        }

        if (rcode.compareToIgnoreCase(node.data.rcode) < 0) {
            // Go left
            node.left = deleteByCopying(node.left, rcode);
        } else if (rcode.compareToIgnoreCase(node.data.rcode) > 0) {
            // Go right
            node.right = deleteByCopying(node.right, rcode);
        } else {
            // Node found
            if (node.left == null) {
                return node.right; // Node with only right child or no child
            } else if (node.right == null) {
                return node.left; // Node with only left child
            } else {
                // Node with two children: find the minimum in the right subtree
                ReaderNode minNode = findMin(node.right);
                // Copy the in-order successor's data to this node
                node.data = minNode.data;
                // Delete the in-order successor
                node.right = deleteByCopying(node.right, minNode.data.rcode);
            }
        }
        return node; // Return the unchanged node pointer
    }

    private static ReaderNode findMin(ReaderNode node) {
        while (node.left != null) {
            node = node.left; // Go to the leftmost child
        }
        return node; // Return the node with the smallest value
    }
//2.7

    public static void searchReaderByName() {

        System.out.print("Enter the name to be searched: ");
        String nameToSearch = Validate.checkInputString();

        Reader foundReader = searchByName(root, nameToSearch);
        if (foundReader != null) {
            System.out.printf("Reader found: %s%n", foundReader);
        } else {
            System.out.println("Reader not found.");
        }
    }

    private static Reader searchByName(ReaderNode node, String name) {
        if (node == null) {
            return null; // Base case: name not found
        }

        // Compare the current node's name with the search name
        if (name.equalsIgnoreCase(node.data.name)) {
            return node.data; // Found the reader
        }

        // Search left subtree
        Reader foundReader = searchByName(node.left, name);
        if (foundReader != null) {
            return foundReader; // Found in the left subtree
        }

        // Search right subtree
        return searchByName(node.right, name); // Search in the right subtree
    }

    public static void menu2() {

        while (true) {
            System.out.println("====Reader Management======");
            System.out.println("1.Load data from file");
            System.out.println("2.Input and add to tree");
            System.out.println("3.Display data by pre-order traversal");
            System.out.println("4.Save reader tree to file by pre-order traversal");
            System.out.println("5.Search by rcode");
            System.out.println("6.Delete by copying rcode");
            System.out.println("7.Search by name");
            System.out.println("8.Search lending books by rcode");
            System.out.println("9.Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice = Validate.checkInputIntLimit(1, 9);
            switch (choice) {
                case 1:
//                    loadFromFile("Resourse\\readers.txt");
                    break;

                case 2:
                    addReader();

                    break;
                case 3:
                    displayPreOrder();
                    break;

                case 4:
                    saveToFile("Resourse\\readers.txt");
                    break;

                case 5:
                    System.out.print("Enter rcode to search: ");
                    String rcode = Validate.checkInputString();
                    searchByRcode(rcode);
                    break;
                case 6:
                    System.out.print("Enter rcode to delete: ");
                    rcode = Validate.checkInputString();
                    deleteByRcode(rcode);
                    break;
                case 7:

                    searchReaderByName();
                    break;
                case 8:

                case 9:
                    return;

            }
        }
    }

    public static void main(String[] args) {
        menu2();
    }

}
