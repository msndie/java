package edu.school21.processor;

public class PreProcessorToLower implements PreProcessor {

	@Override
	public String process(String string) {
		return string.toLowerCase();
	}
	
}
