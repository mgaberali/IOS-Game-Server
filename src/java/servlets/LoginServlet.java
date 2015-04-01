package servlets;

import Beans.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import DBUtilty.*;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/LoginServlet"}
)
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = UserDAO.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();

        String userName = request.getParameter("name");
        String password = request.getParameter("password");
        //String image = request.getParameter("imageName");
        //String score = request.getParameter("score");
        //String email = request.getParameter("email");

        try {

            if (userDAO.validateUser(userName, password)) {
                HttpSession session = request.getSession(true);
                User user = userDAO.getUserByEmail(userName,"");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect("Home.jsp");
    }

}
