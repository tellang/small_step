package Back_Tracking.N15683;

/*
직접 #을 넣어야하나
안넣고 계산할 수 있을까?

    안넣고 계산시 어려움
        * 시선이 겹치는 부분?
직접 #을 넣을때
    각 cctv 최대 8개
    각 4가지 방향 4

    * 모든 방향을 동시에 진행할 수 있을까?
        * 각각의 방향이 다른 시점에서 끝날텐데?
각각의 cctv 에 대해 방향을 돌리고 재귀 호출
* #을 넣으면서 사각지대를 계산해야 할 수 있나?
    * 있지 대신 WALL 갯수도 세어야 하지
 */
import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, WALL_COUNT, BLIND_SPOT, MAX_SIGHT;
    static int [][] map;
    static int [] way = {-1, 0, 1, 0, -1, 0, 1, 0};
    final static int SEEN = -1, NULL = 0,
        ONE = 1, HORIZON = 2, ANGLE = 3, TRIPLE = 4, ALL = 5, WALL = 6;
    static List<Node> CCTVList;
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        map = new int[N][M];

        CCTVList = new ArrayList<>();
        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                map[y][x] = parseInt(st.nextToken());
                if (map[y][x] == NULL) {
                    BLIND_SPOT++;
                }
                else if (map[y][x] == WALL) {
                    WALL_COUNT++;
                }
                else
                    CCTVList.add(new Node(y, x));
            }
        }
        recur(0, 0);
        System.out.println(BLIND_SPOT - MAX_SIGHT);
    }

    private static void recur(int CCTVIndex, int localMaxSight) {
        if (CCTVIndex == CCTVList.size()) {
            MAX_SIGHT = max(MAX_SIGHT, localMaxSight);
            return;
        }
        Node cur = CCTVList.get(CCTVIndex);

        for (int i = 0; i < 4; i++) { //rotate CCTV
            int presentCCTVSight = getCCTVSight(cur, i, SEEN);
            recur(CCTVIndex+1, localMaxSight + presentCCTVSight);
            getCCTVSight(cur, i, -SEEN);
        }
    }

    private static int getCCTVSight(Node pos, int wayIndex, int seen) {
        int result = 0;
        if (map[pos.y][pos.x] == ONE) {
            result += sightCount(pos, wayIndex, seen);
        } else if (map[pos.y][pos.x] == HORIZON) {
            result += sightCount(pos, wayIndex, seen) +
            sightCount(pos, wayIndex + 2, seen);
        } else if (map[pos.y][pos.x] == ANGLE) {
            result += sightCount(pos, wayIndex, seen) +
            sightCount(pos, wayIndex + 1, seen);
        } else if (map[pos.y][pos.x] == TRIPLE) {
            result += sightCount(pos, wayIndex, seen) +
            sightCount(pos, wayIndex + 1, seen) +
            sightCount(pos, wayIndex + 2, seen);
        } else if (map[pos.y][pos.x] == ALL) {
            result += sightCount(pos, wayIndex, seen) +
            sightCount(pos, wayIndex + 1, seen) +
            sightCount(pos, wayIndex + 2, seen) +
            sightCount(pos, wayIndex + 3, seen);
        }
        return result;
    }

    private static int sightCount(Node pos, int wayIndex, int seen) {
        int yPos = pos.y + way[wayIndex];
        int xPos = pos.x + way[wayIndex + 1];
        int count = 0;
        while (isValidPos(yPos, xPos)) {
            if (map[yPos][xPos] == WALL) {
                break;
            }

            if (map[yPos][xPos] == NULL) {
                count++;
            }
            if (map[yPos][xPos] <= NULL) {
                map[yPos][xPos] += seen;
            }
            yPos += way[wayIndex];
            xPos += way[wayIndex + 1];
        }
        return count;
    }

    private static boolean isValidPos(int yOffset, int xOffset) {
        return yOffset < N && xOffset < M &&
            yOffset >= 0 && xOffset >= 0;
    }

}
class Node {
    int y, x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }
}