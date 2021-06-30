package server.util;

public class HostSend {

    private String board;
    private String fails;
    private boolean commanderAlive;
    private int[] life;

    public HostSend(String board, String fails, boolean commanderAlive, int[] life) {
        this.board = board;
        this.fails = fails;
        this.commanderAlive = commanderAlive;
        this.life = life;
    }

    public boolean isCommanderAlive() {
        return commanderAlive;
    }

    public void setCommanderAlive(boolean commanderAlive) {
        this.commanderAlive = commanderAlive;
    }

    public int[] getLife() {
        return life;
    }

    public void setLife(int[] life) {
        this.life = life;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getFails() {
        return fails;
    }

    public void setFails(String fails) {
        this.fails = fails;
    }
}
