import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://unsplash.com/").get();
            Elements imageElements = doc.getElementsByTag("img");
            for (Element element : imageElements) {
                System.out.println("src: "+element.attr("src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
