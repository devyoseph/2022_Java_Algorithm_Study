package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * 
 * @author us13579
 * @since 220613
 * @title 벌꿀채취
 * 
 */

public class SWEA_SW_2115 {
	static int N, M, C, res;
	static int[][] map;
	static int[][] profit;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			sb.append("#").append(tc).append(" ");

			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());

			// 꿀을 담을 용기
			profit = new int[N][N];
			map = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			res = 0;
			process();
			
			sb.append(res).append("\n");
		}
		System.out.println(sb);
	}
	
	static void process() {
		// 꿀을 채취할 수 있는 구간에서의 최대 수익
		maxProfit();
		
		// 일꾼 1 이 채취
		for(int i=0; i<N; i++) {
			for(int j=0; j<=N-M; j++) {
				comb(i,j+M,1,profit[i][j]);
			}
		}
	}
	
	static void comb(int x, int y, int idx, int sum) {
		if(idx == 2) {
			res = Math.max(res, sum);
			return;
		}
		
		// 일꾼 2 가 채취
		for(int i=x; i<N; i++) {
			for(int j=y; j<=N-M; j++) {
				comb(i,j,idx+1,sum+profit[i][j]);
			}
			y = 0;
		}
	}
	
	// 다 돌면서 최대 수익 계산
	static void maxProfit() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<= N-M; j++) {
				// 얻을 수 있는 최대 수익 ( 부분집합 )
				profitSubset(i,j,0,0,0);
			}
		}
	}
	
	// 부분집합 계산 ( 최대 수익 구하기 )
	static void profitSubset(int i, int j, int idx, int sum, int totalSum) {
		// 범위를 넘을시 통과
		if(sum > C) return;
		if(idx == M) {
			//최대 수익 갱신
			profit[i][j-M] = Math.max(profit[i][j-M], totalSum);
			return;
		}
		// 채취
		profitSubset(i, j+1, idx+1, sum + map[i][j], totalSum + map[i][j] * map[i][j]);
		// 채취 x
		profitSubset(i, j+1, idx+1, sum, totalSum);
	}
	
}
