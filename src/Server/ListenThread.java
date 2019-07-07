package Server;
import MainPackage.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ListenThread extends Thread {

    ArrayList<ServerThread> threads;
    ServerSocket serverSocket=null;
    int serverPort;
    //BombermanFrame bombermanFrame=null;
    Server server;

    ListenThread(int serverPort,Server server) {
        threads=new ArrayList<>();
        this.serverPort=serverPort;
        this.server=server;
    }

    public void run() {

        try {
            serverSocket=new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(isInterrupted()==false && serverSocket.isClosed()==false) {
            System.out.println(isInterrupted()+" "+serverSocket.isClosed());
            try {
                Socket clientSocket=serverSocket.accept();
                ServerThread thread=new ServerThread(clientSocket,server);
                threads.add(thread);

                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("PPPP");

        for(ServerThread thread:threads) {
            thread.interrupt();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
