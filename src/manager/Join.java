package manager;

import server.ServerManager;
import server.client.ClientManager;
import server.handlers.HandlerGuest;
import server.handlers.HandlerHost;
import server.util.HostSend;
import server.util.ServerResponse;
import util.Screen;
import validator.InputValidator;

public class Join extends BaseManager{

    public static void join(boolean invitation){
        ServerManager.guestServer();
        String IP;

        if(invitation) {//

            Screen.waitingForPlayer();

            while (!HandlerGuest.isSuccess()) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            HandlerGuest.setSuccess(false);

            System.out.println("Connected");
            IP = HandlerGuest.getPort();
            IP = IP + ":8800";
            ClientManager.setIp(IP);

        }else {
            IP = Screen.enterIP(false);
            IP = IP + ":8800";

            while (InputValidator.validateIP(IP)) {
                IP = Screen.enterIP(true);
            }

            try {
                ClientManager.setIp(IP);
                ServerResponse response = ClientManager.get("/join", ServerResponse.class);

                boolean success = response.getCode() == 200;
                Screen.statusConnection(success);
                if (!success) {
                    GameManager.start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ClientController.sendInstruction();
        ClientManager.get("/join", ServerResponse.class);
        turn();
    }

    public static void turn() {
        int lives;
        int[] life = new int[3];
        boolean continueGame = true;
        life[0] = 2;

        while (continueGame) {

            System.out.println("\nWaiting for Player 1 to make his move...");
            while (!HandlerGuest.isSuccess()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            HandlerGuest.setSuccess(false);

            lives = 0;

            HostSend message = HandlerGuest.getMessage();
            life = message.getLife();
            for (int l:life) {

                if (l > 0){lives ++;}
            }
            continueGame = lives > 0;

            String board = message.getBoard();
            String fails = message.getFails();

            System.out.println(board + "\n" + fails);
            if (lives > 0) {

                ClientController.sendMove(life, message.isCommanderAlive());
            }


            ClientManager.get("/join", ServerResponse.class);

        }

        while (!HandlerGuest.isSuccess()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {

            HandlerGuest.setSuccess(false);

            HostSend message = HandlerGuest.getMessage();

            String board = message.getBoard();

            Screen.showBoard(board);
            GameManager.endGame(message.isCommanderAlive());
        }catch (Exception e){
            addError(e.getMessage());
            for (int x = 0; x < hasError(); ){
                System.out.println(getErrors().get(x));
            }
        }

    }
}
