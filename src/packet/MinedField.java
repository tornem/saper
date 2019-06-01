package packet;

import java.util.ArrayList;
import java.util.Random;

enum GameState {
    PLAYING,
    DEFEAT,
    WIN
}

class Cell {
    private static final char CELL_EMPTY   = 'O';
    private static final char CELL_BOMB    = 'X';
    private static final char CELL_HIDDEN  = '*';
    private static final char CELL_OPENED  = 'N';

    static int openedCell;
    public int aroundBombs;

    private char status = CELL_EMPTY;

    private boolean hasBomb = false;
    boolean isExploded      = false;

    @Override
    public String toString() {
        return Character.toString(status);
    }

    private void setStatus(char status){
        this.status = status;
    }

    String getView(){
        return status == CELL_OPENED ? Integer.toString(aroundBombs) : Character.toString(CELL_HIDDEN);
    }

    public void plantBomb() {
        hasBomb = true;
        setStatus(CELL_BOMB);
    }

    public void openCell() {
        if (!hasBomb){
            setStatus(CELL_OPENED);
        } else {
            isExploded = true;
        }
        ++openedCell;
    }

    public boolean hasBomb() {
        return hasBomb;
    }
}

class MinedField {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;

    private ArrayList<ArrayList<Cell>> field = new ArrayList<>();

    private GameState state = GameState.PLAYING;


    private int howNeedOpenCellsForWin;
    MinedField(final int maxOfBombs){
        for (int j = 0; j < ROWS; j++){
            ArrayList<Cell> cells = new ArrayList<>();
            for (int i = 0; i < COLUMNS; i++) {
                Cell cell = new Cell();
                cells.add(cell);
            }
            field.add(cells);
        }

        int plantedBombs = 0;
        end:
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j){
                Random random = new Random();
                boolean isBomb = random.nextBoolean();
                if (isBomb){
                    ++plantedBombs;
                    field.get(i).get(j).plantBomb();
                }
                if (maxOfBombs == plantedBombs) break end;
            }
        }
        howNeedOpenCellsForWin = ROWS* COLUMNS - plantedBombs;

        int[][] d = {
                {1,0}, {0,1}, {-1,0}, {0,-1},
                {1,1}, {-1,1}, {1,-1}, {-1,-1}
        };

        for (int i = 0; i < ROWS; ++i){
            for (int j = 0; j < COLUMNS; ++j){
                Cell cell = field.get(i).get(j);
                if (cell.hasBomb()) {
                    for (int[] k : d) {
                        int y = k[0] + i;
                        int x = k[1] + j;
                        if (y >= 0 && y < ROWS &&
                            x >= 0 && x < COLUMNS)
                        field.get(y).get(x).aroundBombs++;
                    }
                }
            }
        }
    }

    void getOpenField() {
        field.forEach(rows -> System.out.println(rows.toString()));
    }

    String getView() {
        StringBuilder hiddenField = new StringBuilder(ROWS*COLUMNS);
        for (ArrayList<Cell> cells : field){
            hiddenField.append(" \n");
            for (Cell cell : cells){
                hiddenField.append(cell.getView()).append("  ");
            }
        }
        return hiddenField.toString();
    }

    public boolean isPlaying() {
        return state == GameState.PLAYING;
    }

    public boolean isDefeat() {
        return state == GameState.DEFEAT;
    }

    public void openCell(int y, int x) {
        Cell tempCell = field.get(y).get(x);
        tempCell.openCell();
        if (tempCell.isExploded){
            state = GameState.DEFEAT;
        } else if (Cell.openedCell == howNeedOpenCellsForWin){
            state = GameState.WIN;
        }
    }
}
