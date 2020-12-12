package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8080);
            Thread.sleep(1000);
            Thread server = new Thread(new ClientThread(socket));
            server.start();
        }catch (IOException | InterruptedException e){
            System.err.println("Neuspeshna konekcija");
            e.printStackTrace();
        }
    }
}

 class ClientThread implements Runnable{

    private Socket socket;
    private String name;
    private BufferedReader serverIn;
    private BufferedReader userIn;
    private PrintWriter out;

    public ClientThread(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    public ClientThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(),true);
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userIn = new BufferedReader(new InputStreamReader(System.in));

            while (!socket.isClosed()){
                if(serverIn.ready()){
                    String input = serverIn.readLine();
                    if(input != null){
                        System.out.println(input);
                    }
                }
                if(userIn.ready()){
                    out.println(name+" > "+userIn.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
