package packet;

import java.util.Arrays;
import java.util.Random;

enum gameState{
    PLAYING,
    DEFEAT,
    WIN
}

class MinedField {
    private Random random = new Random();

    private char[][] field = new char[5][5];

    private gameState state = gameState.PLAYING;

    private int plantedBombs = 0;
    private int openedCells  = 0;
    private int needToWinCell;

    MinedField(final int maxOfBombs){
        for (char[] value : field) {
            Arrays.fill(value, 'O');
        }

        end:
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j){
                boolean isBomb = random.nextBoolean();
                if (isBomb){
                    ++plantedBombs;
                    setBomb(i, j);
                }
                if (maxOfBombs == plantedBombs) break end;
            }
        }

        needToWinCell = 25 - plantedBombs;
    }

    private void setCellStatus(int row, int column, char status){
        field[row][column] = status;
    }

    private void setBomb(int row, int column) {
        setCellStatus(row, column, '*');
    }

    void setBoom(int row, int column) {
        setCellStatus(row, column, '⨂');
        openedCells++;
        checkForWin();
    }

    void setOpenCell(int row, int column) {
        setCellStatus(row, column, 'N');
        openedCells++;
        checkForWin();
    }

    void setStatusDefeat() {
        state = gameState.DEFEAT;
    }

    char getCell(int row, int column) {
        return field[row][column];
    }

    int getPlantedBombs() {
        return plantedBombs;
    }

    void printOpenField() {
        for (char[] chars : field) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    //TODO выводит строки инверсивно. Надо выяснить почему
    void printHiddenField() {
        for (char[] chars : field) {
            for (char cell : chars) {
                System.out.print(hiddenCell(cell));
            }
            System.out.println();
        }
    }

    static private char hiddenCell(char openCell){
        switch (openCell){
            case 'N':
                return('N');
            case '⨂':
                return('⨂');
            default:
                return('\u086B');
        }
    }

    boolean isContinueToPlay(){
        return state == gameState.PLAYING;
    }

    private void checkForWin(){
        if (needToWinCell == openedCells &&
                isContinueToPlay()){
            state = gameState.WIN;
        }
    }

    boolean isWin() {
       return state == gameState.WIN;
    }

    boolean isDefeat() {
        return state == gameState.DEFEAT;
    }
}
