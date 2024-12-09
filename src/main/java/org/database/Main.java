package org.database;

import org.database.services.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {

        //Examples examples = new Examples();

        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("secrets.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find secrets.properties");
                return;
            }
            properties.load(input);

            String password = properties.getProperty("PASSWORD");


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //examples.settingEntryOnAndOff();




    }
}