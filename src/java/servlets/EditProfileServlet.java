/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.User;
import DBUtilty.UserDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amal
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = {"/EditProfileServlet"})
public class EditProfileServlet extends HttpServlet {
  static final UserDAO user = UserDAO.getInstance();
    User userObj;
    PrintWriter out;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        out = response.getWriter();
         Gson gson = new Gson();
        HashMap<String, String> resp = new HashMap<>();
        String result = "";
        
        // get request parameters
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String image = request.getParameter("imageName");
        //String score = request.getParameter("score");

        // create user with data
        userObj = new User();
        userObj.setName(name);
        userObj.setPassword(password);
        userObj.setEmail(email);
        userObj.setImageName(image);
        //userObj.setScore(Integer.parseInt(score));

        try {
            
            user.updateUser(userObj);
             
            // prepare response data
            resp.put("status", "success");
           
        } catch (SQLException ex) {
           
             // prepare response data
            resp.put("status", "fail");
            
        } finally {

            // convert response data to json string
            result = gson.toJson(resp);

            // write json string on response
            out.print(result);

        }

    }

}
