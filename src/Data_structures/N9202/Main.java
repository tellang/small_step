package Data_structures.N9202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
1. 알파벳을 개미굴 문제처럼 트리 형태로 저장
	A. Node
		- char c: 현재 문자
		- boolean isLeaf: 단어 종료 여부
		- Map<Character, Node> children: 자식 노드들
2. Boggle 판 마다 전수조사
	A. DFS 기반 전수조사
		- 현재 노드가 단어의 끝이라면 (isLeaf == true)
			i. 단어 Set에 중복 없이 추가
			ii. 점수 및 최대 길이, 사전순 비교 업데이트
 */
public class Main {
	static int W, B;
	static Node rootNode = new Node('0');
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static char[][] MAP;
	static int[] dr = {-1, 1, 0, 0, -1, 1, -1, 1},
		dc = {0, 0, -1, 1, -1, 1, 1, -1},
		score = {0, 0, 0, 1, 1, 2, 3, 5, 11};
	static StringBuilder sb = new StringBuilder();
	static Set<String> visitedWords = new HashSet<>();

	public static void main(String[] args) throws IOException {
		W = Integer.parseInt(br.readLine());
		for (int w = 0; w < W; w++) {
			Node root = rootNode;
			char[] arr = br.readLine().toCharArray();

			for (int i = 0; i < arr.length; i++) {
				char c = arr[i];
				if (!root.children.containsKey(c)) {
					root.children.put(c, new Node(c));
				}
				root = root.children.get(c);
			}
			root.isLeaf = true;
		}
		br.readLine();
		B = Integer.parseInt(br.readLine());

		for (int b = 0; b < B; b++) {
			MAP = new char[4][4];
			for (int i = 0; i < 4; i++) {
				MAP[i] = br.readLine().toCharArray();
			}
			Result result = new Result();
			boolean[][] visited = new boolean[4][4];
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					visited[y][x] = true;
					if (rootNode.children.containsKey(MAP[y][x]))
						dfs(y, x, rootNode.children.get(MAP[y][x]), result, visited, new StringBuilder());
					visited[y][x] = false;
				}
			}
			visitedWords.clear();
			sb.append(result);
			br.readLine();
		}
		System.out.println(sb.toString());
	}

	static boolean isValid(int y, int x) {
		return y >= 0 && y < 4 && x >= 0 && x < 4;
	}

	private static void dfs(int r, int c, Node node, Result result, boolean[][] visited, StringBuilder sb) {
		sb.append(node.c);

		if (node.isLeaf) {
			String curWord = sb.toString();
			int length = sb.length();
			if (!visitedWords.contains(curWord)) {
				visitedWords.add(curWord);
				result.score += score[length];
				result.count++;
				if ((result.maxLen < length) ||
					((result.maxLen == length) && result.maxWord.compareTo(curWord) > 0)) {
					result.maxLen = length;
					result.maxWord = curWord;
				}
			}
		}

		if (!node.children.isEmpty()) {
			for (int i = 0; i < 8; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];
				if (isValid(nr, nc)) {
					char tango = MAP[nr][nc];
					if (node.children.containsKey(tango) && !visited[nr][nc]) {
						visited[nr][nc] = true;
						dfs(nr, nc, node.children.get(tango), result, visited, sb);
						visited[nr][nc] = false;
					}
				}
			}
		}

		sb.setLength(sb.length() - 1);
	}
}

class Result {
	String maxWord = "";
	int count, maxLen, score = 0;

	@Override
	public String toString() {
		return score + " " + maxWord + " " + count + "\n";
	}
}

class Node {
	char c;
	boolean isLeaf = false;
	Map<Character, Node> children = new HashMap<>();

	public Node(char c) {
		this.c = c;
	}
}