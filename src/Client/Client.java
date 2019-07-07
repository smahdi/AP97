package Client;

import MainPackage.GameData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame{
    GameData gameData;
    Point loc = new Point();
    private int rockpercent = 30;
    private int height;
    private int width;
    int distance = 2;
    int moveInBlock = 5;
    int moves = 5;
    private int blocksize = moves * moveInBlock * distance;
    private JPanel mainPanel;

    JFrame frame;
    //JFrame showframe = new JFrame();
    //Graphics G;
    BufferedImage rockimg = null;
    BufferedImage wallimg = null;
    BufferedImage cherriimg = null;
    BufferedImage fireimg = null;
    BufferedImage doorimg = null;
    BufferedImage[] images = new BufferedImage[100];
    BufferedImage bombimg = null;
    Panel panel;
    boolean isFirstTimeLoad = true;
    boolean isVisible=false;
    boolean cccc = false;

    JTextField serverAddressField;
    JButton connectButton;
    String serverIP = "127.0.0.1";
    int serverPort = 9090;
    ClientThread transportThread;
    Client client = this;
    int number = -1;
    int movenumber = -1;

    JLabel timeLabel;
    JLabel bombCapacityLabel;
    JLabel bombRangeLabel;
    JLabel ghostModeLabel;
    JLabel speedLable;
    JLabel bombControlLable;
    JLabel pointLable;

    public JFrame chatFrame = new JFrame();
    public JPanel chatPanel;
    public JPanel chatWritingPanel = new JPanel();
    public JLabel chatLable = new JLabel("<html></html>");
    public JButton sendButton = new JButton("Send");
    public JTextField chatTextField = new JFormattedTextField();
    public String sendigText = "/";
    int imagenumber = 45;


    public Client() {
        initiate();
        Action();
    }


    void addWall(int i, int j) {

        panel.walls.add(new Sshape(wallimg, (int) i * blocksize, j * blocksize, blocksize, blocksize));

    }

    void addRock(Point uprleft) {
        panel.sshapes.add(new Sshape(rockimg, (int) uprleft.getX(), (int) uprleft.getY(), blocksize, blocksize));
    }

    void addplayer(GameData.Pplayer pplayer) {
        if (pplayer.imageCode == -1) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(pplayer.imgAdress));
            } catch (IOException e) {
                e.printStackTrace();
            }
            panel.sshapes.add(new Sshape(image, (int) pplayer.position.getX(), (int) pplayer.position.getY() - blocksize / 10, blocksize, blocksize * 11 / 10));
        } else {
            panel.sshapes.add(new Sshape(images[pplayer.imageCode], (int) pplayer.position.getX(), (int) pplayer.position.getY() - blocksize / 10, blocksize, blocksize * 11 / 10));

        }
    }

    void addenemi(GameData.Enemi enemi) {
        if (enemi.imageCode == -1) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(enemi.imgAddress));
            } catch (IOException e) {
                e.printStackTrace();
            }
            panel.sshapes.add(new Sshape(image, (int) enemi.position.getX(), (int) enemi.position.getY() - blocksize / 10, blocksize, blocksize * 11 / 10));
        } else {
            panel.sshapes.add(new Sshape(images[enemi.imageCode], (int) enemi.position.getX(), (int) enemi.position.getY() - blocksize / 10, blocksize, blocksize * 11 / 10));
        }
    }

    void addbomb(Point uprleft) {
        panel.sshapes.add(new Sshape(bombimg, (int) uprleft.getX(), (int) uprleft.getY(), blocksize, blocksize));
    }


    void addfire(Point uprleft) {

        panel.sshapes.add(new Sshape(fireimg, (int) uprleft.getX(), (int) uprleft.getY(), blocksize, blocksize));
    }

    private void addPowerup(GameData.Poweru poweru) {
        if (poweru.isDoor)
            panel.sshapes.add(new Sshape(doorimg, (int) poweru.position.getX(), (int) poweru.position.getY(), blocksize, blocksize));
        else
            panel.sshapes.add(new Sshape(cherriimg, (int) poweru.position.getX(), (int) poweru.position.getY(), blocksize, blocksize));
    }

    void load() {
        /*System.out.println("+++++++++++++++++++++++");
        width=gameData.width;
        height=gameData.height;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        loc.setLocation(screenSize.width / 2 - blocksize * width / 2, screenSize.height / 2 - blocksize * height / 2);
        showframe.setLocation(loc);
        showframe.setSize(new Dimension(blocksize * (width) + 200, blocksize * (height)));
        showframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showframe.setUndecorated(true);
        mainPanel = (JPanel) showframe.getContentPane();
        mainPanel.setLayout(new BorderLayout());
        panel = new Panel(new Dimension(width, height));
        mainPanel.setBackground(Color.green);
        mainPanel.add(panel, BorderLayout.CENTER);
        panel.setLocation(loc);
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(7, 1));
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.setPreferredSize(new Dimension(200, blocksize * (height)));
        scorePanel.setLocation((int) (loc.getX() + blocksize * (width)), (int) loc.getY());
        mainPanel.add(scorePanel, BorderLayout.EAST);


        for (int i = 0; i < height; i++) {
            addWall(0, i);
            addWall(width - 1, i);
        }
        for (int i = 0; i < width; i++) {
            addWall(i, 0);
            addWall(i, height - 1);
        }
        for (int i = 2; i < width - 1; i += 2) {
            for (int j = 2; j < height - 1; j += 2) {
                addWall(i, j);
            }
        }
        for(Point point:gameData.rocks)
            addRock(point);
        for (Point point:gameData.fires)
            addfire(point);
        for (Point point:gameData.bombs)
            addbomb(point);
        for(GameData.Poweru power:gameData.powerups)
            addPowerup(power);
        for(GameData.Pplayer player:gameData.players)
            addplayer(player);
        for(GameData.Enemi enemi:gameData.enemys)
            addenemi(enemi);*/
        if (isFirstTimeLoad) {


            width = gameData.width;
            height = gameData.height;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            loc.setLocation(screenSize.width / 2 - blocksize * width / 2 + 400, screenSize.height / 2 - blocksize * height / 2);
            client.setLocation(loc);
            client.setSize(new Dimension(blocksize * (width) + 200, blocksize * (height)));
            client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.setUndecorated(true);
            mainPanel = (JPanel) client.getContentPane();
            mainPanel.setLayout(new BorderLayout());
            panel = new Panel(new Dimension(width, height));
            mainPanel.setBackground(Color.green);
            mainPanel.add(panel, BorderLayout.CENTER);
            panel.setLocation(loc);
            JPanel scorePanel = new JPanel();
            scorePanel.setLayout(new GridLayout(7, 1));
            scorePanel.setBackground(Color.LIGHT_GRAY);
            scorePanel.setPreferredSize(new Dimension(200, blocksize * (height)));
            scorePanel.setLocation((int) (loc.getX() + blocksize * (width)), (int) loc.getY());


            chatPanel = (JPanel) chatFrame.getContentPane();
            chatFrame.setLocation(screenSize.width / 2 - blocksize * width / 2 - 400, screenSize.height / 2 - blocksize * height / 2);
            chatFrame.setSize(new Dimension(200, blocksize * (height)));
            chatPanel.setLayout(new BorderLayout());
            chatWritingPanel.setLayout(new BorderLayout());
            chatWritingPanel.setPreferredSize(new Dimension(200, 40));

            chatPanel.setPreferredSize(new Dimension(200, blocksize * (height)));
            chatPanel.add(chatLable, BorderLayout.CENTER);
            //chatTextField.setEditable(true);
            chatWritingPanel.add(chatTextField, BorderLayout.CENTER);
            chatWritingPanel.add(sendButton, BorderLayout.EAST);
            chatLable.setBackground(Color.WHITE);
            chatPanel.add(chatWritingPanel, BorderLayout.SOUTH);
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendigText = chatTextField.getText();
                    chatTextField.setText("");
                }
            });


            if (gameData.players.size() >= number + 1) {
                timeLabel = new JLabel("-");
                bombCapacityLabel = new JLabel("-");
                bombRangeLabel = new JLabel("-");
                ghostModeLabel = new JLabel("-");
                speedLable = new JLabel("-");
                bombControlLable = new JLabel("-");
                pointLable = new JLabel("-");
                mainPanel.add(scorePanel, BorderLayout.EAST);
                timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                bombCapacityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                bombRangeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                ghostModeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                speedLable.setFont(new Font("Arial", Font.PLAIN, 14));
                bombControlLable.setFont(new Font("Arial", Font.PLAIN, 14));
                pointLable.setFont(new Font("Arial", Font.PLAIN, 14));

                scorePanel.add(bombCapacityLabel);
                scorePanel.add(bombRangeLabel);
                scorePanel.add(ghostModeLabel);
                scorePanel.add(speedLable);
                scorePanel.add(bombControlLable);
                scorePanel.add(pointLable);

                scorePanel.add(timeLabel);

            }


            for (int i = 0; i < height; i++) {
                addWall(0, i);
                addWall(width - 1, i);
            }
            for (int i = 0; i < width; i++) {
                addWall(i, 0);
                addWall(i, height - 1);
            }
            for (int i = 2; i < width - 1; i += 2) {
                for (int j = 2; j < height - 1; j += 2) {
                    addWall(i, j);
                }
            }

            isFirstTimeLoad = false;

            //client.setVisible(true);
            //client.chatFrame.setVisible(true);
        } else {
            if (!gameData.newEnemyImages.isEmpty()) {
                for (byte[] data : gameData.newEnemyImages) {
                    try {
                        ByteArrayInputStream bis = new ByteArrayInputStream(data);
                        images[imagenumber] = ImageIO.read(bis);
                        imagenumber++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (gameData.players.size() >= number + 1) {
                GameData.Pplayer player = gameData.players.get(number);
                long a = 300 - gameData.time;
                String str = String.valueOf(a);
                timeLabel.setText("Remaining Time : " + str);
                bombCapacityLabel.setText("BombCapacity: " + player.bombCapacity);
                bombRangeLabel.setText("BombRange: " + player.bombRange);
                ghostModeLabel.setText("GhostMode: " + player.ghostmode);
                speedLable.setText("Speed: " + player.speed);
                bombControlLable.setText("BombCaontrol: " + player.bombcontrol);
                pointLable.setText("Points: " + player.points);
            }


            panel.sshapes.clear();
            for (Point point : gameData.rocks)
                addRock(point);
            for (Point point : gameData.fires)
                addfire(point);
            for (Point point : gameData.bombs)
                addbomb(point);
            for (GameData.Poweru power : gameData.powerups)
                addPowerup(power);
            for (GameData.Pplayer player : gameData.players)
                if (!player.isDead)
                    addplayer(player);
            for (GameData.Enemi enemi : gameData.enemys)
                addenemi(enemi);
            panel.repaint();
            chatLable.setText(gameData.chatString);
        }


    }


    void initiate() {
        {
            /*showframe = new JFrame();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            loc.setLocation(screenSize.width / 2 - blocksize * width / 2, screenSize.height / 2 - blocksize * height / 2);


            showframe.setLocation(loc);
            showframe.setSize(new Dimension(blocksize * (width) + 200, blocksize * (height)));
            showframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            showframe.setUndecorated(true);
            mainPanel = (JPanel) showframe.getContentPane();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(panel);

            mainPanel.setBackground(Color.yellow);



            JPanel scorePanel = new JPanel();
            scorePanel.setBackground(Color.LIGHT_GRAY);
            scorePanel.setPreferredSize(new Dimension(200, blocksize * (height)));
            scorePanel.setLocation((int) (loc.getX() + blocksize * (width)), (int) loc.getY());
            mainPanel.add(scorePanel, BorderLayout.EAST);*/


        }

        try {
            rockimg = ImageIO.read(new File("Images/Rock.png"));
            wallimg = ImageIO.read(new File("Images/Wall.png"));
            fireimg = ImageIO.read(new File("Images/fire.png"));
            cherriimg = ImageIO.read(new File("Images/Cherries.png"));
            doorimg = ImageIO.read(new File("Images/door.png"));
            bombimg = ImageIO.read(new File("Images/Bomb.png"));

            images[1] = ImageIO.read(new File("Images/Enemy1Forward1.png"));
            images[2] = ImageIO.read(new File("Images/Enemy1Forward2.png"));
            images[3] = ImageIO.read(new File("Images/Enemy1Backward1.png"));
            images[4] = ImageIO.read(new File("Images/Enemy1Backward2.png"));
            images[5] = ImageIO.read(new File("Images/Enemy1Left1.png"));
            images[6] = ImageIO.read(new File("Images/Enemy1Left2.png"));
            images[7] = ImageIO.read(new File("Images/Enemy1Right1.png"));
            images[8] = ImageIO.read(new File("Images/Enemy1Right2.png"));
            images[9] = ImageIO.read(new File("Images/Enemi2Forward1.png"));
            images[10] = ImageIO.read(new File("Images/Enemi2Forward2.png"));
            images[11] = ImageIO.read(new File("Images/Enemi2Backward1.png"));
            images[12] = ImageIO.read(new File("Images/Enemi2Backward2.png"));
            images[13] = ImageIO.read(new File("Images/Enemi2Left1.png"));
            images[14] = ImageIO.read(new File("Images/Enemi2Left2.png"));
            images[15] = ImageIO.read(new File("Images/Enemi2Right1.png"));
            images[16] = ImageIO.read(new File("Images/Enemi2Right2.png"));
            images[17] = ImageIO.read(new File("Images/Enemi3Forward1.png"));
            images[18] = ImageIO.read(new File("Images/Enemi3Forward2.png"));
            images[19] = ImageIO.read(new File("Images/Enemi3Backward1.png"));
            images[20] = ImageIO.read(new File("Images/Enemi3Backward2.png"));
            images[21] = ImageIO.read(new File("Images/Enemi3Left1.png"));
            images[22] = ImageIO.read(new File("Images/Enemi3Left2.png"));
            images[23] = ImageIO.read(new File("Images/Enemi3Right1.png"));
            images[24] = ImageIO.read(new File("Images/Enemi3Right2.png"));
            images[25] = ImageIO.read(new File("Images/Enemi4Forward1.png"));
            images[26] = ImageIO.read(new File("Images/Enemi4Forward2.png"));
            images[27] = ImageIO.read(new File("Images/Enemi4Backward1.png"));
            images[28] = ImageIO.read(new File("Images/Enemi4Backward2.png"));
            images[29] = ImageIO.read(new File("Images/Enemi4Left1.png"));
            images[30] = ImageIO.read(new File("Images/Enemi4Left2.png"));
            images[31] = ImageIO.read(new File("Images/Enemi4Right1.png"));
            images[32] = ImageIO.read(new File("Images/Enemi4Right2.png"));
            images[33] = ImageIO.read(new File("Images/BombermanForward1.png"));
            images[34] = ImageIO.read(new File("Images/BombermanForward2.png"));
            images[35] = ImageIO.read(new File("Images/BombermanForward3.png"));
            images[36] = ImageIO.read(new File("Images/BombermanBackward1.png"));
            images[37] = ImageIO.read(new File("Images/BombermanBackward2.png"));
            images[38] = ImageIO.read(new File("Images/BombermanBackward3.png"));
            images[39] = ImageIO.read(new File("Images/BombermanRight1.png"));
            images[40] = ImageIO.read(new File("Images/BombermanRight2.png"));
            images[41] = ImageIO.read(new File("Images/BombermanRight3.png"));
            images[42] = ImageIO.read(new File("Images/BombermanLeft1.png"));
            images[43] = ImageIO.read(new File("Images/BombermanLeft2.png"));
            images[44] = ImageIO.read(new File("Images/BombermanLeft3.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        frame = new JFrame();
        frame.setLocation(new Point(200, 100));
        frame.setSize(new Dimension(600, 600));
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverAddressField = new JFormattedTextField("127.0.0.1:9090");
        serverAddressField.setSize(100, 0);

        connectButton = new JButton("Connect");
        contentPane.add(serverAddressField);
        contentPane.add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transportThread != null && transportThread.isAlive()) {
                    transportThread.interrupt();
                }
                String[] address = serverAddressField.getText().split(":");
                try {
                    serverIP = address[0];
                    serverPort = Integer.parseInt(address[1]);
                    Socket clientSocket = new Socket(serverIP, serverPort);
                    transportThread = new ClientThread(clientSocket, client);
                    transportThread.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);

    }

    void Action() {


        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {


                    case KeyEvent.VK_RIGHT:
                        movenumber = 1;


                        break;

                    case KeyEvent.VK_LEFT:
                        movenumber = 2;


                        break;

                    case KeyEvent.VK_DOWN:
                        movenumber = 3;


                        break;

                    case KeyEvent.VK_UP:
                        movenumber = 4;


                        break;


                    case KeyEvent.VK_B:
                        movenumber = 5;
                        break;
                    case KeyEvent.VK_SPACE:
                        movenumber = 6;

                        break;

                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;


                    case KeyEvent.VK_A:
                        loc.setLocation(loc.getX() - distance * moves, loc.getY());
                        client.setLocation(loc);
                        break;
                    case KeyEvent.VK_D:
                        loc.setLocation(loc.getX() + distance * moves, loc.getY());
                        client.setLocation(loc);
                        break;
                    case KeyEvent.VK_W:
                        loc.setLocation(loc.getX(), loc.getY() - distance * moves);
                        client.setLocation(loc);
                        break;

                    case KeyEvent.VK_S:
                        loc.setLocation(loc.getX(), loc.getY() + distance * moves);
                        client.setLocation(loc);
                        break;
                    case KeyEvent.VK_C:

                        chatFrame.setVisible(true);


                        break;
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {
            }


        });

    }


}


