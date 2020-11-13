package com.company;

import com.company.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public  class Dbconnector {
    private Connection conn;
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

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:cont.db");
            System.out.println("connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*private static Connection c = null;
    public static Connection connect(){
        if(c==null){
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:atm.db");
            }catch(Exception e){

            }
        }
        return c;
    }*/
    public static void close() {
        try {
            connect().close();
        } catch (SQLException e) {

        }
    }

    public void executequery(String query) {
        Scanner scan = new Scanner(System.in);
        connect();
        ResultSet res = null;
        PreparedStatement ps = null;
        String name, pin, phone, email;
        int acountnumber;
        boolean blocked;
        double balance;
        System.out.println("enter name");
        name = scan.next();
        System.out.println("enter account number");
        acountnumber = scan.nextInt();
        System.out.println("enter password");
        pin = scan.next();
        System.out.println("enter your balance");
        balance = scan.nextDouble();
        System.out.println("status of account");
        blocked = scan.nextBoolean();
        System.out.println("enter phone number");
        phone = scan.next();
        System.out.println("enter email");
        email = scan.next();
        try {
            ps = connect().prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, acountnumber);
            ps.setString(3, pin);
            ps.setDouble(4, balance);
            ps.setBoolean(5, blocked);
            ps.setString(6, phone);
            ps.setString(7, email);
            ps.execute();
        } catch (Exception e) {
            System.out.println("error" + e.getMessage() + "");
        }
    }

    /* public static void insertContact(Statement statement, String name, int accountnumbner, String pin, double balance, boolean blocked, String phone, String email) throws SQLException {
         statement.execute("INSERT INTO " + TABLE_CONTACTS +
                 " (" + COLUMN_NAME + ", " +
                 COLUMN_ACCNUM + ", " +
                 COLUMN_PIN + ", " +
                 COLUMN_BALANCE + ", " +
                 COLUMN_BLOCKED + ", " +
                 COLUMN_PHONE + ", " +
                 COLUMN_EMAIL +
                 " ) " +
                 "VALUES('" + name + "', " + accountnumbner + ", " + pin + ", " + balance + ", " + blocked + ", " + phone + ", '" + email + "')");

     }*/
    public List<User> queryUsers() {

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS)) {

            List<User> users = new ArrayList<>();
            while (results.next()) {
                User user = new User();
                user.setName(results.getString(COLUMN_NAME));
                user.setAccountnumber(results.getInt(COLUMN_ACCNUM));
                user.setPin(results.getString(COLUMN_PIN));
                user.setBalance(results.getDouble(COLUMN_BALANCE));
                user.setBlocked(results.getBoolean(COLUMN_BLOCKED));
                user.setPhone(results.getString(COLUMN_PHONE));
                user.setEmail(results.getString(COLUMN_EMAIL));
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }
    public void executequery1(String query,int accountnumber,Double balance)
    {
        int cusa = accountnumber;
        ResultSet res=null;
        connect();
        try{
            String sql="update customers  set balance = ? where accnum= ?";
            PreparedStatement  ps=connect().prepareStatement(sql);
            ps.setInt(1,cusa);
            ps.setDouble(2,balance);
            ps.execute();
            System.out.println("balance updated");
        }
        catch(Exception e)
        {
            System.out.println("error" + e.getMessage() + "");

        }

    }
    public void executequery2(String query,String s,int accountnumber)
    {
        int cusa = accountnumber;
        ResultSet res=null;
        connect();
        try{
            String sql="update customers  set pin = ? where accnum= ?";
            PreparedStatement  ps=connect().prepareStatement(sql);
            ps.setString(1,s);
            ps.setInt(2,cusa);
            ps.execute();
            System.out.println("balance updated");
        }
        catch(Exception e)
        {
            System.out.println("error" + e.getMessage() + "");

        }

    }

}
