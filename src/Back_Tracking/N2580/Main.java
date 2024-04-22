package Back_Tracking.N2580;
/*
    탐색 방식
    최초 입력의 0 에 해당하는 위치를 기억해둔다 List<Node>
    dfs{
        (n, m) 노드에서 큐브 9개와 가로줄 세로줄 각각 동시에 숫자를 본다: 9
        본 숫자중 등장하지 않은 임의의 숫자를 넣는다
        이를 총 81개에 대해 반복한다 : 81
    }
 */

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final static int MAX = 9, EMPTY = 0;
    static StringTokenizer st;
    static int[][] map = new int[MAX][MAX];
    static List<Node> emptyNodes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        for (int y = 0; y < MAX; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < MAX; x++) {
                map[y][x] = parseInt(st.nextToken());
                if (map[y][x] == EMPTY) {
                    emptyNodes.add(new Node(y, x));
                }
            }
        }
        dfs(0);
    }

    static void dfs(int idx) {
        if (idx == emptyNodes.size()) {
            printMap();
            System.exit(0);
        }

        for (int n = 1; n <= MAX; n++) {
            Node node = emptyNodes.get(idx);
            if (canUseNum(node, n)) {
                map[node.y][node.x] = n;
                dfs(idx + 1);
                map[node.y][node.x] = EMPTY;
            }
        }
    }
    private static void printMap() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                sb.append(map[i][j]).append(' ');
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }


    private static boolean canUseNum(Node node, int num) {
        for (int i = 0; i < MAX; i++) {
            if (map[i][node.x] == num ||
                map[node.y][i] == num ||
                map[((node.y / 3) * 3) + i / 3][((node.x / 3) * 3) + i % 3] == num
            )
                return false;
        }

        return true;
    }

}

class Node {

    int x, y;

    public Node(int y, int x) {
        this.x = x;
        this.y = y;
    }
}
