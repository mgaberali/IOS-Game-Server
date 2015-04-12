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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
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
        StringBuilder jsonData = new StringBuilder();

        // get json data
        BufferedReader reader = request.getReader();

        String line = null;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }

        // create json parser
        JsonParser parser = new JsonParser();

        // convert json string to json objects
        JsonObject json = (JsonObject) parser.parse(jsonData.toString());

        // get request parameters
        String password = json.get("password").getAsString();
        String email = json.get("email").getAsString();

        try {
            User u = userDAO.validateUser(email, password);
            Gson gson = new Gson();
            HashMap<String, String> resp = new HashMap<>();
            String result = "";

            if (u != null) {

                // prepare response data
                resp.put("status", "success");
                resp.put("name", u.getName());
                resp.put("score", "" + u.getScore());
                resp.put("image", u.getImageName());

            } else {
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
