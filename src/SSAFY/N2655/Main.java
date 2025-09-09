package SSAFY.N2655;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * DP
 * 
 * width 내림차순 정렬
 * - heightOfTopIdx[i] = i번째를 맨 위로 했을 때 최대 높이
 * - preBrickIdx[i] = heightOfTopIdx[i]를 만들 때 그 아래에 있던 벽돌 인덱스
 * - heightOfTopIdx 최댓값 위치에서 preBrickIdx 따라가며 경로 복원
 *
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int N, MAX_BRICK;
    static Brick[] bricks;
    static int[] heightOfTopIdx, preBrickIdx;

    static ArrayDeque<Integer> stack;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        stacking();
        System.out.print(result);
    }

    static void init() throws IOException {
        MAX_BRICK = N = parseInt(br.readLine().trim());
        bricks = new Brick[MAX_BRICK + 1];
        heightOfTopIdx = new int[MAX_BRICK + 1];
        preBrickIdx = new int[MAX_BRICK + 1];
        stack = new ArrayDeque<>();

        for (int bIdx = 1; bIdx <= MAX_BRICK; bIdx++) {
            st = new StringTokenizer(br.readLine());
            int w = parseInt(st.nextToken());
            int h = parseInt(st.nextToken());
            int m = parseInt(st.nextToken());
            bricks[bIdx] = new Brick(bIdx, w, h, m);
        }

        Arrays.sort(bricks, 1, MAX_BRICK + 1, Comparator.comparingInt(b -> -b.width));
    }

    /**
     * 탑쌓기 로직
     */
    static void stacking() {
        int highestIdx = 1;
        for (int bIdx = 1; bIdx <= MAX_BRICK; bIdx++) {
            heightOfTopIdx[bIdx] = bricks[bIdx].height;
            preBrickIdx[bIdx] = 0;

            for (int pIdx = 1; pIdx < bIdx; pIdx++) {
                if (bricks[pIdx].weight > bricks[bIdx].weight) {
                    if (heightOfTopIdx[pIdx] + bricks[bIdx].height > heightOfTopIdx[bIdx]) {
                        heightOfTopIdx[bIdx] = heightOfTopIdx[pIdx] + bricks[bIdx].height;
                        preBrickIdx[bIdx] = pIdx;
                    }
                }
            }
            if (heightOfTopIdx[bIdx] > heightOfTopIdx[highestIdx])
                highestIdx = bIdx;
        }

        printResult(highestIdx);
    }

    /**
     * 역탐색 출력
     * @param highestIdx
     */
    static void printResult(int highestIdx) {
        while (highestIdx != 0) {
            stack.push(bricks[highestIdx].idx);
            highestIdx = preBrickIdx[highestIdx];
        }

        result.append(stack.size()).append('\n');
        while (!stack.isEmpty()) {
            result.append(stack.pollLast()).append('\n');
        }
    }

    static class Brick {
        int idx, width, height, weight;

        Brick(int idx, int width, int height, int weight) {
            this.idx = idx;
            this.width = width;
            this.height = height;
            this.weight = weight;
        }
    }
}
