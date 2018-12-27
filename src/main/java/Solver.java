class Solver {

    private static Bomb bomb;
    private static Flag flag;
    private Game game;

    void update(Game newGame){
        assert newGame != null;
        game = newGame;
        bomb = newGame.getBomb();
        flag = newGame.getFlag();
    }

    void solverStart() {
        if (!game.gameOver()) {
            if (game.isFirstStep())
                randomStep();
            else if (flaggedClosedCellWithBomb()) {
            } else if (setOpenedToClosedCellsAroundNumber()) {
            } else if (flagAroundThreeHorizontal(Cell.NUM1)){
            } else if (flagAroundThreeHorizontal(Cell.NUM2)){
            } else if (flagAroundThreeVertical(Cell.NUM1)){
            } else if (flagAroundThreeVertical(Cell.NUM2)){
            } else if (flagAroundFourHorizontal(Cell.NUM1)){
            } else if (flagAroundFourHorizontal(Cell.NUM2)){
            } else if (flagAroundFourVertical(Cell.NUM1)){
            } else if (flagAroundFourVertical(Cell.NUM2)){
            } else randomStep();
        }
    }

    private boolean flaggedClosedCellWithBomb() {
        for (Coord coord : Field.getAllCoords()) {
            if (flag.get(coord) != Cell.CLOSEDCELL)
                if (flag.getNumberOfClosedOrFlaggedCellsAround(coord) == bomb.get(coord).getNumber())
                    for (Coord around : Field.getCoordAround(coord))
                        if ((flag.get(around) == Cell.CLOSEDCELL)) {
                            flag.setFlagToCell(around);
                            return true;
                        }
        }
        return false;
    }

    private boolean setOpenedToClosedCellsAroundNumber(){
        for (Coord coord : Field.getAllCoords()) {
            if (bomb.get(coord) != Cell.BOMB)
                if (flag.getNumberOfFlaggedCellsAround(coord) == bomb.get(coord).getNumber())
                    for (Coord around : Field.getCoordAround(coord))
                        if (flag.get(around) == Cell.CLOSEDCELL) {
                            game.pressLeftButton(coord);
                            return true;
                        }
        }
        return false;
    }

    private void randomStep(){
        while (true){
            Coord randomCoord = Coord.randomCoord();
            if (flag.get(randomCoord) == Cell.CLOSEDCELL) {
                game.pressLeftButton(randomCoord);
                break;
            }
        }
    }

    private boolean flagAroundThreeHorizontal(Cell cell){
        for (Coord coord : Field.getAllCoords()){
            int x = coord.x;
            int y = coord.y;
            if(isThreeHorizontal(x, y, Cell.NUM1, cell, Cell.NUM1))
                if (isThreeHorizontal(x, y - 1, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL)){
                    if (isThreeHorizontalClosed(x, y, true))
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x + 1, y - 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x, y - 1));
                            flag.setFlagToCell(new Coord(x + 2, y - 1));
                            return true;
                        }
                } else if (isThreeHorizontal(x, y + 1, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL))
                    if (isThreeHorizontalClosed(x, y, false)) {
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x + 1, y + 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x, y + 1));
                            flag.setFlagToCell(new Coord(x + 2, y + 1));
                            return true;
                        }
                    }
        }
        return false;
    }

    private boolean flagAroundThreeVertical(Cell cell){
        for (Coord coord : Field.getAllCoords()){
            int x = coord.x;
            int y = coord.y;
            if(isThreeVertical(x, y, Cell.NUM1, cell, Cell.NUM1))
                if (isThreeVertical(x + 1, y, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL)) {
                    if (isThreeVerticalClosed(x, y, true))
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x + 1, y - 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x + 1, y));
                            flag.setFlagToCell(new Coord(x + 1, y + 2));
                            return true;
                        }
                } else if (isThreeVertical(x - 1, y, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL))
                    if (isThreeVerticalClosed(x, y, false)) {
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x - 1, y - 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x - 1, y));
                            flag.setFlagToCell(new Coord(x - 1, y + 2));
                            return true;
                        }
                    }
        }
        return false;
    }

    private boolean flagAroundFourHorizontal(Cell cell){
        for (Coord coord : Field.getAllCoords()){
            int x = coord.x;
            int y = coord.y;
            if(isFourHorizontal(x, y, Cell.NUM1, cell, cell, Cell.NUM1))
                if (isFourHorizontal(x, y - 1, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL)){
                    if (isFourHorizontalClosed(x, y, true))
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x, y - 1));
                            flag.setFlagToCell(new Coord(x + 3, y - 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x + 1, y - 1));
                            flag.setFlagToCell(new Coord(x + 2, y - 1));
                            return true;
                        }
                } else if (isFourHorizontal(x, y + 1, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL))
                    if (isFourHorizontalClosed(x, y, false)) {
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x, y + 1));
                            flag.setFlagToCell(new Coord(x + 3, y + 1));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x + 1, y + 1));
                            flag.setFlagToCell(new Coord(x + 2, y + 1));
                            return true;
                        }
                    }
        }
        return false;
    }

    private boolean flagAroundFourVertical(Cell cell){
        for (Coord coord : Field.getAllCoords()){
            int x = coord.x;
            int y = coord.y;
            if(isFourVertical(x, y, Cell.NUM1, cell, cell, Cell.NUM1))
                if (isFourVertical(x + 1, y, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL)) {
                    if (isFourVerticalClosed(x, y, true))
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x + 1, y));
                            flag.setFlagToCell(new Coord(x + 1, y + 3));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x + 1, y + 1));
                            flag.setFlagToCell(new Coord(x + 1, y + 2));
                            return true;
                        }
                } else if (isFourVertical(x - 1, y, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL, Cell.CLOSEDCELL))
                    if (isFourVerticalClosed(x, y, false)) {
                        if (cell == Cell.NUM1) {
                            flag.setFlagToCell(new Coord(x - 1, y));
                            flag.setFlagToCell(new Coord(x - 1, y + 3));
                            return true;
                        } else if (cell == Cell.NUM2) {
                            flag.setFlagToCell(new Coord(x - 1, y + 1));
                            flag.setFlagToCell(new Coord(x - 1, y + 2));
                            return true;
                        }
                    }
        }
        return false;
    }

    private boolean isThreeHorizontal(int x, int y, Cell cell1, Cell cell2, Cell cell3){
        return flag.get(new Coord(x, y)) == cell1 &&
                flag.get(new Coord(x + 1, y)) == cell2 &&
                flag.get(new Coord(x + 2, y)) == cell3;
    }

    private boolean isThreeHorizontalClosed(int x, int y, boolean top){
        if (top){
            return flag.get(new Coord(x - 1, y)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 3, y)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 3, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x - 1, y - 1)) != Cell.CLOSEDCELL;
        }else return flag.get(new Coord(x - 1, y)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x + 3, y)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x + 3, y + 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y + 1)) != Cell.CLOSEDCELL;
    }

    private boolean isThreeVertical(int x, int y, Cell cell1, Cell cell2, Cell cell3){
        return flag.get(new Coord(x, y)) == cell1 &&
                flag.get(new Coord(x, y + 1)) == cell2 &&
                flag.get(new Coord(x, y + 2)) == cell3;
    }

    private boolean isThreeVerticalClosed(int x, int y, boolean right){
        if (right){
            return flag.get(new Coord(x, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x, y + 3)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 1, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 1, y + 3)) != Cell.CLOSEDCELL;
        }else return flag.get(new Coord(x, y - 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x, y + 3)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y - 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y + 3)) != Cell.CLOSEDCELL;
    }

    private boolean isFourHorizontal(int x, int y, Cell cell1, Cell cell2, Cell cell3, Cell cell4){
        return flag.get(new Coord(x, y)) == cell1 &&
                flag.get(new Coord(x + 1, y)) == cell2 &&
                flag.get(new Coord(x + 2, y)) == cell3 &&
                flag.get(new Coord(x + 3, y)) == cell4;
    }

    private boolean isFourHorizontalClosed(int x, int y, boolean top){
        if (top){
            return flag.get(new Coord(x - 1, y)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 4, y)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 4, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x - 1, y - 1)) != Cell.CLOSEDCELL;
        }else return flag.get(new Coord(x - 1, y)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x + 4, y)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x + 4, y + 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y + 1)) != Cell.CLOSEDCELL;
    }

    private boolean isFourVertical(int x, int y, Cell cell1, Cell cell2, Cell cell3, Cell cell4){
        return flag.get(new Coord(x, y)) == cell1 &&
                flag.get(new Coord(x, y + 1)) == cell2 &&
                flag.get(new Coord(x, y + 2)) == cell3 &&
                flag.get(new Coord(x, y + 3)) == cell4;
    }

    private boolean isFourVerticalClosed(int x, int y, boolean right){
        if (right){
            return flag.get(new Coord(x, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x, y + 4)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 1, y - 1)) != Cell.CLOSEDCELL &&
                    flag.get(new Coord(x + 1, y + 4)) != Cell.CLOSEDCELL;
        }else return flag.get(new Coord(x, y - 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x, y + 4)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y - 1)) != Cell.CLOSEDCELL &&
                flag.get(new Coord(x - 1, y + 4)) != Cell.CLOSEDCELL;
    }
}
