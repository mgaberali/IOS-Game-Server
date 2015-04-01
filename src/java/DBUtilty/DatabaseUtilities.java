package DBUtilty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

class DatabaseUtilities {
    // MySQL Database Connections
    private static final String DB_NAME = "matchgame";
    private static final String CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // Database Utilities Instance
    private static final DatabaseUtilities databaseUtilities = new DatabaseUtilities();

    // Get the Database Utilities Instance
    public static DatabaseUtilities getInstance() {
        return databaseUtilities;
    }

    // Database Connection
    private Connection connection;

    // Constructors
    private DatabaseUtilities() {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Select Records
    public ResultSet select(String table) throws SQLException {
        return select(table, null, null);
    }

    public ResultSet select(String table, String[] columns) throws SQLException {
        return select(table, columns, null);
    }

    public ResultSet select(String table, String condition) throws SQLException {
        return select(table, null, condition);
    }

    public synchronized ResultSet select(String table, String[] columns, String condition) throws SQLException {
        String query = "SELECT ";
        if (columns != null && columns.length != 0) {
            for (int i = 0; i < columns.length; i++) {
                query += columns[i];
                if (i != columns.length - 1) {
                    query += ", ";
                }
            }
        } else {
            query += "*";
        }
        query += " FROM " + table;
        if (condition != null) {
            query += " WHERE " + condition;
        }

        ResultSet resultSet = connection.createStatement().executeQuery(query);

        System.out.println(query);
        printResultSet(resultSet);

        resultSet.beforeFirst();

        return resultSet;
    }

    // Insert Record
    public void insert(String table, String[] values) throws SQLException {
        insert(table, null, values);
    }

    public void insert(String table, String[][] columns_values) throws SQLException {
        insert(table, columns_values[0], columns_values[1]);
    }

    public synchronized void insert(String table, String[] columns, String[] values) throws SQLException {
        String query = "INSERT INTO " + table;
        if (columns != null && columns.length != 0) {
            query += " (";
            for (int i = 0; i < columns.length; i++) {
                query += columns[i];
                if (i != columns.length - 1) {
                    query += ", ";
                }
            }
            query += ")";
        }
        query += " VALUES (";
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                query += "null";
            } else {
                query += "'" + values[i] + "'";
            }
            if (i != values.length - 1) {
                query += ", ";
            }
        }
        query += ")";

        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(query);
        statement.close();

        System.out.println(query);
        System.out.println(rows + " row(s) inserted");
    }

    // Update Record
    public void update(String table, String[][] columns_values) throws SQLException {
        update(table, columns_values, null);
    }

    public void update(String table, String[][] columns_values, String condition) throws SQLException {
        update(table, columns_values[0], columns_values[1], condition);
    }

    public void update(String table, String[] columns, String[] values) throws SQLException {
        update(table, columns, values, null);
    }

    public synchronized void update(String table, String[] columns, String[] values, String condition) throws SQLException {
        String query = "UPDATE " + table + " SET ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + "=";
            if (values[i] == null) {
                query += "null";
            } else {
                query += "'" + values[i] + "'";
            }
            if (i != values.length - 1) {
                query += ", ";
            }
        }
        if (condition != null) {
            query += " WHERE " + condition;
        }

        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(query);
        statement.close();

        System.out.println(query);
        System.out.println(rows + " row(s) updated");
    }

    // Delete Record
    public void delete(String table) throws SQLException {
        delete(table, null);
    }

    public synchronized void delete(String table, String condition) throws SQLException {
        String query = "DELETE FROM " + table;
        if (condition != null) {
            query += " WHERE " + condition;
        }
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(query);
        statement.close();

        System.out.println(query);
        System.out.println(rows + " row(s) deleted");
    }

    // Print ResultSet
    public synchronized void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnsNumber = metaData.getColumnCount();

        String[] headers = new String[columnsNumber];
        Vector<String[]> table = new Vector<String[]>();
        int[] maxLenghts = new int[columnsNumber];

        // Set Headers
        for (int i = 0; i < columnsNumber; i++) {
            String value = metaData.getColumnName(i + 1);
            headers[i] = value;
            int length = value.length();
            if (length > maxLenghts[i]) {
                maxLenghts[i] = length;
            }
        }

        // Set Table
        while (resultSet.next()) {
            String[] columns = new String[columnsNumber];
            for (int i = 0; i < columnsNumber; i++) {
                String value = resultSet.getString(i + 1);
                columns[i] = value;
                if (value != null) {
                    int length = value.length();
                    if (length > maxLenghts[i]) {
                        maxLenghts[i] = length;
                    }
                }
            }
            table.add(columns);
        }

        // Print Top Border
        printBorder(maxLenghts);

        // Print Headers
        printRow(headers, maxLenghts);

        // Print Middle Border
        printBorder(maxLenghts);

        // Print Table
        for (String[] columns : table) {
            printRow(columns, maxLenghts);
        }

        // Print Bottom Border
        printBorder(maxLenghts);
    }

    // Print Row
    private void printRow(String[] row, int[] maxLenghts) {
        for (int i = 0; i < row.length; i++) {
            System.out.print("|");
            int length = 0;
            String value = row[i];
            if (value != null) {
                System.out.print(value);
                length = value.length();
            }
            for (int j = 0; j < maxLenghts[i] - length; j++) {
                System.out.print(" ");
            }
        }
        System.out.println("|");
    }

    // Print Border
    private void printBorder(int[] maxLenghts) {
        for (int i = 0; i < maxLenghts.length; i++) {
            System.out.print("+");
            for (int j = 0; j < maxLenghts[i]; j++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    // Commit
    public synchronized void commit() {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("COMMIT");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Roll Back
    public synchronized void rollBack() {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("ROLLBACK");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
}
