package cmu.arktweetnlp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cmu.arktweetnlp.TaggedToken;

public class TestTagger {
	
	String inputFormat = "auto";
	String outputFormat = "auto";
	int inputField = 1;
	
	String inputFilename;
	
	/** Can be either filename or resource name **/
	String modelFilename = "/cmu/arktweetnlp/model.20120919";
	
	@Test
	public void testOneTweet() throws IOException {
		Tagger tagger = new Tagger();
		tagger.loadModel(modelFilename);
		
		String text = "RT @DjBlack_Pearl: wat muhfuckaz wearin 4 the lingerie party?????";
		List<TaggedToken> taggedTokens = tagger.tokenizeAndTag(text);

		for (TaggedToken token : taggedTokens) {
			System.out.printf("%s %s\t", token.tag, token.token);
		}
	}

}
