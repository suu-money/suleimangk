import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SeoOptimizer {

    public static void main(String[] args) {
        String url = "https://suu-money.github.io/suleimangk/";
        try {
            analyzePage(url);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void analyzePage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        System.out.println("Analyzing SEO for: " + url);

        // Check Title
        Element title = doc.selectFirst("title");
        String titleText = title != null ? title.text() : "No title found";
        System.out.println("Title: " + titleText);
        if (titleText.length() > 60) {
            System.out.println("Warning: Title is too long (should be under 60 characters).");
        }

        // Check Meta Description
        Element description = doc.selectFirst("meta[name=description]");
        String metaDescription = description != null ? description.attr("content") : "No meta description found";
        System.out.println("Meta Description: " + metaDescription);
        if (metaDescription.length() < 150 || metaDescription.length() > 160) {
            System.out.println("Warning: Meta description should be between 150-160 characters.");
        }

        // Check for Keywords (optional, some SEOs skip this)
        Element keywords = doc.selectFirst("meta[name=keywords]");
        String keywordsText = keywords != null ? keywords.attr("content") : "No keywords found";
        System.out.println("Keywords: " + keywordsText);

        // Heading Tags Analysis
        Map<String, Integer> headingCounts = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            headingCounts.put("h" + i, doc.select("h" + i).size());
        }
        System.out.println("Headings count: " + headingCounts);
        for (Map.Entry<String, Integer> entry : headingCounts.entrySet()) {
            if (entry.getValue() == 0) {
                System.out.println("Warning: Missing " + entry.getKey() + " heading tags.");
            }
        }

        // Check for Alt Text on Images
        long imagesWithoutAlt = doc.select("img").stream()
            .filter(img -> img.attr("alt").isEmpty())
            .count();
        System.out.println("Images without Alt text: " + imagesWithoutAlt);
        if (imagesWithoutAlt > 0) {
            System.out.println("Warning: Some images are missing alt text, which is important for SEO and accessibility.");
        }

        // Check for Open Graph Image and Twitter Card Image
        String ogImage = doc.selectFirst("meta[property=og:image]") != null ? 
                doc.selectFirst("meta[property=og:image]").attr("content") : "No Open Graph image found";
        System.out.println("Open Graph Image: " + ogImage);

        String twitterImage = doc.selectFirst("meta[name=twitter:image]") != null ? 
                doc.selectFirst("meta[name=twitter:image]").attr("content") : "No Twitter image found";
        System.out.println("Twitter Card Image: " + twitterImage);

        // Check for Robots Meta Tag
        Element robotsTag = doc.selectFirst("meta[name=robots]");
        String robotsContent = robotsTag != null ? robotsTag.attr("content") : "No robots meta tag found";
        System.out.println("Robots Meta Tag: " + robotsContent);
        
        // Output Summary
        System.out.println("SEO Analysis Completed.");
    }
}
