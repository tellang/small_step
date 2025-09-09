package SSAFY.N7579;
/**
 * DP 문제
 * ---
 * dp[cost] = idleMemory
 * long 필요
 * 
 * ---
 * 앱을 다 켜고 지워가자
 * 중복 사용이 불가능하니 역순으로 진행
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

    static long[] idleMemories; //c
	static int[] appMemories, appCosts;
	static int MAX_APP, N, MAX_MEMORY, M, minCost, MAX_COST;

    static {
        minCost = 100 * 1000;
        MAX_COST = 0;
    }

	public static void main(String[] args) throws IOException {
		init();
		cleaner();
		System.out.println(minCost);
	}

    static void cleaner() {
        for (int app = 1; app <= MAX_APP; app++) { //100 끌 앱
			int offCost = appCosts[app];
			int mem = appMemories[app];
			for (int cost = MAX_COST; cost >= offCost; cost--) {// 중복 사용 배제 역순
				idleMemories[cost] = Math.max(
                    idleMemories[cost], // 이미 cost 만큼 cost
                    idleMemories[cost - offCost] + mem); // 앱 끄고 나니 cost 만큼 cost

				if (idleMemories[cost] >= MAX_MEMORY)
					minCost = Math.min(cost, minCost);
			}
		}
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
		MAX_APP = N = parseInt(st.nextToken());
		MAX_MEMORY = M = parseInt(st.nextToken());

		appMemories = new int[MAX_APP + 1];
		appCosts = new int[MAX_APP + 1];

		st = new StringTokenizer(br.readLine());
		for (int app = 1; app <= MAX_APP; app++) {
			appMemories[app] = parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		for (int app = 1; app <= MAX_APP; app++) {
			appCosts[app] = parseInt(st.nextToken());
			MAX_COST += appCosts[app];
		}

		idleMemories = new long[MAX_COST + 1];
    }
}
