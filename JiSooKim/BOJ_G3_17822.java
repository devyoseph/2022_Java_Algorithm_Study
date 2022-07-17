package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/***
 * 
 * @author us13579
 * @since 220710
 * @title 원판 돌리기
 * 
 */

public class BOJ_G3_17822 {

	static class Node {
		int x;
		int y;

		public Node(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	static int[][] map;
	static int N, M, T;
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };
	static boolean[][] v;
	static Queue<Node> q;
	static ArrayList<Node> list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// **** input start ****
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());

		// 원반 좌표들
		map = new int[N + 1][M + 1];

		// 좌표 입력
		for (int i = 1; i < N + 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j < M + 1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 회전
		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());

			// 회전 시키기
			rotate(x, d, k);

			// 인접한 것이 있는지 체크
			boolean check = find();
			// 인접한 것이 없다면
			if (!check) {
				// 평균계산
				calAvg();
			}
		}

		// 출력
		int sum = 0;
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < M + 1; j++) {
					sum += map[i][j];
			}
		}

		System.out.println(sum);

	} // main end

	static void calAvg() {
		double sum = 0;
		double cnt = 0;

		// 평균 구하기
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < M + 1; j++) {
				// 값이 있으면
				if (map[i][j] > 0) {
					sum += map[i][j];
					cnt++;
				}
			}
		}

		// 만약 값이 없다면 통과
		if (cnt == 0)
			return;

		double avg = sum / cnt;

		// 평균값을 가지고 더하기, 빼기 실행
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < M + 1; j++) {
				// 아무것도 없으면 통과
				if (map[i][j] == 0)
					continue;
				// 평균보다 크면 1 빼기
				if (map[i][j] > avg)
					map[i][j] -= 1;
				// 평균보다 작으면 1 더하기
				else if (map[i][j] < avg)
					map[i][j] += 1;
			}
		}
	}

	// 같은 번호인지 찾기
	static boolean find() {
		// 인접한 것 중 같은 값이 있는지 체크 -> 있으면 true, 없으면 false
		boolean flag = false;
		// 인접한지 체크 배열
		v = new boolean[N + 1][M + 1];
		// 큐 생성
		q = new LinkedList<Node>();
		// 리스트 생성 - 인접한 수들이 몇개 있는지 체크
		list = new ArrayList<Node>();

		// 모든 좌표 BFS 탐색
		for (int i = 1; i < N + 1; i++) {
			for (int j = 1; j < M + 1; j++) {
				// 이미 방문했거나 제거했으면 통과
				if (v[i][j] || map[i][j] == 0)
					continue;
				Node cur = new Node(j, i);
				// 큐, 리스트 추가
				q.add(cur);
				list.add(cur);

				// BFS
				bfs(map[i][j]);

				// list에 2개 이상 있다면 -> 삭제해주기
				if (list.size() > 1) {
					// 변경됐다는 의미
					flag = true;
					// 원판에서 숫자 제거
					for (Node node : list) {
						map[node.y][node.x] = 0;
					}
				}
				// 리스트 초기화
				list.clear();
			}
		}
		// 인접한 것이 있는지 유무 반환
		return flag;
	}

	// 4방향 탐색
	static void bfs(int point) {
		while (!q.isEmpty()) {
			Node cur = q.poll();

			int nx, ny;
			for (int i = 0; i < 4; i++) {
				nx = cur.x + dx[i];
				ny = cur.y + dy[i];


				// X 는 다 연결되어 있다.
				if (nx < 1)
					nx = M;
				else if (nx > M)
					nx = 1;
				
				// Y - 범위를 벗어나거나 방문했던 곳이면 통과
				if (ny < 1 || ny > N || v[ny][nx])
					continue;

				// 만약 인접한 좌표의 숫자가 같은 경우
				if (map[ny][nx] == point) {
					// 방문처리
					v[ny][nx] = true;
					Node node = new Node(nx, ny);
					// queue 에 삽입
					q.add(node);
					// 리스트에 삽입
					list.add(node);
				}
			}
		}
	}

	// 회전시키기
	static void rotate(int x, int d, int k) {
		// k번 회전시키기
		while (k > 0) {
			for (int i = 1; i < N + 1; i++) {
				// x의 배수이면 회전
				if (i % x == 0) {
					// 시계방향으로
					if (d == 0) {
						// 마지막 값
						int last = map[i][M];
						for (int j = M; j > 0; j--) {
							map[i][j] = map[i][j - 1];
						}
						map[i][1] = last;
					}
					// 반시계방향으로
					else {
						// 처음 값
						int first = map[i][1];
						for (int j = 1; j < M; j++) {
							map[i][j] = map[i][j + 1];
						}
						map[i][M] = first;
					}
				}
			}
			k--;
		}
	}

} // class end
