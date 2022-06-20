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
 * @since 220620
 * @title 인구이동
 */

public class BOJ_G5_16234 {
	static int N, L, R;
	static int[][] map;
	static boolean[][] v;
	// 4방향 탐색 : 오, 밑, 왼, 위
	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	// 인구이동이 필요한 노드 리스트
	static ArrayList<Node> list;

	public static class Node{
		int x;
		int y;
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// **** input start ****
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		// map 배열
		map = new int[N][N];

		// 배열 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// **** input end ****
		
		
		System.out.println(move());
		
	}
	
	public static int move() {
		int res = 0;
		while(true) {
			boolean isMove = false;
			v = new boolean[N][N];
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					// 이미 방문했으면 통과
					if(v[i][j]) continue;
					
					int sum = bfs(i,j);
					if(list.size() > 1) {
						change(sum);
						isMove = true;
					}
				}
			}
			if(!isMove) return res;
			res++;
		}
	}
	
	// bfs
	public static int bfs(int x, int y) {
		Queue<Node> queue = new LinkedList<>();
		list =  new ArrayList<>();
		
		// 큐에 입력
		queue.offer(new Node(x, y));
		// 리스트에 입력
		list.add(new Node(x, y));
		// 방문처리
		v[x][y] = true;
		
		int sum = map[x][y];
		while(!queue.isEmpty()) {
			// 현재 노드 저장
			Node current = queue.poll();
			
			// 4방향 탐색
			int ny,nx;
			for(int i=0; i<4; i++) {
				nx = current.x + dx[i];
				ny = current.y + dy[i];
				
				// 범위를 벗어나거나 이미 방문했으면 통과
				if(nx < 0 || ny < 0 || nx > N-1 || ny > N-1 || v[nx][ny]) continue;
				
				// 인구수 차이 체크
				int temp = Math.abs(map[current.x][current.y] - map[nx][ny]);
				// 조건에 부합하면
				if(temp >= L && temp <= R) {
					// 입력하기
					queue.offer(new Node(nx,ny));
					list.add(new Node(nx,ny));
					// 값 더해주기
					sum += map[nx][ny];
					// 방문처리
					v[nx][ny] = true;
				}
			}
		}
		return sum;
	}

	// 인구 이동
	public static void change(int sum) {
		int avg = sum / list.size();
		for(Node n : list) {
			map[n.x][n.y] = avg;
		}
	}
}
