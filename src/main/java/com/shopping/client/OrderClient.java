package com.shopping.client;

import com.shopping.stubs.order.Order;
import com.shopping.stubs.order.OrderRequest;
import com.shopping.stubs.order.OrderResponse;
import com.shopping.stubs.order.OrderServiceGrpc;
import io.grpc.Channel;

import java.util.List;

public class OrderClient {

    //get a stub object
    //call service method

    //to establish endpoint connection to server side
    //first open a channel
    //then get stub
    //then call the method

    private OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub;

    public OrderClient(Channel channel){

        orderServiceBlockingStub=OrderServiceGrpc.newBlockingStub(channel);

    }

    public List<Order> getOrders(int userId){
        OrderRequest orderRequest=OrderRequest.newBuilder().setUserId(userId).build();
        OrderResponse ordersForUser = orderServiceBlockingStub.getOrdersForUser(orderRequest);
        return ordersForUser.getOrderList();

    }


}
