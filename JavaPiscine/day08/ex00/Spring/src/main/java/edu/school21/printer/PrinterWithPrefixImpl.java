package edu.school21.printer;

import edu.school21.render.Renderer;

public class PrinterWithPrefixImpl implements Printer {

	Renderer renderer;
	private String prefix;

	public PrinterWithPrefixImpl(Renderer renderer) {
		this.renderer = renderer;
		prefix = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void print(String text) {
		renderer.print(prefix + text);
	}
	
}
