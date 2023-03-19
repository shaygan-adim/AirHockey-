import javax.swing.*;
import java.awt.*;

public abstract class Menu extends JFrame{
    protected JPanel mainPanel;

    Menu(String text, int size, int vGap){
        initMainFrame();
        initComponents();
        initLabel(text, size, vGap);
        this.setVisible(true);
    }

    protected void initMainFrame(){
        Image backgroundImage = Loader.getMenuBackground();
        this.mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        this.setContentPane(this.mainPanel);

        this.setTitle("Air Hockey +");
        this.setSize(300,400);
        BorderLayout mainMenuLayout = new BorderLayout();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainPanel.setLayout(mainMenuLayout);
    }
    abstract protected void initComponents();
    protected void initLabel(String text, int size, int vGap){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial",Font.PLAIN,size));
        label.setForeground(new Color(255,255,255));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.mainPanel.add(label,BorderLayout.NORTH);
        label.setPreferredSize(new Dimension(100,vGap));
    }
}
