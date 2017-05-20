package map;

import java.io.*;

public class ReadMap{
	
	private static String						MAPFILE = "src/Res/BasicTaiwanMap.csv";
	private static int 							rol = 100 , col = 55;
	public  static int[][]						map;
	
	public static void main(String args[]) throws IOException {
	}
	
	public ReadMap() throws IOException {
		BufferedReader	read = null;
		String			line;
		String 			csvSplitBy = ",";
		String[]		linecode;
		map	= new int[rol][col];
		
		try {
			read = new BufferedReader(new FileReader(MAPFILE));
			for (int i = 0; i < rol; i ++) {
				line = read.readLine();
				linecode = line.split(csvSplitBy);
				for (int j = 0; j < col; j ++) {
					map[i][j] = Integer.parseInt(linecode[j]);
				}
			}
		} finally {
			if (read != null) {
				read.close();
			}
		}
	}
	
	public int[][] Map() {
		return map;
	}
	
	public int Rol() {
		return rol;
	}
	
	public int Col() {
		return col;
	}
}
