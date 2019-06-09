package packet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

enum GameState {
    PLAYING,
    DEFEAT,
    WIN
}

// TODO рефактор всего класса. Необходимо пересмотреть его архитектуру в целом
//  после перехода на графику
class MinedField {
    private static final int ROWS = 5;
    private static final int COLUMNS = 5;

    static int howNeedOpenCellsForWin;
    ArrayList<ArrayList<Cell>> field = new ArrayList<>();

    GameState state = GameState.PLAYING;

    MinedField(final int maxOfBombs, JFrame frame, Sapper sapper){
        Cell.minedField = this;

        for (int j = 0; j < ROWS; j++){
            ArrayList<Cell> cells = new ArrayList<>();
            for (int i = 0; i < COLUMNS; i++) {
                Cell cell = new Cell();
                cell.setBounds(i*100, j*100, 100, 100);
                cell.addActionListener(sapper);
                cells.add(cell);
                frame.getContentPane().add(cell);
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

    public boolean isWin() {
        return state == GameState.WIN;
    }

//    public boolean isDefeat() {
//        return state == GameState.DEFEAT;
//    }

//    public void openCell(int y, int x) {
//        Cell tempCell = field.get(y).get(x);
//        tempCell.openCell();
//        if (tempCell.isExploded){
//            state = GameState.DEFEAT;
//        } else if (Cell.openedCell == howNeedOpenCellsForWin){
//            state = GameState.WIN;
//        }
//    }
}
