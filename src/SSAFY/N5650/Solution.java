package SSAFY.N5650;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static final int MAX_HOLE = 11;
    static final int BLACK_HOLE = -1, SPACE = 0;
    static final int U = 0, R = 1, D = 2, L = 3; // 방향 상수 재정의 (0-based)
    static final int[] dy = {-1, 0, 1, 0}; // 상, 우, 하, 좌
    static final int[] dx = {0, 1, 0, -1};

    static int T, N, Y, X;
    static int[][] map;
    static Node[][] wormHole;
    static boolean[][][] visited; // [dir][y][x]

    static {
        X = Y = N = 100;
        map = new int[Y + 2][X + 2];
        visited = new boolean[4][Y + 2][X + 2];
        wormHole = new Node[2][MAX_HOLE];
    }

    public static void main(String[] args) throws IOException {
        // System.setIn(new FileInputStream("sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            result.append(playPinball()).append('\n');
        }
        System.out.println(result);
    }

    static void init() throws NumberFormatException, IOException {
        X = Y = N = parseInt(br.readLine().trim());
        
        // 웜홀 페어링을 위한 초기화
        for(int i=6; i<MAX_HOLE; i++) wormHole[0][i] = wormHole[1][i] = null;

        // 맵 테두리를 5번 블록으로 감싸기
        for(int i=0; i < N+2; i++) {
            map[0][i] = map[N+1][i] = map[i][0] = map[i][N+1] = 5;
        }

        for (int y = 1; y <= Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 1; x <= X; x++) {
                map[y][x] = parseInt(st.nextToken());
                if (map[y][x] >= 6) {
                    setWormHole(y, x);
                }
            }
        }
    }
    
    /**
     * 웜홀 쌍 기록
     */
    static void setWormHole(int y, int x) {
        int holeNum = map[y][x];
        if (wormHole[0][holeNum] == null) {
            wormHole[0][holeNum] = new Node(y, x);
        } else {
            wormHole[1][holeNum] = new Node(y, x);
        }
    }
    
    /**
     * 모든 시작점에서 핀볼 게임 시작
     */
    static int playPinball() {
        int maxScore = 0;
        for (int y = 1; y <= Y; y++) {
            for (int x = 1; x <= X; x++) {
                if (map[y][x] == SPACE) {
                    for (int d = 0; d < 4; d++) {
                        maxScore = Math.max(maxScore, playPinball(new Ball(y, x, d)));
                    }
                }
            }
        }
        return maxScore;
    }

    /**
     * 단일 핀볼 게임 시뮬레이션
     */
    static int playPinball(Ball startBall) {
        int score = 0;
        int y = startBall.y;
        int x = startBall.x;
        int dir = startBall.dir;

        // 이동 시작
        y += dy[dir];
        x += dx[dir];

        while (true) {
            // 시작점으로 돌아오거나 블랙홀을 만나면 종료
            if ((y == startBall.y && x == startBall.x) || map[y][x] == BLACK_HOLE) {
                return score;
            }

            int symbol = map[y][x];

            // 블록 처리 (벽 포함)
            if (symbol >= 1 && symbol <= 5) {
                score++;
                dir = getNextDirBy(symbol, dir);
            }
            // 웜홀 처리
            else if (symbol >= 6) {
                Node otherSide = getWormHole(symbol, y, x);
                y = otherSide.y;
                x = otherSide.x;
            }
            
            // 다음 칸으로 이동
            y += dy[dir];
            x += dx[dir];
        }
    }

    /**
     * 블록에 따른 다음 방향 반환
     */
    static int getNextDirBy(int symbol, int insertDir) {
        // 상(0) <-> 하(2), 우(1) <-> 좌(3)
        if (symbol == 5) return (insertDir + 2) % 4;
        
        switch (symbol) {
            case 1:
                if (insertDir == D) return R;
                if (insertDir == L) return U;
                break;
            case 2:
                if (insertDir == U) return R;
                if (insertDir == L) return D;
                break;
            case 3:
                if (insertDir == U) return L;
                if (insertDir == R) return D;
                break;
            case 4:
                if (insertDir == D) return L;
                if (insertDir == R) return U;
                break;
        }
        return (insertDir + 2) % 4; // 나머지 경우는 반사
    }

    /**
     * 현재 웜홀 위치를 받아 반대편 웜홀 좌표 반환
     */
    static Node getWormHole(int symbol, int y, int x) {
        if (wormHole[0][symbol].y == y && wormHole[0][symbol].x == x) {
            return wormHole[1][symbol];
        }
        return wormHole[0][symbol];
    }

    static class Node {
        int y, x;
        public Node(int y, int x) { this.y = y; this.x = x; }
    }

    static class Ball extends Node {
        int dir;
        public Ball(int y, int x, int dir) {
            super(y, x);
            this.dir = dir;
        }
    }
}