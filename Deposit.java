package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Deposit extends Transaction{
    private int accountNumber;
    private String pin;
    private Bank bank;
    //private Atm atm;
    private double amount;
    private double limit = 50000; // limit
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


    public Deposit(int accountNumber, Bank bank, String pin, String phn) {  // constructor
        super(accountNumber, bank, pin, phn);  // super class constructor
        System.out.println("enter the amount to be deposited");
        this.amount = scan.nextDouble();  // to get amount
    }


    int otp;
    int i = -1;
    ArrayList<Integer> otps = new ArrayList<>();

    public void createotp() {  // to generate otp
        int accnumber1 = getAccnumber();
        String phns = getPhn();
        Random r = new Random();  // random function to generate otp
        String randomNumber = String.format("%04d", r.nextInt(10001));
        otp = r.nextInt(10001); // 4 digit
        otps.add(otp);
        System.out.println("your otp for your transcarion is" + otp);
        ++i;
    }

   @Override
    public void trans() { // overriding method
        Bank bank = getBank();
        int accnumber = getAccnumber();
        String pins = getPin();
        //  createotp();
        System.out.println("enter your otp here to process your transaction");
        int otpEntered = scan.nextInt();
        double x;
        double z;
        double q;

           // Connection conn = DriverManager.getConnection(CONNECTION_STRING);

            if (amount <= limit) { // to check limit
                // System.out.println(otpEntered);
                // System.out.println(otps.get(i));

                //System.out.println(i);
                try {
                    if (otpEntered == otp) { // to verify otp

                        Connection conn = Dbconnector.connect(); // connection to db
                        Statement statement = conn.createStatement();
                        ResultSet rs = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                        x = rs.getDouble(COLUMN_BALANCE);// current balance
                        System.out.println("your initial account balance is " + x);
                        z = x + amount; // to add amount
                        rs.close();
                        statement.close();
                       // Statement statement1 = conn.createStatement();
                       // statement1.executeUpdate("update customers" + " set balance = " + z + " where accnum = " + accnumber);
                        // ResultSet rs1 = statement.executeUpdate("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                        //  q = rs1.getDouble(COLUMN_BALANCE);
                        //  System.out.println("your initial account balance is " + q);
                        // System.out.println("rows changed "+rowsaffected);
                        // statement.executeQuery("UPDATE customers set balance = "+z+" WHERE accnum = "+accnumber);
                        //statement1.close();
                        //ResultSet rs1 = statement1.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                       // q = rs1.getDouble(COLUMN_BALANCE);
                       // System.out.println("your initial account balance is " + q);
                       // rs1.close();
                        getBank().updateBalance(accnumber,z); // to update balance
                      //  statement1.close();
                        conn.close();
                        Dbconnector.close();


                    } else { // if otp verification failed
                        System.out.println("enter correct otp");
                        createotp();
                        trans();
                    }
                }catch (Exception e){  // exception handling
                    System.out.println("Something went wrong: ");
                    e.printStackTrace();
                }finally {
                    Dbconnector.close();
                }
            }else
                System.out.println("enter amount less than or equal to 50000");

           // statement.close();
          //  conn.close();

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

