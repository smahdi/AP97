package MainPackage;

import java.sql.*;

public class DataBase {
    Connection connection;
    String url      = "jdbc:mysql://localhost/phpmyadmin/server_databases.php";   //database specific url.
    String user     = "root";
    String password = "";


    DataBase() throws ClassNotFoundException, SQLException {

        initialize();
    }

    public void initialize() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        runSampleCode();
    }

    public void runSampleCode() throws SQLException {
        connection = getConnection();

        connection.close();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }

    public PreparedStatement getUpadteStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Update humans set  email = ? where name = ? and last_name = ?");
    }

    public PreparedStatement getSelectStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Select ID ,course_id, grade, unit from ((humans join courses_human on ID=human_ID) join course_id on courseID=course_id), where name = ? and last_name = ?",ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public PreparedStatement getInsertGradeStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Insert into course_human (human_id,course_id,grade) VALUES(?,?,?)");
    }

    public PreparedStatement getInsertCourseStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Insert into courses (courseID,course_name,units) VALUES (?,?,?)");
    }

    public PreparedStatement getInsertHumanStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Insert into humans (ID,name,last_name,email) VALUES(?,?,?,?)");
    }

    public PreparedStatement getDeleteStatement() throws SQLException {
        connection = getConnection();
        return connection.prepareStatement("Delete from course_human where human_id=? and course_id=?");
    }
}

