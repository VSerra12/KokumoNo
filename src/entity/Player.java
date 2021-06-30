package entity;

import java.util.List;

public class Player {

    private Board myBoard;
    private List<Ninja> squad;


    public Player(Board myBoard, List<Ninja> squad) {
        this.myBoard = myBoard;
        this.squad = squad;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public void setMyBoard(Board myBoard) {
        this.myBoard = myBoard;
    }

    public List<Ninja> getSquad() {
        return squad;
    }

    public void setSquad(List<Ninja> squad) {
        this.squad = squad;
    }
}
