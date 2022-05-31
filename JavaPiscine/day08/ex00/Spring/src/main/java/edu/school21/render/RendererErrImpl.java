package edu.school21.render;

import edu.school21.processor.*;

public class RendererErrImpl implements Renderer {

	private PreProcessor preProcessor;

	public RendererErrImpl(PreProcessor preProcessor) {
		this.preProcessor = preProcessor;
	}

	@Override
	public void print(String text) {
		System.err.println(preProcessor.process(text));
	}
	
}
