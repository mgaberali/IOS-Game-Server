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
import com.google.gson.Gson;
import java.io.PrintWriter;

@WebServlet(
        name = "LoginServlet",
        urlPatterns = {"/LoginServlet"}
)
public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO = UserDAO.getInstance();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String userMail = request.getParameter("mail");
        String password = request.getParameter("password");
        
        try {
            User u=userDAO.validateUser(userMail, password);
            if (u!=null) {
                HttpSession session = request.getSession(true);
                  Gson gson = new Gson();
//            Type type = new TypeToken<ArrayList<User>>() {
//            }.getType();
            
            String result=gson.toJson(u.getName());
            out.print(result);
                //out.println("yes");
                //User user = userDAO.getUserByEmail(userMail, password);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //response.sendRedirect("Home.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("kvjnfvkjfnrk");
    }
}
