package manager;

import entity.Player;
import server.handlers.HandlerHost;
import server.util.PlayerInstruction;
import util.GraphicBoard;
import util.Screen;
import validator.GameValidator;

import java.util.ArrayList;

public class HostController{

    private static final PlayerManager playerManager = NewGame.getPlayerManager();
    private static final GraphicBoard graphic1 = NewGame.getGraphic1();
    private static final GraphicBoard graphic2 = NewGame.getGraphic2();
    private static final Player player1 = NewGame.getPlayer1();
    private static final Player player2 = NewGame.getPlayer2();

    public static void initialPosition(){

        boolean tryAgain = false;
        String fail;
        int i = 0;

        while (i < 3){

            PlayerInstruction coordinate;
            if(i == 0){
                coordinate = GameManager.setInitialPosition(tryAgain, true);
            }else {
                coordinate = GameManager.setInitialPosition(tryAgain, false);
            }

            fail = playerManager.position(coordinate.getTarget(), i, player1);

            tryAgain = true;
            if(fail.equals("OK")) {
                i++;
                tryAgain = false;
                graphic1.setBoard(player1.getMyBoard().getBoard(), coordinate.getTarget());
                Screen.showBoard(graphic1.convertToString());

            }else {
                System.out.println(fail);
            }
        }

        System.out.println("Waiting for Player 2 to set his board...");

        while (!HandlerHost.isSuccess()){

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void turn (int alive2, boolean commanderAlive){
        int j  = 0;
        boolean tryAgain = false;
        String fail = " ";
        char option;
        String target;
        String actual = " ";
        ArrayList<String> attacks = new ArrayList<>();

        while (j < 3 && alive2 > 0) {
            if (player1.getSquad().get(j).getLife() > 0) {

                option = GameManager.setOption(false, commanderAlive);
                target = GameManager.setPosition(tryAgain, option);

                switch (option) {
                    case 'M' -> {
                        actual = player1.getSquad().get(j).getPosition();
                        fail = playerManager.position(target, j, player1);
                    }
                    case 'A' -> {
                        if (!attacks.contains(target)){
                            fail = playerManager.attack(target, player2);
                            attacks.add(target);
                        }else{
                            fail = "You already attacked this locker this turn";
                        }
                    }
                }

                boolean success = !fail.equals("You already have a ninja in this position.") && !fail.equals("NO")
                        && !fail.equals("You already attacked this locker this turn") && !fail.equals("Can only move one box away")
                        && !fail.equals("Is occupied.");

                if (success) {
                    tryAgain = false;

                    if (option == 'M') {
                        graphic1.deletePosition(actual);
                        graphic1.setBoard(player1.getMyBoard().getBoard(), target);

                    } else {
                        graphic1.setEnemy(fail, target);
                        graphic2.setBoard(player2.getMyBoard().getBoard(), target);
                    }
                    j++;
                    Screen.showBoard(graphic1.convertToString());
                    Screen.successMovement(fail);
                } else {
                    System.out.println(fail);
                    tryAgain = true;
                }
            }else {
                j++;
            }
            alive2 = GameValidator.continueGame(player2.getSquad());
        }
    }
}
