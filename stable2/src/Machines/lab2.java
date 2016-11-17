package Machines;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.Test;

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
	public void t() {
		for (int i = 0; i < 5; i++) {
			if (e == 2) {
				System.out.println("cont");
				continue;
			} 
			System.out.println(i);
		}
	}
}
