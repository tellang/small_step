package Greedy.N15708;
/*
어짜피 이동에 걸리는시간은 가장 멀리있는돌
- 뒤로 돌아갈 필요 없음

전부 다 가져가면서
제한범위를 넘어간다면
가장 구린 돌을 캐지않은 처리 하는 방식
- 우선순위 큐로 가장 오래걸리는걸 총 걸린시간에서 뺴주자
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	static int N, T, P, RESULT;
	static int[] times;
	static long SUM;

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		times = new int[N];
		for (int i = 0; i < N; i++) {
			times[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N; i++) {
			int time = times[i];
			int limit = T - i * P;
			if (limit < 0)
				break;

			pq.offer(time);
			SUM += time;

			while (SUM > limit) {
				SUM -= pq.poll();
			}

			RESULT = Math.max(RESULT, pq.size());
		}

		System.out.println(RESULT);
	}
}
