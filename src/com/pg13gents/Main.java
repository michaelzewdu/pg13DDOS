package com.pg13gents;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) throws Exception {
        launch(args);
/*
        ArrayList<Thread> requestThreads = new ArrayList<Thread>();


        for (int req = 0; req < 6000; req++) {
            //Requests requests = new Requests(Integer.toString(req));
            //requests.start();
            //asynchronousRequest();
            AsyncronousRequests asyncronousRequests = new AsyncronousRequests(Integer.toString(req));
            asyncronousRequests.start();
            requestThreads.add(asyncronousRequests);

        }

       // asyncronousRequests.join();
        /*
        for (Thread thr: requestThreads
             ) {
            thr.join();
            System.out.println("Successful count: "+successfulCount+" failed count: "+failedCount);

        }


        boolean iterator=true;
        while(iterator){
            for (Thread thr:requestThreads) {
                if (thr.isAlive()!=true){
                    requestThreads.remove(thr);
                }
                if(requestThreads.isEmpty()){
                    System.out.println("Successful count: "+Model.successfulCount+" failed count: "+Model.failedCount);
                    iterator=false;

                }

            }
        }

         */



        //getSynchronus("http://localhost:8080/pg13");
    }


    @Override
    public void start(Stage stage) throws Exception {
        Button btn1= new Button("Say, Hello World");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("hello from my first button on javafx");
            }
        });


        StackPane root = new StackPane();
        root.getChildren().add(btn1);
        Scene scene= new Scene(root,500,900);
        stage.setScene(scene);
        stage.setTitle("DDOS attacker");
        stage.show();
    }
}

class Requests extends Thread{
    final String ReqThreadNumber;

    public Requests(String reqThreadNumber) {
        ReqThreadNumber = reqThreadNumber;
    }

    public void run(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/pg13"))
                .build();

        for (int i = 1; i <= 100; i++) {

            HttpResponse<String> response =
                    null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Executing thread request number: "+this.ReqThreadNumber+" for the: "+i+"'s time and the response status is: "+response.statusCode());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}

class AsyncronousRequests extends Thread{
    final String ReqThreadNumber;
    int successfulCount=0;
    int failedCount=0;


    AsyncronousRequests(String reqThreadNumber) {
        ReqThreadNumber = reqThreadNumber;
    }

    @Override
    public void run() {
        super.run();
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:8080/pg13")).header("accept","application/json").build();

        try{
            CompletableFuture resp=     client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::statusCode);
            Object message = resp.join();
            Model.successfulCount++;
            System.out.println("Executing thread request number: "+this.ReqThreadNumber+" the response status is: "+message);
        }catch (Exception e){
            Model.failedCount++;
            System.out.println("Exception on thread number: "+this.ReqThreadNumber);
        }
    }

    public int getFailedCount() {
        return failedCount;
    }

    public int getSuccessfulCount() {
        return successfulCount;
    }
}

class Model{
    static int successfulCount=0;
    static int failedCount=0;


}



