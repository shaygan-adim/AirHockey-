import org.jbox2d.dynamics.Filter;

public class GameLoop implements Runnable {
    private static final int FPS = 200;
    private final AnimationPanel animationPanel;
    private boolean running = false;

    public GameLoop(AnimationPanel animationPanel) {
        this.animationPanel = animationPanel;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastUpdateTime = System.nanoTime();
        long currentTime;
        double deltaTime;

        while (running) {
            currentTime = System.nanoTime();
            deltaTime = (currentTime - lastUpdateTime) / 1000000000.0;
            lastUpdateTime = currentTime;

            try {
                update(deltaTime);
            } catch (InterruptedException e) {}

            animationPanel.repaint();

            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update(double deltaTime) throws InterruptedException {
        CircleBody ball = animationPanel.getBall();
        CircleBody mallet1 = animationPanel.getMallet1();
        CircleBody mallet2 = animationPanel.getMallet2();
        float deltaX1 = ball.getX()-mallet1.getX();
        float deltaY1 = ball.getY()-mallet1.getY();
        float deltaX2 = ball.getX()-mallet2.getX();
        float deltaY2 = ball.getY()-mallet2.getY();
        if (deltaX1*deltaX1+deltaY1*deltaY1<=(ball.getRadius()+mallet1.getRadius())*(ball.getRadius()+mallet1.getRadius())){
            if (!(animationPanel.getGameAnimation().getLastKick()==2 && animationPanel.getGameAnimation().isFireBallEnabled())){
                animationPanel.getGameAnimation().setLastKick(1);
            }
        }
        if (deltaX2*deltaX2+deltaY2*deltaY2<=(ball.getRadius()+mallet2.getRadius())*(ball.getRadius()+mallet2.getRadius())){
            if (!(animationPanel.getGameAnimation().getLastKick()==1 && animationPanel.getGameAnimation().isFireBallEnabled())){
                animationPanel.getGameAnimation().setLastKick(2);
            }
        }

        if (animationPanel.getGameAnimation().isBigGoalAppearance()||animationPanel.getGameAnimation().isMirrorWallsAppearance()||animationPanel.getGameAnimation().isFireBallAppearance()){
            float R = ball.getRadius()+(int)((animationPanel.getGameAnimation().getTimePassed()-animationPanel.getGameAnimation().getLastAppearanceTime())*3);
            float deltaX = ball.getX()-(animationPanel.getGameAnimation().getPowerX());
            float deltaY = ball.getY()-(animationPanel.getGameAnimation().getPowerY());
            if (deltaX*deltaX+deltaY*deltaY<=R*R){
                animationPanel.getGameAnimation().setLastVanishedPowerTime(animationPanel.getGameAnimation().getTimePassed());
                if (animationPanel.getGameAnimation().isBigGoalAppearance()){
                    animationPanel.getGameAnimation().setBigGoalAppearance(false);
                    boolean[] enabled = new boolean[2];
                    enabled[animationPanel.getGameAnimation().getLastKick()-1]=true;
                    enabled[animationPanel.getGameAnimation().getLastKick()%2]=animationPanel.getGameAnimation().getBigGoalEnabled()[animationPanel.getGameAnimation().getLastKick()%2];
                    animationPanel.getGameAnimation().setBigGoalEnabled(enabled);
                }
                if (animationPanel.getGameAnimation().isFireBallAppearance()){
                    animationPanel.getGameAnimation().setFireBallAppearance(false);
                    boolean[] enabled = new boolean[2];
                    enabled[animationPanel.getGameAnimation().getLastKick()-1]=true;
                    Filter filter = new Filter();
                    filter.categoryBits = 0x0001;
                    if (animationPanel.getGameAnimation().getLastKick()==1){
                        filter.maskBits = 0x0004 | 0x0002;
                        ball.getBody().getFixtureList().setFilterData(filter);
                    }
                    else{
                        filter.maskBits = 0x0008 | 0x0002;
                        ball.getBody().getFixtureList().setFilterData(filter);
                    }
                    enabled[animationPanel.getGameAnimation().getLastKick()%2]=animationPanel.getGameAnimation().getFireBallEnabled()[animationPanel.getGameAnimation().getLastKick()%2];
                    animationPanel.getGameAnimation().setFireBallEnabled(enabled);
                }
                if (animationPanel.getGameAnimation().isMirrorWallsAppearance()){
                    animationPanel.getGameAnimation().setMirrorWallsEnabled(true);
                    animationPanel.getGameAnimation().setMirrorWallsAppearance(false);
                }
            }
        }
        if (animationPanel.getGameAnimation().isMirrorWallsEnabled()){
            if (ball.getY()<=10){
                ball.setY(440);
            }
            if (ball.getY()>445){
                ball.setY(15);
            }
        }
        animationPanel.getWorld().step((float)deltaTime, 6, 4);
        if (ball.getX()<0 && ball.getY()>120 && ball.getY()<330 || (animationPanel.getGameAnimation().getBigGoalEnabled()[0] && ball.getX()<0 && ball.getY()>120-60 && ball.getY()<330+60 )){
            animationPanel.goalForPlayer1();
        }
        if (ball.getX()>750 && ball.getY()>120 && ball.getY()<330 || (animationPanel.getGameAnimation().getBigGoalEnabled()[1] && ball.getX()>750 && ball.getY()>120-60 && ball.getY()<330+60 )){
            animationPanel.goalForPlayer2();
        }
    }
}
