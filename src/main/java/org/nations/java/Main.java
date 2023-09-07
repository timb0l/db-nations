package org.nations.java;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "admin";
    private static final String SQL_NATIONS = "SELECT countries.country_id as id, countries.name as Country, regions.name as Region, continents.name as Continent" +
            "FROM countries" +
            "JOIN regions on regions.region_id = countries.region_id" +
            "JOIN continents on continents.continent_id = regions.continent_id" +
            "ORDER BY country; ";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("insert nation: ");
        String search = sc.nextLine();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(SQL_NATIONS)) {
                ps.setString(1, "%" + search + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    boolean found = false;

                    while (rs.next()) {
                        int id = rs.getInt("country_id");
                        String name = rs.getString("country_name");
                        String regionName = rs.getString("region_name");
                        String continent = rs.getString("continent_name");

                        if (!found) {
                            System.out.println("Found Nation: ");
                            found = true;
                        }
                        System.out.println("Nation: " + name + "nation id :" + id + " region name : " + regionName + " continent name : " + continent);
                        if (found) {
                            System.out.println("No nation found");
                        }
                    }
                }
            }
            sc.close();
        } catch (SQLException e) {
            System.out.println("connection error");
        }
    }
}
