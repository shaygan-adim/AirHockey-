import org.jbox2d.dynamics.World;

import java.awt.*;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel  {

    private int player1Goals,player2Goals = 0;

    private final GameLoop gameLoop;
    private static final int WIDTH = 814;
    private static final int HEIGHT = 500;
    private CircleBody ball,mallet1,mallet2;
    private World world;
    private boolean ballDeclared = false;
    private boolean mallet1Declared = false;
    private boolean mallet2Declared = false;
    private boolean worldDeclared = false;
    private final Image tableImage = Loader.getTable();
    private final Image mallet1Image;
    private final Image mallet2Image;
    private final Image goalTable1Image = Loader.getGoalTable1();
    private final Image goalTable2Image = Loader.getGoalTable2();

    private final Image winTable1Image = Loader.getWinTable1();
    private final Image winTable2Image = Loader.getWinTable2();
    private final GameAnimation gameAnimation;

    private boolean isGoalTime = false;
    private int isWinTime = 0;
    private int lastGoal;

    public AnimationPanel(Image mallet1Image, Image mallet2Image, GameAnimation gameAnimation) {
        this.gameAnimation = gameAnimation;
        gameLoop = new GameLoop(this);
        this.mallet1Image = mallet1Image;
        this.mallet2Image = mallet2Image;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
    }

    void setBall(CircleBody circleBody){
        if (!ballDeclared){
            this.ball = circleBody;
            this.ballDeclared = true;
        }

    }
    void setMallet1(CircleBody circleBody){
        if (!mallet1Declared){
            this.mallet1 = circleBody;
            this.mallet1Declared = true;
        }
    }
    void setMallet2(CircleBody circleBody){
        if (!mallet2Declared){
            this.mallet2 = circleBody;
            this.mallet2Declared = true;
        }
    }
    void setWorld(World world){
        if (!worldDeclared){
            this.world = world;
            this.worldDeclared = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameAnimation.setFocusable(true);
        g.setColor(Color.RED);
        if (!isGoalTime && isWinTime==0)
            g.drawImage(tableImage,0,0,this);
        else if(isWinTime==0){
            if (lastGoal==1)
                g.drawImage(goalTable1Image,0,0,this);
            else
                g.drawImage(goalTable2Image,0,0,this);
        }
        else{
            if (isWinTime==1)
                g.drawImage(winTable1Image,0,0,this);
            else
                g.drawImage(winTable2Image,0,0,this);
        }
        if (isWinTime==0){
            g.drawImage(mallet1Image,(int)this.mallet1.getX()-23,(int)this.mallet1.getY()-23,this);
            g.drawImage(mallet2Image,(int)this.mallet2.getX()-23,(int)this.mallet2.getY()-23,this);
            if (this.ballDeclared){
                g.fillOval((int)this.ball.getX(), (int)this.ball.getY(), (int)this.ball.getRadius(), (int)this.ball.getRadius());
            }
        }
        if(gameAnimation.isTimeLimited()) {
            long t = gameAnimation.refreshTime();
            if (t<=0 && isWinTime==0){
                if (player1Goals>player2Goals){
                    this.gameAnimation.refreshScores(true);
                    isWinTime = 1;
                    this.repaint();
                    stopTheAnimation();
                }
                else if(player2Goals>player1Goals){
                    this.gameAnimation.refreshScores(true);
                    isWinTime = 2;
                    this.repaint();
                    stopTheAnimation();
                }
                else{
                    this.gameAnimation.refreshScores(true);
                    stopTheAnimation();
                }
            }
        }

    }
    void startTheAnimation() {
        gameLoop.start();
    }

    void stopTheAnimation() {
        gameLoop.stop();
    }

    World getWorld() {
        return world;
    }

    public CircleBody getBall() {
        return this.ball;
    }
    void goalForPlayer1(){
        lastGoal = 1;
        player1Goals++;
        if (player1Goals==gameAnimation.getGoalLimit()){
            isWinTime = 1;
            this.gameAnimation.refreshScores(false);
            this.repaint();
            stopTheAnimation();
        }
        else{
            if (player1Goals==player2Goals && player1Goals==gameAnimation.getGoalLimit()-1 && gameAnimation.getTwoMargin()){
                gameAnimation.addGoalLimit();
            }
            this.gameAnimation.refreshScores(false);
            isGoalTime = true;
            this.repaint();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){}
            gameAnimation.sleepConfigure();
            isGoalTime = false;
            this.ball.setX(200);
            this.ball.setY(224);
            this.ball.setVx(0);
            this.ball.setVy(0);
            this.mallet1.setX(700);
            this.mallet1.setY(224);
            this.mallet1.setVx(0);
            this.mallet1.setVy(0);
            this.mallet2.setX(47);
            this.mallet2.setY(224);
            this.mallet2.setVx(0);
            this.mallet2.setVy(0);
        }
    }
    void goalForPlayer2(){
        lastGoal = 2;
        player2Goals++;
        if (player2Goals==gameAnimation.getGoalLimit()){
            isWinTime = 2;
            this.gameAnimation.refreshScores(false);
            this.repaint();
            stopTheAnimation();
        }
        else{
            if (player1Goals==player2Goals && player1Goals==gameAnimation.getGoalLimit()-1 && gameAnimation.getTwoMargin()){
                gameAnimation.addGoalLimit();
            }
            this.gameAnimation.refreshScores(false);
            isGoalTime = true;
            this.repaint();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){}
            gameAnimation.sleepConfigure();
            isGoalTime = false;
            this.ball.setX(555);
            this.ball.setY(224);
            this.ball.setVx(0);
            this.ball.setVy(0);
            this.mallet1.setX(700);
            this.mallet1.setY(224);
            this.mallet1.setVx(0);
            this.mallet1.setVy(0);
            this.mallet2.setX(47);
            this.mallet2.setY(224);
            this.mallet2.setVx(0);
            this.mallet2.setVy(0);
        }
    }

    public int getPlayer1Goals() {
        return player1Goals;
    }

    public int getPlayer2Goals() {
        return player2Goals;
    }
}
