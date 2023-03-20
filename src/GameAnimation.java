import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameAnimation extends JFrame implements KeyListener {
    private final boolean timeLimited,twoMargin;
    private final String color1,color2;
    private int goalLimit;
    private long startTime = System.currentTimeMillis();
    private final long timeLimit;
    private long lastSecond;
    private JLabel player1Label,player2Label,finalGoalLabel1,finalGoalLabel2,winnerLabel,drawLabel,timeLabel;
    private boolean stopTiming = false;
    private JButton rpButton;
    private boolean isGameDone = false;
    private boolean isPaused = false;
    private boolean mirrorWallsEnabled = false;
    private boolean[] bigGoalEnabled = {false,false};
    private boolean[] fireBallEnabled = {false,false};
    private boolean bigGoalAppearance,fireBallAppearance,mirrorWallsAppearance;
    private long lastVanishedPowerTime = 0;
    private long lastAppearanceTime = 0;
    private int powerX,powerY;
    private int lastKick = 0;
    private JPanel mainPanel;
    private CircleBody mallet1;
    private CircleBody mallet2;
    private Image mallet1Image,mallet2Image;
    private final String player1Name;
    private final String player2Name;
    private AnimationPanel animationPanel;
    private CircleBody ball;
    private final boolean[] keyPressed = {false,false,false,false,false,false,false,false};
    GameAnimation(String color1, String color2, String player1Name ,String player2Name, boolean timeLimited, int goalLimit,boolean twoMargin,double timeLimit){
        this.timeLimit = (long) timeLimit;
        this.timeLimited = timeLimited;
        this.goalLimit = goalLimit;
        this.twoMargin = twoMargin;
        this.color1 = color1;
        this.color2 = color2;
        if (player1Name.length()>6){
           player1Name = player1Name.substring(0,7);
        }
        if (player2Name.length()>6){
            player2Name = player2Name.substring(0,7);
        }
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        initFrame();
        initPhysics(color1,color2);
        initComponents();
        initAnimation();
        this.setVisible(true);
    }

    void initFrame(){

        this.mainPanel = (JPanel)this.getContentPane();
        this.setIconImage(Loader.getIcon().getImage());

        this.setTitle("Air Hockey +");
        this.setSize(814,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainPanel.setLayout(null);
        this.setFocusable(true);
    }
    void initPhysics(String color1, String color2){
        final short CATEGORY_1 = 0x0001;
        final short CATEGORY_2 = 0x0002;
        final short CATEGORY_3 = 0x0004;
        final short CATEGORY_4 = 0x0008;
        final short CATEGORY_1_AND_2 = CATEGORY_1 | CATEGORY_2;
        final short CATEGORY_2_AND_3 = CATEGORY_3 | CATEGORY_2;
        if (color1.equals("red")){
            this.mallet1Image = Loader.getRedMallet();
        }
        if (color1.equals("green")){
            this.mallet1Image = Loader.getGreenMallet();
        }
        if (color1.equals("blue")){
            this.mallet1Image = Loader.getBlueMallet();
        }
        if (color1.equals("purple")){
            this.mallet1Image = Loader.getPurpleMallet();
        }
        if (color2.equals("red")){
            this.mallet2Image = Loader.getRedMallet();
        }
        if (color2.equals("green")){
            this.mallet2Image = Loader.getGreenMallet();
        }
        if (color2.equals("blue")){
            this.mallet2Image = Loader.getBlueMallet();
        }
        if (color2.equals("purple")){
            this.mallet2Image = Loader.getPurpleMallet();
        }
        this.animationPanel = new AnimationPanel(this.mallet1Image,this.mallet2Image,this);
        this.mainPanel.add(this.animationPanel);
        this.animationPanel.setBounds(0,75,814,500);
        this.animationPanel.setOpaque(false);

        World world = new World(new Vec2(0,0));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        Body wall1 = world.createBody(bodyDef);
        Body wall2 = world.createBody(bodyDef);
        Body wall3 = world.createBody(bodyDef);
        Body wall4 = world.createBody(bodyDef);
        Body wall5 = world.createBody(bodyDef);
        Body wall6 = world.createBody(bodyDef);

        PolygonShape shape1 = new PolygonShape();
        shape1.setAsBox(0, 1000);
        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(2000, 0);
        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape1;
        fixtureDef1.density = 0;
        fixtureDef1.friction = 0f;
        fixtureDef1.restitution = 1.0f;
        fixtureDef1.filter.categoryBits = CATEGORY_1_AND_2;
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = shape2;
        fixtureDef2.density = 0;
        fixtureDef2.friction = 0f;
        fixtureDef2.restitution = 1.0f;
        fixtureDef2.filter.categoryBits = CATEGORY_1_AND_2;
        wall1.createFixture(fixtureDef1);
        wall2.createFixture(fixtureDef1);
        wall3.createFixture(fixtureDef2);
        wall4.createFixture(fixtureDef2);
        FixtureDef fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = shape1;
        fixtureDef3.density = 0;
        fixtureDef3.friction = 1f;
        fixtureDef3.restitution = 0.0f;
        fixtureDef3.filter.categoryBits = CATEGORY_2;
        fixtureDef3.filter.maskBits = CATEGORY_2 | CATEGORY_3 | CATEGORY_4;
        wall5.createFixture(fixtureDef3);
        wall6.createFixture(fixtureDef3);
        wall1.setTransform(new Vec2(-52,-20),0);
        wall2.setTransform(new Vec2(805,-20),0);
        wall3.setTransform(new Vec2(-70,-52),0);
        wall4.setTransform(new Vec2(-70,500),0);
        wall5.setTransform(new Vec2(805/2-47,-20),0);
        wall6.setTransform(new Vec2(805/2-5,-20),0);

        CircleBody ball = new CircleBody(20, 50, 377, 224, world, CATEGORY_1, (short) (CATEGORY_3 | CATEGORY_4 | CATEGORY_2));
        this.ball = ball;
        Random random = new Random();
        int num1 = random.nextInt(201) + 300;
        int num2 = random.nextInt(201) - 500;
        int randomNumber = random.nextBoolean() ? num1 : num2;
        ball.setVx(randomNumber);
        num1 = random.nextInt(201) + 300;
        num2 = random.nextInt(201) - 500;
        randomNumber = random.nextBoolean() ? num1 : num2;
        ball.setVy(randomNumber);
        this.mallet1 = new CircleBody(300,23,700,224,world,CATEGORY_3);
        this.mallet2 = new CircleBody(300,23,47,224,world,CATEGORY_4);
        this.animationPanel.setBall(ball);
        this.animationPanel.setMallet1(this.mallet1);
        this.animationPanel.setMallet2(this.mallet2);
        this.animationPanel.setWorld(world);
        addKeyListener(this);
    }

    void initComponents(){
        JButton homeButton = new JButton("Home");
        homeButton.setFocusPainted(false);
        homeButton.setFocusable(false);
        this.mainPanel.add(homeButton);
        homeButton.setBounds(315,600,80,40);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGameDone){
                    stopTiming = true;
                    animationPanel.stopTheAnimation();
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "", JOptionPane.YES_NO_OPTION);
                    stopTiming = false;
                    animationPanel.startTheAnimation();
                    if(dialogResult == JOptionPane.YES_OPTION){
                        HistoryLoader.addGame(GameAnimation.this);
                        new MainMenu();
                        GameAnimation.super.dispose();
                    }
                }
                else{
                    HistoryLoader.addGame(GameAnimation.this);
                    new MainMenu();
                    GameAnimation.super.dispose();
                }
            }
        });


        rpButton = new JButton("Pause");
        rpButton.setFocusPainted(false);
        rpButton.setFocusable(false);
        this.mainPanel.add(rpButton);
        rpButton.setBounds(405,600,80,40);

        rpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGameDone){
                    if (!isPaused){
                        isPaused=true;
                        stopTiming = true;
                        animationPanel.stopTheAnimation();
                        rpButton.setText("Resume");
                    }
                    else {
                        animationPanel.startTheAnimation();
                        refreshTime();
                        rpButton.setText("Pause");
                        stopTiming = false;
                        isPaused=false;
                    }
                }
                else {
                    HistoryLoader.addGame(GameAnimation.this);
                    new GameAnimation(color1,color2,player1Name,player2Name,timeLimited,goalLimit,twoMargin,timeLimit);
                    GameAnimation.super.dispose();
                }
            }
        });

        player1Label = new JLabel(player1Name+" : "+0);
        player2Label = new JLabel(player2Name+" : "+0);
        this.mainPanel.add(player1Label);
        this.mainPanel.add(player2Label);
        player1Label.setFont(new Font("Arial",Font.PLAIN,30));
        player1Label.setBounds(600,-10,1000,100);
        player2Label.setFont(new Font("Arial",Font.PLAIN,30));
        player2Label.setBounds(50,-10,1000,100);

        finalGoalLabel1 = new JLabel("Final Goal");
        finalGoalLabel1.setFont(new Font("Arial",Font.PLAIN,15));
        finalGoalLabel1.setForeground(Color.red);
        finalGoalLabel1.setVisible(false);
        finalGoalLabel1.setBounds(650,15,1000,100);
        this.mainPanel.add(finalGoalLabel1);
        finalGoalLabel2 = new JLabel("Final Goal");
        finalGoalLabel2.setFont(new Font("Arial",Font.PLAIN,15));
        finalGoalLabel2.setForeground(Color.red);
        finalGoalLabel2.setVisible(false);
        finalGoalLabel2.setBounds(50,15,1000,100);
        this.mainPanel.add(finalGoalLabel2);

        winnerLabel = new JLabel("Winner !");
        winnerLabel.setFont(new Font("Arial",Font.PLAIN,15));
        this.mainPanel.add(winnerLabel);

        drawLabel = new JLabel("Draw !");
        drawLabel.setFont(new Font("Arial",Font.PLAIN,30));
        this.mainPanel.add(drawLabel);

        if (timeLimited){
            timeLabel = new JLabel(String.valueOf((System.currentTimeMillis()-startTime)/1000));
            this.mainPanel.add(timeLabel);
            timeLabel.setFont(new Font("Arial",Font.PLAIN,40));
            timeLabel.setBounds(350,-10,1000,100);
        }
        refreshScores(false);
    }
    void initAnimation(){
        this.animationPanel.startTheAnimation();
    }
     long refreshTime(){
         if (ball.getVx()* ball.getVx() + ball.getVy()*ball.getVy()<250000 && !(ball.getVx()==0 && ball.getVy()==0)){
             float alpha = 500f/(float) Math.pow(ball.getVx()*ball.getVx()+ball.getVy()*ball.getVy(),0.5);
             ball.getBody().setLinearVelocity(new Vec2(ball.getVx()*alpha,ball.getVy()*alpha));
         }
            if (!stopTiming){
                if (getTimePassed()-lastVanishedPowerTime>9 && getTimePassed()-lastAppearanceTime>9 && !isBigGoalAppearance() && !isMirrorWallsAppearance() && !isFireBallAppearance()){
                    lastAppearanceTime=getTimePassed();
                    Random random = new Random();
                    int powerChoice = random.nextInt(1,4);
                    if (powerChoice==1){
                        bigGoalAppearance=true;
                        powerX = random.nextInt(100,700);
                        powerY = random.nextInt(50,450);
                    }
                    if (powerChoice==2){
                        mirrorWallsAppearance = true;
                        powerX = random.nextInt(100,700);
                        powerY = random.nextInt(50,450);
                    }
                    if (powerChoice==3){
                        fireBallAppearance = true;
                        powerX = random.nextInt(100,700);
                        powerY = random.nextInt(50,450);
                    }
                }
                if (getTimePassed()-lastAppearanceTime>9 && getTimePassed()-lastVanishedPowerTime>9){
                    lastVanishedPowerTime = getTimePassed();
                    bigGoalAppearance=false;
                    mirrorWallsAppearance=false;
                    fireBallAppearance=false;
                }
            long seconds = timeLimit/1000-(System.currentTimeMillis()-startTime)/1000;
            lastSecond = System.currentTimeMillis();
            if (seconds<20){
                timeLabel.setForeground(Color.red);
            }
            String second = String.valueOf(seconds- 60L *(int)(seconds/60));
            if (second.length()==1){
                second="0"+second;
            }
            timeLabel.setText("0"+seconds/60+":"+second);
        }
        else{
            startTime+=System.currentTimeMillis()-lastSecond;
        }
         return lastSecond;
    }

    void sleepConfigure(){
        startTime+=2000;
    }

    public String getColor1() {
        return color1;
    }

    public long getLastVanishedPowerTime() {
        return lastVanishedPowerTime;
    }

    public long getLastAppearanceTime() {
        return lastAppearanceTime;
    }

    public void setGoalLimit(int goalLimit) {
        this.goalLimit = goalLimit;
    }

    public void setBigGoalEnabled(boolean[] bigGoalEnabled) {
        this.bigGoalEnabled = bigGoalEnabled;
    }

    public void setFireBallEnabled(boolean[] fireBallEnabled) {
        this.fireBallEnabled = fireBallEnabled;
    }

    public void setMirrorWallsEnabled(boolean mirrorWallsEnabled) {
        this.mirrorWallsEnabled = mirrorWallsEnabled;
    }

    public void setBigGoalAppearance(boolean bigGoalAppearance) {
        this.bigGoalAppearance = bigGoalAppearance;
    }

    public void setFireBallAppearance(boolean fireBallAppearance) {
        this.fireBallAppearance = fireBallAppearance;
    }

    public void setMirrorWallsAppearance(boolean mirrorWallsAppearance) {
        this.mirrorWallsAppearance = mirrorWallsAppearance;
    }

    public void setLastVanishedPowerTime(long lastVanishedPowerTime) {
        this.lastVanishedPowerTime = lastVanishedPowerTime;
    }

    public void setLastAppearanceTime(long lastAppearanceTime) {
        this.lastAppearanceTime = lastAppearanceTime;
    }

    void refreshScores(boolean gameIsDone){
        if (animationPanel.getPlayer1Goals()==animationPanel.getPlayer2Goals() && animationPanel.getPlayer1Goals()==getGoalLimit()-1 && getTwoMargin()){
            addGoalLimit();
        }
        if (!gameIsDone){
            finalGoalLabel1.setVisible(animationPanel.getPlayer1Goals() == goalLimit - 1);
            finalGoalLabel2.setVisible(animationPanel.getPlayer2Goals() == goalLimit - 1);
            if (animationPanel.getPlayer1Goals()==goalLimit){
                isGameDone=true;
                rpButton.setText("Play Again");
                rpButton.setBounds(405,600,100,40);
                finalGoalLabel2.setVisible(false);
                winnerLabel.setBounds(650,15,1000,100);
                player1Label.setForeground(Color.green);
                player2Label.setForeground(Color.red);
            }
            if (animationPanel.getPlayer2Goals()==goalLimit){
                isGameDone=true;
                rpButton.setText("Play Again");
                rpButton.setBounds(405,600,100,40);
                finalGoalLabel1.setVisible(false);
                winnerLabel.setBounds(50,15,1000,100);
                player1Label.setForeground(Color.red);
                player2Label.setForeground(Color.green);
            }
            player1Label.setText(player1Name+" : "+animationPanel.getPlayer1Goals());
            player2Label.setText(player2Name+" : "+animationPanel.getPlayer2Goals());
        }
        else{
            isGameDone=true;
            rpButton.setText("Play Again");
            rpButton.setBounds(405,600,100,40);
            finalGoalLabel1.setVisible(false);
            finalGoalLabel2.setVisible(false);
            if (animationPanel.getPlayer1Goals()>animationPanel.getPlayer2Goals()){
                timeLabel.setVisible(false);
                winnerLabel.setBounds(650,15,1000,100);
                player1Label.setForeground(Color.green);
                player2Label.setForeground(Color.red);
            }
            else if(animationPanel.getPlayer2Goals()>animationPanel.getPlayer1Goals()){
                timeLabel.setVisible(false);
                winnerLabel.setBounds(50,15,1000,100);
                player1Label.setForeground(Color.red);
                player2Label.setForeground(Color.green);
            }
            else{
                timeLabel.setVisible(false);
                drawLabel.setBounds(355,-5,1000,100);
            }
        }
    }

    void addGoalLimit(){
        this.goalLimit++;
    }
    int getGoalLimit(){
        return goalLimit;
    }
    boolean getTwoMargin(){
        return twoMargin;
    }

    boolean isTimeLimited() {
        return timeLimited;
    }

    String getPlayer1Name() {
        return player1Name;
    }

    String getPlayer2Name() {
        return player2Name;
    }

    AnimationPanel getAnimationPanel() {
        return animationPanel;
    }
    boolean isGameDone(){
        return isGameDone;
    }
    long getTimePassed(){
        return (System.currentTimeMillis()-startTime)/1000;
    }

    public boolean isTwoMargin() {
        return twoMargin;
    }

    public boolean isBigGoalEnabaled() {
        return bigGoalEnabled[0]||bigGoalEnabled[1];
    }

    public boolean isFireBallEnabled() {
        return fireBallEnabled[0]||fireBallEnabled[1];
    }

    public boolean isMirrorWallsEnabled() {
        return mirrorWallsEnabled;
    }

    public boolean isBigGoalAppearance() {
        return bigGoalAppearance;
    }

    public boolean isFireBallAppearance() {
        return fireBallAppearance;
    }

    public boolean isMirrorWallsAppearance() {
        return mirrorWallsAppearance;
    }

    public boolean[] getBigGoalEnabled() {
        return bigGoalEnabled;
    }

    public boolean[] getFireBallEnabled() {
        return fireBallEnabled;
    }

    public int getPowerX() {
        return powerX;
    }

    public int getPowerY() {
        return powerY;
    }

    public int getLastKick() {
        return lastKick;
    }

    public void setLastKick(int lastKick) {
        this.lastKick = lastKick;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || keyPressed[0]) {
            keyPressed[0] = true;
            this.mallet1.setVy(-500);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || keyPressed[1]){
            keyPressed[1] = true;
            this.mallet1.setVx(500);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || keyPressed[2]) {
            keyPressed[2] = true;
            this.mallet1.setVy(500);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || keyPressed[3]){
            keyPressed[3] = true;
            this.mallet1.setVx(-500);
        }
        if (e.getKeyCode() == KeyEvent.VK_W || keyPressed[4]) {
            keyPressed[4] = true;
            this.mallet2.setVy(-500);
        }
        if (e.getKeyCode() == KeyEvent.VK_D || keyPressed[5]){
            keyPressed[5] = true;
            this.mallet2.setVx(500);
        }
        if (e.getKeyCode() == KeyEvent.VK_S || keyPressed[6]) {
            keyPressed[6] = true;
            this.mallet2.setVy(500);
        }
        if (e.getKeyCode() == KeyEvent.VK_A || keyPressed[7]){
            keyPressed[7] = true;
            this.mallet2.setVx(-500);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keyPressed[0] = false;
            this.mallet1.setVy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            keyPressed[1] = false;
            this.mallet1.setVx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keyPressed[2] = false;
            this.mallet1.setVy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            keyPressed[3] = false;
            this.mallet1.setVx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keyPressed[4] = false;
            this.mallet2.setVy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_D){
            keyPressed[5] = false;
            this.mallet2.setVx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keyPressed[6] = false;
            this.mallet2.setVy(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            keyPressed[7] = false;
            this.mallet2.setVx(0);
        }
    }
}
