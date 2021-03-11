import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        try {
            // connects to website, then get() fetches and parses html file
            Document doc = Jsoup.connect("https://unsplash.com/").get();
            // we use this to grab each element tag
            Elements imageElements = doc.getElementsByTag("img");
            // for each element tag we print out the link to the source image
            for (Element element : imageElements) {
                System.out.println("src: "+element.attr("src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
