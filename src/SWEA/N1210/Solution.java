package SWEA.N1210;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean[][] ladder;
    static int SIZE_MAX = 100;
    static StringBuilder result = new StringBuilder();
    static char LADDER = '1', START = '2';
    static Queue<Node> q;
    static int[] xOffset = {-1, 1, 0},
            yOffset = {0, 0, -1};

    public static void main(String[] args) throws IOException {
        
        for (int testCase = 1; testCase <= 10; testCase++) {
            br.readLine();
            result.append('#').append(testCase).append(' ');

            ladder = new boolean[SIZE_MAX][SIZE_MAX];
            q = new ArrayDeque<>();
            for (int y = 0; y < SIZE_MAX; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < SIZE_MAX; x++) {
                    char ladderData = st.nextToken().charAt(0);
                    if (ladderData == LADDER) {
                        ladder[y][x] = true;
                    } else if (ladderData == START) {
                        q.offer(new Node(SIZE_MAX - 1, x));
                    }
                }
            }
            while (!q.isEmpty()) {
                Node poll = q.poll();
                if (poll.y == 0) {
                    result.append(poll.x).append('\n');
                    break;
                }

                for (int i = 0; i < 3; i++) {
                    int ny = poll.y + yOffset[i];
                    int nx = poll.x + xOffset[i];

                    if (!isValid(ny, nx) || !ladder[ny][nx]) {
                        continue;
                    }

                    q.offer(new Node(ny, nx));
                    ladder[ny][nx] = false;
                    break;
                }
            }
        }
        System.out.println(result);
    }

    public static boolean isValid(int y, int x) {
        return y >= 0 && y < SIZE_MAX && x >= 0 && x < SIZE_MAX;
    }
}

class Node {

    int y, x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

}
