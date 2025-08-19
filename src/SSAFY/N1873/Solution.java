package SSAFY.N1873;

/**
 * 전차는 하나 전차 위는 평지이다
 *
 *
 * 20x20 - 물론 벽돌을 선형 탐색으로 찾아도 된다 n =20 - 근데 바로 찾을 수 는 없을까 - 어짜피 맵을 y, x 인덱스가
 * 증가하는 방향으로 담는다 - 이진 탐색을 쓸 수 있지 않을까? - 근데 너무 복잡하니 쓰지말자 - 한다면 좌표단축일텐데 20 20에서 왜?
 *
 * --- 무식하게 풀기 --- 전차는 GO 가 아니면 못간다 BRICK, IRON 이 나올때 까지 쏜다 - BRICK 이면 GO로 바꾼다
 *
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, H, W, Y, X, tXpos, tYpos,
            U = 0, D = 1, L = 2, R = 3;

    static char GO = '.', BRICK = '*', IRON = '#',
            UP = '^', DOWN = 'v', LEFT = '<', RIGHT = '>', TANK_AIM,
            SHOOT = 'S';
    static int[] dy = {-1, 1, 0, 0},
            dx = {0, 0, -1, 1}; //UDLR
    static char[][] map;
    static char[] input;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1873/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            st = new StringTokenizer(readLine());
            Y = H = parseInt(st.nextToken());
            X = W = parseInt(st.nextToken());

            map = new char[Y][X];

            for (int y = 0; y < Y; y++) {
                map[y] = readLine().toCharArray();
                for (int x = 0; x < X; x++) {
                    if (isTank(y, x)) {
                        tYpos = y;
                        tXpos = x;
                        TANK_AIM = map[y][x];
                        map[y][x] = GO;
                    }
                }
            }

            N = parseInt(readLine());
            input = new char[N];
            input = readLine().toCharArray();
            result.append(playGame());
        }
        System.out.println(result);

    }

    static StringBuilder playGame() {
        for (char order : input) {
            if (order == SHOOT) {
                shoot();
            } else {
                move(order);
            }
        }

        return printMap();
    }

    static void move(char order) {
        TANK_AIM = getAimByOrder(order);
        if (canGo()) {
            int aim = getTankAimIdx();
            tYpos += dy[aim];
            tXpos += dx[aim];
        }
    }

    static char getAimByOrder(char order) {
        switch (order) {
            case 'U':
                return UP;
            case 'D':
                return DOWN;
            case 'L':
                return LEFT;
            case 'R':
                return RIGHT;
            default:
                System.err.println("WRONG ORDER: "+order);
                return '0';
        }
    }

    static boolean canGo() {
        int aim = getTankAimIdx();
        int y = tYpos + dy[aim];
        int x = tXpos + dx[aim];
        return isValid(y, x)
                && map[y][x] == GO;
    }

    static void shoot() {
        int aim = getTankAimIdx();
        int ny = tYpos;
        int nx = tXpos;
        while (true) {
            ny += dy[aim];
            nx += dx[aim];

            if (!isValid(ny, nx)) {
                break;
            }

            if (map[ny][nx] == BRICK) {
                map[ny][nx] = GO;
                break;
            } else if (map[ny][nx] == IRON) {
                break;
            }
        }
    }

    static StringBuilder printMap() {
        StringBuilder mapPrint = new StringBuilder();
        map[tYpos][tXpos] = TANK_AIM;
        for (char[] row : map) {
            for (char node : row) {
                mapPrint.append(node);
            }
            mapPrint.append('\n');
        }
        return mapPrint;
    }

    static int getTankAimIdx() {
        if (TANK_AIM == UP) {
            return U;
        } else if (TANK_AIM == DOWN) {
            return D;
        } else if (TANK_AIM == LEFT) {
            return L;
        } else if (TANK_AIM == RIGHT) {
            return R;
        } else {
            System.err.println("WORNG AIM");
            return -1;
        }
    }

    static boolean isTank(int y, int x) {
        char node = map[y][x];
        return (node == UP)
                || (node == DOWN)
                || (node == LEFT)
                || (node == RIGHT);
    }

    static String readLine() throws IOException {
        return br.readLine().trim();
    }

    static boolean isValid(int y, int x) {
        return x >= 0 && x < X && y >= 0 && y < Y;
    }
}
