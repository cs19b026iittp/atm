package com.company;

import com.company.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Checkbalance extends Transaction { // checkbalance extends transaction
    private int accountNumber;
    private String pin;
    private Bank bank;
    private Atm atm;
    public static final String DB_NAME = "cont.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLE_CONTACTS = "customers";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACCNUM = "accnum";
    public static final String COLUMN_PIN = "pin";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_BLOCKED = "blocked";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";

    public Checkbalance(int accountNumber, Bank bank, String pin, String phn) { // constructor
        super(accountNumber, bank, pin, phn); // super class onstructor
    }
     int otp;
    @Override
    public void createotp() { // to generate otp
      int accnumber1 = getAccnumber();
      String phns = getPhn();
        Random r = new Random(); // random function to generate randomn number
        String randomNumber = String.format("%04d",r.nextInt(10001));
        otp=r.nextInt(1001);
        System.out.println("your otp for your transcarion is"+otp);
    }

   // @Override
    public void trans() { // overriding method
        Bank bank = getBank();
        int accnumber = getAccnumber(); // to get account number
        String pins = getPin(); // to get pin
        System.out.println("enter your otp here to process your transaction");
        int otpEntered = scan.nextInt();
        double x;
        try {
           // Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Connection conn = Dbconnector.connect(); // connrection
            Statement statement = conn.createStatement();
            if(otpEntered==otp) { // to verify otp
                ResultSet rs = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                x = rs.getDouble(COLUMN_BALANCE);// current account balance
                System.out.println("your current account balance is " + x);
            }
            else {  // if otp verification fails
                System.out.println("enter correct otp");
                createotp();
                trans();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {

            }
        }
    }


}
