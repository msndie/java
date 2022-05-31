package edu.school21.render;

import edu.school21.processor.*;

public class RendererStandardImpl implements Renderer {

	private PreProcessor preProcessor;

	public RendererStandardImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void print(String text) {
		System.out.println(preProcessor.process(text));
	}
	
}
