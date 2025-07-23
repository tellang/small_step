package Segment_Tree.N2042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 기본적인 세그먼트 트리
 */
public class Main {

    static int N, M, K, UPDATE = 1, SUM = 2;
    static long[] original, segmentSum;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        original = new long[N];
        segmentSum = new long[N * 4];

        for (int n = 0; n < N; n++) {
            original[n] = Long.parseLong(br.readLine());
        }

        buildSegmentTree(0, N - 1, 1);

        for (int mk = 0; mk < M + K; mk++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken()) - 1;
            long c = Long.parseLong(st.nextToken());

            if (a == UPDATE) {
                long diff = c - original[b];
                update(0, N - 1, 1, b, diff);
                original[b] = c;
            } else if (a == SUM) {
                result.append(query(b, (int)(c - 1))).append('\n');
            }
        }
        System.out.println(result);
    }

    public static long query(int sumStartIdx, int sumEndIdx) {
        return query(0, N - 1, sumStartIdx, sumEndIdx, 1);
    }

    private static long query(int originalStartIdx, int originalEndIdx, int tangoStartIdx, int tangoEndIdx,
            int segmentIdx) {
        if (tangoStartIdx > originalEndIdx || tangoEndIdx < originalStartIdx) {
            return 0L;
        }
        if (tangoStartIdx <= originalStartIdx && tangoEndIdx >= originalEndIdx) {
            return segmentSum[segmentIdx];
        }
        int sumMidIdx = (originalStartIdx + originalEndIdx) >> 1;

        return query(originalStartIdx, sumMidIdx, tangoStartIdx, tangoEndIdx, segmentIdx << 1) +
                query(sumMidIdx + 1, originalEndIdx, tangoStartIdx, tangoEndIdx, (segmentIdx << 1) + 1);
    }

    private static void update(int originalStartIdx, int originalEndIdx, int segmentIdx, int originalIdx, long diff) {
        if (originalIdx > originalEndIdx || originalIdx < originalStartIdx) {
            return;
        }
        segmentSum[segmentIdx] += diff;

        if (originalStartIdx != originalEndIdx) {
            int sumMidIdx = (originalStartIdx + originalEndIdx) >> 1;
            update(originalStartIdx, sumMidIdx, segmentIdx << 1, originalIdx, diff);
            update(sumMidIdx + 1, originalEndIdx, (segmentIdx << 1) + 1, originalIdx, diff);
        }
    }

    private static long buildSegmentTree(
            int originalStartIdx, int originalEndIdx, int segmentIdx) {
        if (originalStartIdx == originalEndIdx) {
            return segmentSum[segmentIdx] = original[originalStartIdx];

        }

        int midIdx = (originalStartIdx + originalEndIdx) >> 1;
        return segmentSum[segmentIdx] = buildSegmentTree(originalStartIdx, midIdx, segmentIdx << 1) +
                buildSegmentTree(midIdx + 1, originalEndIdx, (segmentIdx << 1) + 1);
    }

}
