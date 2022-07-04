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
 * @since 220704
 * @title 마법사 상어와 파이어볼
 *
 */

public class BOJ_G4_20056 {
	static class ball {
		int y;
		int x;
		int ballM;
		int ballS;
		int ballD;

		// 좌표 y,x, 질량, 속력, 방향
		public ball(int y, int x, int ballM, int ballS, int ballD) {
			this.y = y;
			this.x = x;
			this.ballM = ballM;
			this.ballS = ballS;
			this.ballD = ballD;
		}
	}

	static int N, M, K, r, c, m, s, d;
	// 8방향 0,1,2,3,4,5,6,7
	static int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static Queue<ball> queue;
	static ArrayList<ball>[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// **** input start ****
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// ball 저장 큐
		queue = new LinkedList<ball>();
		// 좌표마다 ball 저장 리스트
		map = new ArrayList[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			// 좌표
			r = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken())-1;
			// 질량
			m = Integer.parseInt(st.nextToken());
			// 속력
			s = Integer.parseInt(st.nextToken());
			// 방향
			d = Integer.parseInt(st.nextToken());

			// queue 에 저장
			queue.add(new ball(r, c, m, s, d));
		}
		// **** input end ****

		// K번 반복
		while (K > 0) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				// 좌표 이동하기
				move();
			}
			// 전체 돌면서 겹친 것이 있나 없나 체크
			for (int j = 0; j < N; j++) {
				for (int z = 0; z < N; z++) {
					// 없으면 통과
					if (map[j][z].size() == 0)
						continue;
					// 1개 있으면 그냥 큐에 넣는다
					if (map[j][z].size() == 1) {
						queue.add(map[j][z].get(0));
						continue;
					}
					// 2개 이상인 것이 있으면
					else {
						int weight = 0;
						int speed = 0;
						ArrayList<Integer> list = new ArrayList<>();
						for (int i = 0; i < map[j][z].size(); i++) {
							weight += map[j][z].get(i).ballM;
							speed += map[j][z].get(i).ballS;
							list.add(map[j][z].get(i).ballD);
						}

						weight = weight(weight);
						speed = speed(speed, map[j][z].size());
						int check = check(list);

						// 질량이 0 이면 없애기
						if (weight == 0)
							continue;
						// 다 같으면 짝수
						if (check == 1) {
							int idx = 0;
							for (int i = 0; i < 4; i++) {
								queue.add(new ball(j, z, weight, speed, idx));
								idx += 2;
							}
						}
						// 다르면 홀수
						else {
							int idx = 1;
							for (int i = 0; i < 4; i++) {
								queue.add(new ball(j, z, weight, speed, idx));
								idx += 2;
							}
						}
					}
				}
			}
			// map 초기화
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					map[i][j] = new ArrayList<>();
				}
			}
			K--;
		}

		int res = 0;
		int size = queue.size();
		for(int i=0; i<size; i++) {
			res += queue.poll().ballM;
		}
		
		System.out.println(res);

	}// main end

	// 이동하는 메서드
	static void move() {
		// 이동할 ball
		ball cur = queue.poll();

		// 이동
		int ny = cur.y + dy[cur.ballD]*(cur.ballS%N);
		int nx = cur.x + dx[cur.ballD]*(cur.ballS%N);
		
		ny = (ny + N)%N;
		nx = (nx + N)%N;

		// list 에 추가
		map[ny][nx].add(new ball(ny, nx, cur.ballM, cur.ballS, cur.ballD));
	}

	// 질량 구하는 메서드
	static int weight(int sum) {
		return sum / 5;
	}

	// 속력 구하는 메서드
	static int speed(int sum, int cnt) {
		return sum / cnt;
	}

	// 방향 체크 - 방향들 모두 모아서 ( 방향 리스트 가져와서 1-> 다같다 , 짝수 / 2-> 다 안같다, 홀수로 반환 )
	static int check(ArrayList<Integer> list) {
		int num = 0;
		boolean flag = true;

		int temp = list.get(0) % 2;

		for (int i = 1; i < list.size(); i++) {
			// 만약 모두 같은 홀수나 짝수가 아닌 경우 false
			if (list.get(i) % 2 != temp)
				flag = false;
		}

		// 만약 다 같으면 1, 다 다르면 2
		if (flag)
			num = 1;
		else
			num = 2;

		return num;
	}

}// class end
