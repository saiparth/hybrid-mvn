package Machines;

import java.util.Scanner;

import org.junit.Test;

public class lab2 extends ExcelUtils {
	static int e = 2;

	/*
	 * public static void main(String[] args) { int h = 1; boolean cond = false;
	 * String path = "C:\\Users\\partha\\Desktop\\excelLib.xlsx"; int rowcount =
	 * 0; try { rowcount = ExcelUtils.getRowCount("login",
	 * "C:\\Users\\partha\\Desktop\\excelLib.xlsx"); } catch (IOException e) {
	 * e.printStackTrace(); } for (int i = h + 1; i <= rowcount; i++) { if
	 * ((ExcelUtils.reader("login", i, 1, path).toString()).contains("z")) {
	 * System.out.println("null"+cond); break; }
	 * System.out.println(ExcelUtils.reader("login", i, 1, path)); } } }
	 */
	@Test
	public void g(){
		for (int i = 0; i < 2; i++) {
			t();
		}
		
		
		}
	
	public String t() {
		int i=10,b=3;
		String val=null;
		for (int j = 0; j < 3; j++) {
			val=ta();
		}
		System.out.println(val);
		return val;
		
	}
	public String ta() {
		Scanner sc=new Scanner(System.in);
		String f=sc.next();
		return f;
	}
}
