package main;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class ErrorHandle {

    public static void error(String message){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ERROR: COULD NOT COMPLETE " + message + " ACTION. " + dtf.format(now));
    }

    public static void warning(String message){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("WARNING: " + message + " MAY NOT BE STABLE. " + dtf.format(now));
    }
}
