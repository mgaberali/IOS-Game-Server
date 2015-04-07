/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.User;
import DBUtilty.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AYA
 */
@WebServlet(name = "TopTen", urlPatterns = {"/TopTen"})
public class TopTen extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            UserDAO userDAO = UserDAO.getInstance();
            ArrayList<User> allUsers = userDAO.getAllUsers();

            Gson gson = new Gson();
//            Type type = new TypeToken<ArrayList<User>>() {
//            }.getType();
            
            String result=gson.toJson(allUsers);
            out.print(result);
            
        } catch (SQLException ex) {
            Logger.getLogger(TopTen.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
