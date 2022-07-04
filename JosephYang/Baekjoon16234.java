import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Baekjoon16234 {
	static int N, L, R;
	static int[][] map, visit;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static ArrayList<Integer> score = new ArrayList<>(); // 나라들의 점수를 기록
	
	static boolean moveCheck() { // 경로 겹치지 않게 탐색
		for(int i=0; i<N; i++) { //무조건 오른쪽, 아래와만 비교
			for(int j=0; j<N; j++) {
				int RIGHT = L-1;
				int UNDER = L-1;
				
				if(j!=N-1) {
					RIGHT = Math.abs(map[i][j] - map[i][j+1]);
				}
				
				if(i!=N-1) {
					UNDER = Math.abs(map[i][j] - map[i+1][j]);
				}
				
				if( (L<=RIGHT && RIGHT<=R) || (L<=UNDER && UNDER<=R) ) {
					return true;
				}
			}
		}
		//System.out.println(judge+" arr.size : "+arr.size());
		return false;
	}
	
	static int num_people, num_nation;
	
	static void dfs(int row, int col, int order) {
		visit[row][col] = order;
		num_people += map[row][col];
		num_nation ++;
		
		//System.out.println(row+" "+col);
		for(int k=0; k<4; k++) {
			int r = row + dx[k];
			int c = col + dy[k];
			if(r<0 || r>=N || c<0 || c>=N || visit[r][c] != 0) {
				continue;
			}
			int diff = Math.abs(map[row][col]- map[r][c]);
			if(diff<L || diff>R) continue;
			
			dfs(r, c, order);
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int res = 0;
		
		while(moveCheck()) {
			res ++;
			visit = new int[N][N];
			int order = 1;
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(visit[i][j] == 0) {
						num_people = 0;
						num_nation = 0;
						dfs(i, j, order);
						order++;
//						for(int[] v: map) {
//							System.out.println(Arrays.toString(v));
//						}
						score.add(num_people/num_nation); //나중에 한번에 계산
					}
				}
			}
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					map[i][j] = score.get(visit[i][j]-1);
				}
			}	
			score.clear();
		}
		System.out.println(res);
	}

}
