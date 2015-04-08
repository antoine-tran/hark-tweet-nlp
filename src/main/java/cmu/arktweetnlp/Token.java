package cmu.arktweetnlp;

/** A token is a string with offset inside its context */
public class Token {
	public String token;
	public int offset;
	
	public Token() {}
	
	public Token(String token, int offset) {
		this.token = token;
		this.offset = offset;
	}
}
