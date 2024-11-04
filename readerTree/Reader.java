/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package readerTree;

/**
 *
 * @author admin
 */
public class Reader {
     String rcode, name;
    int byear;
    

    public Reader(String rcode, String name, int byear) {
        this.rcode = rcode;
        this.name = name;
        this.byear = byear;
        
    }

   @Override
public String toString() {
         
    return String.format("%-10s | %-20s | %-10d", rcode, name, byear);
}
}
