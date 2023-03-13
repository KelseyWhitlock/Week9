/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author Kelsey
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userId = new UserService();
        RoleService roleInfo= new RoleService();
        String action = request.getParameter("action");
        try{
            if(action !=null && action.equals("edit")){
                String email = request.getParameter("email");
                User userInfo = userId.get(email);
                request.setAttribute("userInfo", userInfo);
                request.setAttribute("email", userInfo.getEmail());     
            }
            else if(action != null && action.equals("delete")){
                String email = request.getParameter("email");
                userId.delete(email);
                 List<User> users = userId.getAll();
                 if(users.isEmpty()){
                     request.setAttribute("errorMessage","Add a user.");
                 }
                
            }
            List<User> users = userId.getAll();
                request.setAttribute("users", users);
                List<Role> role = roleInfo.getAll();
                request.setAttribute("role",role);
            
        }catch(Exception ex){
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE,null, ex);
            request.setAttribute("errorMessage", "There is a error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }
      
      
       @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            UserService userId = new UserService();
            RoleService roleInfo= new RoleService();
            
            
           try{
               String action = request.getParameter("action");
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String password = request.getParameter("password");
            String rolePlace = request.getParameter("role");
               
               if(action.equals("add")){
                   userId.insert(email, firstName, lastName, password,roleInfo.get(rolePlace));
               }
               if(action.equals("update")){
                   userId.update(email, firstName, lastName,password, roleInfo.get(rolePlace));
               }
               List<User> users = userId.getAll();
                request.setAttribute("users", users);
               
           }catch(Exception ex){
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE,null, ex);
            request.setAttribute("errorMessage", "There is a error");
        }
  
           getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
 
}  
    }

   
