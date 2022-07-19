import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int[][] map = new int[10][10];
	static int[][] brick_x = {{0}, {0, 0}, {0, 1}};
	static int[][] brick_y = {{0}, {0, 1}, {0, 0}};
	static ArrayList<int[]> arr_G = new ArrayList<>();
	static ArrayList<int[]> arr_B = new ArrayList<>();
	static int score = 0;
	
	static void move(int type, int x, int y) {
		for(int i=0; i<brick_x[type].length; i++) {
			arr_G.add(new int[] {x+brick_x[type][i], y+brick_y[type][i]});
			arr_B.add(new int[] {x+brick_x[type][i], y+brick_y[type][i]});
		}
		moveToGreen(arr_G);
		moveToBlue(arr_B);
		for(int i=0; i<arr_G.size(); i++) { // 파랑 초록 동시에 표시 
			map[arr_G.get(i)[0]][arr_G.get(i)[1]] = 1;
			map[arr_B.get(i)[0]][arr_B.get(i)[1]] = 1;
		}
		
		blueCheck(arr_B.get(arr_B.size()-1)[1]);
		greenCheck(arr_G.get(arr_G.size()-1)[0]);
		arr_G.clear();
		arr_B.clear();
		
		blueCheck2();
		greenCheck2();
	}

	private static void moveToBlue(ArrayList<int[]> arr) { //오른쪽 이동 (끝: 9)
		boolean flag = true;
		while(flag) {
			for(int i=0; i<arr.size(); i++) {
				int nextX = arr.get(i)[0];
				int nextY = arr.get(i)[1] + 1;
				//System.out.println(nextX+" B "+ nextY);
				if(nextY>9 || map[nextX][nextY] == 1) {
					flag = false;
					break; // 경계값 & 블럭있으면 멈추기
				}
			}
			if(!flag) break;
			for(int i=0; i<arr.size(); i++) {
				int nextX = arr.get(i)[0];
				int nextY = arr.get(i)[1] + 1;
				arr.set(i, new int[] {nextX, nextY});
			}
		}
	}
	private static void moveToGreen(ArrayList<int[]> arr) {
		boolean flag = true;
		while(flag) {
			for(int i=0; i<arr.size(); i++) {
				int nextX = arr.get(i)[0] + 1;
				int nextY = arr.get(i)[1];
				//System.out.println(nextX+" G "+nextY);
				if(nextX>9 || map[nextX][nextY] == 1) {
					flag = false;
					break; // 경계값 & 블럭있으면 멈추기
				}
			}
			if(!flag) break;
			for(int i=0; i<arr.size(); i++) {
				int nextX = arr.get(i)[0] + 1;
				int nextY = arr.get(i)[1];
				arr.set(i, new int[] {nextX, nextY});
			}
		}
	}
	
	private static void greenCheck(int row) {
		for(int i=0; i<2; i++) {
			boolean flag = true;
			for(int j=0; j<4; j++) {
				if(map[row][j] == 0) {
					flag = false;
				}
			}
			if(!flag) {
				row--;
				continue;
			}
			
			score ++;
			for(int k=row; k>4; k--) {
				for(int m=0; m<4; m++) {
					map[k][m] = map[k-1][m];
				}
			}
			for(int k=0; k<4; k++) {
				map[4][k] = 0; //맨위 삭제
			}
		}
	}
	private static boolean blueCheck(int col) {
		for(int j=0; j<2; j++) {
			boolean flag = true;
			for(int i=0; i<4; i++) {
				if(map[i][col] == 0) {
					flag = false;
				}
			}
			if(!flag) {
				col--;
				continue;
			}
			
			score ++;
			for(int k=col; k>4; k--) {
				for(int m=0; m<4; m++) {
					map[m][k] = map[m][k-1];
				}
			}
			for(int k=0; k<4; k++) {
				map[k][4] = 0; //맨위 삭제
			}
			
		}
		return false;
	}
	
	private static void greenCheck2() {
		for(int k=0; k<4; k++) {
			if(map[4][k] == 1) { //맨 윗줄이 차있으면
				for(int i=9; i>5; i--) {
					for(int j=0; j<4; j++) {
						map[i][j] = map[i-2][j];
					}
				}
				for(int i=4; i<6; i++) {
					for(int j=0; j<4; j++) {
						map[i][j] = 0;
					}
				}
				return;
			}
		}
		
		for(int k=0; k<4; k++) {
			if(map[5][k] == 1) { //두번째 줄이 차있으면
				for(int i=9; i>5; i--) {
					for(int j=0; j<4; j++) {
						map[i][j] = map[i-1][j];
					}
				}
				for(int i=5; i<6; i++) {
					for(int j=0; j<4; j++) {
						map[i][j] = 0;
					}
				}
				return;
			}
		}
	}

	private static void blueCheck2() {
		for(int k=0; k<4; k++) {
			if(map[k][4] == 1) { //맨 왼줄이 차있으면
				for(int j=9; j>5; j--) {
					for(int i=0; i<4; i++) {
						map[i][j] = map[i][j-2];
					}
				}
				for(int j=4; j<6; j++) {
					for(int i=0; i<4; i++) {
						map[i][j] = 0;
					}
				}
				return;
			}
		}
		
		for(int k=0; k<4; k++) {
			if(map[k][5] == 1) { //두번쨰 왼줄이 차있으면
				for(int j=9; j>5; j--) {
					for(int i=0; i<4; i++) {
						map[i][j] = map[i][j-1];
					}
				}
				for(int j=5; j<6; j++) {
					for(int i=0; i<4; i++) {
						map[i][j] = 0;
					}
				}
				return;
			}
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		int sum = 0;
		for(int i=0; i<N ;i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken())-1; // 블록 타입
			int x = Integer.parseInt(st.nextToken()); // 행
			int y = Integer.parseInt(st.nextToken()); // 열
			move(t,x,y);
		}
		
		for(int[] a: map) {
			//System.out.println(Arrays.toString(a));
			for(int b : a) {
				if(b==1) {
					sum ++;
				}
			}
		}
		System.out.println(score);
		System.out.println(sum);
	}
}
