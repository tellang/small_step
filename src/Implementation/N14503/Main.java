package Implementation.N14503;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3,
    WALL = 1, UNCLEANED = 0, CLEANED = 2, CURSOR, Y, X, CLEAN_COUNTER = 0;
    static int[] moveY = {-1, 0, 1, 0},
    moveX = {0, 1, 0, -1};
    static int[][] map;
    static boolean CAN_MOVE = true, IS_ALL_CLEARED = true;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        Y = parseInt(st.nextToken());
        X = parseInt(st.nextToken());
        CURSOR = parseInt(st.nextToken());
        map = new int[N][M];

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                map[n][m] = parseInt(st.nextToken());
            }
        }


        while (CAN_MOVE){
            if (map[Y][X] == UNCLEANED){
                map[Y][X] = CLEANED;
                CLEAN_COUNTER++;
            }
            IS_ALL_CLEARED = true;
            for (int i = 0; i < 4; i++) {
                int nc = rotateLeft();
                int ny = Y + moveY[nc];
                int nx = X + moveX[nc];
                CURSOR = nc;
                if (isValid(ny, nx) && map[ny][nx] != WALL) {
                    if (map[ny][nx] == UNCLEANED){
                        IS_ALL_CLEARED = false;
                        Y = ny;
                        X = nx;
                        break;
                    }
                }
            }
            if (IS_ALL_CLEARED){
                if (!canGoBack()){
                    CAN_MOVE = false;
                }
            }
        }
        System.out.println(CLEAN_COUNTER);
    }

    private static boolean canGoBack() {
        int backCursor = (CURSOR + 2) % 4;
        int ny = Y + moveY[backCursor];
        int nx = X + moveX[backCursor];
        if (isValid(ny, nx) && map[ny][nx] != WALL) {
            Y = ny;
            X = nx;
            return true;
        }
        return false;
    }

    private static boolean isValid(int y, int x){
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    private static int rotateLeft() {
        return (CURSOR + 3) % 4;
    }
}
/*
   0
3     1
   2
 */