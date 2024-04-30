package DP.N12783;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;
import static java.util.Collections.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, MAX;
    static List<Integer> dp = new ArrayList<>(), list = new ArrayList<>();
    static StringTokenizer st;
    public static void main(String[] args) throws IOException {
        N = parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        list.add(parseInt(st.nextToken()));
        dp.add(1);
        MAX = 1;
        for (int i = 1; i < N; i++) {
            int num = parseInt(st.nextToken());
            int orderSize = getOrderSize(num);
            dp.add(orderSize);
            MAX = max(MAX, orderSize);
        }
        System.out.println(MAX);
    }

    private static int getOrderSize(int num) {
        if (list.get(list.size() - 1) < num){
            list.add(num);
            return list.size();
        } else {
            int idx = binarySearch(list, num);
            if (idx < 0) idx = -idx - 1;

            list.set(idx, num);
            return idx+1;
        }
    }
}
