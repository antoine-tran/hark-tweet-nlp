package cmu.arktweetnlp.io;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
public class JsonTweetReader  {
	ObjectMapper mapper;
	
	public JsonTweetReader() {
		mapper = new ObjectMapper();
	}
	
	/**
	 * Get the text from a raw Tweet JSON string.
	 * 
	 * @param tweetJson
	 * @return null if there is no text field, or invalid JSON.
	 */
	public String getText(String tweetJson) {
		JsonNode rootNode; 
		
		try {
			rootNode = mapper.readValue(tweetJson, JsonNode.class);
		} catch (JsonParseException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		if (! rootNode.isObject())
			return null;
		
		JsonNode textValue = rootNode.get("text");
		if (textValue==null)
			return null;
		
		return textValue.asText();
	}
	
	public boolean isJson(String isThisJson) {		
		if (isThisJson.charAt(0) != '{')
			return false;
		
		try {
			mapper.readValue(isThisJson, JsonNode.class);
		} catch (JsonParseException e) {
			return false;
		} catch (IOException e) {
			System.err.println("WTF -- got IOException in isJson()");
			return false;
		}
		return true;
	}

}
