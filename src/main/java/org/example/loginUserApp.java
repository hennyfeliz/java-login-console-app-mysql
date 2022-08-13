package org.example;

import static org.example.Inits.conn;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.Scanner;

public class loginUserApp {

    public static Statement statement;
    public static ResultSet result;
    static Scanner scanner;

    public static void main(String[] args) {
        initConnection();
        menuRegister();
    }
    public static void menuRegister(){
        scanner = new Scanner(System.in);
        int opt;

        do {
            System.out.println("1. Login. \n2. Registrar. \n100. Salir. \nOpcion: ");
            opt = scanner.nextInt();

            switch (opt){
                case 1: login_user(); break;
                case 2: sing_in_user(); break;
                default:
                    System.out.println("Opcion no disponible...");
            }

        }while(opt!=100);
    }

    public static void sing_in_user(){
        scanner = new Scanner(System.in);
        String email, password;

        System.out.print("\n ----- [ REGISTRO ] -----\nCorreo electronico: ");
        email = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();

        boolean des = verifyUser(email, password);

        if(des){
            System.out.println("Ya existe un registro con estos datos...");
        } else {
            try {
                String sql = "insert into users (email_user, password_user) " +
                        "values (?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(1, email);
                statement.setString(2, password);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("\nA new user was inserted successfully!\n");
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e);
                e.getMessage();
            }
        }
    }

    public static void login_user(){
        scanner = new Scanner(System.in);
        String email, password;

        System.out.print("\n ----- [ LOGIN ] -----\nCorreo electronico: ");
        email = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();

        boolean des = verifyUser(email, password);

        if(des){
            System.out.println("Login exitoso!");
        } else {
            System.out.println("Error de cuenta o contraseña...");
        }
    }

    public static boolean verifyUser(String email, String password){
        boolean des = false;
        try {

            String sql = "SELECT * FROM users";

            statement = conn.createStatement();
            result = statement.executeQuery(sql);

            while (result.next()){
                if(email.equals(result.getString(2)) && (password.equals(result.getString(3)))){
                    des = true;
                }
            }


        } catch (Exception e){
            System.out.println("Exception: " + e);
            e.getMessage();
        }
        return des;
    }

    private static void initConnection() {
        String dbURL = "jdbc:mysql://localhost:3306/login_user";
        String username = "root";
        String password = "feferrefe2020";

        try {
            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
