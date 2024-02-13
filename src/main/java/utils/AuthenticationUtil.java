package utils;

import classes.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationUtil {
    private static final String FILE_PATH = "dat/user_credentials.txt";

    private static Map<String, User> userCredentials;

    public AuthenticationUtil() {

    }

    private static Map<String, User> loadCredentialsFromFile() {
        Map<String, User> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String username = parts[0];
                boolean isAdmin = Boolean.parseBoolean(parts[1]);
                String hashedPassword = parts[2];
                credentials.put(username, new User(username, hashedPassword, isAdmin));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    private static void saveCredentialsToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH,true)) {
            for (Map.Entry<String, User> entry : userCredentials.entrySet()) {
                User user = entry.getValue();
                writer.write(user.getUsername() + ":" + user.getAdministrator() + ":" + user.getPassword() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean authenticate(String username, String password) {
        userCredentials = loadCredentialsFromFile();
        String hashedPassword = hashPassword(password);
        User user = userCredentials.get(username);

        return user != null && hashedPassword != null && hashedPassword.equals(user.getPassword());
    }

    public static void addUser(String username, String password, boolean isAdmin) {
        if (userCredentials == null) {
            userCredentials = new HashMap<>();
        }
        String hashedPassword = hashPassword(password);
        User newUser=new User(username,hashedPassword,isAdmin);

        if (userCredentials.containsKey(username)) {
            userCredentials.replace(username, newUser);
        } else {
            userCredentials.put(username, newUser);
        }

        saveCredentialsToFile();
    }

    public static boolean isAdmin(String username) {
        User user = userCredentials.get(username);
        return user != null && user.getAdministrator();
    }
}
