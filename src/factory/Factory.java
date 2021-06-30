package factory;

import entity.Board;
import entity.Ninja;

import java.util.List;

public interface Factory {

    Board createBoard();
    List<Ninja> createSquad();
    Ninja newNinja(int life, boolean commander);
    char[][] newGraphicBoard();


}
