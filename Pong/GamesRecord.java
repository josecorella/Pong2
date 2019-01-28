package Pong;

import java.util.ArrayList;

public class GamesRecord  {
    ArrayList<Object> gamesRecord = new ArrayList<>();


    public void add(Game game){
        gamesRecord.add(game);
    }

    public float average() {
        float av = 0;
        for (int i = 0; i < gamesRecord.size(); i++) {
            if (this.gamesRecord.get(i) instanceof Pong) {
                av += ((Pong) this.gamesRecord.get(i)).getScore();
            }
        }
        float average = av / gamesRecord.size();
        return average;
    }

    public float averageInd (String user){
        float av = 0;
        int index = 0;
        for (int i = 0; i < gamesRecord.size(); i++) {
            if (this.gamesRecord.get(index) instanceof Pong) {
                String player = ((Pong) this.gamesRecord.get(index)).getPlayerId();
                if(user.equals(player)) {
                    av += ((Pong) this.gamesRecord.get(index)).getScore();
                }

            }
        }
        float average = av / gamesRecord.size();
        return average;
    }

    public ArrayList highGameList(){
        int i = 0;
        while(i <gamesRecord.size()){
            ((Pong) this.gamesRecord.get(i)).compareTo(this.gamesRecord.get(i++));
            i++;

        }
        return gamesRecord;
    }

}