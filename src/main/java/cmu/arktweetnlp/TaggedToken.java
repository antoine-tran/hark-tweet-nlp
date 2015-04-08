package cmu.arktweetnlp;

/**
 * One token and its tag.
 **/
public class TaggedToken extends Token {
	
	public TaggedToken() {
		super();
	}
	
	public TaggedToken(String substring, int start) {
		super(substring,start);
	}

	public char tag;
	
	public String toString() {
		return token;
	}
}
