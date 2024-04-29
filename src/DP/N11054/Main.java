package DP.N11054;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, MAX = 0;
    static int[] arr, result;
    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine());
        arr = new int[N];
        result = new int[N];
        List<Integer> decreaseDP = new ArrayList<>();
        List<Integer> increaseDP = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; n++) {
            arr[n] = parseInt(st.nextToken());
        }
        increaseDP.add(arr[0]);
        result[0] += 1;
        for (int i = 1; i < N; i++) {
            result[i] += getOrderSize(arr[i], increaseDP);
        }
        decreaseDP.add(arr[N-1]);

        for (int i = N-1; i >= 0; i--) {
            result[i] += getOrderSize(arr[i], decreaseDP);
            MAX = max(MAX, result[i]);
        }
        System.out.println(MAX - 1);
    }

    public static int getOrderSize(int target, List<Integer> dp) {
        int idx = dp.size() - 1;
        if (dp.get(idx) < target) {
            dp.add(target);
            idx++;
        }else{
            int searchIdx = Collections.binarySearch(dp, target);
            if (searchIdx < 0)
                searchIdx = -searchIdx - 1;
            dp.set(searchIdx, target);
            idx = searchIdx;
        }
        return idx + 1;
    }
}