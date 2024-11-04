/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookTree;

/**
 *
 * @author admin
 */
public class BSTNode {

    public Book data;
    public BSTNode left, right;

    public BSTNode(Book data) {
        this.data = data;
        this.left = this.right = null;
    }

}
