package LocalUtill;

import java.util.function.Function;

public interface PrintHelper {
    interface Map {
        interface Number{
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
            public static void print(int[][] map, int yLimit, int xLimit, Function<Integer, ?> convert) {
                StringBuilder print = new StringBuilder();
                print.append(" \\  X").append('\n').append(" Y").append('\n');
                for (int y = 0; y < yLimit; y++) {
                    print.append("[").append(y).append("] ");
                    for (int x = 0; x < xLimit; x++) {
                        print.append(convert.apply(map[y][x])).append(' ');
                    }
                    print.append('\n');
                }
                System.out.println(print);
            }
        }
        interface Boolean {
            public static void print(boolean[][] map, int yLimit, int xLimit) {
                StringBuilder print = new StringBuilder();
                print.append(" \\  X").append('\n').append(" Y").append('\n');
                for (int y = 0; y < yLimit; y++) {
                    print.append("[").append(y).append("] ");
                    for (int x = 0; x < xLimit; x++) {
                        print.append(map[y][x] ? 'T' : 'F').append(' ');
                    }
                    print.append('\n');
                }
                System.out.println(print);
            }

        } 
    }
}