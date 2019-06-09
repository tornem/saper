package packet;

import javax.swing.*;

class Cell extends JButton {
    private static final String CELL_EMPTY   = "O";
    private static final String CELL_BOMB    = "X";

    static int openedCell;
    public int aroundBombs;

    private String status = CELL_EMPTY;

    private boolean hasBomb = false;
    boolean isExploded      = false;

    static MinedField minedField = null;

    public Cell() {
        super();
        this.setActionCommand(toString());
    }

    @Override
    public String toString() {
        return status;
    }

    private void setStatus(String status){
        this.status = status;
        this.setActionCommand(toString());
    }

    String getView(){
        return status.equals(CELL_EMPTY) ? Integer.toString(aroundBombs) : CELL_BOMB;
    }

    public void plantBomb() {
        hasBomb = true;
        setStatus(CELL_BOMB);
    }

    public void openCell() {
        if (hasBomb){
            isExploded = true;
        }
        ++openedCell;
        if (Cell.openedCell == MinedField.howNeedOpenCellsForWin){
            Cell.minedField.state = GameState.WIN;
        }
    }

    public boolean hasBomb() {
        return hasBomb;
    }
}
