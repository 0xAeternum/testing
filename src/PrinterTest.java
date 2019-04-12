import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrinterTest {
	
	boolean loggedAsEmp;
	boolean loggedAsUser;
	Printer printer;
	Printer lowInkPrinter;
	File file;
	
	@BeforeEach
	public void init() {
		printer = new Printer(53, null);
		lowInkPrinter = new Printer(5, null);
		loggedAsEmp = false;
		loggedAsUser = false;
		file = new File(System.getProperty("user.dir") + "/src/test-print.txt");
	}

	@Test
	void testPrint() {
		
		assertFalse(printer.print(file));
		
		printer.loginUser();
		
		assertTrue(printer.print(file));
	}

	@Test
	void testScan() {
		
		assertFalse(printer.scan(file));
		
		printer.loginUser();
		
		assertTrue(printer.scan(file));
		
		printer.logout();
		printer.loginEmployee();
		
		assertTrue(printer.scan(file));
	}
	
	@Test
	void testLoginUser() {
		assertFalse(printer.isLoggedAsUser());
		
		printer.loginUser();
		
		assertTrue(printer.isLoggedAsUser());
	}
	
	@Test
	void testLoginEmp() {
		assertFalse(printer.isLoggedAsEmp());
		
		printer.loginEmployee();
		
		assertTrue(printer.isLoggedAsEmp());
	}
	
	@Test
	void testLogout() {
		assertFalse(printer.isLoggedAsEmp());
		assertFalse(printer.isLoggedAsUser());
		
		printer.loginEmployee();
		
		assertTrue(printer.isLoggedAsEmp());
		
		printer.logout();
		
		assertFalse(printer.isLoggedAsEmp());
	}
	
	@Test
	void testFileContent() {
		try {
			String content = printer.getFileContents(file);
			System.out.println(content);
			assertEquals(content, "test print\n");
		} catch (IOException e) {
			e.printStackTrace();
			fail("File contents could not be read.");
		}
	}
	
	
	@Test
	void testFillInk() {
		assertEquals(printer.getInk(), 53);
		
		printer.loadInk();
		assertEquals(printer.getInk(), 53);
		
		printer.loginEmployee();
		printer.loadInk();
		assertEquals(printer.getInk(), 100);
	}
	
	@Test
	void testLowInkNotification() {
		assertFalse(printer.isInkLow());
		assertTrue(lowInkPrinter.isInkLow());
	}

}
