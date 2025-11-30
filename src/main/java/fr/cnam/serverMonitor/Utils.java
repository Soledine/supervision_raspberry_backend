package fr.cnam.serverMonitor;

import java.time.format.DateTimeFormatter;

public class Utils {

    public static DateTimeFormatter dateFormatter(){
        return DateTimeFormatter.ofPattern("HH:mm:ss");
    }
}
