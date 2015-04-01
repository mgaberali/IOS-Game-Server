/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Beans.User;
import DBUtilty.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AYA
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    static final UserDAO user = UserDAO.getInstance();
    User userObj = new User();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("###############################################################");

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String image = request.getParameter("imageName");
        String score = request.getParameter("score");

        userObj.setName(name);
        userObj.setPassword(password);
        userObj.setEmail(email);
        userObj.setImageName(image);
        userObj.setScore(Integer.parseInt(score));


        try {
            user.addUser(userObj);
            PrintWriter out = response.getWriter();
            out.println("ddd");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
