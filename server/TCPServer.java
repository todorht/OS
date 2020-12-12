package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            acceptClients();
        } catch (IOException e){
            System.err.println("Ne moze da slusa na portata 8080");
            System.exit(1);
        }

    }

    private static void acceptClients(){
        ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
        while (true){
            try{
                ServerSocket serverSocket = new ServerSocket(8080);
                Socket socket = serverSocket.accept();
                server.ClientThread client = new server.ClientThread(socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException e){
                System.out.println("Ne uspeshno povrzuvanje so 8080");
            }
        }
    }
}

public class ClientThread extends TCPServer implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(!socket.isClosed()){
                String input = in.readLine();
                if(input != null){
                    for(ClientThread client : clients){
                        client.getWriter().write(input);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getWriter(){
        return out;
    }

}