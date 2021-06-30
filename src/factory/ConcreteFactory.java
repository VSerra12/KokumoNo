package factory;

import entity.Board;
import entity.Ninja;

import java.util.ArrayList;
import java.util.List;

public class ConcreteFactory implements Factory {

    private static final int BOARD = 11;

    @Override
    public Board createBoard() {
       return new Board(); }

    @Override
    public List<Ninja> createSquad() {
        return new ArrayList<>();
    }

    @Override
    public Ninja newNinja(int life, boolean commander) {

        Ninja ninja = new Ninja();

        ninja.setLife(life);
        ninja.setCommander(commander);
        ninja.setPosition(" ");
        ninja.setOccupied(true);
        ninja.setIsNinja(true);

        return ninja;
    }

    @Override
    public char[][] newGraphicBoard() {

        char[][] board = new char[BOARD][BOARD];

        board[0][0] = 'A';
        board[2][0] = 'B';
        board[4][0] = 'C';
        board[6][0] = 'D';
        board[8][0] = 'E';
        board[10][2] = '1';
        board[10][4] = '2';
        board[10][6] = '3';
        board[10][8] = '4';
        board[10][10] = '5';


        int i = 1;

        while (i < 11){

            for (int j = 0; j < BOARD; j++){
                board[i][j] = '-';
            }
            i += 2;
        }

        i = 1;

        while (i < 11){

            for (int j = 0; j < BOARD; j++){
                board[j][i] = '|';
            }
            i += 2;
        }

        i = 0;
        int j;

        while (i < BOARD -1){

            j = 2;

            while (j < BOARD){

                board[i][j] = ' ';

                j = j + 2;
            }

            i += 2;

        }
        board[10][0] = ' ';

        return board;
    }

}
