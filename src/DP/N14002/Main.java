package DP.N14002;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;
import static java.util.Collections.binarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, MAX;
    static List<Integer> list = new ArrayList<>(),
        originalLIS = new ArrayList<>(),
        dp = new ArrayList<>();
    static ArrayDeque<Integer> dq = new ArrayDeque<>();
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        originalLIS.add(parseInt(st.nextToken()));
        list.add(originalLIS.get(0));
        dp.add(1);
        MAX = 1;
        for (int i = 1; i < N; i++) {
            int num = parseInt(st.nextToken());
            originalLIS.add(num);
            int orderSize = getOrderSize(num);
            dp.add(orderSize);
            MAX = max(MAX, orderSize);
        }
        sb.append(MAX).append('\n');
        int localMax = MAX - 1;
        for (int oriIdx = dp.size() - 1; oriIdx >= 0 && localMax >= 0; oriIdx--) {
            int dpIdx = dp.get(oriIdx) - 1;
            if (dpIdx == localMax) {
                dq.offerFirst(originalLIS.get(oriIdx));
                localMax--;
            }
        }
        while (!dq.isEmpty()) {
            sb.append(dq.poll()).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    private static int getOrderSize(int num) {
        if (list.get(list.size() - 1) < num) {
            list.add(num);
            return list.size();
        } else {
            int idx = binarySearch(list, num);
            if (idx < 0)
                idx = -idx - 1;

            list.set(idx, num);
            return idx + 1;
        }
    }
}
