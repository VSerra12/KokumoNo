package validator;

import converter.MatrixConverter;
import entity.Board;
import entity.Ninja;
import entity.Player;
import errors.Error;

import java.util.ArrayList;
import java.util.List;

public class GameValidator {

    private static Error error;

    private final ArrayList<String> errors = createList();

    private ArrayList<String> createList(){
        return new ArrayList<>();
    }

    public GameValidator() {
        error = new Error();
    }

    protected ArrayList<String> getErrors() {
        return error.getErrors();
    }

    protected int hasError() {
        return error.hasError();
    }

    protected static void addError(String description) {
        error.addError(description);
    }

    protected void addError(ArrayList<String> errors) {
        errors.addAll(errors);
    }

    public static boolean isValid(String position, Board pieces){//validar si la posicion esta ocupada

        boolean found;

        try {
            found = pieces.getAPiece(position).isOccupied();
        }catch (Exception e){
            addError("La posicion en el tablero" + e.getMessage());
            found = false;
        }

        return found;
    }

    public static boolean isValid(int ninja, Player player){//validar si el ninja esta vivo

        boolean success;

        try {
            success = player.getSquad().get(ninja).getLife() > 0 ;

        }catch (Exception e){
            addError("No hay ninjas" + e.getMessage());
            success = false;
        }

        return success;
    }

    public static boolean thereTarget(String target, Board board){

        boolean success;

        try {
            success = board.getAPiece(target).hasNinja();

        }catch (Exception e){
            addError("el objetivo..." + e.getMessage());
            success = false;
        }
        return success;
    }

    public static int whoDied(String target, List<Ninja> squad){

        int dead = -1;
        try {

            for (int i = 0; i < squad.size() && dead == -1; i++){

                if (squad.get(i).getPosition().equals(target)){
                    dead = i;
                }
            }

        }catch (Exception e){
            addError("la squad enemiga.. " + e.getMessage());
            dead = -1;
        }
        return dead;

    }

    public static int continueGame(List<Ninja> player){

        int life = 0;

        for (Ninja ninja : player) {

            if (ninja.getLife() > 0){
                life++;
            }
        }

        return life;
    }

    public static boolean validateMovement(String movement, String oldPosition){

        int rowOld = MatrixConverter.rowConverter(oldPosition);
        int columnOld = MatrixConverter.columnConverter(oldPosition);
        int rowNew = MatrixConverter.rowConverter(movement);
        int columnNew = MatrixConverter.columnConverter(movement);

        return (rowOld == rowNew - 1 || rowOld == rowNew + 1 || rowOld == rowNew)
                && (columnOld == columnNew - 1 || columnOld == columnNew + 1 || columnOld == columnNew);
    }

}
