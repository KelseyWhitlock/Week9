/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataacces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Role;

/**
 *
 * @author Kelsey
 */
public class RoleDB {
     public List<Role> getAll() throws Exception {
         List<Role> roles = new ArrayList<>();
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql= "SELECT * FROM role";
        
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String roleName = rs.getString(2);
                Role role= new Role(id,roleName);
                roles.add(role);
            }    
        }finally{
         DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con);
     }
        return roles;
     }
     
     public Role get(int id) throws Exception{
         Role role = null;
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM  user WHERE role_id=?";
        
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
           rs = ps.executeQuery();
           
           while(rs.next()){
               String roleName = rs.getString(2);
               role = new Role(id, roleName);
           }
        }finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(con); 
        }
        return role;
     }
      
}
