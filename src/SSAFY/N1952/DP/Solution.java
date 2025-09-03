package SSAFY.N1952.DP;
/**
 * 3달 이용 금액이 한달이나 하루보다 저렴할 수 도 있으려나
 * 
 * dp[month<=15] = min(
 *  dp[month-1] + daily*plan[month],
 *  dp[month-1] + plan[month],
 *  dp[month-3] + plan[3month]
 * ) -> minCost
 * min(minCost, plan[year])
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;

    static final int BUFFERED_MONTH_MAX = 15, 
    DECEMBER = 12 , TICKET_KINDS = 4,
            DAILY = 0, MONTH = 1, THREE_MONTH = 2, YEAR = 3,
            MAX_COST = 3000*12*30*10;

    static StringBuilder result = new StringBuilder();
    static int T;
    static int[] ticketCosts, monthPlan, minCostsByMonth;

    static {
        ticketCosts = new int[TICKET_KINDS];
        monthPlan = new int[BUFFERED_MONTH_MAX];
        minCostsByMonth = new int[BUFFERED_MONTH_MAX];
        Arrays.fill(minCostsByMonth, 1, DECEMBER + 1, MAX_COST);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1952/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            init();
            dp();

            result.append(minCostsByMonth[DECEMBER]).append('\n');
        }
        System.out.println(result);
    }

    static void dp() {
        
        for (int m = 1; m < BUFFERED_MONTH_MAX; m++) {
            minCostsByMonth[m] = minCostsByMonth[m - 1] + monthPlan[m] * ticketCosts[DAILY];
            minCostsByMonth[m] = Math.min(minCostsByMonth[m], minCostsByMonth[m - 1] + ticketCosts[MONTH]);
            if(m < 3) continue;
            minCostsByMonth[m] = Math.min(minCostsByMonth[m], minCostsByMonth[m - 3] + ticketCosts[THREE_MONTH]);
        }
        minCostsByMonth[DECEMBER] = Math.min(minCostsByMonth[DECEMBER], ticketCosts[YEAR]);
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        for (int ticketType = 0; ticketType < TICKET_KINDS; ticketType++) {
            ticketCosts[ticketType] = parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine().trim());
        for (int month = 1; month <= DECEMBER; month++) { //1-based idx
            monthPlan[month] = parseInt(st.nextToken());
        }
    }

    
}
