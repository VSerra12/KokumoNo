package util;

public abstract class Screen {

    private static final Show show = Show.getShow();

    public static char menu(boolean tryAgain){

        char option;

        if(tryAgain) {

            show.printer(Text.wrongOption);

        }else {
            show.printer(Text.menu);

        }
        option = show.inputChar();

        return option;

    }

    public static char invitationOption(boolean tryAgain) {

        char response;

        if (!tryAgain){
            show.printer(Text.invitation);
        }else{
            show.printer(Text.wrongOption);
        }
        response = show.inputChar();

        return response;
    }

    public static String enterIP(boolean success){

        String IP;

        if(success){
            show.printer(Text.ipFail);
        }else {
            show.printer(Text.ip);
        }
        IP = show.inputString();

        return IP;
    }

    public static void statusConnection(boolean success){
        if (success){
            show.printer(Text.ipGoodConnection);
        }else {
            show.printer(Text.ipBadConnection);
            show.printer(Text.exit);
        }
    }

    public static void waitingForPlayer(){

        show.printer(Text.waitingConnection);
    }

    public static String setInitialPosition(boolean success, boolean isCommander){

        String position;
        if(!success) {
            if (isCommander) {
                show.printer(Text.strategyCommander);
            } else {
                show.printer(Text.strategyNinja);
            }
        }else{
            show.printer(Text.again);
        }
        position = show.inputString();
        return position;

    }

    public static char setOption(boolean tryAgain, boolean isCommander){

        char option;

        if (!tryAgain){

            show.printer(Text.optionAttack);
            if(isCommander){
                show.printer(Text.optionMove);
            }
        }else{
            show.printer(Text.wrongOption);
        }
        option = show.inputChar();

        return option;
    }

    public static String setCoordinate(boolean tryAgain, char option){

        String coordinate;

        if (!tryAgain){
            if (option == 'M'){
                show.printer(Text.move);
            }else {
                show.printer(Text.attack);
            }
        }else {
            show.printer(Text.again);
        }
        coordinate = show.inputString();

        return coordinate;
    }

    public static void showBoard(String board){

        show.printer(board);
    }

    public static void successMovement(String success){

        if (success.equals("OK")){
            show.printer("\n" + Text.success);
        }else {
            show.printer(success);
        }

    }

    public static void endGame(boolean result){
        if (result){
            show.printer(Text.win);
        }else {
            show.printer(Text.loose);
        }
    }

}
