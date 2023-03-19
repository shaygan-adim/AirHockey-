import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoryMenu extends Menu{
    HistoryMenu(){
        super("Game History :",23,60);
        this.setVisible(true);
    }
    @Override
    protected void initComponents() {
        JPanel centerPanel = new JPanel();

        String[] stringArray = HistoryLoader.getGames().toArray(new String[0]);
        JList<String> gameList = new JList<>(stringArray);

        JScrollPane scrollPane = new JScrollPane(gameList);

        centerPanel.add(scrollPane);

        centerPanel.setOpaque(false);

        JButton backButton = new JButton("Back");
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenu();
                HistoryMenu.super.dispose();
            }
        });

        JPanel helperPanel = new JPanel();
        helperPanel.setOpaque(false);
        helperPanel.add(backButton);

        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.WEST);
        this.mainPanel.add(centerPanel, BorderLayout.CENTER);
        this.mainPanel.add(Box.createHorizontalGlue(), BorderLayout.EAST);
        this.mainPanel.add(helperPanel,BorderLayout.SOUTH);
    }
}
