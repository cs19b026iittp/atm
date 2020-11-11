package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Withdrawal extends Transaction{ // class withdrawal extends transaction
    private int accountNumber;
    private String pin;
    private Bank bank;
   private Cashdispenser cashdispenser;
    private double amount;
    private double limit = 50000;
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
    public static final String UPDATE_BALANCE = "UPDATE " + TABLE_CONTACTS + " SET " + COLUMN_BALANCE + " = ? WHERE " + COLUMN_ACCNUM + " = ?";


    public Withdrawal(int accountNumber, Bank bank, String pin, String phn,Cashdispenser cashdispenser) { // constructor
        super(accountNumber, bank, pin, phn);  // super constructor
        System.out.println("enter the amount to be deposited");
        this.amount = scan.nextDouble();
    }


    int otp;
    int i = -1;
    ArrayList<Integer> otps = new ArrayList<>();

    public void createotp() { // to create otp
        int accnumber1 = getAccnumber();
        String phns = getPhn();
        Random r = new Random();
        String randomNumber = String.format("%04d", r.nextInt(10001));
        otp = r.nextInt(10001);
        otps.add(otp);
        System.out.println("your otp for your transcarion is" + otp);
        ++i;
    }

   // @Override
    public void trans() { // overriding method
        Bank bank = getBank();
        int accnumber = getAccnumber();
        String pins = getPin();
        double available = cashdispenser.getmoney();
        //  createotp();
        System.out.println("enter your otp here to process your transaction");
        int otpEntered = scan.nextInt();
        double x;
        double z;
        double q;
        try {
            // Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Connection conn = Dbconnector.connect();
            Statement statement = conn.createStatement();
            if (amount <= limit) { // to check the limit condition

               if(amount > available) {  // condition to check with amount available in atm
                   //System.out.println(i);
                   if (otpEntered == otp) {  // to verify with the otp
                       ResultSet rs = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                       x = rs.getDouble(COLUMN_BALANCE);  // current balance
                       System.out.println("your initial account balance is " + x);
                       z = x - amount;
                       //updateBalance(z);
                       // statement.addBatch("update customers set balance=balance+"+amount+" where accnum = "+accnumber);
                       statement.executeUpdate("update customers" + " set balance = " + z + " where accnum = " + accnumber); // to update balance
                       ResultSet rs1 = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                       q = rs1.getDouble(COLUMN_BALANCE); // balance after withdrawal
                       System.out.println("your initial account balance is " + q);
                       cashdispenser.dispense(amount); // to dispense cash from atm

                   } else {  // if otp verification fails
                       System.out.println("enter correct otp");
                       createotp(); // to get otp again
                       trans();
                   }
               }else{  // if amunt in atm is less than amount required
                   System.out.println("sufficient cash is not available here");
               }
            } else   // limit conditon
                System.out.println("enter amount less than or equal to 50000");

            statement.close();
            conn.close(); // to close
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {

            }
        }
    }
   /* public void updateBalance ( double s) {
        double l = s;
        PreparedStatement ps = null;
        try {
           // Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Connection conn = Dbconnector.connect();
            String sql = "UPDATE "+TABLE_CONTACTS+" SET "+"balance = ? WHERE accnum = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1,s);
            ps.setInt(2,getAccnumber());
            ps.execute();
            conn.close();
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }*/
}

