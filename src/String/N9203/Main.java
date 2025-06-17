package String.N9203;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
3년 년 거의 1K
5000개의 방을 끝나는 순으로 정렬
매일을 갯수세기 5M
2013-01-01-00:00 을 StartOffset으로
여기서부터 분 차이를 idx로 하는 int[] arr 에 숫자 순차 증가
1. Reserve Class, LocalDateTime 변수 2개
	LocalTime, LocalDate로 LocalDateTime 으로 시간 생성 후
	끝 시간엔 청소시간 분을 더함
	시작 idx 끝 idx
 */
public class Main {
	static int T, B, MAX_IDX, MAX;
	static StringTokenizer st;
	static long cleanOffset;
	static Set<String> reserveSet = new HashSet<>();
	static LocalDateTime startOffset = LocalDateTime.of(2013, Month.JANUARY, 1, 0, 0);
	static LocalDateTime endOffset = LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0).plusMinutes(360);
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1024);
	static Map<Integer, Integer> count = new TreeMap<>();
	static StringBuilder result = new StringBuilder();

	public static void main(String[] args) throws IOException {
		T = Integer.parseInt(br.readLine());
		MAX_IDX = getIdx(endOffset);
		for (int t = 0; t < T; t++) {
			MAX = 0;
			count.clear();
			reserveSet.clear();
			st = new StringTokenizer(br.readLine());
			B = Integer.parseInt(st.nextToken());
			cleanOffset = Long.parseLong(st.nextToken());
			for (int b = 0; b < B; b++) {
				st = new StringTokenizer(br.readLine());
				String key = st.nextToken();
				if (!reserveSet.contains(key)) {
					LocalDate startDate = LocalDate.parse(st.nextToken());
					LocalTime startTime = LocalTime.parse(st.nextToken());
					LocalDate endDate = LocalDate.parse(st.nextToken());
					LocalTime endTime = LocalTime.parse(st.nextToken());
					LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
					LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime).plusMinutes(cleanOffset);
					int startIdx = getIdx(startDateTime);
					int endIdx = getIdx(endDateTime);
					count.put(startIdx, count.getOrDefault(startIdx, 0) + 1);
					count.put(endIdx, count.getOrDefault(endIdx, 0) - 1);

					reserveSet.add(key);
				}
			}
			int localMax = 0;
			for (int k : count.keySet()) {
				localMax += count.get(k);
				MAX = Math.max(MAX, localMax);
			}
			result.append(MAX).append("\n");
			System.gc();
		}
		System.out.println(result);
	}

	static int getIdx(LocalDateTime localDateTime) {
		return Math.toIntExact(Duration.between(startOffset, localDateTime).toMinutes());
	}
}

