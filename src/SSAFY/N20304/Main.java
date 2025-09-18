package SSAFY.N20304;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 입력받은 침투 번호로 부터 비트값을 하나씩 비틀어서
 * 계산
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    // 2^20 = 1048576, N < 1M

    static int N, MAX_PASSWORD, M, MAX_QUERY, MAX_BIT_IDX;

    static int[] usedList, differBitCounts;
    static Queue<Integer> q;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N20304/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        int maxDist = getDistance();
        System.out.println(maxDist);
    }

    /*
     * 하나씩 비트를 바꿔서
     * 차이를 늘려감
     */
    static int getDistance() {
        int localMax = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            int nd = differBitCounts[cur] + 1;
            for (int bIdx = 0; bIdx <= MAX_BIT_IDX; bIdx++) {
                int nextNum = cur ^ (1 << bIdx);
                if(nextNum > MAX_PASSWORD) continue;
                if (isVisited(nextNum))
                    continue;
                differBitCounts[nextNum] = nd;
                localMax = Math.max(localMax, nd);
                q.offer(nextNum);
            }
        }

        return localMax;
    }

    /*
     * 어짜피 간선 즉,
     * 비트 차이간의 가중치가 없으므로
     * 선방문 최적해
     */
    static boolean isVisited(int nextNum) {
        return differBitCounts[nextNum] >= 0;
    }

    static void init() throws NumberFormatException, IOException {
        N = MAX_PASSWORD = parseInt(br.readLine().trim());
        M = MAX_QUERY = parseInt(br.readLine().trim());
        usedList = new int[MAX_QUERY];
        differBitCounts = new int[MAX_PASSWORD + 1];
        q = new ArrayDeque<>();

        Arrays.fill(differBitCounts, -1);
        st = new StringTokenizer(br.readLine().trim());
        for (int qIdx = 0; qIdx < MAX_QUERY; qIdx++) {
            usedList[qIdx] = parseInt(st.nextToken());
            q.offer(usedList[qIdx]);
            differBitCounts[usedList[qIdx]] = 0;
        }
        MAX_BIT_IDX = 0;
        while ((1 << (MAX_BIT_IDX + 1)) <= MAX_PASSWORD)
            MAX_BIT_IDX++;
    }
}