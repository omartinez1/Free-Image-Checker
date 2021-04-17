package application;

import java.util.ArrayList;

import java.io.IOException;
import java.io.FileWriter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;



public class SampleController {
	
	//Used for date format used URL access date
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//first page
	int count = 0;
	
	@FXML
    private Pane rootPage;

	@FXML
    private Button enterBtn;

    @FXML
    private TextField keyword;
    
    @FXML
    private Label tryLabel;
    
    //second page
    @FXML
    private AnchorPane resultPage;

    @FXML
    private Button backBtn;

    @FXML
    private Label resultLabel;
    
    @FXML
    private Button tryBtn;
    
    public static String searchWord;
    
    @FXML
    void display(ActionEvent event) {
    	try {
    		ArrayList<String> urls = new ArrayList<String>();
    		
    		String strA = "";
    		String strB = "";
    		String result = "";
    		String urlStr = "";
    		
    		//webscraping part
            // connects to website, then get() fetches and parses html file
    		searchWord = searchWord.replaceAll(" ", "-");
            Document doc = Jsoup.connect("https://unsplash.com/s/photos/" + searchWord).get();
            // we use this to grab each element tag
            Elements imageElements = doc.getElementsByTag("img");
            
            //for each element tag we print out the link to the source image
            for (Element element : imageElements) {
                urlStr = element.attr("src");
                //Check to exclude ad images
                //ads have https://secure while images are https://images
                if(urlStr.charAt(8) == 's') {
                	continue;
                }
                //Add URLs w/ timestamps to the ArrayList to save into txt file
                urls.add(timeStampString() + "     " + urlStr);
                //Save URL results into 1 big string for in app display
                strA = "src: "+urlStr+"\n";
                result = strB.concat(strA);
                strB = result;
            }
            //Display all urls
            resultLabel.setText("Here are the results: \n" + result);
            //Save URLs into a text file
            saveAsText(urls, searchWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //switch to new screen when search button is pressed
    @FXML
    void loadResult(ActionEvent event) throws IOException {
    	searchWord = keyword.getText();
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("Result.fxml"));
    	rootPage.getChildren().setAll(pane);
    }
    
    //switch back to main search page when back button is pressed
    @FXML
    void goBack(ActionEvent event) throws IOException {
    	Pane pane = FXMLLoader.load(getClass().getResource("Sample.fxml"));
    	resultPage.getChildren().setAll(pane);
    }
    
    //Function used to save URL results into a text file using ArrayLists
    //Input: ArrayList of String URLs, String for to use for text file name
    //Output: Text file in java file
    public static void saveAsText(ArrayList<String>urls, String fileName) throws IOException {
    	FileWriter writer = new FileWriter(fileName +".txt");
		int len = urls.size();
		writer.write("Date                    URL's\n");
		//Loop through URL ArrayList and add it into the text file
		for(int i = 0; i < len; i++) {
			writer.write(urls.get(i) + "\n");
		}
		writer.close();
    }
    
    //Use this to create a time stamp that contains date and time (24hour format)
    //Input: none
    //Output: String of date and time in the format of dateFormat (yyyy-MM-dd HH:mm:ss)
    public static String timeStampString() {
    	//Grab time stamp from local System
    	Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
    	//Give back time stamp as a String in dateFormat format
    	return dateFormat.format(timeStamp);
    }
    
}
