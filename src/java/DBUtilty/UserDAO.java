package DBUtilty;

import Beans.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

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

    public boolean validateUser(String email, String password) throws SQLException {

        email = email.toLowerCase();

        String[] columns = {EMAIL};

        String condition = EMAIL + " = '" + email + "' AND " + password + " = '" + password + "'";

        ResultSet resultSet = databaseUtilities.select(USER, columns, condition);

        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    public void addUser(User user) throws SQLException {

        String[] columns = {EMAIL, NAME, PASSWORD, IMAGENAME, SCORE};
        String email = user.getEmail().toLowerCase();
        String password = user.getPassword();
        String image = user.getImageName();
        String name = user.getName();
        String score = user.getScore() + "";

        String[] values = {email, name, password, image, score};

        databaseUtilities.insert(USER, columns, values);
    }

    public void updateUser(User user) throws SQLException {

        String[] columns = {EMAIL, NAME, PASSWORD, IMAGENAME, SCORE};

        String email = user.getEmail().toLowerCase();
        String password = user.getPassword();
        String image = user.getImageName();
        String name = user.getName();
        String score = user.getScore() + "";

        String[] values = {email, name, password, image, score};

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

        //String condition ="";
        ResultSet resultSet = databaseUtilities.select(USER, columns);

        User user = null;
        ArrayList<User> allUsers = new ArrayList<User>();
        while (resultSet.next()) {
            user = new User();
            user.setEmail(resultSet.getString(1));
            user.setName(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setImageName(resultSet.getString(4));
            user.setScore(resultSet.getInt(5));
            allUsers.add(user);
        }

        return allUsers;
    }

    /////////////
    public static void main(String[] args) {

        try {
            User x = new User();
            User y = new User();
            UserDAO dao = new UserDAO();
            x.setEmail("amal mail");
            x.setName("amal");
            x.setImageName("vffsb");
            x.setPassword("1245");
            x.setScore(8);

            y.setEmail("aya");
            y.setName("aya");
            y.setImageName("ay");
            y.setPassword("1245");
            y.setScore(8);

            // dao.addUser(x);
            //dao.addUser(y);
            //System.out.println("added");
         

            ArrayList<User> res = dao.getAllUsers();
            for (int i = 0; i < res.size(); i++) {
                System.out.println("resulllllllt  " + res.get(i).getEmail() + "  " + res.get(i).getName());
            }
            //System.out.println("result is  "+res.getEmail() +"  "+ res.getName()+" "+res.getPassword()+" "+res.getScore() );
        } catch (SQLException ex) {
            System.out.println("eroooooooooooor insert");
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }
}
