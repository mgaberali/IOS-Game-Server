package DBUtilty;

import Beans.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    
    private static final String LIMIT = "10";

    private static final String USER = "user";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String IMAGENAME = "imageName";
    private static final String SCORE = "score";

    private static final UserDAO USER_DAO = new UserDAO();

    public static UserDAO getInstance() {
        return USER_DAO;
    }

    private final DatabaseUtilities databaseUtilities = DatabaseUtilities.getInstance();

    private UserDAO() {
    }

    public User validateUser(String email, String password) throws SQLException {

        email = email.toLowerCase();

        String[] columns = {EMAIL , NAME,PASSWORD ,IMAGENAME ,SCORE };

        String condition = EMAIL + " = '" + email + "' AND " + PASSWORD + " = '" + password + "'";

        ResultSet resultSet = databaseUtilities.select(USER, columns, condition);
       User user=null;
        if (resultSet.next()) {
            user = new User();
            user.setEmail(email);
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setImageName(resultSet.getString(4));
            user.setScore(resultSet.getInt(5));
           
        }
        return user;
    }

    public void addUser(User user) throws SQLException {

        String[] columns = {EMAIL, NAME, PASSWORD, IMAGENAME, SCORE};
        String email = user.getEmail();
        String password = user.getPassword();
        String image = user.getImageName();
        String name = user.getName();
        String score = user.getScore() + "";

        System.out.println("aaaaaaaaa" + email);

        String[] values = {email, name, password, image, score};
        databaseUtilities.insert(USER, columns, values);

    }

    public void updateUser(User user) throws SQLException {

        String[] columns = { NAME , IMAGENAME, SCORE};

        String email = user.getEmail().toLowerCase();
        String password = user.getPassword();
        String image = user.getImageName();
        String name = user.getName();
        String score = user.getScore() + "";

        String[] values = { name, image, score};

        String condition = EMAIL + " = '" + email + "'";

        databaseUtilities.update(USER, columns, values, condition);
    }
    
        public void updateUserScore(User user) throws SQLException {

        String[] columns = {SCORE};

        String email = user.getEmail().toLowerCase();
        String score = user.getScore() + "";

        String[] values = { score};

        String condition = EMAIL + " = '" + email + "'";

        databaseUtilities.update(USER, columns, values, condition);
    }

    public User getUserByEmail(String email, String password) throws SQLException {

        email = email.toLowerCase();

        String[] columns = {EMAIL, NAME, PASSWORD, IMAGENAME, SCORE};

        String condition = EMAIL + " = '" + email + "' and password = '" + password + "'";

        ResultSet resultSet = databaseUtilities.select(USER, columns, condition);

        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setEmail(email);
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setImageName(resultSet.getString(4));
            user.setScore(resultSet.getInt(5));
        }

        return user;
    }

//    public void deleteUser(String email) throws SQLException {
//        email = email.toLowerCase();
//        String condition = EMAIL + " = '" + email + "'";
//        databaseUtilities.delete(USER, condition);
//    }
    ///////get All Users
    public ArrayList<User> getAllUsers() throws SQLException {

        String[] columns = {EMAIL, NAME, PASSWORD, IMAGENAME, SCORE};

        String[] orderby = {SCORE};
        
        //String condition ="";
        ResultSet resultSet = databaseUtilities.select(USER, columns, null, orderby, false, LIMIT);

        User user = null;
        ArrayList<User> allUsers = new ArrayList<User>();
        while (resultSet.next()) {
            user = new User();
            user.setEmail(resultSet.getString(1));
            user.setName(resultSet.getString(2));
           // user.setPassword(resultSet.getString(3));
            user.setImageName(resultSet.getString(4));
            user.setScore(resultSet.getInt(5));
            allUsers.add(user);
        }

        return allUsers;
    }

}
