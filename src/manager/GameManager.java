package manager;

import server.util.PlayerInstruction;
import util.Screen;
import validator.InputValidator;


public class GameManager extends BaseManager {

    private static PlayerInstruction coordinate;

    private static GameManager myGameManager;

    public static GameManager getInstance() {

        if (myGameManager == null) {
            myGameManager = new GameManager(new PlayerInstruction());
            coordinate = new PlayerInstruction();
            coordinate.setTarget(" ");
        }

        return myGameManager;
    }

    protected GameManager(PlayerInstruction instruction) {
        GameManager.coordinate = instruction;
    }


    public static void start() {//valida que la opcion sea la correcta y llama a la funcion que eligio

        boolean success = false;
        char option;
        boolean invitation;

        while (!success) {
            option = Screen.menu(false);
            success = true;

            while (!InputValidator.validateOption(option)) {
                option = Screen.menu(true);
            }

            switch (option) {
                case 'N' -> {
                    NewGame newGame = NewGame.getInstance();
                    invitation = invitation();
                    newGame.newGame(invitation);//crea el server y espera a que el cliente le pegue
                    newGame.play();
                    success = false;//coloca las posiciones iniciales e inicia la ronda de turnos
                }
                case 'J' -> {
                    invitation = invitation();
                    Join.join(invitation);
                    success = false;
                }//crea el server del cliente y espera que el servidor le pegue
                case 'E' -> success = false;
            }
        }

    }
    
    public static boolean invitation(){
        
        boolean tryAgain = false;
        char response = ' ';
        boolean invitation;
        
        while (!tryAgain){
            response = Screen.invitationOption(tryAgain);
            tryAgain = InputValidator.validateInvitation(response);
        }
        
        invitation = response == 'Y';
        
        return invitation;        
    }

    public static PlayerInstruction setInitialPosition(boolean success, boolean isCommander) {//valida las posciones ingresadas

        boolean tryAgain = false;

        while (!tryAgain) {
            coordinate.setTarget(Screen.setInitialPosition(success, isCommander));
            tryAgain = InputValidator.validateCoordinates(coordinate.getTarget());
            success = true;
        }
        coordinate.setAction('M');

        return coordinate;

    }

    public static char setOption(boolean success, boolean isCommander) {//valida la opcion del jugador

        boolean tryAgain = false;
        char option = ' ';

        while (!tryAgain) {
            option = Screen.setOption(success, isCommander);
            tryAgain = InputValidator.validateInstruction(option);
            success = true;
        }

        return option;
    }

    public static String setPosition(boolean success, char option) {//valida la coordenada

        boolean tryAgain = false;
        String coordinate = " ";

        while (!tryAgain) {
            coordinate = Screen.setCoordinate(success, option);
            tryAgain = InputValidator.validateCoordinates(coordinate);
            success = true;

        }

        return coordinate;
    }

    public static void endGame(boolean result){

        Screen.endGame(result);
    }
}


