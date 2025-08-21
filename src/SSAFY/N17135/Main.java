package SSAFY.N17135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M, D, ENEMY = 1, MAX = 0, ROUND = 2, NON = 0;
    static int[][] enemyMap;
    static boolean[] isArcher;
    
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        D = parseInt(st.nextToken());
        enemyMap = new int[N][M];
        isArcher = new boolean[M];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                int i = parseInt(st.nextToken());
                if(i == ENEMY)
                    enemyMap[n][m] = ENEMY;
            }
        }

        // M개의 열 중 3개를 선택하는 조합
        for (int i = 0; i < M; i++) {
            for (int j = i + 1; j < M; j++) {
                for (int k = j + 1; k < M; k++) {
                    isArcher[i] = true;
                    isArcher[j] = true;
                    isArcher[k] = true;

                    MAX = Math.max(MAX, blitzkrieg());
                    ROUND++;

                    isArcher[i] = false;
                    isArcher[j] = false;
                    isArcher[k] = false;
                }
            }
        }
        System.out.println(MAX);
    }

    /**
     * 궁수 배치가 완료된 후 시뮬레이션을 실행
     * @return 해당 배치에서 제거한 총 적의 수
     */
    private static int blitzkrieg() {
        int archerY = N, killAmount = 0;

        while (archerY > 0) {
            killAmount += getDownTango(archerY);
            archerY--;
        }
        return killAmount;
    }

    /**
     * 한 라운드 동안 적을 제거하는 메서드
     * @param archerY 궁수의 행 위치
     * @return 이번 라운드에 제거한 적의 수
     */
    private static int getDownTango(int archerY) {
        Set<Node> set = new HashSet<>();
        for (int archerX = 0; archerX < M; archerX++) {
            if (isArcher[archerX]){
                Node node = pickTango(archerY, archerX);
                if (node != null) {
                    set.add(node);
                }
            }
        }
        for (Node node : set) {
            enemyMap[node.y][node.x] = ROUND;
        }
        return set.size();
    }

    /**
     * 가장 가까운 적을 선택하는 메서드
     * @param archerY 궁수의 행 위치
     * @param archerX 궁수의 열 위치
     * @return 선택된 적의 Node 객체, 없으면 null
     */
    private static Node pickTango(int archerY, int archerX) {
        int minDistance = Integer.MAX_VALUE;
        Node result = null;

        for (int tangoY = archerY-1; tangoY >= archerY-D; tangoY--) {
            for (int tangoX = 0; tangoX < M; tangoX++) {
                if (isValid(tangoY, tangoX) && isUnderReach(archerY, archerX, tangoY, tangoX) && isAlive(tangoY, tangoX)){
                    int distance = getDistance(archerY, archerX, tangoY, tangoX);
                    if (distance < minDistance) {
                        minDistance = distance;
                        result = new Node(tangoY, tangoX);
                    } else if (distance == minDistance) {
                        if (result.x > tangoX)
                            result = new Node(tangoY, tangoX);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 적이 살아있는지 확인
     * @param i 적의 행 위치
     * @param j 적의 열 위치
     * @return 살아있으면 true, 아니면 false
     */
    private static boolean isAlive(int i, int j) {
        return enemyMap[i][j] != NON && enemyMap[i][j] != ROUND;
    }

    /**
     * 적이 사정거리 안에 있는지 확인
     * @param y 궁수의 y 위치
     * @param x 궁수의 x 위치
     * @param i 적의 y 위치
     * @param j 적의 x 위치
     * @return 사정거리 내에 있으면 true, 아니면 false
     */
    private static boolean isUnderReach(int y, int x, int i, int j) {
        return D >= getDistance(y, x, i, j);
    }

    /**
     * 두 지점 사이의 맨해튼 거리를 계산
     * @param y1 첫 번째 지점 y
     * @param x1 첫 번째 지점 x
     * @param y2 두 번째 지점 y
     * @param x2 두 번째 지점 x
     * @return 맨해튼 거리
     */
    private static int getDistance(int y1, int x1, int y2, int x2) {
        return Math.abs(y1 - y2) + Math.abs(x1 - x2);
    }

    /**
     * 좌표가 유효한 맵 범위 내에 있는지 확인
     * @param i 확인할 행 인덱스
     * @param j 확인할 열 인덱스
     * @return 유효한 범위면 true, 아니면 false
     */
    private static boolean isValid(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < M;
    }
}

/**
 * 좌표를 저장하는 클래스
 */
class Node {
    int y, x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Node node = (Node) o;
        return y == node.y && x == node.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return "{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
