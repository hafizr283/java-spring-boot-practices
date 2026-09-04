package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Hello world!");
        String url = "jdbc:mysql://localhost:3306/notification";
        String user = "root";
        String pass = "9268";
        String query = "select * from employees where salary>=?";
        String withdrawMoney = "update employees set salary=salary-? where id=?";
        String depositMoney = "update employees set salary=salary+? where id=?";
        try (
                Connection con = DriverManager.getConnection(url, user, pass);
                PreparedStatement ps = con.prepareStatement(query);
                PreparedStatement depositPs = con.prepareStatement(depositMoney);
                PreparedStatement withdrawPs = con.prepareStatement(withdrawMoney);

        ) {
            depositPs.setInt(1, 10000);
            depositPs.setInt(2, 103);
            withdrawPs.setInt(1, 1000000000);
            withdrawPs.setInt(2, 102);

            try {
                con.setAutoCommit(false);
                depositPs.executeUpdate();
                withdrawPs.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();

            }

            ps.setInt(1, 95000);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");
                System.out.println(id + " " + name + " " + salary);
            }
            // System.out.println(rs.getRow());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}