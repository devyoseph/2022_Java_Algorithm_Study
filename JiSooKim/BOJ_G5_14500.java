package BOJ;

/**
 * @author us13579
 * @since 2022-04-26, 1시간 30분
 * @title 테트로미노
 */

import java.io.*;
import java.util.*;

public class BOJ_G5_14500 {
	static int N, M;
	static int[][] map;
	static boolean[][] v;
	static int max = Integer.MIN_VALUE;
	static int sum;
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// 세로 크기
		N = Integer.parseInt(st.nextToken());

		// 가로 크기
		M = Integer.parseInt(st.nextToken());

		// 배열
		map = new int[N][M];

		// 방문 체크
		v = new boolean[N][M];

		// 배열 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				v[i][j] = true;
				sum = map[i][j];
				dfs(0, j, i);
				v[i][j] = false;
				check(j,i);
			}
		}

		// 최댓값 출력
		System.out.println(max);
	}

	static void dfs(int idx, int x, int y) {
		// 기저조건 4칸 이동 ( 처음 값은 넣어주고 넘어 온다 )
		if (idx == 3) {
			max = Math.max(max, sum);
			return;
		}

		// 4방향 탐색
		int nx, ny;
		for (int i = 0; i < 4; i++) {
			nx = x + dx[i];
			ny = y + dy[i];

			// 범위 넘어가는 경우 , 이미 갔던 경우
			if (nx < 0 || ny < 0 || nx > M - 1 || ny > N - 1 || v[ny][nx])
				continue;

			// 방문 체크
			v[ny][nx] = true;
			sum += map[ny][nx];
			dfs(idx + 1, nx, ny);

			// 백트래킹
			v[ny][nx] = false;
			sum -= map[ny][nx];
		}
	}

	// ㅗ 모양 따로 검사
	static void check(int x, int y) {
		// ㅏ
		if (x >= 0 && y >= 0 && x + 1 < M && y + 2 < N) {
			max = Math.max(max, map[y][x] + map[y + 1][x] + map[y + 2][x] + map[y + 1][x + 1]);
		}

		// ㅓ
		if (x < M && y >= 0 && x - 1 >= 0 && y + 2 < N) {
			max = Math.max(max, map[y][x] + map[y + 1][x - 1] + map[y + 1][x] + map[y + 2][x]);
		}

		// ㅜ
		if (x >= 0 && y >= 0 && x + 2 < M && y + 1 < N) {
			max = Math.max(max, map[y][x] + map[y][x + 1] + map[y][x + 2] + map[y + 1][x + 1]);
		}

		// ㅗ
		if (x + 1 < M && y >= 0 && x - 1 >= 0 && y + 1 < N) {
			max = Math.max(max, map[y][x] + map[y + 1][x - 1] + map[y + 1][x] + map[y + 1][x + 1]);
		}
	}
}
