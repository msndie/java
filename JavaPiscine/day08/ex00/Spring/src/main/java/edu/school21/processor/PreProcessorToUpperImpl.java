package edu.school21.processor;

public class PreProcessorToUpperImpl implements PreProcessor {

	@Override
	public String process(String string) {
		return string.toUpperCase();
	}
	
}
