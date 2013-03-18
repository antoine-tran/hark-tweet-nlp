package cmu.arktweetnlp.util;

import java.io.IOException;
import java.util.List;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Tagger.TaggedToken;

/**
 * utility methods for handling tweet contents 
 */
public class TweetUtils {
	
	private Tagger tagger;
	
	/** only one instance should be created */
	public static final TweetUtils instance = new TweetUtils();
	
	private TweetUtils() {
		tagger = new Tagger();
		try {
			tagger.loadModel("/cmu/arktweetnlp/model.20120919");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void reloadModel(String model) throws IOException {
		tagger.loadModel("/cmu/arktweetnlp/model.20120919");
	}
	
	/** normalize a tweet by removing URLs, hashtags, emoticons, 
	 * discourse markers, replace all white spaces by a single space */
	public String normalize(String tw) {
		return normalize(tagger.tokenizeAndTag(tw));
	}
	
	/** normalize a tweet by removing URLs, hashtags, emoticons, 
	 * discourse markers, replace all white spaces by a single space */
	public static String normalize(List<TaggedToken> tokens) {
		StringBuilder sb = new StringBuilder();
		if (tokens == null || tokens.isEmpty()) return null;
		int n = tokens.size();
		for (int i = 0; i < n - 1; i++) {
			TaggedToken t = tokens.get(i);
			if (t.tag != '#' && t.tag != '@' && 
					t.tag != '~' && t.tag != 'U' && t.tag != 'E') {
				sb.append(t.token);
				if (tokens.get(i + 1).tag != ',') sb.append(" ");
			}
		}
		TaggedToken t = tokens.get(n - 1);
		if (t.tag != '#' && t.tag != '@' && 
				t.tag != '~' && t.tag != 'U' && t.tag != 'E') {
			sb.append(t.token);
		}
		return sb.toString();
	}
}
