package cmu.arktweetnlp.impl.features;

import java.util.List;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Metaphone;

import cmu.arktweetnlp.impl.features.FeatureExtractor.FeatureExtractorInterface;
import cmu.arktweetnlp.impl.features.FeatureExtractor.PositionFeaturePairs;

/** We should test if these are obsolete yet **/
public class MetaphoneFeatures {
	
	

	private static Metaphone _metaphone = null;
	private static DoubleMetaphone dblmetaphone = null;
	public static Metaphone getMetaphone() {
		if (_metaphone == null) {
			_metaphone = new Metaphone();
			_metaphone.setMaxCodeLen(100);
		} 
		return _metaphone;
	};
	public static DoubleMetaphone getDblMetaphone() {
		if (dblmetaphone == null) {
			dblmetaphone = new DoubleMetaphone();
			dblmetaphone.setMaxCodeLen(100);
		} 
		return dblmetaphone;
	};
	
	public static class MetaphoneLexical implements FeatureExtractorInterface{
		@Override
		public void addFeatures(List<String> tokens, PositionFeaturePairs pairs) {
			for (int t=0; t < tokens.size(); t++) {
				String tok = tokens.get(t);
				if(tok.length()>1){
					//String ppword=MetaphoneNum(tok);
					String metaphone_word = getDblMetaphone().encode(tok);
					String alternate_word = getDblMetaphone().doubleMetaphone(tok, true);
					pairs.add(t, "metaphone_word|"+metaphone_word);
					if(!metaphone_word.equals(alternate_word))
						pairs.add(t, "metaphone_word|"+alternate_word);
				}
			}        	
		}
	}
}
