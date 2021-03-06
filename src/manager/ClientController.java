package manager;

import server.client.ClientManager;
import server.handlers.HandlerGuest;
import server.util.HostSend;
import server.util.PlayerInstruction;
import server.util.ServerResponse;
import util.Screen;
import validator.InputValidator;

import java.util.ArrayList;

public class ClientController extends BaseManager{


    private static PlayerInstruction playerInstruction;

    public static void sendInstruction (){
        boolean tryAgain = false;

        int i = 0;

        while (!HandlerGuest.isSuccess()){

            try {
                Thread.sleep(1001);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HandlerGuest.setSuccess(false);

        System.out.println(HandlerGuest.getMessage().getBoard());

        while (i < 3){

            if (i == 0) {
                playerInstruction = GameManager.setInitialPosition(tryAgain, true);
            }else {
                playerInstruction = GameManager.setInitialPosition(tryAgain, false);
            }

            playerInstruction.setNinja(i);

            try {
                ServerResponse response = ClientManager.post("/game", playerInstruction, ServerResponse.class);
                tryAgain = true;
                HostSend data = response.getData();

                if (response.getResponse().equals("OK")){
                    tryAgain = false;
                    i++;
                    System.out.println(data.getBoard());
                }else {
                    System.out.println(data.getBoard());
                    System.out.println(data.getFails());
                }
            }catch (Exception e){
                addError(e.getMessage());
            }

        }

    }

    public static void sendMove(int[] life, boolean commanderAlive){

        boolean tryAgain = false;
        char option;
        String coordinate;
        String board;
        String fails = " ";
        boolean allGood;
        boolean makeAMove = true;
        ArrayList<String> attacks = new ArrayList<>();

        int i = 0;

        while (i < 3){

            if(life[i] > 0) {
                option = GameManager.setOption(false, commanderAlive);
                coordinate = GameManager.setPosition(tryAgain, option);
                if (option == 'A'){
                    allGood = InputValidator.attackInThisTurn(coordinate, attacks);
                    fails = "You already attacked this locker this turn";
                }else {

                    if (makeAMove){
                        allGood = true;
                    }else{
                        allGood = false;
                        fails = "You can only move one turn in between.";
                    }
                }

                if (allGood) {
                    playerInstruction.setAction(option);
                    playerInstruction.setTarget(coordinate);
                    playerInstruction.setNinja(i);

                    ServerResponse response = ClientManager.post("/game", playerInstruction, ServerResponse.class);

                    HostSend message = response.getData();

                    life = message.getLife();

                    board = message.getBoard();
                    fails = message.getFails();

                    if (response.getResponse().equals("OK")) {
                        tryAgain = false;
                        i++;
                        Screen.showBoard(board);
                        Screen.successMovement(fails);
                        makeAMove = option != 'M';
                    } else {
                        Screen.showBoard(board);//en el response recibe lo que esta mal
                        Screen.successMovement(fails);
                        tryAgain = true;
                    }
                }else {
                    System.out.println(fails);
                }
            }else {
                i++;
            }

        }
    }


}
