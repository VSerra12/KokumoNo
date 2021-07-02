package manager;

import server.ServerManager;
import server.client.ClientManager;
import server.handlers.HandlerGuest;
import server.util.HostSend;
import server.util.ServerResponse;
import util.Screen;
import validator.InputValidator;

public class Join extends BaseManager{

    public static void join(boolean invitation){
        ServerManager.guestServer();
        String IP;

        if(invitation) {

            Screen.waitingForPlayer();

            while (!HandlerGuest.isSuccess()) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            HandlerGuest.setSuccess(false);

            IP = HandlerGuest.getPort();
            IP = "http://" + IP + ":8000";
            ClientManager.setIp(IP);

            ClientManager.get("/join", ServerResponse.class);

            System.out.println("Connected");
        }else {
            IP = Screen.enterIP(false);
            IP = IP + ":8000";

            while (InputValidator.validateIP(IP)) {
                IP = Screen.enterIP(true);
                IP = IP + ":8000";
            }

            IP = "http://" + IP;

            try {
                ClientManager.setIp(IP);
                ServerResponse response = ClientManager.get("/join", ServerResponse.class);

                boolean success = response.getCode() == 200;
                Screen.statusConnection(success);
                if (!success) {
                    GameManager.start();
                }

                while (!HandlerGuest.isSuccess()) {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
            if (lives > 0) {

                String board = message.getBoard();
                String fails = message.getFails();


                ClientController.sendMove(life, message.isCommanderAlive());
                System.out.println(board + "\n" + fails);
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
        HandlerGuest.setSuccess(false);
        try {


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
