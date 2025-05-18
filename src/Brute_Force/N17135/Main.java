package Brute_Force.N17135;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (j == i)
                    continue;
                for (int k = 0; k < M; k++) {
                    if (k == i || k == j)
                        continue;
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

    private static int blitzkrieg() {
        int archerY = N, killAmount = 0;
//        System.out.println("\n======="+ROUND+"=======\n");

        while (archerY > 0) {
//            System.out.println("phase: " + archerY);
            killAmount += getDownTango(archerY);
            archerY--;
        }
//        System.out.println("\nresult: " + killAmount);
        return killAmount;
    }

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
//        System.out.print("{" +
//            "archerX=" + archerX +
//            ", archerY=" + archerY +
//            "} --> ");
//        System.out.println(result);
        return result;
    }

    private static boolean isAlive(int i, int j) {
        return enemyMap[i][j] != NON && enemyMap[i][j] != ROUND;
    }

    private static boolean isUnderReach(int y, int x, int i, int j) {
        return D >= getDistance(y, x, i, j);
    }

    private static int getDistance(int y, int x, int i, int j) {
        return Math.abs(y - i) + Math.abs(x - j);
    }

    private static boolean isValid(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < M;
    }
}
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
