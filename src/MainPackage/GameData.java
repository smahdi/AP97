package MainPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {

    public int width = 4, height,level;
    public long time;
    public ArrayList<Point> rocks = new ArrayList<>();
    public ArrayList<Point> bombs = new ArrayList<>();
    public ArrayList<Point> fires = new ArrayList<>();

    public ArrayList<Poweru> powerups = new ArrayList<>();
    public ArrayList<Pplayer> players = new ArrayList<>();
    public ArrayList<Enemi> enemys = new ArrayList<>();
    public String chatString;
    public ArrayList<byte []> newEnemyImages=new ArrayList<>();


    public GameData() {
        width = -1;
    }

    public GameData(boolean a) {
        width = -2;
    }

    GameData(BombermanFrame bombermanFrame) {
        time=bombermanFrame.timePased;
        width = bombermanFrame.width;
        height = bombermanFrame.height;
        chatString=bombermanFrame.chatLable.getText();
        level=bombermanFrame.level;

        if(!bombermanFrame.newEnemyImages.isEmpty()){
            for(BufferedImage image:bombermanFrame.newEnemyImages){

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", bos );
                    byte [] data = bos.toByteArray();
                    newEnemyImages.add(data);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            bombermanFrame.newEnemyImages.clear();
        }

        synchronized (bombermanFrame.rocks) {
            for (Rock rock : bombermanFrame.rocks) {
                Point a = rock.getUpprleft();
                rocks.add(a);
            }
        }
        synchronized (bombermanFrame.bombs) {
            for (Bomb bomb : bombermanFrame.bombs) {
                Point a = bomb.getUpprleft();
                bombs.add(a);
            }
        }
        synchronized (bombermanFrame.fires) {
            for (Fire fire : bombermanFrame.fires) {
                Point a = fire.getUpprleft();
                fires.add(a);
            }
        }
        synchronized (bombermanFrame.powerUps) {
            for (PowerUp powerUp : bombermanFrame.powerUps) {
                Poweru a = new Poweru();
                a.position = powerUp.getUpprleft();
                a.isDoor = false;
                if (powerUp.poweUpKind == PoweUpKind.NextLevelPortal)
                    a.isDoor = true;
                powerups.add(a);
            }
        }
        synchronized (bombermanFrame.players) {
            for (Player player : bombermanFrame.players) {

                    Pplayer a = new Pplayer();
                    a.position = player.getUpprleft();
                     a.bombCapacity = player.bombCapacity;
                     a.bombRange = player.bombRange;
                     a.placedBombs=player.placedBombs;
                     a.bombcontrol=player.bombcontrol;
                     a.ghostmode=player.ghostmode;
                     a.speed=player.speed;
                    a.points = player.points;
                    a.imageCode =player.stance.imagecode;
                    a.imgAdress = player.stance.address;
                    a.isDead=player.isDead;
                    players.add(a);

            }
        }
        synchronized (bombermanFrame.enemies) {
            for (Enemy enemy : bombermanFrame.enemies) {
                if (!enemy.isDead) {
                    Enemi a = new Enemi();
                    a.position = enemy.getUpprleft();
                    a.imgAddress = enemy.stance.address;
                    a.imageCode=enemy.stance.imagecode;
                    enemys.add(a);
                }
            }
        }
    }


    GameData(BombermanFrame bombermanFrame,int llll) {
        time=bombermanFrame.timePased;
        width = bombermanFrame.width;
        height = bombermanFrame.height;
        chatString=bombermanFrame.chatLable.getText();
        synchronized (bombermanFrame.rocks) {
            for (Rock rock : bombermanFrame.rocks) {
                Point a = rock.getUpprleft();
                rocks.add(a);
            }
        }
        synchronized (bombermanFrame.powerUps) {
            for (PowerUp powerUp : bombermanFrame.powerUps) {
                Poweru a = new Poweru();
                a.position = powerUp.getUpprleft();
                a.isDoor = false;
                if (powerUp.poweUpKind == PoweUpKind.NextLevelPortal)
                    a.isDoor = true;
                powerups.add(a);
            }
        }
        synchronized (bombermanFrame.players) {
            for (Player player : bombermanFrame.players) {

                Pplayer a = new Pplayer();
                a.position = player.getUpprleft();
                a.bombCapacity = player.bombCapacity;
                a.bombRange = player.bombRange;
                a.placedBombs=player.placedBombs;
                a.bombcontrol=player.bombcontrol;
                a.ghostmode=player.ghostmode;
                a.speed=player.speed;
                a.points = player.points;
                a.imageCode =player.stance.imagecode;
                a.imgAdress = player.stance.address;
                a.isDead=player.isDead;
                players.add(a);

            }
        }
        synchronized (bombermanFrame.enemies) {
            for (Enemy enemy : bombermanFrame.enemies) {
                if (!enemy.isDead) {
                    Enemi a = new Enemi();
                    a.position.x = enemy.getUpprleft().x-enemy.getUpprleft().x%bombermanFrame.blocksize;
                    a.position.y = enemy.getUpprleft().y-enemy.getUpprleft().y%bombermanFrame.blocksize;
                    a.imgAddress = enemy.stance.address;
                    a.imageCode=enemy.stance.imagecode;
                    a.kind=enemy.kind;
                    enemys.add(a);
                }
            }
        }
    }







    public class Poweru implements Serializable {
        public Point position = new Point();
        public boolean isDoor;

    }

    public class Enemi implements Serializable {
        public Point position = new Point();
        public String imgAddress;
        public int imageCode;
        public int kind;

    }

    public class Pplayer implements Serializable {
        public Point position = new Point();
        public int bombCapacity = 1;
        public int bombRange = 1;
        public int placedBombs=0;
        public boolean bombcontrol=false;
        public boolean ghostmode=false;
        public int points=0;
        public String imgAdress;
        public int imageCode;
        public double speed=1.0;
        public boolean isDead=false;
    }

    /*class ImageCanvas implements Serializable {
        transient BufferedImage image;

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            ImageIO.write(image, "png", out);

        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            image=ImageIO.read(in);

        }
    }*/


}
