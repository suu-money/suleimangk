import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SEOCrawler {
    public static void main(String[] args) {
        String url = "https://suu-money.github.io/suleimangk/";

        try {
            // Fetch the HTML code
            Document document = Jsoup.connect(url).get();

            // Get the title
            String title = document.title();
            System.out.println("Title: " + title);

            // Get meta description
            Elements metaTags = document.getElementsByTag("meta");
            for (Element metaTag : metaTags) {
                String name = metaTag.attr("name");
                String content = metaTag.attr("content");

                if ("description".equals(name)) {
                    System.out.println("Meta description: " + content);
                }
                if ("keywords".equals(name)) {
                    System.out.println("Meta keywords: " + content);
                }
            }

            // Get all headings
            for (int i = 1; i <= 6; i++) {
                Elements headings = document.select("h" + i);
                for (Element heading : headings) {
                    System.out.println("Heading " + i + ": " + heading.text());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
