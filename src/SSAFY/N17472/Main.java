package SSAFY.N17472;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 땅 별로 int로 구별 좀 하고
 * 땅의 edge 들을 구별하고
 * 땅 별로 이을 수 있는 다리를 모두 구하고
 * 각 땅의 edged에서 직선으로 그어서 땅 찾기
 * 해당 다리를 기준으로 또 DFS
 * map 100
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static final int OCEAN_START_IDX = -2, GROUND_START_IDX = -1;
    static final int[] move = { -1, 0, 1, 0, -1 };

    static int N, Y, M, X, GROUND, OCEAN;

    static int[][] map;

    static List<Map<Integer, Integer>> bridges;
    static List<ArrayDeque<Node>> edges;
    static Queue<Node> groundList, innerGroundSearchQ;
    static PriorityQueue<Bridge> pq;

    static {
        GROUND = 1;
        OCEAN = 0;
        br = new BufferedReader(new InputStreamReader(System.in));
        bridges = new ArrayList<>();
        edges = new ArrayList<>();
        groundList = new ArrayDeque<>();
        innerGroundSearchQ = new ArrayDeque<>();
        pq = new PriorityQueue<>((Comparator.comparingInt(o -> o.v)));
    }

    public static void main(String[] args) throws IOException {

        init();
        int groundSize = getGroundSize();
        makeBridge();
        int minBridgeLen = connectBridge(groundSize);
        System.out.println(minBridgeLen);
    }

    static int getGroundSize() {
        int groundSize = 0;
        while (!groundList.isEmpty()) {
            Node groundStartPos = groundList.poll();
            if (groundStartPos.getVal() != GROUND)
                continue;

            bridges.add(new HashMap<>());
            edges.add(new ArrayDeque<>());

            innerGroundSearchQ.offer(groundStartPos);
            map[groundStartPos.y][groundStartPos.x] = groundSize;

            while (!innerGroundSearchQ.isEmpty()) {
                Node cur = innerGroundSearchQ.poll();

                for (int i = 0; i < 4; i++) {
                    int ny = cur.y + move[i];
                    int nx = cur.x + move[i + 1];
                    if (isValid(ny, nx)) {
                        if (map[ny][nx] == GROUND) {
                            map[ny][nx] = groundSize;
                            innerGroundSearchQ.offer(new Node(ny, nx));
                        } else if (map[ny][nx] == OCEAN) {
                            edges.get(groundSize).add(new Node(cur.y, cur.x));
                        }
                    }
                }
            }
            groundSize++;
        }
        return groundSize;
    }

    /**
     * 다리 이어주기 프림
     */
    static int connectBridge(int groundSize) {
        
        pq.clear();
        boolean[] visited = new boolean[groundSize];
        int minBridgeLen = 0;

        pq.offer(new Bridge(0, 0));
        while (!pq.isEmpty()) {
            Bridge curBridge = pq.poll();
            if (visited[curBridge.y])
                continue;
            visited[curBridge.y] = true;
            minBridgeLen += curBridge.v;

            Map<Integer, Integer> gList = bridges.get(curBridge.y);
            for (int i : gList.keySet()) {
                if (gList.get(i) != 0 && !visited[i]) {
                    pq.offer(new Bridge(i, gList.get(i)));
                }
            }
        }

        for (boolean isVisited : visited) {
            if (!isVisited) {
                minBridgeLen = -1;
                break;
            }
        }

        return minBridgeLen;
    }

    /**
     * 다리 짓기, 같은 섬 이라면 가장 짧은 거리로, a->b b->a 의 최소값도 구해야하나?
     */
    static void makeBridge() {
        ArrayDeque<Node> edgeQ;
        for (int gy = 0; gy < edges.size(); gy++) {
            edgeQ = edges.get(gy);
            while (!edgeQ.isEmpty()) {
                Node curEdge = edgeQ.poll();

                for (int dir = 0; dir < 4; dir++) {
                    int t = 1;
                    int ny = curEdge.y + move[dir] * t;
                    int nx = curEdge.x + move[dir + 1] * t;
                    while (isValid(ny, nx) && map[ny][nx] == OCEAN) {
                        t++;
                        ny = curEdge.y + move[dir] * t;
                        nx = curEdge.x + move[dir + 1] * t;
                    }
                    if (!isValid(ny, nx)) {
                        continue;
                    }
                    if (map[ny][nx] != gy) {
                        if (t > 2) {
                            int shortestBridge = bridges.get(gy)
                                    .getOrDefault(map[ny][nx], Integer.MAX_VALUE);
                            bridges.get(gy).put(map[ny][nx], Math.min(shortestBridge, t - 1));
                        }
                    }

                }
            }
        }
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        Y = N = parseInt(st.nextToken());
        X = M = parseInt(st.nextToken());
        map = new int[Y][X];

        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < X; x++) {
                int symbol = parseInt(st.nextToken());
                if (symbol == OCEAN) {
                    map[y][x] = OCEAN_START_IDX;
                } else if (symbol == GROUND) {
                    map[y][x] = GROUND_START_IDX;
                    groundList.offer(new Node(y, x));
                }
            }
        }
        OCEAN = OCEAN_START_IDX;
        GROUND = GROUND_START_IDX;
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    static class Node {

        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getVal() {
            return map[this.y][this.x];
        }

    }

    static class Bridge {
        int y, v;

        public Bridge(int y, int v) {
            this.y = y;
            this.v = v;
        }
    }
}
