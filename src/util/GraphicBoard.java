package util;

import converter.MatrixConverter;
import entity.Ninja;
import entity.Piece;

public class GraphicBoard {

    private final char[][] board;
    private final char[][] enemy;

    public GraphicBoard(char [][] newBoard, char[][] newEnemy){

        this.board = newBoard;
        this.enemy = newEnemy;
    }

    public void setEnemy(String success, String position){

        int row = MatrixConverter.rowConverter(position);
        int column = MatrixConverter.columnConverter(position);

        row = row * 2;
        column = column + column + 2;

        if (success.equals("OK")){

            enemy[row][column] = 'X';
        }else {
            enemy[row][column] = '#';
        }

    }

    public String convertToString(){

        return String.copyValueOf(board[0]) + "\t" + String.copyValueOf(enemy[0]) + "\n"
                + String.copyValueOf(board[1]) + "\t" + String.copyValueOf(enemy[1]) + "\n"
                + String.copyValueOf(board[2]) + "\t" + String.copyValueOf(enemy[2]) + "\n"
                + String.copyValueOf(board[3]) + "\t" + String.copyValueOf(enemy[3]) + "\n"
                + String.copyValueOf(board[4]) + "\t" + String.copyValueOf(enemy[4]) + "\n"
                + String.copyValueOf(board[5]) + "\t" + String.copyValueOf(enemy[5]) + "\n"
                + String.copyValueOf(board[6]) + "\t" + String.copyValueOf(enemy[6]) + "\n"
                + String.copyValueOf(board[7]) + "\t" + String.copyValueOf(enemy[7]) + "\n"
                + String.copyValueOf(board[8]) + "\t" + String.copyValueOf(enemy[8]) + "\n"
                + String.copyValueOf(board[9]) + "\t" + String.copyValueOf(enemy[9]) + "\n"
                + String.copyValueOf(board[10]) + "\t" + String.copyValueOf(enemy[10]) + "\n";
    }

    public void setBoard(Piece[][] pieces, String position){

        int row = MatrixConverter.rowConverter(position);
        int column = MatrixConverter.columnConverter(position);

        char action;
        Piece piece = pieces[row][column];

        row = row * 2;
        column = column + column + 2;


        if (piece.isOccupied()){
            if (piece.hasNinja()){
                action = 'N';
                Ninja ninja = (Ninja) piece;
                if (ninja.isCommander()){
                    action = 'C';
                    if (ninja.getLife() == 1){action = '/';}
                }
                if (ninja.getLife() == 0){action = 'X';}
            }else {
                action = '#';
            }
        }else {
            action = ' ';
        }

        board[row][column] = action;
    }

    public void deletePosition (String actual){

        int row = MatrixConverter.rowConverter(actual);
        int column = MatrixConverter.columnConverter(actual);

        row = row * 2;
        column = column + column + 2;

        board[row][column] = ' ';
    }

}
