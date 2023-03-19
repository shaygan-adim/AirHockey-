import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerMenu extends Menu{
    private boolean timeLimited,twoMargin;
    private int goalLimit;
    private double timeLimit;
    PlayerMenu ( boolean timeLimited, int goalLimit,boolean twoMargin,double timeLimit) {
        super("Personalize Players :", 23,25);
        this.timeLimited = timeLimited;
        this.timeLimit = timeLimit;
        this.goalLimit = goalLimit;
        this.twoMargin = twoMargin;
    }

    @Override
    protected void initComponents() {
        JPanel centerPanel = new JPanel();
        GridLayout centerPanelLayout = new GridLayout(3,2);
        centerPanel.setLayout(centerPanelLayout);

        JLabel player1Label = new JLabel("Player 1 :");
        JLabel player2Label = new JLabel("Player 2 :");
        JTextField player1TextField = new JTextField("Player 1       ");
        JTextField player2TextField = new JTextField("Player 2       ");
        JRadioButton p1Red = new JRadioButton("Red");
        JRadioButton p1Green = new JRadioButton("Green");
        p1Green.setSelected(true);
        JRadioButton p1Blue = new JRadioButton("Blue");
        JRadioButton p1Purple = new JRadioButton("Purple");
        JRadioButton p2Red = new JRadioButton("Red");
        JRadioButton p2Green = new JRadioButton("Green");
        p2Green.setSelected(true);
        JRadioButton p2Blue = new JRadioButton("Blue");
        JRadioButton p2Purple = new JRadioButton("Purple");
        JButton startButton = new JButton("Start");
        JButton backButton = new JButton("Back");

        startButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModeMenu();
                PlayerMenu.super.dispose();
            }
        });

        player1Label.setFont(new Font("Arial", Font.PLAIN , 15));
        player1Label.setForeground(Color.white);
        player2Label.setFont(new Font("Arial", Font.PLAIN , 15));
        player2Label.setForeground(Color.white);

        p1Red.setForeground(Color.white);
        p1Red.setOpaque(false);
        p1Red.setFocusPainted(false);
        p1Green.setForeground(Color.white);
        p1Green.setOpaque(false);
        p1Green.setFocusPainted(false);
        p1Blue.setForeground(Color.white);
        p1Blue.setOpaque(false);
        p1Blue.setFocusPainted(false);
        p1Purple.setForeground(Color.white);
        p1Purple.setOpaque(false);
        p1Purple.setFocusPainted(false);

        ButtonGroup group1 = new ButtonGroup();
        group1.add(p1Red);
        group1.add(p1Green);
        group1.add(p1Blue);
        group1.add(p1Purple);

        p2Red.setForeground(Color.white);
        p2Red.setOpaque(false);
        p2Red.setFocusPainted(false);
        p2Green.setForeground(Color.white);
        p2Green.setOpaque(false);
        p2Green.setFocusPainted(false);
        p2Blue.setForeground(Color.white);
        p2Blue.setOpaque(false);
        p2Blue.setFocusPainted(false);
        p2Purple.setForeground(Color.white);
        p2Purple.setOpaque(false);
        p2Purple.setFocusPainted(false);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(p2Red);
        group2.add(p2Green);
        group2.add(p2Blue);
        group2.add(p2Purple);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String color1 = null;
                String color2 = null;
                if (p1Red.isSelected()){
                    color1 = "red";
                }
                if (p1Green.isSelected()){
                    color1 = "green";
                }
                if (p1Blue.isSelected()){
                    color1 = "blue";
                }
                if (p1Purple.isSelected()){
                    color1 = "purple";
                }
                if (p2Red.isSelected()){
                    color2 = "red";
                }
                if (p2Green.isSelected()){
                    color2 = "green";
                }
                if (p2Blue.isSelected()){
                    color2 = "blue";
                }
                if (p2Purple.isSelected()){
                    color2 = "purple";
                }
                new GameAnimation(color1,color2,player1TextField.getText(),player2TextField.getText(),timeLimited,goalLimit,twoMargin,timeLimit);
                PlayerMenu.super.dispose();
            }
        });

        JPanel helperOfHelperPanel1 = new JPanel();
        helperOfHelperPanel1.add(p1Green);
        helperOfHelperPanel1.setOpaque(false);
        JPanel helperPanel1 = new JPanel();
        BoxLayout helperPanel1Layout = new BoxLayout(helperPanel1,BoxLayout.Y_AXIS);
        helperPanel1.setLayout(helperPanel1Layout);
        helperPanel1.add(helperOfHelperPanel1);
        helperPanel1.add(p1Red);
        helperPanel1.add(p1Blue);
        helperPanel1.add(p1Purple);
        JPanel helperOfHelperPanel2 = new JPanel();
        helperOfHelperPanel2.add(p2Green);
        helperOfHelperPanel2.setOpaque(false);
        JPanel helperPanel2 = new JPanel();
        BoxLayout helperPanel2Layout = new BoxLayout(helperPanel2,BoxLayout.Y_AXIS);
        helperPanel2.setLayout(helperPanel2Layout);
        helperPanel2.add(helperOfHelperPanel2);
        helperPanel2.add(p2Red);
        helperPanel2.add(p2Blue);
        helperPanel2.add(p2Purple);
        JPanel helperPanel3 = new JPanel();
        helperPanel3.add(player2TextField);
        JPanel helperPanel4 = new JPanel();
        helperPanel4.add(player1TextField);
        JPanel helperPanel5 = new JPanel();
        helperPanel5.add(Box.createVerticalStrut(100));
        helperPanel5.add(player2Label);
        JPanel helperPanel6 = new JPanel();
        helperPanel6.add(Box.createVerticalStrut(100));
        helperPanel6.add(player1Label);


        helperPanel1.setOpaque(false);
        helperPanel2.setOpaque(false);
        helperPanel3.setOpaque(false);
        helperPanel4.setOpaque(false);
        helperPanel5.setOpaque(false);
        helperPanel6.setOpaque(false);

        centerPanel.add(helperPanel5);
        centerPanel.add(helperPanel6);
        centerPanel.add(helperPanel3);
        centerPanel.add(helperPanel4);
        centerPanel.add(helperPanel2);
        centerPanel.add(helperPanel1);

        JPanel southPanel = new JPanel();
        southPanel.add(backButton);
        southPanel.add(startButton);
        southPanel.setOpaque(false);

        centerPanel.setOpaque(false);


        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.WEST);
        this.mainPanel.add(centerPanel, BorderLayout.CENTER);
        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);
        this.mainPanel.add(Box.createVerticalStrut(50),BorderLayout.SOUTH);
        this.mainPanel.add(southPanel,BorderLayout.SOUTH);
    }
}
