package com.shopping.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDao {

    private Logger logger= Logger.getLogger(OrderDao.class.getName());



    public List<Order> getOrders(int userId){
        Connection connection=null;
        List<Order> orders=new ArrayList<>();
        try {
            connection= H2DatabaseConnection.getConnectionToDatabase();
        PreparedStatement preparedStatement= null;

            preparedStatement = connection.prepareStatement("select * from orders where user_id=?");
            preparedStatement.setInt(1,userId);

            ResultSet rs=preparedStatement.executeQuery();

            while(rs.next()){
                Order order=new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setNoOfItems(rs.getInt("no_of_items"));
                orders.add(order);
            }
        } catch (SQLException e) {
           logger.log(Level.SEVERE,"could not execute query",e);
        }

        return orders;
    }
}
