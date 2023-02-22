package com.shopping.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    private static final Logger logger=Logger.getLogger(UserDao.class.getName());

    public User getDetails(String username){

        User user=new User();
        try{

            Connection connection=H2DatabaseConnection.getConnectionToDatabase();
            PreparedStatement ps=connection
                    .prepareStatement("select * from user where username=?");

            ps.setString(1,username);

            ResultSet resultSet=ps.executeQuery();

            while(resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
            }

        }catch(SQLException exception){
            logger.log(Level.SEVERE,"Could not execute query",exception);
        }
        return user;
    }
}
