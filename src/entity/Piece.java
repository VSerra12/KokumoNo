package entity;

public class Piece {

    protected boolean occupied;
    protected boolean isNinja;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setIsNinja(boolean isNinja){
        this.isNinja = isNinja;
    }

    public boolean hasNinja(){
        return isNinja;
    }
}
