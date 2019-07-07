package Client;


import MainPackage.BombermanFrame;
import MainPackage.GameData;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    BombermanFrame bombermanFrame = null;
    Socket clientSocket = null;
    BufferedOutputStream outputStream = null;
    BufferedInputStream inputStream = null;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
    boolean isStarted;
    //GameData gameData;
    Client client;
    boolean isVisible = false;

    ClientThread(Socket clientSocket, Client client) {
        this.clientSocket = clientSocket;
        this.client = client;
    }

    public void run() {
        try {
            outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            inputStream = new BufferedInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.write(5);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(inputStream);
            client.number = objectInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        while (isInterrupted() == false && clientSocket.isConnected()) {
            try {


                objectOutputStream.write(client.movenumber);
                objectOutputStream.writeObject(client.sendigText);
                objectOutputStream.flush();
                client.movenumber = -1;
                client.sendigText = "/";

                try {

                    isStarted = objectInputStream.readBoolean();
                    client.gameData = (GameData) objectInputStream.readObject();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (isStarted && client.gameData.width != -2) {

                    client.load();
                    if (!isVisible) {
                        client.setVisible(true);
                        isVisible = true;
                    }
                    client.frame.dispose();
                    //tem.out.println(8888888);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
