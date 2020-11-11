package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Transfer extends Transaction { // class transfer extends transaction
    private int srcaccountNumber;
    private String pin;
    private Bank bank;
    //private Atm atm;
    private double amount;
    private int destaccountnumber;

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

    public Transfer(int accountNumber, Bank bank, String pin, String phn) { // constructor
        super(accountNumber, bank, pin, phn);  // super class constructor
       // System.out.println("enter the account number for which you want to send");
       // this.destaccountnumber = scan.nextInt();
       // System.out.println("enter the amount to be deposited");
        //this.amount = scan.nextDouble();
    }

    int otp;
    int i = -1;
    ArrayList<Integer> otps = new ArrayList<>();

    public void createotp() {  // to generate otp
        int accnumber1 = getAccnumber();
        String phns = getPhn();
        Random r = new Random();
        String randomNumber = String.format("%04d", r.nextInt(10001));
        otp = r.nextInt(10001);
        otps.add(otp);
        System.out.println("your otp for your transcarion is" + otp);
        ++i;
    }
     double x,y,z,w;
   // @Override
    public void trans(){  // overriding method
        Bank bank = getBank();
        int accnumber = getAccnumber(); // to get account number
        String pins = getPin(); // to get pin
        String phs = getPhn(); // to get phone number
        try {


            //rs.close();
            //statement.close();
            //conn.close();
            System.out.println("enter the account number for which you want to send");
            this.destaccountnumber = scan.nextInt(); // destination ccount number
            if(getBank().isThereAccount(destaccountnumber)){ // to check whether it is valid input or not
                System.out.println("enter amount to be transfered");
                this.amount= scan.nextDouble();
               // System.out.println(getBank().getAmount(accnumber,pins));
                if(this.amount<= getBank().getAmount(accnumber,getPin())){ // to compare it with balance of source account
                   // System.out.println("yes1");
                    Connection conn = Dbconnector.connect();
                    Statement statement = conn.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                    x = rs.getDouble(COLUMN_BALANCE);
                    System.out.println("your current account balance is " + x); // x is source account balance
                    z=x-this.amount;
                    rs.close();
                   // statement.close();
                   // conn.close();
                   // Connection connection = Dbconnector.connect();
                   // Statement statement1 = connection.createStatement();
                    System.out.println("yes");
                    statement.executeUpdate("update customers"+" set balance = "+z+" where accnum = "+accnumber); // to update source account balance
                    ResultSet rs1 = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber);
                    y = rs1.getDouble(COLUMN_BALANCE);
                    System.out.println("your initial account balance is " + y); // balace after transfering
                    rs1.close();
                    //statement1.close();
                   // connection.close();

                    //statement1.close();
                   // connection.close();
                    System.out.println("yes");
                   // Connection connection1 = Dbconnector.connect();
                   // Statement statement2 = connection1.createStatement();
                    ResultSet rs2 = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + this.destaccountnumber);
                    x = rs2.getDouble(COLUMN_BALANCE); // current account balance of dest account
                    w = x+this.amount;
                    statement.executeUpdate("update customers"+" set balance = "+w+" where accnum = "+this.destaccountnumber);// to update balance of destination account
                  rs2.close();
                    statement.close();
                    //connection1.close();
                    conn.close();
                }
            }
            else{  // if dest account entered not valid
                System.out.println("your destination customer no long exists");
                System.out.println("enter 1 to exit or 2 to proceed");
                int b = scan.nextInt();
                switch (b){
                    case 1:  // to ext from here
                        break;
                    case 2: // to run again
                        createotp();
                        trans();
                        break;
                }
            }
        }catch (Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
            } catch (Exception e) {

            }
        }
    }
}
