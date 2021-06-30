package server.util;

public class PlayerInstruction {

    private String target;
    private char action;
    private int ninja;

    public int getNinja() {
        return ninja;
    }

    public void setNinja(int ninja) {
        this.ninja = ninja;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public char getAction() {
        return action;
    }

    public void setAction(char action) {
        this.action = action;
    }
}
