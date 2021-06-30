package manager;

import entity.Board;
import entity.Ninja;
import entity.Player;
import factory.ConcreteFactory;
import server.ServerManager;
import server.client.ClientManager;
import server.handlers.HandlerHost;
import server.util.HostSend;
import server.util.ServerResponse;
import util.GraphicBoard;
import util.Screen;
import validator.GameValidator;
import validator.InputValidator;

import java.util.List;

public class NewGame{

    private static PlayerManager playerManager;
    private static Player player1;
    private static Player player2;
    private static GraphicBoard graphic1;
    private static GraphicBoard graphic2;
    private static NewGame myNewGame;

    public void newGame(boolean invitation) {

        ServerManager.hostServer();

        if(invitation) {//van a pegarle al host

            Screen.waitingForPlayer();

            while (!HandlerHost.isSuccess()) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Connected");

            HandlerHost.setSuccess(false);
        }else {//va a pegarle al guest
            String IP = Screen.enterIP(false);
            IP = IP + ":8500";

            while (InputValidator.validateIP(IP)){
                IP = Screen.enterIP(true);
            }

            try {
                ClientManager.setIp(IP);
                ServerResponse response = ClientManager.get("/join", ServerResponse.class);

                boolean success = response.getCode() == 200;
                Screen.statusConnection(success);
                if(!success){GameManager.start();}

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void play(){

        int[] life = new int[3];
        life[0] = player2.getSquad().get(0).getLife();
        life[1] = player2.getSquad().get(1).getLife();
        life[2] = player2.getSquad().get(2).getLife();

        HostSend send = new HostSend(graphic2.convertToString(), " ", true, life);

        ClientManager.post("/join", send, ServerResponse.class);

        Screen.showBoard(graphic1.convertToString() + "\n");
        HostController.initialPosition();

        while (!HandlerHost.isSuccess()){

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HandlerHost.setSuccess(false);

        boolean continueGame = true;
        int alive1;
        int alive2;

        while (continueGame){

            boolean commanderAlive = player1.getSquad().get(0).getLife() > 0;
            alive1 = GameValidator.continueGame(player1.getSquad());
            alive2 = GameValidator.continueGame(player2.getSquad());

            if (alive1 > 0 ){
                HostController.turn(alive2, commanderAlive);
            }

            life[0] = player2.getSquad().get(0).getLife();
            life[1] = player2.getSquad().get(1).getLife();
            life[2] = player2.getSquad().get(2).getLife();

            send.setLife(life);
            send.setCommanderAlive(player2.getSquad().get(0).getLife() > 0);
            send.setBoard(graphic2.convertToString());

            ClientManager.post("/join", send,ServerResponse.class);

            System.out.println("\nWaiting for Player 2 to make his move...");

            while (!HandlerHost.isSuccess()){

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            HandlerHost.setSuccess(false);

            Screen.showBoard(graphic1.convertToString());

            alive1 = GameValidator.continueGame(player1.getSquad());

            continueGame = alive1 > 0 && alive2 > 0;

            if(!continueGame) {
                life[0] = 0;
                life[1] = 0;
                life[2] = 0;
            }
        }
        alive1 = GameValidator.continueGame(player1.getSquad());
        alive2 = GameValidator.continueGame(player2.getSquad());

        if (alive2 <= 0){
            send.setCommanderAlive(false);
            send.setLife(life);
        }else {
            send.setCommanderAlive(true);
            send.setLife(life);
        }

        send.setBoard(graphic2.convertToString());
        ClientManager.post("/join", send,ServerResponse.class);

        GameManager.endGame(alive1 > 0);
    }

    public static NewGame getInstance() {

        if (myNewGame == null){
            myNewGame = createNewGame();
        }

        return myNewGame;
    }

    private NewGame(PlayerManager playerManager,Player player1,
                    Player player2,GraphicBoard graphic1,GraphicBoard graphic2) {

        NewGame.playerManager = playerManager;
        NewGame.player1 = player1;
        NewGame.player2 = player2;
        NewGame.graphic1 = graphic1;
        NewGame.graphic2 = graphic2;
    }

    public static NewGame createNewGame(){
        Player player1 = createPlayer();
        Player player2 = createPlayer();

        PlayerManager manager = new PlayerManager();
        GraphicBoard graphic1 = createGraphic();
        GraphicBoard graphic2 = createGraphic();

        return new NewGame(manager, player1, player2, graphic1, graphic2);

    }

    private static GraphicBoard createGraphic(){

        ConcreteFactory factory = new ConcreteFactory();

        char[][] board = factory.newGraphicBoard();
        char[][] enemy = factory.newGraphicBoard();

        return new GraphicBoard(board, enemy);
    }

    private static Player createPlayer(){

        ConcreteFactory factory = new ConcreteFactory();

        Board board = factory.createBoard();
        List<Ninja> squad = factory.createSquad();
        Ninja ninja = factory.newNinja(2, true);
        Ninja ninja1 = factory.newNinja(1, false);
        Ninja ninja2 = factory.newNinja(1, false);

        squad.add(ninja);
        squad.add(ninja1);
        squad.add(ninja2);

        return new Player(board, squad);
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static GraphicBoard getGraphic1() {
        return graphic1;
    }

    public static GraphicBoard getGraphic2() {
        return graphic2;
    }

}
