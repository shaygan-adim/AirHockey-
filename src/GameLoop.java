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

            // update the game state
            try {
                update(deltaTime);
            } catch (InterruptedException e) {}

            // repaint the panel
            animationPanel.repaint();

            // sleep for a short time to limit the frame rate
            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update(double deltaTime) throws InterruptedException {
        // update the physics and game logic here
        animationPanel.getWorld().step((float)deltaTime, 6, 4);
        CircleBody ball = animationPanel.getBall();
        if (ball.getVx()* ball.getVx() + ball.getVy()*ball.getVy()<250000 && !(ball.getVx()==0 && ball.getVy()==0)){
            float alpha = 500f/(float) Math.pow(ball.getVx()*ball.getVx()+ball.getVy()*ball.getVy(),0.5);
            ball.setVy(ball.getVy()*alpha);
            ball.setVx(ball.getVx()*alpha);
        }
        if (ball.getX()<0 && ball.getY()>120 && ball.getY()<330){
            animationPanel.goalForPlayer1();
        }
        if (ball.getX()>750 && ball.getY()>120 && ball.getY()<330){
            animationPanel.goalForPlayer2();
        }
    }
}
