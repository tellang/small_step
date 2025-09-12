package SSAFY.N2383;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 백트래킹
 * 
 * 사람 수 N = 10
 * 내려갈 계단 S = 2
 * 
 * 계단 위치, 사람 위치 겹치치 않음
 * 
 * 백트래킹으로 조합 뽑고
 * 시간 계산
 * - 이동 + 계단 길이
 * 최솟값
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static final int LIMIT_MAN = 10, SYMBOL_MAN = 1, SYMBOL_MIN_STAIR_LEN = 2;
    static final int[] offset;

    static int T, N, mIdx, sIdx, minTime;

    static BitSet firstStairMan; //
    static Node[] mans;
    static SNode[] stairs;
    static int[][] timeList;

    static {
        firstStairMan = new BitSet(LIMIT_MAN + 1);
        mans = new Node[LIMIT_MAN + 1];
        stairs = new SNode[2];
        timeList = new int[2][LIMIT_MAN + 1]; // [LIMIT_MAN] = 의 0 값을 offset 0, 1, 2에 활용

        offset = new int[LIMIT_MAN + 1];
        for (int i = 4; i <= LIMIT_MAN; i++) {
            offset[i] = i - 3;
        }

        Arrays.setAll(mans, m -> new Node(-1, -1));
        Arrays.setAll(stairs, s -> new SNode(-1, -1, -1));
    }

    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class SNode extends Node {
        int len;

        public SNode(int y, int x, int len) {
            super(y, x);
            this.len = len;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N2383/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            getExitTime(0);

            result.append(minTime).append('\n');
        }
        System.out.println(result);
    }

    static void init() throws NumberFormatException, IOException {
        N = parseInt(br.readLine().trim());
        mIdx = sIdx = 0;
        minTime = Integer.MAX_VALUE;
        firstStairMan.clear();

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < N; x++) {
                int symbol = parseInt(st.nextToken());
                if (symbol == SYMBOL_MAN) {
                    mans[mIdx].y = y;
                    mans[mIdx++].x = x;
                } else if (symbol >= SYMBOL_MIN_STAIR_LEN) {
                    stairs[sIdx].y = y;
                    stairs[sIdx].x = x;
                    stairs[sIdx++].len = symbol;
                }
            }
        }
    }

    /**
     * 백트래킹 형식의 탈출시간 조합 생성
     * 조합 완료시 가장 늦은 탈출시간을 반환
     * 
     * @param count
     * @return
     */
    static void getExitTime(int count) {
        if (count == mIdx) {
            minTime = Math.min(minTime, getMaxExitTime());
            return;
        }

        /*
         * 첫번째 계단을 가거나
         * 두번째 계단을 가거나
         */
        firstStairMan.set(count);
        getExitTime(count + 1);
        firstStairMan.clear(count);
        getExitTime(count + 1);

    }

    /**
     * firstStairMan.get 의 결과에 따른 계단 설정
     * 최대 계단을 내려갈 수 있는 사람은 3명
     * 정렬을 하고
     * n>3 번째 사람부터 
     * 대기시간과 n-3 번째 사람이 내려가서
     * 시간이 비는 경우를 추정
     * @return
     */
    static int getMaxExitTime() {
        
        int firstCount = firstStairMan.cardinality();
        int secondCount = mIdx - firstCount;
        int f = 1, s = 1;
        
        for (int m = 0; m < mIdx; m++) {
            if (firstStairMan.get(m)) {
                timeList[0][f++] = getManhattan(stairs[0], mans[m]);
            } else {
                timeList[1][s++] = getManhattan(stairs[1], mans[m]);
            }
        }

        if (firstCount > 0)
            Arrays.sort(timeList[0], 1, firstCount + 1);
        if (secondCount > 0)
            Arrays.sort(timeList[1], 1, secondCount + 1);


        for (int i = 1; i <= firstCount; i++) {
            int start = timeList[0][i] + 1; // 진입 +1
            // 대기시간
            if (i >= 4)
                start = Math.max(start, timeList[0][i - 3]);
            timeList[0][i] = start + stairs[0].len;
        }
        
        for (int i = 1; i <= secondCount; i++) {
            int start = timeList[1][i] + 1; // 진입 +1
            // 대기시간
            if (i >= 4)
                start = Math.max(start, timeList[1][i - 3]);
            timeList[1][i] = start + stairs[1].len;
        }

        
        return Math.max(timeList[0][firstCount], timeList[1][secondCount]);
    }

    /**
     * 맨해튼 거리로 계산
     * 
     * @param stair
     * @param node
     * @return
     */
    static int getManhattan(Node stair, Node node) {
        return Math.abs(stair.x - node.x) + Math.abs(stair.y - node.y);
    }
}