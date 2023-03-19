import java.util.ArrayList;
import java.util.List;

public class HistoryLoader {

    private static List<String> games = new ArrayList<>();

    private HistoryLoader(){}

    static void addGame(GameAnimation gameAnimation){
        if (gameAnimation.isGameDone()){
            games.add(gameAnimation.getPlayer1Name()+" : "+gameAnimation.getAnimationPanel().getPlayer1Goals()+" - "+gameAnimation.getPlayer2Name()+" : "+gameAnimation.getAnimationPanel().getPlayer2Goals()+" - finished");
        }
        else{
            games.add(gameAnimation.getPlayer1Name()+" : "+gameAnimation.getAnimationPanel().getPlayer1Goals()+" - "+gameAnimation.getPlayer2Name()+" : "+gameAnimation.getAnimationPanel().getPlayer2Goals()+" - not finished");
        }

    }

    static List<String> getGames(){
        if (games.size()==0){
            games.add("History is empty.");
        }
        return games;
    }
}
