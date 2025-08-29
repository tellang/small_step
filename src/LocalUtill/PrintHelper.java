package LocalUtill;

public interface PrintHelper {
    interface Map {
        public static void print(int[][] map, int yLimit, int xLimit) {
            StringBuilder print = new StringBuilder();
            print.append(" \\  X").append('\n').append(" Y").append('\n');
            for (int y = 0; y < yLimit; y++) {
                print.append("[").append(y).append("] ");
                for (int x = 0; x < xLimit; x++) {
                    print.append(map[y][x]).append(' ');
                }
                print.append('\n');
            }
            System.out.println(print);
        }
    }
}