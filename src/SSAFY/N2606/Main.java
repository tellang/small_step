package SSAFY.N2606;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 완탐
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    // 1based
    static boolean[] visited;
    static boolean[][] map;
    static int N, MAX_CPU, M, MAX_EDGE;
    static Queue<Integer> q;

    static {
        q = new ArrayDeque<>();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/RELATIVE_FILE_PATH/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        int count = getCount(1);
        System.out.println(count);
    }

    static void init() throws NumberFormatException, IOException {
        N = MAX_CPU = parseInt(br.readLine().trim());
        M = MAX_EDGE = parseInt(br.readLine().trim());

        visited = new boolean[MAX_CPU + 1];
        map = new boolean[MAX_CPU + 1][MAX_CPU + 1];

        for (int e = 0; e < MAX_EDGE; e++) {
            st = new StringTokenizer(br.readLine().trim());
            int left = parseInt(st.nextToken());
            int right = parseInt(st.nextToken());
            map[left][right] = true;
            map[right][left] = true;
        }
    }

    /**
     * 감염 수 반환
     * 
     * @param startIdx
     * @return
     */
    static int getCount(int startIdx) {
        int count = -1;

        q.offer(startIdx);

        /*
         * 감염 bfs
         */
        while (!q.isEmpty()) {
            int tango = q.poll();

            if (visited[tango])
                continue;

            visited[tango] = true;
            count++;
            for (int c = 1; c <= MAX_CPU; c++) {
                if (!map[tango][c])
                    continue;

                q.offer(c);
            }

        }

        return count;
    }
}