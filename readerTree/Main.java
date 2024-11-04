/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package readerTree;

import BookTree.BSTree;
import BookTree.Book;
import LendingLinkedList.*;
import java.util.Scanner;

public class Main {
    public static LendingList le = new LendingList();
    public static BSTree bs = new BSTree();
    public static ReaderBST re = new ReaderBST();
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
                    re.loadFromFile("Resourse\\readers.txt");
                    break;

                case 2:
                    ReaderBST.addReader();

                    break;
                case 3:
                    ReaderBST.displayPreOrder();
                    break;

                case 4:
                    ReaderBST.saveToFile("Resourse\\readers.txt");
                    break;

                case 5:
                    System.out.print("Enter rcode to search: ");
                    String rcode = Validate.checkInputString();
                    ReaderBST.searchByRcode(rcode);
                    break;
                case 6:
                    System.out.print("Enter rcode to delete: ");
                    rcode = Validate.checkInputString();
                    ReaderBST.deleteByRcode(rcode);
                    break;
                case 7:

                    ReaderBST.searchReaderByName();
                    break;
                case 8:

                case 9:
                    return;

            }

        }

    }

    public static void menu3() {
        Scanner sc = new Scanner(System.in);
        int choice;
        
        do {
            le.displayMenu();
            System.out.print("Please choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    re.loadFromFile("Resource\\Reader.txt");
                    bs.loadDataFromFile("Resource\\Book.txt");
                    le.loadFromFile("Resource\\Lending.txt"); 
                    break;
                case 2:
                    le.lendBook(sc); 
                    break;
                case 3:
                    le.displayLendings(); 
                    break;
                case 4:
                    le.saveLendingListToFile("Resource\\lending.txt"); 
                    break;
                case 5:
                    le.sortByBcodeAndRcode(); 
                    le.displayLendings();
                    break;
                case 6:                  
                    le.returnBookByBcodeAndRcode(); 
                    System.out.println("Returning book completed~");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        } while (choice != 0);

        sc.close();
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("====Library Management Program====");
            System.out.println("1.Book");
            System.out.println("2.Reader");
            System.out.println("3.Lending");
            System.out.println("4.Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice = Validate.checkInputIntLimit(1, 4);
            switch (choice) {
                case 1:
//                    ReaderBST.menu1();
                    break;
                case 2:
                    ReaderBST.menu2();
                    break;
                case 3:
                    menu3();
                    break;
                case 4:
                    return;

            }

        }
    }

}
