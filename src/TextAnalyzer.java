//Imports from Java
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//Imports from External Libraries
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TextAnalyzer {

	public static void main(String[] args) {
		/*Initialize URL and Map, create object to hold 
		  it's URL and associated word count of HTML from URL
		*/
		String URL = "http://shakespeare.mit.edu/macbeth/full.html";
		Map<String, Integer> WordCount = new HashMap<String, Integer>();	
		WebpageWordCount MacbethPlay = new WebpageWordCount(URL, WordCount);
		
		WebpageConvert(MacbethPlay.getURL());
		
		
	}//END MAIN

	
	public static void WebpageConvert(String page) {
		
		try {
			URL url = new URL(page);
            URLConnection conn = url.openConnection();
            String HTML = "";
            

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
            	HTML = HTML.concat(inputLine);
            }
            
            HTML = Jsoup.parse(HTML).text();
            System.out.println(HTML);
            br.close();
            System.out.println("Done");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}//end WebpageToWordList
	
}//end TextAnalyzer
