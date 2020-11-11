package com.company;
import java.util.Scanner;

import java.util.Scanner;
public class Cashdispenser {  // the class is regarding dispenser
    Scanner scan = new Scanner(System.in);
    private int totalnotes;
    private int remaining;
    private int notesvalue;

    public Cashdispenser(){  // constructor
        this.totalnotes = scan.nextInt();
        this.notesvalue=scan.nextInt();
        this.remaining=this.totalnotes; // initially remaining notes is same as total notes
    }

    public void setNotesvalue(int y) {  // to set note value
        this.notesvalue = y;
    }

    public void dispense(double amount){  // to take requred number of notes from dispenser
        int x = (int)(int)Math.floor(amount)/this.notesvalue;
        if(remaining>=x){ //checking the amount available or not
            remaining = remaining-x;
            System.out.println("PLEASE COLLECT YOUR CASH");
        }
        else{
            System.out.println("your required amount is not available");
        }
    }

    public void setTotalnotes(int x) {  // to set total notes
        this.totalnotes = x;
    }

    public double getmoney(){ // to get the money availale in dispenser
        // System.out.println(this.totalnotes);
        //System.out.println(this.remaining);
        //System.out.println(this.notesvalue);
        //System.out.println(this.notesvalue*this.remaining)
        return this.notesvalue*this.remaining;
    }
    public int getRemaining() { // to get remaining notes
        return remaining;
    }

    public void setRemaining(int x) {
        this.remaining = x;
    }

    public int getTotalnotes() { // to get total notes
        return totalnotes;
    }

    public int getNotesvalue() {  // to get the notes value
        return notesvalue;
    }
}
