import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ModeMenu extends Menu {
    ModeMenu () {
        super("Choose Game Mode :", 23,50);
    }
    @Override
    protected void initComponents(){
        JPanel centerPanel = new JPanel();
        BoxLayout centerPanelLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
        centerPanel.setLayout(centerPanelLayout);

        JCheckBox timeLimitedOption = new JCheckBox("Time Limited");
        JCheckBox goalLimitedOption = new JCheckBox("Goal Limited");
        JCheckBox twomarginOption = new JCheckBox("2 Margin");
        twomarginOption.setVisible(false);
        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                ModeMenu.super.dispose();
            }
        });

        JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(2, 0.5, 5, 0.5));
        timeSpinner.setVisible(false);
        JSpinner goalSpinner = new JSpinner(new SpinnerNumberModel(2,1,5,1));
        goalSpinner.setVisible(false);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (goalLimitedOption.isSelected() || timeLimitedOption.isSelected()){
                    int goalLimit = -1;
                    if (goalLimitedOption.isSelected()){
                        goalLimit = (int) goalSpinner.getValue();
                    }
                    new PlayerMenu(timeLimitedOption.isSelected(),goalLimit,twomarginOption.isSelected(),(double)timeSpinner.getValue()*60000);
                    ModeMenu.super.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please choose at least one of the Modes.","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        goalLimitedOption.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    goalSpinner.setVisible(true);
                    twomarginOption.setVisible(true);
                } else {
                    goalSpinner.setVisible(false);
                    twomarginOption.setVisible(false);
                }
            }
        });
        timeLimitedOption.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    timeSpinner.setVisible(true);
                } else {
                    timeSpinner.setVisible(false);
                }
            }
        });

        timeLimitedOption.setForeground(new Color(255,255,255));
        timeLimitedOption.setFocusPainted(false);
        timeLimitedOption.setOpaque(false);
        goalLimitedOption.setForeground(new Color(255,255,255));
        goalLimitedOption.setFocusPainted(false);
        goalLimitedOption.setOpaque(false);
        twomarginOption.setForeground(new Color(255,255,255));
        twomarginOption.setFocusPainted(false);
        twomarginOption.setOpaque(false);
        backButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);


        JPanel helperPanel1 = new JPanel();
        helperPanel1.add(timeSpinner);
        helperPanel1.setOpaque(false);
        JPanel helperPanel2 = new JPanel();
        helperPanel2.add(goalSpinner);
        helperPanel2.setOpaque(false);

        timeLimitedOption.setPreferredSize(new Dimension(100,50));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(timeLimitedOption);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(helperPanel1);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(goalLimitedOption);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(helperPanel2);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(twomarginOption);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(nextButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(backButton);
        centerPanel.add(Box.createVerticalGlue());

        backButton.setMaximumSize(new Dimension(95,100));
        nextButton.setMaximumSize(new Dimension(95,100));
        timeSpinner.setPreferredSize(new Dimension(35,20));
        goalSpinner.setPreferredSize(new Dimension(30,20));

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
