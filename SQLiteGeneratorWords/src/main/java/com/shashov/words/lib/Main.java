package com.shashov.words.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
        Подготовка файла:
            categories.csv
                id;parent_id;title;lvl;position
            words.csv
                id;category_id;position;is_base;title;translate;transcription;example

         CREATE TABLE `categories` (
            `id`	INTEGER NOT NULL UNIQUE,
            `parent_id`	INTEGER NOT NULL,
            `title`	TEXT NOT NULL,
            `lvl`	INTEGER NOT NULL,
            `position`	INTEGER NOT NULL DEFAULT 0,
	        `saved`	INTEGER NOT NULL DEFAULT 0,
            PRIMARY KEY(`id`)
        );

        CREATE TABLE `words` (
            `id`	INTEGER NOT NULL UNIQUE,
            `category_id`	INTEGER NOT NULL,
            `position`	INTEGER NOT NULL,
            `is_base`	INTEGER NOT NULL,
            `saved`	INTEGER NOT NULL DEFAULT 1,
            `title`	TEXT NOT NULL,
            `translate`	TEXT NOT NULL,
            `transcription`	TEXT NOT NULL,
            `example`	TEXT NOT NULL,
            PRIMARY KEY(`id`)
        );
*/

public class Main {
    static Connection connection = null;

    private static String JDBC_CONNECTION_URL = "jdbc:sqlite:D:\\WORKSPACE\\Words\\SQLiteGeneratorWords\\res\\words.db";

    public static void main(String[] args) {


        try {
            CSVLoader loader = new CSVLoader(getCon());
            loader.loadCsv(
                    "D:/WORKSPACE/Words/SQLiteGeneratorWords/res/categories.csv",
                    "categories",
                    (PreparedStatement ps, String[] nextLine) -> {
                        int index = 1;
                        ps.setInt(index++, new Integer(nextLine[0]));
                        ps.setInt(index++, new Integer(nextLine[1]));
                        ps.setString(index++, nextLine[2]);
                        ps.setInt(index++, new Integer(nextLine[3]));
                        ps.setInt(index++, new Integer(nextLine[4]));
                    },
                    true);

            loader.loadCsv(
                    "D:/WORKSPACE/Words/SQLiteGeneratorWords/res/words.csv",
                    "words",
                    (PreparedStatement ps, String[] nextLine) -> {
                        int index = 1;
                        ps.setInt(index++, new Integer(nextLine[0]));
                        ps.setInt(index++, new Integer(nextLine[1]));
                        ps.setInt(index++, new Integer(nextLine[2]));
                        ps.setInt(index++, nextLine[3].equalsIgnoreCase("true") ? 1 : 0);

                        ps.setString(index++, nextLine[4]);
                        ps.setString(index++, nextLine[5]);
                        ps.setString(index++, nextLine[6]);
                        ps.setString(index++, nextLine[7]);
                    },
                    true);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Connection getCon() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC_CONNECTION_URL);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
