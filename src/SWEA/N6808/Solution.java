package SWEA.N6808;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean[] selected;
    static int[] IY, KY;
    static int T, TOTAL_DUAL_COUNT = 362_880, WIN_COUNT, KY_POINT, IY_POINT,
    MAX_CARD_NUM = 19, MAX_ROUND = 9, MAX_CARD_SUM = 171;
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            result.append('#').append(testCase).append(' ');
            WIN_COUNT = 0;
            // LOCAL_SUM = 0;
            KY_POINT = 0;
            IY_POINT = 0;
            selected = new boolean[MAX_CARD_NUM];
            KY = new int[9];
            int KYIdx = 0;
        
            st = new StringTokenizer(br.readLine());
            for (int selectedNumCount = 0; selectedNumCount < MAX_ROUND; selectedNumCount++) {
                int selectedNum = Integer.parseInt(st.nextToken());
                selected[selectedNum] = true;
                KY[KYIdx++] = selectedNum;
            }

            backTracking(0);

            result.append(WIN_COUNT).append(' ').append(TOTAL_DUAL_COUNT - WIN_COUNT).append('\n');
        }
        System.out.println(result);
    }

    private static void backTracking(int round) {
        if(round == MAX_ROUND){
            if(IY_POINT < KY_POINT)
                WIN_COUNT++;
            return;
        }

        for (int IYNum = 1; IYNum < MAX_CARD_NUM; IYNum++) {
            if(selected[IYNum]) continue;
            int point = IYNum + KY[round];

            selected[IYNum] = true;
            if(IYNum > KY[round]){
                IY_POINT += point;
                backTracking(round + 1);
                IY_POINT -= point;
            }else{
                KY_POINT += point;
                backTracking(round + 1);
                KY_POINT -= point;
            }
            selected[IYNum] = false;
        }
    }
}
