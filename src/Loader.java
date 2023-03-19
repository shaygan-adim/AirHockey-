import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Loader {
    private static Image menuBackground;
    private static Image table;
    private static Image redMallet;
    private static Image blueMallet;
    private static Image greenMallet;
    private static Image purpleMallet;

    private static Image goalTable1,goalTable2;
    private static Image winTable1,winTable2;

    private static String menuBackgroundPath = "Resources/MenuBackground.jpg";

    private static String animationPath = "Resources/Animation";

    private Loader(){}

    static void loadImages(){
        Image backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File(Loader.menuBackgroundPath));
        } catch (IOException ignored) {}
        Loader.menuBackground = backgroundImage;
        Image tableImage = null;
        try {
            tableImage = ImageIO.read(new File(Loader.animationPath+"/Table.jpg"));
        } catch (IOException ignored) {}
        Loader.table = tableImage;
        Image goalTable1Image = null;
        try {
            goalTable1Image = ImageIO.read(new File(Loader.animationPath+"/GoalTable1.jpg"));
        } catch (IOException ignored) {}
        Loader.goalTable1 = goalTable1Image;
        Image goalTable2Image = null;
        try {
            goalTable2Image = ImageIO.read(new File(Loader.animationPath+"/GoalTable2.jpg"));
        } catch (IOException ignored) {}
        Loader.goalTable2 = goalTable2Image;
        Image winTable1Image = null;
        try {
            winTable1Image = ImageIO.read(new File(Loader.animationPath+"/WinTable1.jpg"));
        } catch (IOException ignored) {}
        Loader.winTable1 = winTable1Image;
        Image winTable2Image = null;
        try {
            winTable2Image = ImageIO.read(new File(Loader.animationPath+"/WinTable2.jpg"));
        } catch (IOException ignored) {}
        Loader.winTable2 = winTable2Image;
        Image redMalletImage = null;
        try {
            redMalletImage = ImageIO.read(new File(Loader.animationPath+"/RedMallet.png"));
        } catch (IOException ignored) {}
        Loader.redMallet = redMalletImage;
        Image greenMalletImage = null;
        try {
            greenMalletImage = ImageIO.read(new File(Loader.animationPath+"/GreenMallet.png"));
        } catch (IOException ignored) {}
        Loader.greenMallet = greenMalletImage;
        Image purpleMalletImage = null;
        try {
            purpleMalletImage = ImageIO.read(new File(Loader.animationPath+"/PurpleMallet.png"));
        } catch (IOException ignored) {}
        Loader.purpleMallet = purpleMalletImage;
        Image blueMalletImage = null;
        try {
            blueMalletImage = ImageIO.read(new File(Loader.animationPath+"/BlueMallet.png"));
        } catch (IOException ignored) {}
        Loader.blueMallet = blueMalletImage;
    }

    static Image getTable() {
        return Loader.table;
    }

    public static Image getGoalTable1() {
        return goalTable1;
    }

    public static Image getGoalTable2() {
        return goalTable2;
    }

    public static Image getWinTable1() {
        return winTable1;
    }

    public static Image getWinTable2() {
        return winTable2;
    }

    static Image getRedMallet() {
        return Loader.redMallet;
    }

    static Image getBlueMallet() {
        return Loader.blueMallet;
    }

    static Image getGreenMallet() {
        return Loader.greenMallet;
    }

    static Image getPurpleMallet() {
        return Loader.purpleMallet;
    }

    static Image getMenuBackground() {
        return Loader.menuBackground;
    }
}
