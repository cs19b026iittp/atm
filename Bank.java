package com.company;
import java.sql.*;
import java.util.Scanner;
public class Bank {  // bank class
    public static final String DB_NAME = "cont.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLE_CONTACTS = "customers";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ACCNUM = "accnum";
    public static final String COLUMN_PIN = "pin";
    public static final String COLUMN_BLOCKED = "blocked";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BALANCE = "balance";

    boolean flag=false;
    boolean flag1 = false;
  // Connection conn = null;
   // Statement statement = null;
   // ResultSet rs = null;
   public Bank(){ // constructor

   }

    Scanner scan = new Scanner(System.in);
    public boolean check(int accountnumber,String pin) throws SQLException { // to check whether he is valid user or not
        int accnumber = accountnumber;
        String pins = pin;

        String query = "SELECT accnum FROM " + TABLE_CONTACTS + " WHERE accnum = " + accnumber;
        String query1 = "SELECT pin FROM customers WHERE pin = " + pins;
        try {
            /* Connection */ //conn = DriverManager.getConnection(CONNECTION_STRING);
            Connection conn = Dbconnector.connect(); // connection
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query); // to check whether that account number is there or not
            flag = rs.isBeforeFirst();
            if (flag) {
                ResultSet rs1 = statement.executeQuery(query1); // if pin matches
                flag1 = rs1.isBeforeFirst();
                if (flag1)
                    return true; // then returns true
            }
            rs.close();
            statement.close();
            conn.close(); // close

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        } finally {
            Dbconnector.close();
        }

       /* Dbconnector dbconnector = new Dbconnector();
        if(!dbconnector.open()) {
            System.out.println("Can't open datasource");
        }
        List<User> users = dbconnector.queryUsers();
        if (users == null) {
            System.out.println("No users!");
        }
        if (users != null) {
            for (User user : users) {
                if (user.getAccountnumber() == accnumber && user.getPin().equals(pins)) {
                    System.out.println(user.getAccountnumber());
                    System.out.println(user.getPin());
                    return true;
                }
                System.out.println(user.getAccountnumber());
                System.out.println(user.getPin());
            }
        }*/
        return false;
    }
    public boolean isThereAccount(int accountnumber){ // is the account exist
        int cusa = accountnumber;
        try {
           Connection conn = Dbconnector.connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT accnum from customers");
            while (rs.next()){
               if(cusa == rs.getInt(COLUMN_ACCNUM)) // if customer account number exists in database
                   return true;
            }
            rs.close();
            statement.close();
            conn.close(); // closing
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{

            Dbconnector.close();
            }
            catch (Exception e){
            System.out.println("error3");
        }
        }
        return false;
    }
  double l=-1;
   public double getAmount(int accontnumber,String pin){ // to get the amount
        int cusb = accontnumber;
        String piny = pin;
        try {
          // /* Connection */conn = DriverManager.getConnection(CONNECTION_STRING);
             Connection conn = Dbconnector.connect(); // connection
            Statement statement = conn.createStatement();

                System.out.println("hii");
                ResultSet rs = statement.executeQuery("SELECT balance FROM " + TABLE_CONTACTS + " WHERE accnum = " + cusb);
                l = rs.getDouble(COLUMN_BALANCE); // balance of the particular account

            rs.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {

            Dbconnector.close();
        }
        return l;
    }
    public boolean status(int accountnumber){ // to check the status of the card
        int cusa = accountnumber;
        Connection conn = Dbconnector.connect(); // connection
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT blocked FROM " + TABLE_CONTACTS + " WHERE accnum = " + cusa);
            return rs.getBoolean(COLUMN_BLOCKED);  // to return status
        }catch(Exception e){
            System.out.println("error 5");
        }
       return false;
    }
  /* public void add(int accountnumber,String pin,double amount) {
        int cusa = accountnumber;
        String pins = pin;
        try {
            Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            Statement statement = conn.createStatement();

           if (confirmAccount(cusa, pins) != -1) {
                statement.executeQuery("UPDATE "+TABLE_CONTACTS+" set balance=balance+"+amount+" WHERE accnum = "+cusa);
           }

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                statement.close();
                conn.close();
            }catch (Exception e){

            }
        }
    }
    public void take(int accountnumber,String pin,double amount) {
        int cusa = accountnumber;
        String pins = pin;
        try {
            Statement  statement = conn.createStatement();

            if (confirmAccount(cusa, pins) != -1) {
                statement.executeQuery("UPDATE "+TABLE_CONTACTS+" set balance=balance-"+amount+" WHERE accnum = "+cusa);
            }
            rs.close();
            statement.close();
            conn.close();
        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                statement.close();
                conn.close();
            }catch (Exception e){

            }
        }
    }*/
    public void addcustomer(){ // to add customer
        Dbconnector db = new Dbconnector();
        try {
            Connection conn = Dbconnector.connect();
            Statement statement = conn.createStatement();
            System.out.println("would you like to add account: yes or no"); // to check whether he wants or not
            String choice = scan.next(); // to get choice
            if (choice.equals("yes")) {
                String q = "INSERT INTO customers(name,accnum,pin,balance,blocked,phone,email) VALUES(?,?,?,?,?,?,?)";
                db.executequery(q); // to execute the query
            }
            statement.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            Dbconnector.close();
        }
    }
    public void updateBalance(int a,double d){ // to update balance
        Dbconnector db = new Dbconnector();
        try {
            Connection conn = Dbconnector.connect(); // connection
            Statement statement = conn.createStatement();
                String q = "update customers  set balance = ? where accnum= ?"; // to set the updated balance
                db.executequery1(q,a,d); // to execute it
           // }
            statement.close();
            conn.close();
        }catch(Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            Dbconnector.close();
        }
    }
    public void updatePin(String s,int accountnumber){ // to uodate pin
        Dbconnector db = new Dbconnector();
        try {
            Connection conn = Dbconnector.connect(); // connection
            Statement statement = conn.createStatement();
            // System.out.println("would you like to add account: yes or no");
            // String choice = scan.next();
            // if (choice.equals("yes")) {
            String q = "update customers  set pin = ? where accnum= ?"; // to set pin
            db.executequery2(q,s,accountnumber); // to execute query
            // }
            statement.close();
            conn.close(); // closing
        }catch(Exception e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            Dbconnector.close();
        }
    }
}

