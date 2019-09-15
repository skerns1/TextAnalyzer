//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;
//Imports from External Libraries
import org.jsoup.Jsoup;

public class TextAnalyzer {

	public static void main(String[] args) {
		/*Initialize URL and Map, create object to hold 
		  its URL and map of word/word count
		  Future Implementation could create this object through User Input/Interface*/
		String URL = "http://shakespeare.mit.edu/macbeth/full.html";
		LinkedHashMap<String, Integer> WordCount = new LinkedHashMap<String, Integer>();
		WebpageWordCount MacbethPlay = new WebpageWordCount(URL, WordCount);
		
		//Uses the Object String of URL to create an unsorted word count in a hash map
		Map<String, Integer> Unsorted = URLToUnsortedWordCount(MacbethPlay.getURL());
		  
	    // now let's sort the map in decreasing order of value
		Map<String, Integer> sorted = Unsorted
	        .entrySet()
	        .stream()
	        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	        .collect(
	            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
	                LinkedHashMap::new));
	 
		for (Entry<String, Integer> entry : sorted.entrySet())  
            System.out.println(entry.getKey() + " , " + entry.getValue()); 
		
	}//END MAIN
	
	//Put all the entries in unsorted map into LinkedHashMap of Object in descending value order
	public static void SortedWordCount (LinkedHashMap<String, Integer> Sorted, HashMap<String, Integer> Unsorted) {
		
	}//end SortedWordCount
	
	//Map the word count to unsorted map
	public static void UnsortedWordCount (String[] SplitText, HashMap<String, Integer> UnsortedMap) {
		//Check if word exists in map and increase count, if not add word to unsorted map
		int count = 0;
		for (String word:SplitText) { 
			if (!UnsortedMap.containsKey(word))
				UnsortedMap.put(word, 1);
			else {
				count = UnsortedMap.get(word);
				UnsortedMap.put(word, count+1);
				}//end else statement
		}//end for loop
	}//end UnsortedWordCount
	
	//Takes the WebpageWordCount URL to make an unsorted map of word count
	public static HashMap<String, Integer> URLToUnsortedWordCount(String URL) {
		
		//Call function and put parsed HTML into string
		String Text = WebpageToString(URL);
		
		//Take the string text remove punctuation and capitalization, and split the string into a String array of words
		String[] SplitText = Text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"); //Make regex pattern if used multiple times
		
		//Call function and create unsorted word count map
		HashMap<String, Integer> UnsortedMap = new HashMap<String, Integer>();
		UnsortedWordCount(SplitText, UnsortedMap);
		
		return UnsortedMap;
	}//end URLToWordCount
	
	//Gets HTML from URL, parses tags from HTML with Jsoup and returns result in string
	public static String WebpageToString(String page) {
        String HTML = "";
        String inputLine = "";
		
		try {
			//Initialize variables
			URL url = new URL(page);
            URLConnection conn = url.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
            
            //Read in each line of webpage into String
            while ((inputLine = br.readLine()) != null) {
            	HTML = HTML.concat(inputLine);
            }//end while loop
            br.close();
            //Parse HTML tags
            HTML = Jsoup.parse(HTML).text();
        }//end try
		catch (MalformedURLException e) {
            e.printStackTrace();
        }//end catch 
		catch (IOException e) {
            e.printStackTrace();
        }//end catch

		return HTML;
	}//end WebpageToString

}//end TextAnalyzer
