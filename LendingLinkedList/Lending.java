package LendingLinkedList;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author admin
 */
public class Lending {
    private String bcode;
    private String rcode;
    private Date ldate;
    private Date rdate;
    private int state;

    public Lending() {
    }

    public Lending(String bcode, String rcode, Date ldate, Date rdate, int state) {
        this.bcode = bcode;
        this.rcode = rcode;
        this.ldate = ldate;
        this.rdate = rdate;
        this.state = state;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public Date getLdate() {
        return ldate;
    }

    public void setLdate(Date ldate) {
        this.ldate = ldate;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedLdate = dateFormat.format(ldate);
        String formattedRdate = (rdate != null) ? dateFormat.format(rdate) : "null";
        return String.format("%-7s| %-7s| %-13s| %-13s| %-4d", bcode, rcode, formattedLdate, formattedRdate, state);
    }
    
}
