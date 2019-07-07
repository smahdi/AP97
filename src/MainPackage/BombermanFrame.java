package MainPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by user on 18/04/11.
 */
public class BombermanFrame extends JFrame {


    Point loc = new Point();
    public int rockpercent = 30;
    public int height = 49 + 2;
    public int width = 49 + 2;
    public int distance = 2;
    public int moveInBlock = 5;
    public int moves = 5;
    public int blocksize = moves * moveInBlock * distance;
    public Block block[][] = new Block[200][200];
    Random random = new Random();
    private JPanel mainPanel;
    public PaintPanel paintPanel;
    public ArrayList<Player> players = new ArrayList<>();
    Player player;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<PowerUp> powerUps = new ArrayList<>();
    ArrayList<Fire> fires = new ArrayList<>();
    ArrayList<Block> fireblocks = new ArrayList<>();
    Thread bombThread;
    int boobdelay = 500;
    ArrayList<Block> boomBlocks = new ArrayList<>();
    ArrayList<Rock> rocks = new ArrayList<>();
    ArrayList<Block> spaceBlocks = new ArrayList<>();
    int level;
    boolean isMultiplayer = false;
    boolean isLoaded = false;
    Date startingtime;
    Thread enemyThread;
    BufferedImage finalImg;
    public GameData gameData = new GameData();
    long timePased;
    BufferedImage[] images=new BufferedImage[50];

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

    transient ArrayList<BufferedImage> newEnemyImages = new ArrayList<>();
    int imagenumber = 45;


    public BombermanFrame(int level, Player player) {
        if(player.ghostmode){
            player.classesNotAllowed.clear();
            player.classesNotAllowed.add("MainPackage.Wall2");


        }
        this.level = level;
        this.rockpercent = 50;
        this.height = 7 + (level / 2) * 2;
        this.width = 11 + (level / 2) * 2;
        paintPanel = new PaintPanel(new Dimension(width, height), this);
        this.player = new Player(blocksize, new Point(blocksize, blocksize), distance, moveInBlock, moves, paintPanel);
        if (player != null) {
            this.player.bombCapacity = player.bombCapacity;
            this.player.bombRange = player.bombRange;
            this.player.placedBombs = player.placedBombs;
            this.player.bombcontrol = player.bombcontrol;
            this.player.ghostmode = player.ghostmode;
            this.player.points = player.points;
        }

        players.add(this.player);
        initiate();
        Show();
        Action();

    }

    public BombermanFrame(int height, int width, int level) {
        // paintPanel = new PaintPanel(new Dimension(width, height), this);
        this.level = level;
        this.rockpercent = 50;
        this.height = height;
        this.width = width;
        isMultiplayer = true;
        paintPanel = new PaintPanel(new Dimension(width, height), this);
        player = new Player(blocksize, new Point(blocksize, blocksize), distance, moveInBlock, moves, paintPanel);
        players.add(player);
        initiate();
        Show();
        Action();

    }

    public BombermanFrame(GameData gameData) {
        this.level = gameData.level;
        this.rockpercent = 50;
        this.height = gameData.height;
        this.width = gameData.width;
        paintPanel = new PaintPanel(new Dimension(width, height), this);
        player = new Player(blocksize, new Point(blocksize, blocksize), distance, moveInBlock, moves, paintPanel);
        players.add(player);
        initiate();
        Show();
        Action();
    }


    void addWall(int i, int j) {
        Wall wall = new Wall(blocksize, new Point(i * blocksize, j * blocksize));
        paintPanel.addShape(wall);
        block[i][j].add(wall);
    }

    void addWall2(int i, int j) {
        Wall2 wall = new Wall2(blocksize, new Point(i * blocksize, j * blocksize));
        paintPanel.addShape(wall);
        block[i][j].add(wall);
    }

    void addRock(int i, int j) {
        Rock rock = new Rock(blocksize, new Point(i * blocksize, j * blocksize), block[i][j], this);
        paintPanel.addAnimatableShape(rock);
        block[i][j].add(rock);
        synchronized (rocks) {
            rocks.add(rock);
        }
    }


    boolean isBombVertical(Bomb bomb) {
        if (bomb.getBlockX() % (2 * moveInBlock * distance) == (moveInBlock * distance)) {
            return true;
        }
        return false;
    }

    boolean isBombHorizontal(Bomb bomb) {
        if (bomb.getBlockY() % (2 * moveInBlock * distance) == (moveInBlock * distance)) {
            return true;
        }
        return false;
    }

    void initiate() {
        startingtime = new Date();
        timeLabel = new JLabel("-");
        bombCapacityLabel = new JLabel("-");
        bombRangeLabel = new JLabel("-");
        ghostModeLabel = new JLabel("-");
        speedLable = new JLabel("-");
        bombControlLable = new JLabel("-");
        pointLable = new JLabel("-");

        bombThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //////////////////////////////////////////////
                    timePased = ((new Date()).getTime() - startingtime.getTime()) / 1000;
                    String str = new String();
                    long a = 300 - timePased;
                    str = str + a;
                    timeLabel.setText("Remaining Time : " + str);
                    bombCapacityLabel.setText("BombCapacity: " + player.bombCapacity);
                    bombRangeLabel.setText("BombRange: " + player.bombRange);
                    ghostModeLabel.setText("GhostMode: " + player.ghostmode);
                    speedLable.setText("Speed: " + player.speed);
                    bombControlLable.setText("BombCaontrol: " + player.bombcontrol);
                    pointLable.setText("Points: " + player.points);

                    //////////////////////////////////////////////


                    Iterator<Bomb> iterator = bombs.iterator();
                    while (iterator.hasNext()) {
                        Bomb currentBomb = iterator.next();
                        Date currentDate = new Date();
                        if (currentBomb.blowUpTime.before(currentDate)) {
                            currentBomb.player.placedBombs--;
                            currentBomb.player.bombs.remove(currentBomb);
                            //System.out.println(currentBomb.getBlockY() / (moveInBlock * distance)  +" " + ceil10(currentBomb.getBlockY()) / (moveInBlock * distance)+" "+isBombVertical(currentBomb));
                            int begin = Math.max(currentBomb.getBlockY() / (moveInBlock * distance) - currentBomb.range, 0);
                            int end = Math.min(currentBomb.range + ceil10(currentBomb.getBlockY()) / (moveInBlock * distance), height - 1);
                            for (int i = begin; i <= end && isBombVertical(currentBomb); i++) {
                                // System.out.println(currentBomb.getBlockX() / (moveInBlock * distance) + "+" + i);
                                block[currentBomb.getBlockX() / (moveInBlock * distance)][i].boomDate.setTime(currentDate.getTime() + boobdelay * (Math.min(Math.abs(i - currentBomb.getBlockY() / (moveInBlock * distance)), Math.abs(i - ceil10(currentBomb.getBlockY()) / (moveInBlock * distance)))));
                                boomBlocks.add(block[currentBomb.getBlockX() / (moveInBlock * distance)][i]);


                            }
                            begin = Math.max(currentBomb.getBlockX() / (moveInBlock * distance) - currentBomb.range, 0);
                            end = Math.min(currentBomb.range + ceil10(currentBomb.getBlockX()) / (moveInBlock * distance), width - 1);
                            for (int i = begin; i <= end && isBombHorizontal(currentBomb); i++) {
                                // System.out.println(i + "+" + currentBomb.getBlockY() / (moveInBlock * distance));
                                block[i][currentBomb.getBlockY() / (moveInBlock * distance)].boomDate.setTime(currentDate.getTime() + boobdelay * (Math.min(Math.abs(i - currentBomb.getBlockX() / (moveInBlock * distance)), Math.abs(i - ceil10(currentBomb.getBlockX()) / (moveInBlock * distance)))));
                                boomBlocks.add(block[i][currentBomb.getBlockY() / (moveInBlock * distance)]);


                            }
                            iterator.remove();
                        }
                    }

                    Iterator<Block> iterator1 = boomBlocks.iterator();
                    while (iterator1.hasNext()) {
                        Block currentBlock = iterator1.next();
                        Date currentDate = new Date();
                        int i = 0;
                        if (currentBlock.boomDate.before(currentDate)) {
                            i++;
                            Fire fire = new Fire(blocksize, new Point(currentBlock.x * blocksize, currentBlock.y * blocksize), finalImg);
                            paintPanel.addAnimatableShape(fire);
                            long date = (new Date().getTime()) + 1000 + i;
                            synchronized (fires) {
                                fires.add(fire);
                            }
                            fire.addAnimation(new Animation(0, 1, date) {
                                @Override
                                public void step() {
                                    fire.img = null;
                                    synchronized (fires) {
                                        fires.remove(fire);
                                    }

                                }
                            });
                            currentBlock.finalboomdate = date;
                            currentBlock.boomPlayer = player;
                            //currentBlock.boom();
                            synchronized (fireblocks) {
                                fireblocks.add(currentBlock);
                            }
                            iterator1.remove();
                        }


                    }
                    try {
                        repaint();

                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bombThread.start();

        Thread fireThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //System.out.println(4444);
                    synchronized (fireblocks) {
                        Iterator<Block> iterator = fireblocks.iterator();
                        while (iterator.hasNext()) {

                            Block block = iterator.next();
                            block.boom();
                            //System.out.println(555554);
                            long currentDate = (new Date()).getTime();
                            if (currentDate >= block.finalboomdate)
                                iterator.remove();

                        }
                    }
                }
            }
        });
        fireThread.start();


        enemyThread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    synchronized (enemies) {
                        Iterator<Enemy> iterator = enemies.iterator();
                        while (iterator.hasNext()) {
                            Enemy currentEnemy = iterator.next();

                            if (currentEnemy.isDead) {
                                iterator.remove();
                                continue;
                            }


                            for (Player player2 : players) {

                                int a = Math.abs((int) (currentEnemy.getBlockX() - currentEnemy.player.getBlockX())) + Math.abs((int) (currentEnemy.getBlockY() - currentEnemy.player.getBlockY()));
                                int b = Math.abs((int) (currentEnemy.getBlockX() - player2.getBlockX())) + Math.abs((int) (currentEnemy.getBlockY() - player2.getBlockY()));
                                if (a >= b || currentEnemy.player.isDead)
                                    currentEnemy.player = player2;

                            }

                            currentEnemy.moveChooser((new Date()).getTime());
                            synchronized (players) {
                                for (Player player1 : players)

                                    if (Math.abs(currentEnemy.getBlockX() - player1.getBlockX()) < moveInBlock * distance / 2 && Math.abs(currentEnemy.getBlockY() - player1.getBlockY()) < moveInBlock * distance / 2) {
                                        player1.isDead = true;
                                        player1.die();

                                    }

                            }

                        }
                    }
                    try {
                        repaint();

                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    void Show() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        loc.setLocation(screenSize.width / 2 - blocksize * width / 2, screenSize.height / 2 - blocksize * height / 2);


        setLocation(loc);
        this.setSize(new Dimension(blocksize * (width) + 200, blocksize * (height)));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        mainPanel = (JPanel) this.getContentPane();
        mainPanel.setLayout(new BorderLayout());


        mainPanel.setBackground(Color.green);
        mainPanel.add(paintPanel, BorderLayout.CENTER);
        paintPanel.setLocation(loc);


        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(7, 1));
        scorePanel.setBackground(Color.LIGHT_GRAY);
        scorePanel.setPreferredSize(new Dimension(200, blocksize * (height)));
        scorePanel.setLocation((int) (loc.getX() + blocksize * (width)), (int) loc.getY());
        mainPanel.add(scorePanel, BorderLayout.EAST);


        //timeLabel.setLocation((int) (loc.getX() + blocksize * (width)), (int) loc.getY());
        //timeLabel.setPreferredSize(new Dimension(200, 200));
        //=new JLabel("BombCapacity = "+String.valueOf(player.bombCapacity));
        {
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
        /////////////////////////////////////////////////////////


        chatPanel = (JPanel) chatFrame.getContentPane();
        chatFrame.setLocation(screenSize.width / 2 - blocksize * width / 2 - 220, screenSize.height / 2 - blocksize * height / 2);
        chatFrame.setSize(new Dimension(200, blocksize * (height)));
        chatPanel.setLayout(new BorderLayout());
        chatWritingPanel.setLayout(new BorderLayout());
        chatWritingPanel.setPreferredSize(new Dimension(200, 40));

        chatPanel.setPreferredSize(new Dimension(200, blocksize * (height)));
        chatPanel.add(chatLable, BorderLayout.CENTER);
        chatWritingPanel.add(chatTextField, BorderLayout.CENTER);
        chatWritingPanel.add(sendButton, BorderLayout.EAST);
        chatLable.setBackground(Color.WHITE);
        chatPanel.add(chatWritingPanel, BorderLayout.SOUTH);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = chatLable.getText();
                s = s.substring(0, s.length() - 7);
                String s2 = chatTextField.getText();
                s = s + "<br> Player 0: " + s2 + "</html>";
                chatLable.setText(s);
                chatTextField.setText("");
            }
        });

        ////////////////////////////////////////////////////////////


        player = players.get(0);

        BufferedImage img3 = null;
        BufferedImage img4 = null;
        BufferedImage img5 = null;

        try {
            BufferedImage img1 = ImageIO.read(new File("Images/Rock.png"));
            BufferedImage img2 = ImageIO.read(new File("Images/Wall.png"));
            img3 = ImageIO.read(new File("Images/fire.png"));
            img4 = ImageIO.read(new File("Images/Cherries.png"));
            img5 = ImageIO.read(new File("Images/door.png"));
            Rock.img = img1;
            Wall.img = img2;
            Wall2.img = img2;
            Rock.pimg = img4;
            Rock.dimg = img5;





        } catch (IOException e) {
            e.printStackTrace();
        }
        finalImg = img3;


        {//placing walls and rocks
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    block[i][j] = new Block(paintPanel, i, j);
                }
            }
            block[0][0].left = block[0][0];
            block[0][0].up = block[0][0];
            for (int i = 1; i < width; i++) {
                block[i][0].left = block[i - 1][0];
                block[i - 1][0].right = block[i][0];
                block[i][0].up = block[i][0];

            }
            for (int i = 1; i < height; i++) {
                block[0][i].up = block[0][i - 1];
                block[0][i - 1].down = block[0][i];
                block[0][i].left = block[0][i];

            }
            for (int i = 1; i < width; i++) {
                for (int j = 1; j < height; j++) {
                    block[i][j].left = block[i - 1][j];
                    block[i - 1][j].right = block[i][j];
                    block[i][j].up = block[i][j - 1];
                    block[i][j - 1].down = block[i][j];
                }
            }


            for (int i = 0; i < height; i++) {
                addWall2(0, i);
                addWall2(width - 1, i);
            }
            for (int i = 0; i < width; i++) {
                addWall2(i, 0);
                addWall2(i, height - 1);
            }
            for (int i = 2; i < width - 1; i += 2) {
                for (int j = 2; j < height - 1; j += 2) {
                    addWall(i, j);
                }
            }
            if (!isLoaded)
                addNewRockEnemy();
            else
                addOldRockEnemy();

            enemyThread.start();
        }
    }

    void addNewRockEnemy() {
        for (int i = 1; i < width - 2; i += 2) {
            for (int j = 1; j < height - 2; j++) {
                int a = random.nextInt(100);
                if (a < rockpercent) {
                    addRock(i, j);
                } else
                    spaceBlocks.add(block[i][j]);
            }
        }
        for (int i = 2; i < width - 2; i += 2) {
            for (int j = 1; j < height - 2; j += 2) {
                int a = random.nextInt(100);
                if (a < rockpercent) {
                    addRock(i, j);
                } else
                    spaceBlocks.add(block[i][j]);
            }
        }


        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                block[i][j].clearNW();
                spaceBlocks.remove(block[i][j]);
                synchronized (rocks) {
                    rocks.remove(block[i][j]);
                }
            }
        }
        if (isMultiplayer) {
            for (int i = width - 5; i < width - 2; i++) {
                for (int j = 1; j < 4; j++) {
                    block[i][j].clearNW();
                    spaceBlocks.remove(block[i][j]);
                    synchronized (rocks) {
                        rocks.remove(block[i][j]);
                    }
                }
            }
            for (int i = 1; i < 4; i++) {
                for (int j = height - 5; j < height - 2; j++) {
                    block[i][j].clearNW();
                    spaceBlocks.remove(block[i][j]);
                    synchronized (rocks) {
                        rocks.remove(block[i][j]);
                    }
                }
            }
            for (int i = width - 5; i < width - 2; i++) {
                for (int j = height - 5; j < height - 2; j++) {
                    block[i][j].clearNW();
                    spaceBlocks.remove(block[i][j]);
                    synchronized (rocks) {
                        rocks.remove(block[i][j]);
                    }
                }
            }
        }


        //player = new Player(blocksize, new Point(blocksize, blocksize), distance, moveInBlock, moves, paintPanel);
        block[1][1].add(player);
        synchronized (player.blocksIn) {
            player.blocksIn.add(block[1][1]);
        }
        player.setBlockXY((moveInBlock * distance), (moveInBlock * distance));
        paintPanel.addAnimatableShape(player);
        for (int i = 0; i < (height - 2); i++) {

            int a = random.nextInt(100) + 1;
            int b = random.nextInt(spaceBlocks.size());
            System.out.println();
            int xx = ((Block) spaceBlocks.get(b)).x;
            int yy = ((Block) spaceBlocks.get(b)).y;
            if (a > level * 10) {
                Enemy1 enemy = new Enemy1(blocksize, new Point(blocksize * xx, blocksize * yy), distance, moveInBlock, moves, player);
                block[xx][yy].add(enemy);
                enemy.blocksIn.add((Block) spaceBlocks.get(b));
                enemy.setBlockXY((moveInBlock * distance) * xx, (moveInBlock * distance) * yy);
                paintPanel.addEnemy(enemy);
                synchronized (enemies) {
                    enemies.add(enemy);
                }
            }
            if (a < level * 10 && a > level * 10 - 40) {
                Enemy2 enemy = new Enemy2(blocksize, new Point(blocksize * xx, blocksize * yy), distance, moveInBlock, moves, player);
                block[xx][yy].add(enemy);
                enemy.blocksIn.add((Block) spaceBlocks.get(b));
                enemy.setBlockXY((moveInBlock * distance) * xx, (moveInBlock * distance) * yy);
                paintPanel.addEnemy(enemy);
                synchronized (enemies) {
                    enemies.add(enemy);
                }
            }
            if (a < level * 10 - 40 && a > level * 10 - 60) {
                Enemy3 enemy = new Enemy3(blocksize, new Point(blocksize * xx, blocksize * yy), distance, moveInBlock, moves, player);
                block[xx][yy].add(enemy);
                enemy.blocksIn.add((Block) spaceBlocks.get(b));
                enemy.setBlockXY((moveInBlock * distance) * xx, (moveInBlock * distance) * yy);
                paintPanel.addEnemy(enemy);
                synchronized (enemies) {
                    enemies.add(enemy);
                }
            }
            if (a < level * 10 - 60) {
                Enemy4 enemy = new Enemy4(blocksize, new Point(blocksize * xx, blocksize * yy), distance, moveInBlock, moves, player);
                block[xx][yy].add(enemy);
                enemy.blocksIn.add((Block) spaceBlocks.get(b));
                enemy.setBlockXY((moveInBlock * distance) * xx, (moveInBlock * distance) * yy);
                paintPanel.addEnemy(enemy);
                synchronized (enemies) {
                    enemies.add(enemy);
                }
            }

        }


        int b = random.nextInt(rocks.size());
        ((Rock) rocks.get(b)).poweUpKind = PoweUpKind.NextLevelPortal;
    }

    void addOldRockEnemy() {
        synchronized (rocks) {
            for (Point point : gameData.rocks) {
                Rock rock = new Rock(blocksize, point, block[(int) point.getX() / blocksize][(int) point.getY() / blocksize], this);
                paintPanel.addAnimatableShape(rock);
                block[(int) point.getX() / blocksize][(int) point.getY() / blocksize].add(rock);
                synchronized (rocks) {
                    rocks.add(rock);
                }
            }
        }






    }


    private int ceil10(int a) {
        int x = moveInBlock * distance;
        if (a % x == 0)
            return a;
        else
            return a + x - a % x;
    }


    void Action() {

        this.setVisible(true);
        //chatFrame.setVisible(true);
        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                    switch (keyCode) {


                        case KeyEvent.VK_RIGHT:
                            if (!player.isDead) {
                                player.moveRight((new Date()).getTime());
                                System.out.println(player.getBlockX() + " " + player.getBlockY());
                            }

                            break;

                        case KeyEvent.VK_LEFT:
                            if (!player.isDead) {
                                player.moveLeft((new Date()).getTime());
                                System.out.println(player.getBlockX() + " " + player.getBlockY());

                            }
                            break;

                        case KeyEvent.VK_DOWN:
                            if (!player.isDead) {
                                player.moveDown((new Date()).getTime());
                                System.out.println(player.getBlockX() + " " + player.getBlockY());
                            }
                            break;

                        case KeyEvent.VK_UP:
                            if (!player.isDead) {
                                player.moveUp((new Date()).getTime());

                                System.out.println(player.getBlockX() + " " + player.getBlockY());
                            }
                            break;


                        case KeyEvent.VK_B:
                            if (!player.isDead) {
                                placebomb(player);
                            }
                            break;
                        case KeyEvent.VK_SPACE:
                            if (!player.isDead) {
                                System.out.println(player.bombcontrol + " " + !player.bombs.isEmpty());
                                if (player.bombcontrol && !player.bombs.isEmpty())
                                    ((Bomb) player.bombs.get(0)).die();
                            }
                            break;

                        case KeyEvent.VK_ESCAPE:
                            System.exit(0);
                            break;


                        case KeyEvent.VK_A:
                            loc.setLocation(loc.getX() - player.speed * distance * moves, loc.getY());
                            setLocation(loc);
                            break;
                        case KeyEvent.VK_D:
                            loc.setLocation(loc.getX() + player.speed * distance * moves, loc.getY());
                            setLocation(loc);
                            break;
                        case KeyEvent.VK_W:
                            loc.setLocation(loc.getX(), loc.getY() - player.speed * distance * moves);
                            setLocation(loc);
                            break;

                        case KeyEvent.VK_S:
                            loc.setLocation(loc.getX(), loc.getY() + player.speed * distance * moves);
                            setLocation(loc);
                            break;
                        case KeyEvent.VK_O:
                            addNewEnemyKind();
                            break;
                        case KeyEvent.VK_C:
                            chatFrame.setVisible(true);
                            break;


                    }
                if ((keyCode == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                    try {
                        save();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                }
                if ((keyCode == KeyEvent.VK_X) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {


                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {
            }


        });
        this.setVisible(true);
    }

    private void addNewEnemyKind() {
        JFrame frame = new JFrame();
        frame.setLocation(new Point(100, 100));
        frame.setSize(new Dimension(600, 50));
        frame.setUndecorated(true);
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        String s = (new File(".")).getAbsolutePath();
        s = s.replace('\\', '/');
        s = s.substring(0, s.length() - 1);
        JTextField pathTextfield = new JFormattedTextField(s);
        JTextField numberOfNewEnemiesField = new JFormattedTextField(1);
        numberOfNewEnemiesField.setPreferredSize(new Dimension(100, 50));
        JButton changeButton = new JButton("Add Enemy Class");
        contentPane.add(pathTextfield, BorderLayout.CENTER);
        contentPane.add(changeButton, BorderLayout.EAST);
        contentPane.add(numberOfNewEnemiesField, BorderLayout.WEST);
        frame.setVisible(true);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:///" + pathTextfield.getText())});
                    Class a = loader.loadClass("newEnemy.NewEnemy");
                    Enemy enemy = null;

                    try {
                        try {
                            Constructor constructor = a.getConstructor(int.class, Point.class, int.class, int.class, int.class, Player.class);


                            for (int i = 0; i < Integer.parseInt(numberOfNewEnemiesField.getText()); i++) {

                                int b = random.nextInt(spaceBlocks.size());
                                int xx = ((Block) spaceBlocks.get(b)).x;
                                int yy = ((Block) spaceBlocks.get(b)).y;

                                enemy = (Enemy) constructor.newInstance(blocksize, new Point(blocksize * xx, blocksize * yy), distance, moveInBlock, moves, player);
                                block[xx][yy].add(enemy);
                                enemy.blocksIn.add((Block) spaceBlocks.get(b));
                                enemy.setBlockXY((moveInBlock * distance) * xx, (moveInBlock * distance) * yy);
                                paintPanel.addEnemy(enemy);
                                synchronized (enemies) {
                                    enemies.add(enemy);
                                }
                            }
                            enemy.enemyForward1.imagecode = imagenumber;
                            enemy.enemyForward2.imagecode = imagenumber + 1;
                            enemy.enemyBackward1.imagecode = imagenumber + 2;
                            enemy.enemyBackward2.imagecode = imagenumber + 3;
                            enemy.enemyLeft1.imagecode = imagenumber + 4;
                            enemy.enemyLeft2.imagecode = imagenumber + 5;
                            enemy.enemyRight1.imagecode = imagenumber + 6;
                            enemy.enemyRight2.imagecode = imagenumber + 7;
                            imagenumber += 8;

                            newEnemyImages.add(enemy.enemyForward1.img);
                            newEnemyImages.add(enemy.enemyForward2.img);
                            newEnemyImages.add(enemy.enemyBackward1.img);
                            newEnemyImages.add(enemy.enemyBackward2.img);
                            newEnemyImages.add(enemy.enemyLeft1.img);
                            newEnemyImages.add(enemy.enemyLeft2.img);
                            newEnemyImages.add(enemy.enemyRight1.img);
                            newEnemyImages.add(enemy.enemyRight2.img);


                        } catch (InvocationTargetException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchMethodException e1) {
                            e1.printStackTrace();
                        }

                    } catch (InstantiationException e1) {
                        e1.printStackTrace();

                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }

                } catch (MalformedURLException e3) {
                    e3.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                frame.setVisible(false);
            }
        });


    }

    void save() throws Exception {
        GameData gameData = new GameData(this, 0);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to Save...");
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream ous = new ObjectOutputStream(fos);
        ous.writeObject(gameData);
        ous.flush();
        ous.close();
        fos.close();
       /* FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
                writer.println(level);
                writer.print((player.getBlockX()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                writer.print((player.getBlockY()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                writer.print(player.points+" ");
                writer.print(player.bombCapacity+" ");
                writer.print(player.bombRange+" ");
                writer.print(player.placedBombs+" ");
                writer.print(player.bombcontrol+" ");
                writer.println();

                writer.println(enemies.size());
                for(int i=0;i<enemies.size();i++){
                    MainPackage.Enemy enemy=enemies.get(i);
                    writer.print((enemy.getBlockX()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                    writer.print((enemy.getBlockY()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                    switch (enemy.getClass().getName()){
                        case "MainPackage.Enemy1":
                            writer.println(1);
                            break;
                        case "MainPackage.Enemy2":
                            writer.println(2);
                            break;
                        case "MainPackage.Enemy3":
                            writer.println(3);
                            break;
                        case "MainPackage.Enemy4":
                            writer.println(4);
                            break;


                    }
                }
                writer.println(rocks.size());
                for(int i=0;i<rocks.size();i++){
                    MainPackage.Rock rock=rocks.get(i);
                    writer.print((rock.getBlockX()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                    writer.print((rock.getBlockY()/(distance*moveInBlock))*(distance*moveInBlock)+" ");
                    writer.println(rock.randompowerup);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

*/
    }

    void load() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to Save...");
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();

        try {
            FileInputStream fis = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameData gameData = (GameData) ois.readObject();
            BombermanFrame bombermanFrame = new BombermanFrame(gameData);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void dataSave(ObjectOutputStream printer) {
        gameData = new GameData(this);
        synchronized (this) {
            try {
                //printer.reset();
                printer.writeObject(gameData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void placebomb(Player player) {
        if (player.placedBombs < player.bombCapacity) {
            Bomb bomb = player.bombSet();
            paintPanel.addAnimatableShape(bomb);
            bombs.add(bomb);
            block[bomb.getBlockX() / (moveInBlock * distance)][player.getBlockY() / (moveInBlock * distance)].add(bomb);
            if (ceil10((bomb.getBlockX())) / (moveInBlock * distance) != bomb.getBlockX() / (moveInBlock * distance))
                block[bomb.getBlockX() / (moveInBlock * distance) + 1][player.getBlockY() / (moveInBlock * distance)].add(bomb);
            if (ceil10((bomb.getBlockY())) / (moveInBlock * distance) != bomb.getBlockY() / (moveInBlock * distance))
                block[bomb.getBlockX() / (moveInBlock * distance)][player.getBlockY() / (moveInBlock * distance) + 1].add(bomb);
        }

    }

}