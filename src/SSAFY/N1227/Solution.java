package SSAFY.N1227;

/**
 * 양방향 BFS(Two-way BFS)를 이용한 미로 탐색 솔루션
 * 시작점과 도착점에서 동시에 탐색을 시작하여, 중간에서 만나는 지점을 찾습니다.
 */
import java.io.*;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, MAX_SIZE = 100, FIND = 1, CANT_FIND = 0;

    static Node START, END;
    static char[][] map = new char[MAX_SIZE][MAX_SIZE];
    // 시작점 탐색용 큐, 도착점 탐색용 큐
    static Queue<Node> startQ = new ArrayDeque<>(),
            endQ = new ArrayDeque<>();
    static char[] row;
    static char START_SIMBOL = '2', END_SIMBOL = '3',
            WALL_SIMBOL = '1';
    static int[] mv = {-1, 0, 1, 0, -1}; // 상하좌우 이동을 위한 배열

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1227/input"));
        br = new BufferedReader(new InputStreamReader(System.in));
        
        T = 10;
        for (int tc = 1; tc <= T; tc++) {
            br.readLine().trim(); // 테스트 케이스 번호 읽기
            result.append('#').append(tc).append(' ');

            init();
            result.append(twoWayBfs()).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 양방향 BFS를 이용한 경로 탐색
     * @return 경로 존재 시 FIND, 존재하지 않을 시 CANT_FIND
     */
    static int twoWayBfs() {
        while(!startQ.isEmpty() && !endQ.isEmpty()){
            Node startNode = startQ.poll();
            Node endNode = endQ.poll();

            for (int mvIdx = 0; mvIdx < 4; mvIdx++) {
                int yOffset = mv[mvIdx];
                int xOffset = mv[mvIdx+1];
                int sny = startNode.y + yOffset;
                int snx = startNode.x + xOffset;

                int eny = endNode.y + yOffset;
                int enx = endNode.x + xOffset;

                // 시작점 탐색
                if(isValid(sny, snx) && map[sny][snx] != START_SIMBOL){
                    if(map[sny][snx] == END_SIMBOL){
                        return FIND;
                    }
                    map[sny][snx] = START_SIMBOL;
                    startQ.offer(new Node(sny, snx));
                }

                // 도착점 탐색
                if(isValid(eny, enx) && map[eny][enx] != END_SIMBOL){
                    if(map[eny][enx] == START_SIMBOL){
                        return FIND;
                    }
                    map[eny][enx] = END_SIMBOL;
                    endQ.offer(new Node(eny, enx));
                }
            }
        }
        return CANT_FIND;
    }

    /**
     * 주어진 좌표가 유효한 미로 범위 내에 있고 벽이 아닌지 확인
     * @param y 확인할 행 인덱스
     * @param x 확인할 열 인덱스
     * @return 유효한 좌표 및 벽이 아닐 경우 true
     */
    static boolean isValid(int y, int x){
        return y >= 0 && y < MAX_SIZE && x >= 0 && x < MAX_SIZE &&
        map[y][x] != WALL_SIMBOL;
    }

    /**
     * 테스트 케이스 초기화
     * @throws IOException 입출력 오류 발생 시
     */
    static void init() throws IOException {
        startQ.clear();
        endQ.clear();

        boolean findEnd, findStart;
        findEnd = findStart = false;
        for (int y = 0; y < MAX_SIZE; y++) {
            row = br.readLine().trim().toCharArray();
            map[y] = row;
            for (int x = 0; !(findEnd && findStart) && (x < MAX_SIZE); x++) {
                if (!findEnd && map[y][x] == END_SIMBOL) {
                    findEnd = true;
                    END = new Node(y, x);
                    endQ.offer(END);
                }
                if (!findStart && map[y][x] == START_SIMBOL) {
                    findStart = true;
                    START = new Node(y, x);
                    startQ.offer(START);
                }
            }
        }
    }

    /**
     * BFS 탐색을 위한 노드 클래스
     */
    static class Node {

        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
