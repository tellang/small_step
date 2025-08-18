package SSAFY.N1952;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 백트래킹 N = 12 매달 1일권 1달권 3달권 계산 - 단 3달권은 N+3으로 점프
 *
 * 최저가를 찾고 1년권과 비교하여 결정한다
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;

    static StringBuilder result = new StringBuilder();
    static int T, DECEMBER = 12, TICKET_KINDS = 4,
            DAILY = 0, MONTH = 1, THREE_MONTH = 2, YEAR = 3, MIN_COST;
    static int[] ticketCosts = new int[TICKET_KINDS], monthPlan = new int[DECEMBER];

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1952/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            st = new StringTokenizer(br.readLine().trim());
            for (int ticketType = 0; ticketType < TICKET_KINDS; ticketType++) {
                ticketCosts[ticketType] = parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine().trim());
            for (int month = 0; month < DECEMBER; month++) { //0-based idx
                monthPlan[month] = parseInt(st.nextToken());
            }

            MIN_COST = ticketCosts[YEAR];
            backtracking(0, 0); // 0 month == JAN

            result.append(MIN_COST).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 각 월별로 가능한 모든 이용권 구매 경우의 수를 탐색
     *
     * @param monthIdx 현재 탐색할 월 (0-based)
     * @param localCost 현재까지의 누적 비용
     */
    static void backtracking(int monthIdx, int localCost) {
        if (monthIdx == DECEMBER) {
            MIN_COST = Math.min(MIN_COST, localCost);
            return;
        }

        // 1일 이용권 구매
        int nextCost = localCost + monthPlan[monthIdx] * ticketCosts[DAILY];
        backtracking(monthIdx + 1, nextCost);

        // 1달 이용권 구매
        nextCost = localCost + ticketCosts[MONTH];
        backtracking(monthIdx + 1, nextCost);

        // 3달 이용권 구매
        nextCost = localCost + ticketCosts[THREE_MONTH];
        backtracking(Math.min(DECEMBER, monthIdx + 3), nextCost);

    }
}
