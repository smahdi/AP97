package MainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Client.Client;
import Server.*;

public class MenuFrame extends JFrame {
    JPanel contentPane;

    MenuFrame() {
        setLocation(new Point(200, 100));
        this.setSize(new Dimension(600, 600));
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new GridLayout(4, 1));
        JPanel multimlayer=new JPanel();
        multimlayer.setLayout(new GridLayout(1, 2));
        JButton startButton = new JButton("Start");
        JButton loadButton = new JButton("Load");
        JButton exitButton = new JButton("Exit");
        JButton server = new JButton("Server");
        JButton client = new JButton("Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BombermanFrame bomberman = new BombermanFrame(0,null);
                dispose();
            }
        });
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server server=new Server();
                dispose();
            }
        });
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client=new Client();
                dispose();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        multimlayer.add(server);
        multimlayer.add(client);
        contentPane.add(startButton);
        contentPane.add(multimlayer);
        contentPane.add(loadButton);
        contentPane.add(exitButton);








        /*this.setSize(new Dimension(600, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        JPanel contanepane = (JPanel) this.getContentPane();
        MainPackage.PaintPanel mainPanel = new MainPackage.PaintPanel(new Dimension(600, 800));
        contanepane.add(mainPanel,BorderLayout.CENTER);
        this.setLocation(500, 300);


        JButton startButton = new JButton("Start");
        startButton.setSize(200,150);
        startButton.setLocation(200,100);
        mainPanel.add(startButton);
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(200,400,200,150);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200,650,200,150);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPackage.BombermanFrame bomberman=new MainPackage.BombermanFrame(11,19,50);
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });*/


        this.setVisible(true);


    }
}
