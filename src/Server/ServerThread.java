package Server;

import MainPackage.Bomb;
import MainPackage.BombermanFrame;
import MainPackage.GameData;
import MainPackage.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerThread extends Thread {

    //BombermanFrame bombermanFrame = null;
    Socket clientSocket = null;
    BufferedOutputStream outputStream = null;
    BufferedInputStream inputStream = null;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
    Server server;
    int number=-1;
    int movenumber=-1;
    Player player;
    String sendingText="/";

    ServerThread(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;

    }

    public void run() {
        number=server.listenThread.threads.size();
        try {
            outputStream = new BufferedOutputStream(this.clientSocket.getOutputStream());
            inputStream = new BufferedInputStream(this.clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.write(server.listenThread.threads.size());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(inputStream);
            objectInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }




        while (isInterrupted() == false && clientSocket.isConnected()) {
            if (server.isStarted) {

                synchronized (server.bombermanFrame) {
                    try {
                        objectOutputStream.writeBoolean(server.isStarted);
                        server.bombermanFrame.dataSave(objectOutputStream);
                        objectOutputStream.flush();

                        movenumber=objectInputStream.read();
                        sendingText =(String) objectInputStream.readObject();
                        moveplayer(movenumber);
                        sendText(sendingText);
                        movenumber=-1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                    try {
                        objectInputStream.read();

                        objectOutputStream.writeBoolean(server.isStarted);
                        objectOutputStream.writeObject((new GameData(true)));
                        objectOutputStream.flush();
                        sendingText =(String) objectInputStream.readObject();
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendText(String sendingText) {
        System.out.println(5555);
        if(!sendingText.equals("/") && !sendingText.equals("")){
            String s=server.bombermanFrame.chatLable.getText();
            s=s.substring(0,s.length()-7);
            s=s+"<br> Player "+number+": "+sendingText+"</html>";
            server.bombermanFrame.chatLable.setText(s);

        }
    }

    private void moveplayer(int movenumber) {
        player=server.bombermanFrame.players.get(number);
        switch (movenumber){
            case 1:
                player.moveRight((new Date()).getTime());
                break;

            case 2:
                player.moveLeft((new Date()).getTime());
                break;

            case 3:
                player.moveDown((new Date()).getTime());
                break;

            case 4:
                player.moveUp((new Date()).getTime());
                break;


            case 5:
                server.bombermanFrame.placebomb(player);
                break;
            case 6:
                if (player.bombcontrol && !player.bombs.isEmpty())
                    ((Bomb) player.bombs.get(0)).die();

                break;
        }
    }
}
