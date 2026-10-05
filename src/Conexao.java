/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/imobilar";
    private static final String USUARIO = "root";
    private static final String SENHA = "Taila0909*";

    public static Connection conectar() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexao = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA
            );

            System.out.println("Conexao realizada com sucesso!");

            return conexao;

        } catch (Exception e) {

            System.out.println("Erro ao conectar com o banco!");
            e.printStackTrace();

            return null;
        }
    }
}