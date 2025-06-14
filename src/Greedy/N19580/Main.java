package Greedy.N19580;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
구매자와 마스크 매칭 로직:

1. 구매자 정렬
   - maxPrice(최대 허용 가격) 오름차순 정렬
   - maxPrice가 같으면 minPrice(최소 허용 가격) 내림차순 정렬
   → 조건이 까다로운(허용 범위가 좁은) 구매자부터 먼저 처리하여 손해를 줄임

2. 마스크 저장 및 탐색
   - TreeMap<Long, Long> 사용하여 마스크를 가격 오름차순으로 저장
   - key: 마스크 가격, value: 해당 가격의 마스크 수량

3. 구매 처리
   - 각 구매자에 대해, minPrice 이상인 가장 저렴한 마스크를 TreeMap의 ceilingEntry로 탐색
   - 찾은 마스크가 maxPrice 이하면 할당(구매)
   - 마스크 수량이 1이면 해당 가격 삭제, 아니면 수량 -1로 갱신
*/

public class Main {
	static int N, M, RESULT = 0;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Buyer> bq = new ArrayList<>();
	static TreeMap<Long, Long> maskMap = new TreeMap<>();
	static Entry<Long, Long> pair;
	static PriorityQueue<Mask> mq = new PriorityQueue<>(
		((o1, o2) -> {
			if (o1.price == o2.price) {
				return Long.compare(o2.count, o1.count);
			} else {
				return Long.compare(o1.price, o2.price);
			}
		})
	);

	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			long min = Long.parseLong(st.nextToken());
			long max = Long.parseLong(st.nextToken());
			bq.add(new Buyer(min, max));
		}
		bq.sort(((o1, o2) -> {
			if (o1.maxPrice == o2.maxPrice) {
				return Long.compare(o2.minPrice, o1.minPrice);
			} else {
				return Long.compare(o1.maxPrice, o2.maxPrice);
			}
		}));

		for (int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine());
			long price = Long.parseLong(st.nextToken());
			long count = Long.parseLong(st.nextToken());
			maskMap.put(price, maskMap.getOrDefault(price, 0L) + count);
		}

		for (Buyer buyer : bq) {
			pair = maskMap.ceilingEntry(buyer.minPrice);
			if (pair == null || pair.getKey() > buyer.maxPrice)
				continue;
			long localMaskCount = pair.getValue();
			if (localMaskCount == 1) {
				maskMap.remove(pair.getKey());
			} else {
				maskMap.put(pair.getKey(), localMaskCount - 1);
			}
			RESULT++;
		}
		System.out.println(RESULT);
	}

}

class Mask {
	long price, count;

	public Mask(long price, long count) {
		this.price = price;
		this.count = count;
	}
}

class Buyer {
	long minPrice, maxPrice;

	public Buyer(long minPrice, long maxPrice) {
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}
}