package com.company;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("set your atm");
        System.out.println("set total number of notes and notes value in atm");

        Atm atm =new Atm();
        System.out.println("enter th condition of atm  1 for start" +
                " 2 for off");
        int flag = scan.nextInt();
        if(flag==1)
            atm.start();
        else
            atm.off();
    }
}