package sparta.등산코스_정하기;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//방문처리를 value로 해서 좀 더 경제적인 pruning을 하자

class Solution {

	static Set<Integer> summitSet = new HashSet<>();
	static Set<Integer> gateSet = new HashSet<>();
	static PriorityQueue<Node> q;
	static ArrayList<Node>[] map;
	static int HEAD = 0, INTENSITY = 1;

	public static int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
		int[] answer = {Integer.MAX_VALUE, Integer.MAX_VALUE};
		map = new ArrayList[n + 1];
		for (int i = 1; i <= n; i++) {
			map[i] = new ArrayList<>();
		}
		for (int[] path : paths) {
			map[path[0]].add(new Node(path[1], path[2]));
			map[path[1]].add(new Node(path[0], path[2]));
		}
		for (int node : summits) {
			summitSet.add(node);
		}
		for (int node : gates) {
			gateSet.add(node);
		}
		Arrays.sort(summits); // 정상에서 출발하므로 같은 Intensity의 경우 낮은 node 번호를 골라야 하기에 정렬
		int[] isVisitedInt = new int[n + 1]; // 현재 노드를 방문한 경로 중 가장 낮은 Intensity를 저장
		Arrays.fill(isVisitedInt, Integer.MAX_VALUE);// 초기화
		for (int startNode : summits) {
			q = new PriorityQueue<>(
				(n0, n1) -> {
					if (n0.intensity == n1.intensity) {
						return n0.node - n1.node;
					} else {
						return n0.intensity - n1.intensity;
					}
				}
			);
			q.offer(new Node(startNode, 1));
			while (!q.isEmpty()) {
				Node node = q.poll();
				if (node.intensity > answer[INTENSITY]) // 어짜피 우선순위 큐이므로 더 볼 필요 없다
					break;

				isVisitedInt[node.node] = node.intensity;

				if (gateSet.contains(node.node)) {
					if (node.intensity < answer[INTENSITY]) {
						answer[HEAD] = startNode;
						answer[INTENSITY] = node.intensity;
					}
				} else {
					for (Node next : map[node.node]) {
						int nextInt = Math.max(node.intensity, next.intensity);
						if ((isVisitedInt[next.node] > nextInt) &&
							!summitSet.contains(next.node) &&
							answer[INTENSITY] >= nextInt) {
							q.offer(new Node(next.node, nextInt));
						}
					}
				}
			}
		}

		System.out.println("answer = " + Arrays.toString(answer));
		return answer;
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		int n = 7, n0 = 6;
		int[][] paths = new int[][] {{1, 4, 4}, {1, 6, 1}, {1, 7, 3}, {2, 5, 2}, {3, 7, 4},
			{5, 6, 6}},
			paths0 = new int[][] {{1, 2, 3}, {2, 3, 5}, {2, 4, 2}, {2, 5, 4}, {3, 4, 4}, {4, 5, 3},
				{4, 6, 1}, {5, 6, 1}};
		int[] gates = new int[] {1}, gates0 = new int[] {1, 3};
		int[] summits = new int[] {2, 3, 4}, summits0 = new int[] {5};
		solution(n, paths0, gates0, summits0);
	}
}

class Node {

	int node, intensity;

	public Node(int node, int intensity) {
		this.node = node;
		this.intensity = intensity;
	}
}