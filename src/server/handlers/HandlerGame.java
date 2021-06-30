package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.Player;
import manager.NewGame;
import manager.PlayerManager;
import server.util.HostSend;
import server.util.Json;
import server.util.PlayerInstruction;
import server.util.ServerResponse;
import util.GraphicBoard;
import validator.GameValidator;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HandlerGame implements HttpHandler {

    private final PlayerManager playerManager = NewGame.getPlayerManager();
    private final GraphicBoard graphicBoard = NewGame.getGraphic2();
    private final GraphicBoard graphicEnemy = NewGame.getGraphic1();
    private final Player enemy = NewGame.getPlayer1();
    private final Player player = NewGame.getPlayer2();

    private static boolean success = false;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        PlayerInstruction instruction = Json.fromJsonInput(PlayerInstruction.class, exchange.getRequestBody());

        String itsOk;
        String allGood;
        String actual = " ";

        if (instruction.getAction() == 'M'){
            actual = player.getSquad().get(instruction.getNinja()).getPosition();
            itsOk = playerManager.position(instruction.getTarget(), instruction.getNinja(), player);
        }else {
            itsOk = playerManager.attack(instruction.getTarget(), enemy);
        }

        boolean success = !itsOk.equals("You already have a ninja in this position.") && !itsOk.equals("NO")
                && !itsOk.equals("You already attacked this locker this turn") && !itsOk.equals("Can only move one box away")
                && !itsOk.equals("Is occupied.");


        if (success){
            allGood = "OK";

            if (instruction.getAction() == 'M'){
                if(!actual.equals(" ")){
                    graphicBoard.deletePosition(actual);
                }
                graphicBoard.setBoard(player.getMyBoard().getBoard(), instruction.getTarget());
            }else {
                graphicBoard.setEnemy(itsOk, instruction.getTarget());
                graphicEnemy.setBoard(enemy.getMyBoard().getBoard(), instruction.getTarget());
                graphicEnemy.setBoard(enemy.getMyBoard().getBoard(), instruction.getTarget());

            }

        }else {
            allGood = "NO";
        }

        int[] life = new int[3];

        if (GameValidator.continueGame(enemy.getSquad()) > 0) {
            life[0] = player.getSquad().get(0).getLife();
            life[1] = player.getSquad().get(1).getLife();
            life[2] = player.getSquad().get(2).getLife();
        }else {
            life[0] = 0;
            life[1] = 0;
            life[2] = 0;
        }
        boolean commanderAlive = player.getSquad().get(0).getLife() > 0;

        HostSend data =  new HostSend(graphicBoard.convertToString(), itsOk, commanderAlive, life);

        ServerResponse response = new ServerResponse(200, data, allGood);
        String toString = Json.toJson(response);
        exchange.sendResponseHeaders(response.getCode(), toString.length());
        OutputStream out = exchange.getResponseBody();
        out.write(toString.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();

        setSuccess(true);

    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        HandlerGame.success = success;
    }
}
