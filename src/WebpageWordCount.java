import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WebpageWordCount {

	//String of URL to pull HTML file for Text Analyzer List and WordCount Map of word and frequency
	private String URL;
	private HashMap<String, Integer> WordCount = new LinkedHashMap<String, Integer>();

	//Default Constructor
	public WebpageWordCount(String uRL, HashMap<String, Integer> wordCount) {
		super();
		URL = uRL;
		setWordCount(wordCount);
	}//end Default Constructor
	
	//Getters and Setters
	public String getURL() {
		return URL;
	}//end getURL
	public void setURL(String uRL) {
		URL = uRL;
	}//end setURL
	public Map<String, Integer> getWordCount() {
		return WordCount;
	}//end getWordCount
	public void setWordCount(HashMap<String, Integer> wordCount) {
		WordCount = wordCount;
	}//end setWordCount
	
}//end WebpageWordCount
