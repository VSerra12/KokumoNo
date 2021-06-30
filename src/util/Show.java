package util;

import java.util.Scanner;

public class Show {

    private final Scanner keyboard;

    private static Show myShow;

    public static Show getShow(){

        if (myShow == null){
            myShow = new Show(new Scanner(System.in));
        }

        return myShow;
    }

    public Show(Scanner keyboard) {
        this.keyboard = keyboard;
    }


    public void printer(String message){

        System.out.print(message);
    }

    public String inputString(){
        return keyboard.next();
    }

    public char inputChar(){
        return keyboard.next().charAt(0);
    }
}
