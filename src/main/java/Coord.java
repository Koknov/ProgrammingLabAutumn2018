import java.util.Random;

public class Coord {

    int x;
    int y;
    private static Random random = new Random();

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Coord randomCoord(){
        return new Coord(random.nextInt(Settings.getCols()), random.nextInt(Settings.getCols()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord coord = (Coord) obj;
            return coord.x == x && coord.y == y;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
