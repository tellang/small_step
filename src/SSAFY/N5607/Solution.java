package SSAFY.N5607;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {
    // System.setIn(new FileInputStream("./src/SSAFY/N5607/input.txt"));
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result;

    
    static final int MOD = 1234567891, MAX_N = 1_000_000;
    static int T, N, R;

    static long[] fact;

    static {
        fact = new long[MAX_N + 1];
        fact[0] = 1;
        for (int i = 1; i <= MAX_N; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }
    }

    public static void main(String[] args) throws IOException {
        // System.setIn(new FileInputStream("./src/SSAFY/N5607/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        result = new StringBuilder();

        T = parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc);
            init();

            long comb = getCombination(N, R);
            result.append(' ').append(comb).append('\n');
        }
        System.out.print(result);
    }


    static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        R = parseInt(st.nextToken());
    }

    /*
     * nCr mod MOD 계산
     */
    static long getCombination(int n, int r) {
        if (r == 0 || r == n)
            return 1; 
        long nFact = fact[n];
        long rFact_n_rFact = (fact[r] * fact[n - r]) % MOD;
        long reverse_rFact_n_rFact = modPow(rFact_n_rFact, MOD - 2);
        return (nFact * reverse_rFact_n_rFact) % MOD;
    }

    /*
     * 거듭제곱 형식 
     * b^t mod MOD
     */
    static long modPow(long bot, long top) {
        long base = bot % MOD;
        long res = 1;
        while (top > 0) {
            if ((top & 1L) == 1) // t를 깎아가다 1 남았을때
                res = (res * base) % MOD;
            base = (base * base) % MOD;
            top >>= 1;
        }
        return res;
    }
}
