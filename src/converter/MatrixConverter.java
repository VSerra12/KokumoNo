package converter;

public class MatrixConverter {

    public static int rowConverter(String position){
        int row = 0;
        switch (position.toCharArray()[0]) {
            case 'A' -> row = 0;
            case 'B' -> row = 1;
            case 'C' -> row = 2;
            case 'D' -> row = 3;
            case 'E' -> row = 4;
        }

        return row;
    }

    public static int columnConverter(String position){

        int value = 0;
        char[] toChar = position.toCharArray();

        switch (toChar[1]) {
            case '1' -> value = 0;
            case '2' -> value = 1;
            case '3' -> value = 2;
            case '4' -> value = 3;
            case '5' -> value = 4;
        }

        return value;
    }
}
