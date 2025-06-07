package Data_structures.N16934;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/*
있는지 확인
앞글자씩 따서 트리만들기
 */
public class Main {
	static int N;
	static Node root = new Node();
	static Map<String, Integer> duplicates = new HashMap<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		for (int n = 0; n < N; n++) {
			String name = br.readLine();
			Node head = root;
			boolean stillDuplicate = true;
			if (duplicates.containsKey(name)) {
				int count = duplicates.get(name);
				sb.append(name).append(count).append("\n");
				duplicates.put(name, count + 1);
			} else {
				duplicates.put(name, 2);
				char[] charArray = name.toCharArray();
				int i = 0;
				for (; i < charArray.length; i++) {
					char c = charArray[i];
					sb.append(c);
					if (head.children.containsKey(c)) {
						head = head.children.get(c);
					} else {
						head = new Node(head, c);
						break;
					}
				}
				sb.append('\n');
				i++;
				for (; i < charArray.length; i++) {
					char c = charArray[i];
					head = new Node(head, c);
				}
			}
		}
		System.out.println(sb);
	}
}

class Node {

	Map<Character, Node> children = new HashMap<>();

	public Node(Node head, char c) {
		if (head != null) {
			head.children.put(c, this);
		}
	}

	public Node() {

	}
}
