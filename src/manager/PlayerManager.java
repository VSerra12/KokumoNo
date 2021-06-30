package manager;

import entity.Empty;
import entity.Piece;
import entity.Player;
import validator.GameValidator;


public class PlayerManager extends BaseManager {


    public String position(String position, int ninja, Player player){ //renueva posicion


        boolean success;
        boolean haveOldPosition;
        boolean successMovement = true;
        String fail = "OK";
        String oldPosition = " ";

        try {
            success = !GameValidator.isValid(position, player.getMyBoard()) && GameValidator.isValid(ninja, player);
            haveOldPosition = !player.getSquad().get(ninja).getPosition().equals(" ");

            if (haveOldPosition && success){
                oldPosition = player.getSquad().get(ninja).getPosition();
                successMovement = GameValidator.validateMovement(position, oldPosition);
                success = successMovement;
            }

            if (success){

                if (haveOldPosition){

                    Piece piece = new Empty();
                    player.getMyBoard().setAPiece(oldPosition, piece);
                }

                player.getSquad().get(ninja).setPosition(position);
                Piece piece = player.getSquad().get(ninja);
                player.getMyBoard().setAPiece(position, piece);
            }else {

                if (player.getMyBoard().getAPiece(position).hasNinja()) {
                    fail = "You already have a ninja in this position.";
                }else if(!successMovement){
                    fail = "Can only move one box away";
                }else {
                    fail = "Is occupied.";
                }
            }


        }catch (Exception e){
            addError("la posicion.." + e.getMessage());
            fail = "there is a error";
        }

        return fail;
    }

    public String attack(String target, Player player2){


        boolean success;
        String itsOk = "OK";
        int ninja;

        try{

            success = GameValidator.thereTarget(target, player2.getMyBoard());

            if (!success) itsOk = "You didn't hit anything";

            if (success){

                ninja = GameValidator.whoDied(target, player2.getSquad());
                int life = player2.getSquad().get(ninja).getLife();
                player2.getSquad().get(ninja).setLife(life - 1);

                player2.getMyBoard().getAPiece(target).setIsNinja(false);

            }else {
                Empty empty = new Empty();
                empty.setOccupied(true);
                empty.setIsNinja(false);
                player2.getMyBoard().setAPiece(target,empty);
            }
        }catch (Exception e){
            addError("El ataque.." + e.getMessage());
            itsOk = "NO";
        }


        return itsOk;

    }
}
