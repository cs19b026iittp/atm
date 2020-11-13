package com.company;
import java.util.Scanner;

public abstract class Transaction {
    private Atm atm1;
    private int accountnumber;
    private String pin;
    private Bank bank;
    private String phn;


    public Transaction() {
       // this.atm1 = new Atm();
        //this.cashDispenser = new CashDispenser();

    }

    public Transaction(int accountnumber, Bank bank, String pin, String phn) { //constructor
        this.accountnumber = accountnumber;
        this.bank = bank;
        this.pin = pin;
      //  this.atm1 = new Atm();
        this.phn = phn;
    }

    Scanner scan = new Scanner(System.in);

    public int getAccnumber() { // to get account number
        return accountnumber;
    } // getter function

    public Bank getBank() {
        return bank;
    }

    public String getPin() { // to get pin
        return pin;
    }

    public String getPhn() {
        return phn;
    }

    public abstract void trans(); // this method is to be executed by withdrawal , checkbalance,deposit
   public abstract void createotp(); // to create otp
}
