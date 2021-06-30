package entity;

public class Empty extends Piece{


    private boolean occupied = false;

    @Override
    public void setOccupied(boolean occupied) {
        super.setOccupied(occupied);
    }

    @Override
    public void setIsNinja(boolean isNinja) {
        super.setIsNinja(occupied);
    }
}
