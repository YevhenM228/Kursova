package com.example.mykursova.db;

import com.example.mykursova.taxes.Income;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tax";
    private static final String USER = "root";
    private static final String PASSWORD = "***********";
    private static final String TABLE_INCOME_INFO = "income";
    private static final String TABLE_TAXES_INFO = "taxes";
    private static final String COLUMN_INCOME_ID = "id";
    private static final String COLUMN_INCOME_NAME = "Name";
    private static final String COLUMN_INCOME_SIZE = "Size";
    private static final String COLUMN_TAXES_ID = "id_tax";
    private static final String COLUMN_TAXES_SIZE = "Tax";
    private static final String COLUMN_TAXES_PERCENTAGE = "Percentage";
    private static final String COLUMN_TAXES_INCOME_ID = "id_income";

    public static List<Income> getTaxesFromDatabase() {
        List<Income> taxes = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + TABLE_INCOME_INFO + " INNER JOIN " + TABLE_TAXES_INFO +
                     " ON " + TABLE_TAXES_INFO + "." + COLUMN_TAXES_INCOME_ID + " = " + TABLE_INCOME_INFO + "." + COLUMN_INCOME_ID)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Income income = new Income();
                income.setName(rs.getString(COLUMN_INCOME_NAME));
                income.setSize(rs.getDouble(COLUMN_INCOME_SIZE));
                income.setPercentage(rs.getDouble(COLUMN_TAXES_PERCENTAGE));
                income.setTax(rs.getDouble(COLUMN_TAXES_SIZE));
                taxes.add(income);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taxes;
    }

    public static void writeTaxesToDatabase(List<Income> taxes) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            // Clear tables
            stmt.executeUpdate("TRUNCATE TABLE " + TABLE_TAXES_INFO);
            stmt.executeUpdate("TRUNCATE TABLE " + TABLE_INCOME_INFO);

            // Insert new data
            PreparedStatement incomeStmt = conn.prepareStatement(
                    "INSERT INTO " + TABLE_INCOME_INFO + " (" + COLUMN_INCOME_ID + ", " + COLUMN_INCOME_NAME + ", " + COLUMN_INCOME_SIZE + ")" +
                            " VALUES (?, ?, ?)");
            PreparedStatement taxesStmt = conn.prepareStatement(
                    "INSERT INTO " + TABLE_TAXES_INFO + " (" + COLUMN_TAXES_ID + ", " + COLUMN_TAXES_SIZE + ", " + COLUMN_TAXES_PERCENTAGE + ", " + COLUMN_TAXES_INCOME_ID + ")" +
                            " VALUES (?, ?, ?, ?)");

            int id = 1;
            for (Income income : taxes) {
                incomeStmt.setInt(1, id);
                incomeStmt.setString(2, income.getName());
                incomeStmt.setDouble(3, income.getSize());
                incomeStmt.executeUpdate();

                taxesStmt.setInt(1, id);
                taxesStmt.setDouble(2, income.getTax());
                taxesStmt.setDouble(3, income.getPercentage());
                taxesStmt.setInt(4, id);
                taxesStmt.executeUpdate();

                id++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
