package dbConnection;

import sample.User;

import java.sql.*;

public class DBConnection extends ConfigsDB {
    private Connection dbConnection;
    private String driver ="com.mysql.jdbc.Driver";
    private String connectionString;

    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        connectionString = "jdbc:mysql://" + DBHOST + ":"
                + PORT + "/" + DBNAME + "?" +
                "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName(driver);
        dbConnection = DriverManager.getConnection(connectionString, DBUSER, DBPASSWORD);
        return dbConnection;
    }

    public ResultSet getUsers(User user) {
        ResultSet resultSet = null;
        String select = "SELECT * FROM "+ConstantVariables.USER_TABLE+
                " WHERE "+ConstantVariables.USER_USERNAME+"=? AND "+ConstantVariables.USER_PASSWORD+"=?";
        try {
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2,user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public int counterOfUsers() throws SQLException {
        int counter = 0;
        ResultSet resultSet = null;
        String select = "SELECT * FROM "+ConstantVariables.USER_TABLE;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDBConnection().prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (resultSet.next())
            counter++;

        return counter;
    }
    public void signUpNewUser(User user) {
        String insert = "INSERT INTO " + ConstantVariables.USER_TABLE + " (" +ConstantVariables.USER_ID + "," +
                ConstantVariables.USER_FIRSTNAME + "," + ConstantVariables.USER_LASTNAME + "," +
                ConstantVariables.USER_USERNAME + "," + ConstantVariables.USER_PASSWORD + "," +
                ConstantVariables.USER_LOCATION + "," + ConstantVariables.USER_GENDER + ")" + " VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(insert); // передаем запрос
            prSt.setInt(1,counterOfUsers()+1);
            prSt.setString(2, user.getFirstName());
            prSt.setString(3, user.getSecondName());
            prSt.setString(4, user.getLogin());
            prSt.setString(5, user.getPassword());
            prSt.setString(6, user.getCountry());
            prSt.setString(7, user.getGender());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
