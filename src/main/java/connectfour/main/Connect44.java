package connectfour.main;

import java.util.Objects;
import java.util.Scanner;

public class Connect44 {
    String[][] board;
    Boolean winner;
    Boolean draw;
    int Nyertes;
    int playerTurn;




    public Connect44() {
        Nyertes = 0;
        draw = false;
        playerTurn = 1;
        board = new String[6][7];
        ujTabla();
        irjaKiATablat();
    }





    private void ujTabla() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) { board[i][j] = " - "; }
        }
    }

    private void irjaKiATablat() {
        System.out.println(" ");
        System.out.println("     Connect44:");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(board[i][j]);
            } System.out.println();
        }
    }





    private boolean lehetEBelerakni(String input) { //lehetseges-e belerakni az x vagy o-t a tablaba
        return (Objects.equals(input, "1") ||
                Objects.equals(input, "2") ||
                Objects.equals(input, "3") ||
                Objects.equals(input, "4") ||
                Objects.equals(input, "5") ||
                Objects.equals(input, "6") ||
                Objects.equals(input, "7"));
    }

    private boolean teleVanEAzOszlop(int column) {
        return (board[0][column - 1].equals(" X ") || board[0][column - 1].equals(" 0 "));
    }

    private int egymasraRakas(int column) { //ehhez kaptam segitseget discordon keresztul
        int position = 5;
            while (position >= 0) {
                if (!board[position][column].equals(" X ") && !board[position][column].equals(" 0 ")) {
                    return position;
                }
                position--;
            }
        return -1; // Return -1 if no slot is available
    }

    private void csereJatekost() {
        if (playerTurn == 1) { playerTurn = 2; }
        else { playerTurn = 1; }
    }

    private void rakjaLeADarabot() {
        Scanner sc = new Scanner(System.in);
        System.out.println("jatekos: " + playerTurn + " - valasz egy oszlopot (1-7)");
        String input = sc.nextLine();

        while (!lehetEBelerakni(input) || teleVanEAzOszlop(Integer.parseInt(input))) {
            if (!lehetEBelerakni(input)) {
                System.out.println("1-7 rakjal be egy szamot");
            } else {
                System.out.println("oszlop tele van");
            }
            input = sc.nextLine();
        }

        int columnChoice = Integer.parseInt(input) - 1;
        int rowChoice = egymasraRakas(columnChoice);

        String pieceToPlace = playerTurn == 1 ? " X " : " 0 ";
        board[rowChoice][columnChoice] = pieceToPlace;
        irjaKiATablat();
        csereJatekost();
    }

    private boolean teleVaneATabla() {
        for (int j = 0; j < 7; j++) {
            if (!board[0][j].equals(" X ") && !board[0][j].equals(" 0 ")) {
                return false;
            }
        }
        return true;
    }






    private String fuggolegesNyertes() {
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                if (board[i][j].equals(board[i + 1][j]) &&
                        board[i][j].equals(board[i + 2][j]) &&
                        board[i][j].equals(board[i + 3][j]) &&
                        !board[i][j].equals(" - ")) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    private String sornyertes() {  //sornyertes
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i][j + 1]) &&
                        board[i][j].equals(board[i][j + 2]) &&
                        board[i][j].equals(board[i][j + 3]) &&
                        !board[i][j].equals(" - ")) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    private String BalraDontotNyertes() { //45 balra fokos nyertes
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(board[i + 1][j + 1]) &&
                    board[i][j].equals(board[i + 2][j + 2]) &&
                    board[i][j].equals(board[i + 3][j + 3]) &&
                   !board[i][j].equals(" - "))
                    {return board[i][j];}
            }
        }
        return null;
    }

    private String JobbraDontotNyertes() { //45 (135 fok) jobbra fokos nyertes
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if (board[i][j].equals(board[i + 1][j - 1]) &&
                    board[i][j].equals(board[i + 2][j - 2]) &&
                    board[i][j].equals(board[i + 3][j - 3]) &&
                   !board[i][j].equals(" - ")) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    private int vanNyertes() {
        String symbol = fuggolegesNyertes();
        if (symbol == null) symbol = sornyertes();
        if (symbol == null) symbol = BalraDontotNyertes();
        if (symbol == null) symbol = JobbraDontotNyertes();

        if (symbol == null) return 0;
        return symbol.equals(" X ") ? 1 : 2;
    }

    private boolean DontetlenE() {
        return teleVaneATabla() && vanNyertes() == 0;
    }
    private void showWinner() {
        System.out.println("a(z) " + Nyertes + "es szamu jatekos nyert");
    }





    public void playConnect4() {
        while (Nyertes == 0) {
            Nyertes = vanNyertes();
            draw = DontetlenE();
            if (Nyertes == 1 || Nyertes == 2) {
                showWinner();
                break;
            } else if (draw) {
                System.out.println("dontetlen");
                break;
            }
            rakjaLeADarabot();
        }
    }

    public static void main(String[] args) {
        Connect44 c4 = new Connect44();
        c4.playConnect4();
    }
}
