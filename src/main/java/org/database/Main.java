package org.database;

import org.database.services.Examples;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Main {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String password, String storedHash) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(storedHash);
    }

    public static void main(String[] args) {

        //Examples examples = new Examples();
        //examples.ex();

        String password = "moje_super_haslo";
        String hashedPassword = hashPassword(password);
        System.out.println("Zahaszowane hasło: " + hashedPassword);


        String userInput = "moje_super_haslo";
        if (checkPassword(userInput, hashedPassword)) {
            System.out.println("Hasło jest poprawne!");
        } else {
            System.out.println("Niepoprawne hasło!");
        }



    }
}