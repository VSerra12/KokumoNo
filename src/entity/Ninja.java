package entity;

public class Ninja extends Piece{

    private String position;
    private int life;
    private boolean isCommander;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = true;
    }

    public void setIsNinja(boolean isNinja){
        this.isNinja = true;
    }

    public boolean hasNinja(){
        return isNinja;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isCommander() {
        return isCommander;
    }

    public void setCommander(boolean commander) {
        isCommander = commander;
    }
}
