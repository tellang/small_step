package SSAFY.N2458;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 플로이드 와샬과 비트셋을 이용한
 * 갯수세기
 * 큰 사람 + 작은 사란 = 총 인원 - 1 이면 판단 가능 
 * 
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int N, MAX_STEUDENTS, M, MAX_EDGE;
    static BitSet[] bigSet; // 1 based

    static {
        N = MAX_STEUDENTS = 500;
        M = MAX_EDGE = 25000;

        bigSet = new BitSet[MAX_STEUDENTS + 1];
        for (int s = 1; s <= MAX_STEUDENTS; s++) {
            bigSet[s] = new BitSet(MAX_STEUDENTS + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N2458/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        int count = floyd();
        System.out.println(count);
    }

    /**
     * b 보다 큰 noc 이 있다면
     * noc보다 큰 것 들은 b 보다 크다
     * 
     * @return
     */
    static int floyd() {
        for (int noc = 1; noc <= MAX_STEUDENTS; noc++) {
            for (int b = 1; b <= MAX_STEUDENTS; b++) {
                if (noc == b)
                    continue;

                if (bigSet[b].get(noc))
                    bigSet[b].or(bigSet[noc]);
            }
        }

        return getCount();
    }

    /**
     * 큰 것을 저장하는 비트셋의 비트 갯수와 (큰 것 수)
     * 다른 비트셋에 포함되어 있는지를 세어 (작은 것 수)
     * 합쳐서 MAX_STEUDENTS - 1 이면 판단 가능
     * @return
     */
    static int getCount() {
        int totalCount = 0;
        for (int b = 1; b <= MAX_STEUDENTS; b++) {
            bigSet[b].clear(b);
            int bigManCount = bigSet[b].cardinality();
            int smallManCount = 0;
            for (int s = 1; s <= MAX_STEUDENTS; s++) {
                if (b == s)
                    continue;
                if (bigSet[s].get(b))
                    smallManCount++;
            }

            if (bigManCount + smallManCount == MAX_STEUDENTS - 1)
                totalCount++;
        }
        return totalCount;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = MAX_STEUDENTS = parseInt(st.nextToken());
        M = MAX_EDGE = parseInt(st.nextToken());
        for (int bs = 1; bs <= MAX_STEUDENTS; bs++) {
            bigSet[bs].clear();
        }

        /*
         * s < b
         */
        for (int e = 0; e < MAX_EDGE; e++) {
            st = new StringTokenizer(br.readLine().trim());
            int s = parseInt(st.nextToken());
            int b = parseInt(st.nextToken());
            bigSet[s].set(b);
        }
    }

}