class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState gameState;
    private boolean firstStep;

    Game(int cols, int rows, int bombs){
        Field.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    Bomb getBomb() {
        return bomb;
    }

    Flag getFlag() {
        return flag;
    }

    GameState getGameState() {
        return gameState;
    }

    void start(){
        bomb.start();
        flag.start();
        firstStep = true;
        gameState = GameState.PLAYED;
    }

    Cell getCell(Coord coord){
        if (flag.get(coord) == Cell.CELL)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    boolean isFirstStep() {
        return firstStep;
    }

    private void checkWin(){
        if (gameState == GameState.PLAYED)
            if (flag.getNumberOfClosedCell() == bomb.getTotalBombs())
                gameState = GameState.WINNER;
    }

    void pressLeftButton(Coord coord) {
        if(gameOver()) return;
        if (isFirstStep()) {
            while (bomb.get(coord) == Cell.BOMB)
                bomb.start();
            openCell(coord);
            firstStep = false;
        } else
            openCell(coord);
        checkWin();

    }

    private void openCell(Coord coord){
        if(flag.get(coord) == null) return;
        switch (flag.get(coord)){
            case CELL : setOpenedToClosedCellsAroundNumber(coord); break;
            case FLAG : break;
            case CLOSEDCELL : switch (bomb.get(coord)){
                case ZERO : openCellsAround(coord); break;
                case BOMB : openBomb(coord); break;
                default : flag.setOpenedToCell(coord); break;
            }
        }
    }

    private void setOpenedToClosedCellsAroundNumber(Coord coord){
        if (bomb.get(coord) != Cell.BOMB)
            if (flag.getNumberOfFlaggedCellsAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Field.getCoordAround(coord))
                    if (flag.get(around) == Cell.CLOSEDCELL)
                        openCell(around);
    }

    private void openBomb(Coord exploded) {
        gameState = GameState.BOMBED;
        flag.setBombedToCell(exploded);
        for (Coord coord : Field.getAllCoords())
            if (bomb.get(coord) == Cell.BOMB)
                flag.setOpenedToClosedBombCell(coord);
            else
                flag.setNoBombToFlaggedCell(coord);
    }

    private void openCellsAround(Coord coord) {
        flag.setOpenedToCell(coord);
        for(Coord around : Field.getCoordAround(coord))
            if(flag.get(around) != Cell.CELL)
                openCell(around);
    }

    void pressRightButton(Coord coord) {
        if(gameOver()) return;
        flag.toggleFlag(coord);
    }

    boolean gameOver() {
        if(gameState == GameState.PLAYED)
            return false;
        start();
        return true;
    }

}
