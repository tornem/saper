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

    static int openedCell = 0;

    private int x, y;
    private char status = CELL_EMPTY;

    private boolean hasBomb = false;
    boolean isExploded      = false;

    @Override
    public String toString() {
        return Character.toString(status);
    }

    public String toStringHidden(){
        return Character.toString(status == CELL_OPENED ? status : CELL_HIDDEN);
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void setStatus(char status){
        this.status = status;
    }

    public void plantBomb() {
        hasBomb = true;
        setStatus(CELL_BOMB);
    }

    public char getHiddenStatus(){
        if (status == CELL_OPENED || (status == CELL_BOMB && isExploded)){
            return status;
        }
        return CELL_HIDDEN;
    }

    public void openCell() {
        if (!hasBomb){
            setStatus(CELL_OPENED);
        } else {
            isExploded = true;
        }
        ++openedCell;
    }
}

class MinedField {
    private static final int ROWS = 5;
    private static final int COLUMN = 5;

    private ArrayList<ArrayList<Cell>> field = new ArrayList<>();

    private GameState state = GameState.PLAYING;

    private int howNeedOpenCellsForWin;

    MinedField(final int maxOfBombs){
        for (int j = 0; j < ROWS; j++){
            ArrayList<Cell> cells = new ArrayList<>();
            for (int i = 0; i < COLUMN; i++) {
                Cell cell = new Cell(i, j);
                cells.add(cell);
            }
            field.add(cells);
        }

        int plantedBombs = 0;
        end:
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMN; ++j){
                Random random = new Random();
                boolean isBomb = random.nextBoolean();
                if (isBomb){
                    ++plantedBombs;
                    field.get(i).get(j).plantBomb();
                }
                if (maxOfBombs == plantedBombs) break end;
            }
        }
        howNeedOpenCellsForWin = ROWS*COLUMN - plantedBombs;
    }

    void printOpenField() {
        field.forEach(rows -> System.out.println(rows.toString()));
    }

    void printHiddenField() {
        for (ArrayList<Cell> cells : field){
            System.out.print(' ');
            for (Cell cell : cells){
                System.out.print(cell.getHiddenStatus() + "  ");
            }
            System.out.println();
        }
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
