package entity;

import converter.MatrixConverter;

public class Board {

    private Piece[][] board = createBoard();

    private static Piece[][] createBoard(){

        Piece [][] board = new Piece[5][5];

        for (int i = 0; i < 5; i++){

            for (int j = 0; j < 5; j++){
                board[i][j] = new Empty();
            }
        }

        return board;
    }


    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public Piece getAPiece(String position){

        int row = MatrixConverter.rowConverter(position);
        int column = MatrixConverter.columnConverter(position);

        return board[row][column];
    }

    public void setAPiece(String position, Piece piece){

        int row = MatrixConverter.rowConverter(position);
        int column = MatrixConverter.columnConverter(position);

        board[row][column] = piece;
    }

}
