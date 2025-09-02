package SSAFY.N15961;

/**
 * 
 * 같은 초밥 가능
 * 연속된 k개에 대해 할인이 적용
 * - 고객별로 추가 초밥이 있다
 * 
 * 가장 많은 종류의 초밥을 먹고싶다
 * - 행사에 참여를 안해도 되나?
 *   - 아니 해야만 한다
 * 
 * N = 3M, d = 3K, k = 3K
 * 
 * 무식하게 Bitset으로 확인하고싶다
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int N, SUSHI_MAX = 3_000_000, D, SUSHI_KIND = 3000, K, WINDOW_SIZE, C, COUPON,
    sushiMax = 0;

    static BitSet ateKind = new BitSet(SUSHI_KIND);
    static int[] sushies = new int[SUSHI_MAX], ateKindCounts = new int[SUSHI_KIND+1];


    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N15961/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        System.out.println(sushiMax);
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = SUSHI_MAX = parseInt(st.nextToken());
        D = SUSHI_KIND = parseInt(st.nextToken());
        K = WINDOW_SIZE = parseInt(st.nextToken());
        C = COUPON = parseInt(st.nextToken());

        int sIdx = 0;
        

        /**
         * WINDOW_SIZE 만큼 먹기
         */
        for (; sIdx < WINDOW_SIZE; sIdx++) {
            int addNum = parseInt(br.readLine().trim());
            sushies[sIdx] = addNum;
            
            ateKind.set(addNum);
            ateKindCounts[addNum]++;
            
        }

        ateKind.set(COUPON);
        ateKindCounts[COUPON] = 6_000_000; //영구 처리
        sushiMax = Math.max(sushiMax, ateKind.cardinality());
        
        /**
         * WINDOW_SIZE 를 유지하며 먹뱉
         */
        for (; sIdx < SUSHI_MAX; sIdx++) {
            int deleteNum = sushies[sIdx - WINDOW_SIZE];
            int addNum = parseInt(br.readLine().trim());
            sushies[sIdx] = addNum;

            cycleSushi(addNum, deleteNum);
        }

        /**
         * 시작지점을 포함하여 윈도우
         */
        for (; sIdx < SUSHI_MAX + WINDOW_SIZE; sIdx++) {
            int deleteNum = sushies[(sIdx - WINDOW_SIZE) % SUSHI_MAX];
            int addNum = sushies[sIdx % SUSHI_MAX];

            cycleSushi(addNum, deleteNum);
        }
    }

    static void cycleSushi(int addNum, int deleteNum) {
        ateKind.set(addNum);
        ateKindCounts[addNum]++;

        ateKindCounts[deleteNum]--;
        if(ateKindCounts[deleteNum] == 0){ // 0 언더로 내려 갈 수 없을 듯
            ateKind.clear(deleteNum);
        } 
        
        sushiMax = Math.max(sushiMax, ateKind.cardinality());
    }
}