import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static int N, M, T;
	static int[][] map; //원판정보
	static ArrayList<Integer>[] arr;
	//static int[] start;
	static boolean can_remove = false; //소거가 가능한지 확인할 수 있는 플래그 변수

	static void rotate(int I, int D, int G) { //원판 번호, 방향(0:시계), 회전수
		//System.out.println(I+" "+D+" "+G);
		int R = D==0? 1:-1; //0은 시계: - , 1은 반시계: +
		int real_rotate = (R*G) % M;
		if(real_rotate<0) {
			real_rotate += M;
		}

		for(int i=0; i<real_rotate; i++) {
			int ADD = arr[I].get(M-1);
			arr[I].add(0, ADD);
			arr[I].remove(M);
		}
	}
	
	static void remove() {
		boolean[][] visit = new boolean[N][M]; //방문체크 배열: -1(지움), 0(탐색x), 1(지우지 않음)		
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(!visit[i][j]) {
					int num = arr[i].get(j);
					if(num==0) {
						visit[i][j] = true;
						continue;
					}
					//System.out.println("탐색시작 : "+i+" "+j+" "+" "+num);
					if(dfs(i, j,visit,num,0)) {
						//System.out.println("제거 : "+i+" "+j);
						dfs_remove(i,j, num);
						can_remove = true;
					}
				}
			}
		}
	}
	
	static boolean dfs(int I, int J, boolean[][] visit, int num, int depth) {
		if(I<0 || I>=N) { //원판 경계 나감
			return false;
		}
		if(J<0) {
			J+=M;
		}else if(J>=M) {
			J-=M;
		}
		
		if(visit[I][J]) {
			return false;
		}
		
		if(arr[I].get(J) == num) {
			visit[I][J] = true; //일단 방문체크
			//System.out.println("방문 : "+I+" "+J);
		}else {
			return false;
		}
		
		//총 4방향으로 이동가능
		boolean[] judge = new boolean[4];
		judge[0] = dfs(I-1, J, visit, num, depth+1);
		judge[1] = dfs(I+1, J, visit, num, depth+1);
		judge[2] = dfs(I, J+1, visit, num, depth+1);
		judge[3] = dfs(I, J-1, visit, num, depth+1);
		
		if(depth>0) {
			return true;
		}
		
		for(int i=0; i<4; i++) {
			if(judge[i]) {
				return true;
			}
		}
		return false;
	}
	
	static void dfs_remove(int I, int J, int num) {
		if(I<0 || I>=N) { //원판 경계 나감
			return;
		}
		if(J<0) {
			J+=M;
		}if(J>=M) {
			J-=M;
		}
		
		if(arr[I].get(J) == num) {
			arr[I].set(J, 0); //지워주기
		}else {
			return;
		}
		//System.out.println(I+" "+J+" "+num);
		
		//총 4방향으로 지우기
		dfs_remove(I-1, J, num);
		dfs_remove(I+1, J, num);
		dfs_remove(I, J+1, num);
		dfs_remove(I, J-1, num);
	}
	
	static void avg() {
		float sum = 0;
		float count = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(arr[i].get(j)>0) {
					sum += arr[i].get(j);
					count ++;
				}
			}
		}
	
		float avg = sum/count;
		//System.out.println(avg);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(arr[i].get(j)==0)continue;
				
				if(arr[i].get(j)>avg) {
					arr[i].set(j, arr[i].get(j)-1);
				}else if(arr[i].get(j)<avg) {
					arr[i].set(j, arr[i].get(j)+1);
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); //원판의 크기
		M = Integer.parseInt(st.nextToken()); //숫자
		T = Integer.parseInt(st.nextToken()); //회전 수
		
		arr = new ArrayList[N];
		
		for(int i=0; i<N; i++) {
			arr[i] = new ArrayList<Integer>();
		}
		//map = new int[N][M];
		//start = new int[N]; //원판의 시작점
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				//map[i][j] = Integer.parseInt(st.nextToken());
				arr[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		
		for(int t=0; t<T; t++) {
			st = new StringTokenizer(br.readLine());
			int L = Integer.parseInt(st.nextToken()); //배수
			int D = Integer.parseInt(st.nextToken()); //방향: 0 시계
			int G = Integer.parseInt(st.nextToken()); //회전수
			
			//회전
			int L_start = L-1; //배열이므로 -1
			for(int n=L_start; n<N; n+=L) {
				//System.out.println("회전: "+n);
				rotate(n, D, G);
			}
			
			
			can_remove = false;
			//소거
			remove();
			
			if(!can_remove) {
				avg();
			}
		}
		
		int SUM = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				SUM += arr[i].get(j);
			}
		}
		System.out.println(SUM);
	}

}
