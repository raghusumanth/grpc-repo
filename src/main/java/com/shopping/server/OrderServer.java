package com.shopping.server;

import com.shopping.service.OrderServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderServer {


    private static final Logger logger=Logger.getLogger(OrderServer.class.getName());

    private Server server;
    public void startServer(){

        int port=50052;
        try {
            server= ServerBuilder.forPort(port)
                    .addService(new OrderServiceImpl())
                    .build()
                    .start();

            logger.info("server started on port: "+port);
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run(){
                    logger.info("clean server shutdown in can JVM was shutdown");
                    try{
                        OrderServer.this.stopServer();

                    }catch (InterruptedException exception){
                        logger.log(Level.SEVERE,"server shutdown interrupted",exception);

                    }
                }
            });
        } catch (IOException e) {

            logger.log(Level.SEVERE,"server didnot start",e);
        }
    }

    public void stopServer() throws InterruptedException {

        if(server!=null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if(server!=null){
            server.awaitTermination();
        }
    }

    public static void main(String... args) throws InterruptedException {
        OrderServer orderServer=new OrderServer();
        orderServer.startServer();
        orderServer.blockUntilShutdown();

        //java -jar pathtojar
        //changes in pom.xml
        //add a plugin to create a fat jar
        //so that jars will be added
        //add main method class in pom to indicate UserServer
        //add resources folder to jar for db scripts to execute





    }
}
