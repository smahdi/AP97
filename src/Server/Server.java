package Server;

import MainPackage.BombermanFrame;
import MainPackage.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server {
    BombermanFrame bombermanFrame = null;
    int serverPort = 9090;
    ListenThread listenThread;
    boolean isStarted = false;

    void addPlayer(int i, int j) {
        Player player1 = new Player(bombermanFrame.blocksize, new Point(i * bombermanFrame.blocksize, j * bombermanFrame.blocksize), bombermanFrame.distance, bombermanFrame.moveInBlock, bombermanFrame.moves, bombermanFrame.paintPanel);
        synchronized (bombermanFrame.players){bombermanFrame.players.add(player1);}
        bombermanFrame.block[i][j].add(player1);
        player1.blocksIn.add(bombermanFrame.block[i][j]);
        player1.setBlockXY(i * (bombermanFrame.moveInBlock * bombermanFrame.distance), j * (bombermanFrame.moveInBlock * bombermanFrame.distance));
        bombermanFrame.paintPanel.addAnimatableShape(player1);
    }

    public Server() {
        initialize();
    }

    void initialize() {
        JFrame frame = new JFrame();
        frame.setLocation(new Point(800, 100));
        frame.setSize(new Dimension(600, 600));
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JButton startButton = new JButton("Start");
        contentPane.add(startButton, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel secondPanel = new JPanel();
        JPanel thirdPanel = new JPanel();
        JPanel forthpanel = new JPanel();
        forthpanel.setLayout((new BorderLayout()));
        thirdPanel.setLayout(new GridLayout(1, 6));
        JLabel heighLabel = new JLabel("Height :");
        JLabel widthLabel = new JLabel("Width :");
        JLabel levelLabel = new JLabel("Level :");
        JTextField heightField = new JFormattedTextField("14");
        JTextField widthField = new JFormattedTextField("12");
        JTextField levelField = new JFormattedTextField("5");
        thirdPanel.add(heighLabel);
        thirdPanel.add(heightField);
        thirdPanel.add(widthLabel);
        thirdPanel.add(widthField);
        thirdPanel.add(levelLabel);
        thirdPanel.add(levelField);




        secondPanel.setLayout(new BorderLayout());
        JButton changeServerButton = new JButton("Change Server");
        JTextField serverAddressField = new JFormattedTextField("9090");
        serverAddressField.setSize(100, 0);
        secondPanel.add(serverAddressField, BorderLayout.CENTER);
        secondPanel.add(changeServerButton, BorderLayout.EAST);
        forthpanel.add(thirdPanel,BorderLayout.CENTER);
        forthpanel.add(secondPanel, BorderLayout.SOUTH);
        contentPane.add(forthpanel,BorderLayout.SOUTH);

        changeServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverPort = Integer.parseInt(serverAddressField.getText());
            }
        });


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //synchronized (bombermanFrame) {
                bombermanFrame = new BombermanFrame(Integer.parseInt(heightField.getText())+1, Integer.parseInt(widthField.getText())+1, Integer.parseInt(levelField.getText()));
                bombermanFrame.setLocation(bombermanFrame.getLocation().x-600,bombermanFrame.getLocation().y);
                if (listenThread.threads.size() >= 1) {
                    addPlayer(bombermanFrame.width - 2, bombermanFrame.height - 2);
                }
                if (listenThread.threads.size() >= 2) {
                    addPlayer(bombermanFrame.width - 2, 1);
                }
                if (listenThread.threads.size() >= 3) {
                    addPlayer(1, bombermanFrame.height - 2);
                }

                isStarted = true;
                frame.dispose();
                //}


            }
        });
        if (listenThread != null && listenThread.isAlive()) {
            listenThread.interrupt();
        }
        listenThread = new ListenThread(serverPort, this);
        listenThread.start();
        frame.setVisible(true);


    }
}
