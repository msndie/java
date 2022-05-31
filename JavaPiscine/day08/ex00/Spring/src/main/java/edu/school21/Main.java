package edu.school21;

import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithPrefixImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		Printer printer = context.getBean("print", Printer.class);
		printer.print("Hello!");
	}
	
}
