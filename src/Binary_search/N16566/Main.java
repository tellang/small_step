package Binary_search.N16566;

import static java.lang.Integer.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Integer> list = new ArrayList<>();
	static List<Integer> indexList = new ArrayList<>();
	static int[] arr;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		K = parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		arr = new int[K];
		for (int m = 0; m < M; m++) {
			int e = parseInt(st.nextToken());
			list.add(e);
			indexList.add(m);
		}
		list.sort(Comparator.naturalOrder());
		st = new StringTokenizer(br.readLine());

		for (int k = 0; k < K; k++) {
			arr[k] = parseInt(st.nextToken());
		}

		for (int i : arr) {
			int result = Collections.binarySearch(list, i);
			if (result < 0) {
				result = (result+1)*(-1);
			} else {
				result++;
			}
			if (result >= M){
				result = 0;
			}
			int rootIdx = findRootIdx(result);
			if (rootIdx >= N) {
				rootIdx = findRootIdx(0);
			}
			sb.append(list.get(rootIdx)).append('\n');
			indexList.set(result, rootIdx+1);
		}

		System.out.println(sb);
	}
	public static int findRootIdx(int n){
		if (indexList.get(n) == n)
			return n;
		else return findRootIdx(indexList.get(n));
	}
}
