package com.company;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Atm {
    private int customeraccnum;
    private String pin;
    private boolean isuser;
    private Bank bank;
    private Cashdispenser cashdispenser;
    private Transaction transaction;
    private String phn;

    public Atm() {
        this.customeraccnum = -1;
        this.isuser=false;
        this.pin = pin;
        this.bank = new Bank();
        this.cashdispenser = new Cashdispenser();
    }

    public void start(){ // to start the atm
        while (true) {
            while (isuser==false) {
                System.out.println("welcome");// shows welcome mssegate upto valid inputs are entered
                System.out.println("enter 1. if you are existing customer" +
                        "2. if you are new customer");
                int r = scan.nextInt();
                if(r==1){  // to check whether he is existing or new customer

                } else if(r==2){
                    Bank bank = new Bank();
                    bank.addcustomer();

                }
                else{
                    System.out.println("choose appropriate option");
                }
                checkuser();// checks user until valid inputs given
            }
            System.out.println("would you like to do changes: yes or no"); // if valid user to get changes if he want
            String option = scan.next();
            if(option.equals("yes")){
                System.out.println("enter 1: To change your pin" +
                                          "2: To change your name"+
                                          "3: to change your phone number");
                int g = scan.nextInt();
                switch (g) {
                    case 1 -> { // to change pin
                        System.out.println("enter your new pin");
                        String newpin = scan.next();
                        bank.updatePin(newpin,customeraccnum);
                       /* Connection connect = Dbconnector.connect();
                        try {
                            try (Statement st = connect.createStatement()) {
                                st.executeUpdate("update customers set pin = " + newpin + " where accnum =" + customeraccnum);
                                System.out.println("your pin is updated");
                            }catch (SQLException e){

                            }
                            connect.close();
                        } catch (Exception e) {
                            System.out.println("Something went wrong: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            Dbconnector.close();
                        }*/
                    }
                    case 2 -> { // to change name
                        System.out.println("enter your new name");
                        String newname = scan.next();
                        Connection connect1 = Dbconnector.connect();
                        try {
                            Statement st = connect1.createStatement();
                            st.executeUpdate("update customers set name = " + newname + " where accnum =" + customeraccnum);
                            st.close();
                            connect1.close();
                        } catch (Exception e) {
                            System.out.println("Something went wrong: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            Dbconnector.close();
                        }
                    }
                    case 3 -> {  // to change status of the card
                        System.out.println("enter your new phone number");
                        Boolean newblockes = scan.nextBoolean();
                        Connection connect2 = Dbconnector.connect();
                        try {
                            try (Statement st = connect2.createStatement()) {
                                st.executeUpdate("update customers set phone = " + newblockes + " where accnum =" + customeraccnum);
                                st.close();
                            }
                            connect2.close();
                        } catch (Exception e) {
                            System.out.println("Something went wrong: " + e.getMessage());
                            e.printStackTrace();
                        } finally {
                            Dbconnector.close();
                        }
                    }
                    default -> System.out.println("choose appropriate option");
                }
            }
            Bank bank = new Bank();
            if(!bank.status(customeraccnum)) { // to check whether card is blocked or not
                try {
                    dothis();  // if not blocked do this
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            else
                System.out.println("your account is blocked");
                // to do the required operation
            isuser = false; // at last
            customeraccnum = -1; // at last
            System.out.println("thank you"); // at last
        }
    }
    public void off(){ // to off the atm
        System.out.println("sorry the atm is off come tomorrow");
    }
    Scanner scan = new Scanner(System.in);
    public void checkuser(){ // to check the user based on inputs given
        System.out.println("enter account number");
        int accnumber = scan.nextInt();
        System.out.println("enter your pin");
        String pin = scan.next();
        try {
            isuser = bank.check(accnumber, pin);  // t0 check user

            double x;


            System.out.println(isuser);
            if(isuser==true){ // to check whether he is user or not if user allot it to cusaccountnumber
                customeraccnum = accnumber;
            }
            else {  // else to try again
                System.out.println("Invalid account number or pin .try again");
            }

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }finally {
            Dbconnector.close();
        }
    }
    public int getAccNumber(){
        return customeraccnum;
    }
    private Transaction dothis() throws Exception {
        Transaction cusTransaction = null;
        boolean exited = false;
        int select = menu(); // to select from the menu
        switch (select){
            case 1: // if selected 1 to do check balance
                cusTransaction = new Checkbalance(customeraccnum,bank,pin,phn);

                cusTransaction.createotp();
                cusTransaction.trans();

                break;
           case 2: // if selected 2 withdawal is selected
                cusTransaction = new Withdrawal(customeraccnum,bank,pin,phn,cashdispenser);
                cusTransaction.createotp();
                cusTransaction.trans();

                break;
            case 3: // if selected 3 deposit is done
                cusTransaction = new Deposit(customeraccnum,bank,pin,phn);
                cusTransaction.createotp();
                cusTransaction.trans();
                break;
           case 4:
                cusTransaction = new Transfer(customeraccnum,bank,pin,phn);
               cusTransaction.createotp();
              cusTransaction.trans();


            case 5: // if selected 4 to exit
                exited = true;
                break;
            default:
                System.out.println("please select from the above only or invalid selection");
                break;
        }
        return cusTransaction;
    }

    int a;
    private int menu(){  // menu to select options
        System.out.println("select the below given options");
        System.out.println("enter 1 to check balance");
        System.out.println("enter 2 to withdrawal");
        System.out.println("enter 3 to deposit funds");
        System.out.println(" enter 4 to transfer");
        System.out.println(" enter 5 to exit");
        a = scan.nextInt();
        return a;
    }


}


