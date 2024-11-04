/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookTree;

/**
 *
 * @author admin
 */
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
public class BSTree {

    static BSTNode root;
    public BSTree() {
        root = null;
    }
    public void loadDataFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\| ");
                Book book = new Book(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]), Double.parseDouble(parts[7]));
                add(book);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public void add(Book book) {
        root = addRecursive(root, book);
    }
    private BSTNode addRecursive(BSTNode node, Book book) {
        if (node == null) {
            return new BSTNode(book);
        }
        if (book.bcode.compareTo(node.data.bcode) < 0) {
            node.left = addRecursive(node.left, book);
        } else if (book.bcode.compareTo(node.data.bcode) > 0) {
            node.right = addRecursive(node.right, book);
        }
        return node;
    }
    public void inOrderTraversal() {
        inOrderRecursive(root);
    }
    private void inOrderRecursive(BSTNode node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.println(node.data);
            inOrderRecursive(node.right);
        }
    }
    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            saveInOrder(root, bw);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    private void saveInOrder(BSTNode node, BufferedWriter bw) throws IOException {
        if (node != null) {
            saveInOrder(node.left, bw);
            bw.write(node.data.toString());
            bw.newLine();
            saveInOrder(node.right, bw);
        }
    }
    public Book search(String bcode) {
        return searchRecursive(root, bcode);
    }
    private Book searchRecursive(BSTNode node, String bcode) {
        if (node == null) {
            return null;
        }
        if (bcode.equals(node.data.bcode)) {
            return node.data;
        }
        if (bcode.compareTo(node.data.bcode) < 0) {
            return searchRecursive(node.left, bcode);
        } else {
            return searchRecursive(node.right, bcode);
        }
    }
    public void deleteByCopying(String bcode) {
        root = deleteByCopyingRecursive(root, bcode);
    }
    private BSTNode deleteByCopyingRecursive(BSTNode node, String bcode) {
        if (node == null)
            return null;
        if (bcode.compareTo(node.data.bcode) < 0) {
            node.left = deleteByCopyingRecursive(node.left, bcode);
        } else if (bcode.compareTo(node.data.bcode) > 0) {
            node.right = deleteByCopyingRecursive(node.right, bcode);
        } else {
            // Node to be deleted found
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            // Find the largest node in the left subtree
            BSTNode largest = findLargest(node.left);
            node.data = largest.data;
            node.left = deleteByCopyingRecursive(node.left, largest.data.bcode);
        }
        return node;
    }
    private BSTNode findLargest(BSTNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
    public void deleteByMerging(String bcode) {
        root = deleteByMergingRecursive(root, bcode);
    }
    private BSTNode deleteByMergingRecursive(BSTNode node, String bcode) {
        if (node == null)
            return null;
        if (bcode.compareTo(node.data.bcode) < 0) {
            node.left = deleteByMergingRecursive(node.left, bcode);
        } else if (bcode.compareTo(node.data.bcode) > 0) {
            node.right = deleteByMergingRecursive(node.right, bcode);
        } else {
            // Node to be deleted found
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            // Merge left subtree with the right subtree
            BSTNode leftSubtree = node.left;
            BSTNode rightSubtree = node.right;
            node = leftSubtree;
            BSTNode largestInLeft = findLargest(leftSubtree);
            largestInLeft.right = rightSubtree;
        }
        return node;
    }
    // Bước 1: Lưu các nút cây vào danh sách theo thứ tự in-order
    private void storeInOrder(BSTNode node, ArrayList<Book> books) {
        if (node != null) {
            storeInOrder(node.left, books);
            books.add(node.data);
            storeInOrder(node.right, books);
        }
    }
    // Bước 2: Xây dựng cây cân bằng từ danh sách
    private BSTNode buildBalancedTree(ArrayList<Book> books, int start, int end) {
        if (start > end)
            return null;
        int mid = (start + end) / 2;
        BSTNode node = new BSTNode(books.get(mid));
        node.left = buildBalancedTree(books, start, mid - 1);
        node.right = buildBalancedTree(books, mid + 1, end);
        return node;
    }
    // Hàm cân bằng cây chính
    public void balanceTree() {
        ArrayList<Book> books = new ArrayList<>();
        storeInOrder(root, books);
        root = buildBalancedTree(books, 0, books.size() - 1);
    }
    public void breadthFirstTraversal() {
        if (root == null)
            return;
        Queue<BSTNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode node = queue.poll();
            System.out.println(node.data);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
    }
    public int countBooks() {
        return countBooksRecursive(root);
    }
    private int countBooksRecursive(BSTNode node) {
        if (node == null)
            return 0;
        return 1 + countBooksRecursive(node.left) + countBooksRecursive(node.right);
    }
    public Book searchByTitle(String title) {
        return searchByTitleRecursive(root, title);
    }
    private Book searchByTitleRecursive(BSTNode node, String title) {
        if (node == null)
            return null;
        if (node.data.title.equalsIgnoreCase(title))
            return node.data;
        Book leftSearch = searchByTitleRecursive(node.left, title);
        if (leftSearch != null)
            return leftSearch;
        return searchByTitleRecursive(node.right, title);
    }
    public Book searchByPrice(double price) {
        return searchByPriceRecursive(root, price);
    }
    private Book searchByPriceRecursive(BSTNode node, double price) {
        if (node == null)
            return null;
        if (node.data.price == price)
            return node.data;
        Book leftSearch = searchByPriceRecursive(node.left, price);
        if (leftSearch != null)
            return leftSearch;
        return searchByPriceRecursive(node.right, price);
    }
    
     // 1.5 Search by bcode
    public static Book searchByBcode(String bcode) {
        return searchByBcode(root, bcode);
    }

    private static Book searchByBcode(BSTNode node, String bcode) {
        if (node == null) {
            return null;
        }
        if (bcode.equals(node.data.bcode)) {
            return node.data;
        }
        if (bcode.compareTo(node.data.bcode) < 0) {
            return searchByBcode(node.left, bcode);
        } else {
            return searchByBcode(node.right, bcode);
        }
    }
}
