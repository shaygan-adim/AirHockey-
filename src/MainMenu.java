import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends Menu{

    MainMenu () {
        super("Air Hockey +",33,70);
    }
    @Override
    protected void initComponents(){
        JPanel centerPanel = new JPanel();
        BoxLayout centerPanelLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(centerPanelLayout);

        JButton newGameButton = new JButton("New Game");
        JButton historyButton = new JButton("History");
        JButton exitButton = new JButton("Exit");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ModeMenu();
                MainMenu.super.dispose();
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistoryMenu();
                MainMenu.super.dispose();
            }
        });
        exitButton.addActionListener(e -> MainMenu.super.dispose());

        newGameButton.setFocusPainted(false);
        historyButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // Center the buttons vertically
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(newGameButton);
        centerPanel.add(Box.createVerticalStrut(20)); // add vertical space between buttons
        centerPanel.add(historyButton);
        centerPanel.add(Box.createVerticalStrut(20)); // add vertical space between buttons
        centerPanel.add(exitButton);
        centerPanel.add(Box.createVerticalGlue());

        // Set the maximum width of the buttons to make them center horizontally
        Dimension buttonSize = new Dimension(500, 50);
        newGameButton.setMaximumSize(buttonSize);
        historyButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        centerPanel.setOpaque(false);

        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerContainer.add(centerPanel, gbc);

        // Add horizontal glue components to center the buttons horizontally
        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.WEST);
        this.mainPanel.add(centerContainer, BorderLayout.CENTER);
        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);
    }
    }
