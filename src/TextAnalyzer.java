/* 
 ---Program summary---
 Program creates object with String that is the URL of website and a Hashmap to contain all the words
 from website as keys and their respective values being their word count. 
 First the program gets the HTML file of the URL and puts it in a string, then it cleans this string
 by removing all HTML tags, punctuation, and making all words lower case
 The program then counts the frequency of each word and sorts it in descending order
 Finally, the HashMap of the object gets this sorted word count map and that is saved as a text file
 locally.
 
 The Program also outputs the top 20 words in order to meet the acceptance criteria of the assignment
 */

//Imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.stream.Collectors.*;
import java.util.LinkedHashMap;
//Imports from External Libraries
import org.jsoup.Jsoup;

public class TextAnalyzer {

	public static void main(String[] args) {
		/*Initialize URL and Map, create object to hold 
		  its URL and map of word/word count
		  Future Implementation could create this object through User Input/Interface*/
		String URL = "http://shakespeare.mit.edu/macbeth/full.html";
		Map<String, Integer> WordCount = new HashMap<String, Integer>();
		WebpageWordCount MacbethPlay = new WebpageWordCount(URL, WordCount);
		
		//Uses the Object String of URL to create a sorted word count and map
		URLToWordCount(MacbethPlay);
		
		//Write the sorted Hashmap of the Play to a text file
		TextWriter(MacbethPlay.getWordCount());
		
		//Top 20 words using iterator
		Iterator<Map.Entry<String, Integer>> entries = MacbethPlay.getWordCount().entrySet().iterator();
		int i = 0;
		while (entries.hasNext() && i < 20) {
		    Map.Entry<String, Integer> entry = entries.next();
		    System.out.println(i+1 + ". " + entry.getKey() + ", " + entry.getValue());
		    i++;
		}//end while loop
		
	}//END MAIN
	
	//Sort Hash Map and return to Object
	
	//Put all the entries in unsorted map into LinkedMap of Object in descending value order
	public static Map<String, Integer> SortedWordCount (Map<String, Integer> Unsorted) {
		//Create a Sorted Map
		Map<String, Integer> Sorted = Unsorted
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(
						toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
								LinkedHashMap::new));
		return Sorted; 
	}//end SortedWordCount
	
	//Write Hashmap keys and values to text file
	public static void TextWriter (Map<String, Integer> Map) {
		//Initialize Filewriter and Buffer
		try {
			FileWriter fstream = new FileWriter("WordCount.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			//Create Iterator object to iterate over each pair of map in a loop for writing
			Iterator<Entry<String, Integer>> It = Map.entrySet().iterator();
		
			while (It.hasNext()) {
				Map.Entry<String, Integer> Pairs = It.next();
				out.write(Pairs.getKey() + " , " + Pairs.getValue() + "\n");
			}//end while loop
			out.close();
		}//end try
		catch (IOException e) {
			e.printStackTrace();
		}//end catch
		System.out.println("Text file of word count created");
	}//end TextWriter
	
	//Map the word count to unsorted map
	public static void UnsortedWordCount (String[] SplitText, Map<String, Integer> UnsortedMap) {
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
	public static void URLToWordCount(WebpageWordCount Page) {
		
		//Call function and put parsed HTML into string
		String Text = WebpageToString(Page.getURL());
		
		//Take the string text remove punctuation and capitalization, and split the string into a String array of words
		String[] SplitText = Text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"); //Make regex pattern if used multiple times
		
		//Call function and create unsorted word count map
		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		UnsortedWordCount(SplitText, tempMap);
		
		//Call function and sort the word count map
		Page.setWordCount(SortedWordCount(tempMap));
		
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
