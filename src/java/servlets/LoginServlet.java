package servlets;

import Beans.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DBUtilty.*;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.*;

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
            User u = userDAO.validateUser(userMail, password);
            Gson gson = new Gson();
            HashMap<String, String> resp = new HashMap<>();
            String result = "";
            
            if (u != null) {

                // prepare response data
                resp.put("status", "success");
                resp.put("name", u.getName());
                resp.put("score", "" + u.getScore());
                resp.put("image", u.getImageName());
    
            }else{                
                // prepare response data
                resp.put("status", "fail");
            }
            
            // convert response data to json string
            result = gson.toJson(resp);
            
            // write json string on response
            out.print(result);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
