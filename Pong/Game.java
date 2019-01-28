package Pong;

public abstract class Game implements Comparable{
    public abstract int getScore();

    public abstract String getPlayerId();

    @Override
    public int compareTo(Object o) {
        Game g = (Game) o;
        return this.getScore() - g.getScore();
    }
}
