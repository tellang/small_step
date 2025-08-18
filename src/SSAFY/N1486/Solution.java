package SSAFY.N1486;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * N개의 점원 높이로 탑을 쌓아, 선반 높이 B 이상이 되는 탑 중 가장 낮은 탑을 찾는 솔루션 클래스
 * 모든 부분집합을 고려하는 백트래킹 방식을 사용
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder result = new StringBuilder();

    static int T, N, B, TargetHeight, HEIGHTS_IDX_LIMIT, MIN_TARGET_SUM;
    static int[] heights;

    public static void main(String[] args) throws IOException {
        //System.setIn(new FileInputStream("src/SSAFY/N1486/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            
            st = new StringTokenizer(br.readLine());
            N = parseInt(st.nextToken());
            B = parseInt(st.nextToken());
            TargetHeight = B;
            HEIGHTS_IDX_LIMIT = N;
            heights = new int[HEIGHTS_IDX_LIMIT];

            MIN_TARGET_SUM = Integer.MAX_VALUE;

            st = new StringTokenizer(br.readLine());
            for (int heightIdx = 0; heightIdx < HEIGHTS_IDX_LIMIT; heightIdx++) {
                heights[heightIdx] = parseInt(st.nextToken());
            }

            backTracking(0, 0);

            result.append(MIN_TARGET_SUM - TargetHeight).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 점원들을 탑에 포함하거나 포함하지 않는 모든 경우를 탐색
     * @param heightsIdx 현재 탐색할 점원의 인덱스
     * @param localHeights 현재까지 쌓은 탑의 높이
     */
    static void backTracking(int heightsIdx, int localHeights) {
        // 이미 최소 높이를 초과했다면, 더 이상 탐색할 필요 없음
        if (localHeights >= MIN_TARGET_SUM) {
            return;
        }

        // 모든 점원을 탐색했을 경우
        if (heightsIdx >= HEIGHTS_IDX_LIMIT) {
            // 탑의 높이가 목표 높이 B 이상인 경우에만 최소값 갱신
            if (localHeights >= TargetHeight) {
                MIN_TARGET_SUM = Math.min(MIN_TARGET_SUM, localHeights);
            }
            return;
        }
        
        // 현재 점원을 탑에 포함하는 경우
        backTracking(heightsIdx + 1, localHeights + heights[heightsIdx]);
        // 현재 점원을 탑에 포함하지 않는 경우
        backTracking(heightsIdx + 1, localHeights);
    }
}
