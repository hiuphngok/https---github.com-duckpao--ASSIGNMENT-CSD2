package LendingLinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import readerTree.*;
import BookTree.*;

/**
 *
 * @author admin
 */
public class LendingList {

    Node head;
    Node tail;

    public LendingList() {
        head = null;
        tail = null;
    }

    boolean isEmpty() {
        return (head == null);
    }

    void clear() {
        head = null;
        tail = null;
    }

    void addLast(Lending x) {
        Node p = new Node(x);
        if (isEmpty()) {
            head = p;
            tail = p;
        } else {
            tail.next = p;
            tail = p;
        }
    }

    public void loadFromFile(String filename) {
        clear();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s*,\\s*");
                if (data.length == 5) {

                    String bcode = data[0];
                    String rcode = data[1];
                    Date ldate = dateFormat.parse(data[2]);
                    Date rdate = data[3].equals("null") ? null : dateFormat.parse(data[3]);
                    int state = Integer.parseInt(data[4]);

                    Lending lending = new Lending(bcode, rcode, ldate, rdate, state);
                    addLast(lending);
                }
            }
            System.out.println("Lending data loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing state value: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void lendBook(Scanner sc) {
        try {
            System.out.print("Enter bcode: ");
            String bcode = sc.nextLine();

            Book book = BSTree.searchByBcode(bcode);
            if (book == null) {
                System.out.println("Book with (" + bcode + ") does not exist.");
                return;
            }

            if (book.getQuantity() <= 0) {
                System.out.println("No available copies of book (" + bcode + ").");
                return;
            }
            System.out.print("Enter rcode: ");
            String rcode = sc.nextLine();
            if (!ReaderBST.validateRcode(ReaderBST.getRoot(), rcode)) {
                System.out.println("Reader with (" + rcode + ") not exist.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter the correct data type for each attribute.");
        }
    }

    public void saveLendingListToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            Node current = head;
            while (current != null) {
                pw.println(current.data.toString());
                current = current.next;
            }

            System.out.println("Lending list saved successfully to  " + filename);
        } catch (IOException e) {
            System.err.println("Fail to save lending list to fila " + e.getMessage());
        }
    }

    public void displayLendings() {
        if (head == null) {
            System.out.println("No lending records available.");
        } else {
            Node current = head;
            while (current != null) {
                System.out.println(current.data);
                current = current.next;
            }
        }
    }

    void swap(Node p, Node q) {
        Lending temp = p.data;
        p.data = q.data;
        q.data = temp;
    }

    public void sortByBcodeAndRcode() {
        Node p = head;
        while (p != null) {
            Node q = p.next;
            while (q != null) {
                int bcodeCompare = p.data.getBcode().compareTo(q.data.getBcode());
                if (bcodeCompare > 0 || (bcodeCompare == 0 && p.data.getRcode().compareTo(q.data.getRcode()) > 0)) {
                    swap(p, q);
                }
                q = q.next;
            }
            p = p.next;
        }
    }

    public boolean returnBookByBcodeAndRcode() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter bcode: ");
        String bcode = sc.nextLine();
        
        Book book = BSTree.searchByBcode(bcode);
        if (book == null) {
            System.out.println("Book with (" + bcode + ") does not exist.");
            return false;
        }
        System.out.print("Enter rcode: ");
        String rcode = sc.nextLine();
        if (!ReaderBST.validateRcode(ReaderBST.getRoot(), rcode)) {
            System.out.println("Reader with (" + rcode + ") not exist.");
            return false;
        }
        Node current = head;

        while (current != null) {
            Lending lending = current.data;
            if (lending.getBcode().toUpperCase().equals(bcode.toUpperCase()) && lending.getRcode().toUpperCase().equals(rcode.toUpperCase())) {
                lending.setState(2);
                lending.setRdate(new Date());

                if (book.getLended() > 0) {
                    book.setLended(book.getLended() - 1);
                }

                return true;
            }
            current = current.next;
        }
        return false;

    }

    public void displayMenu() {
        System.out.println("====== Lending Management Menu ======");
        System.out.println("3.1. Load data from file");
        System.out.println("3.2. Lend book");
        System.out.println("3.3. Display data");
        System.out.println("3.4. Save lending list to file");
        System.out.println("3.5. Sort by bcode + rcode");
        System.out.println("3.6. Return book by bcode + rcode");
        System.out.println("0. Exit");
    }

}
