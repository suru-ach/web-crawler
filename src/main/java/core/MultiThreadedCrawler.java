package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Just Scraps the links from href tag,
class WebReader {
    List<String> links = new ArrayList<>();
    String baseUrl;
    Integer id;
    String url;

    WebReader(String baseUrl, Integer id) {
        this.id = id;
        this.baseUrl= baseUrl;
        this.url = baseUrl+"/?p="+id;
    }

    void generate() throws InterruptedException, IOException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();

        Document html = Jsoup.parse(responseString);
        Elements links_body = html.getElementsByClass("athing");

        for(Element link: links_body) {
            Elements link_value = link.getElementsByTag("a");
            if(link_value.isEmpty()) continue;
            String link_url = link_value.get(1).attr("href");

            // If the link is internal append the baseURL
            if(link_url.startsWith("http")) {
                links.add(link_url);
            } else {
                links.add(baseUrl+"/"+link_url);
            }
        }
    }

    synchronized void appendToList(List<String> mainList) {
        mainList.addAll(links);
    }

}

class Crawler extends Thread {
    private List<String> links;
    private String url;
    private Integer id;

    Crawler(String crawl_url, Integer id, List<String> list) {
        this.links = list;
        this.url = crawl_url;
        this.id = id;
    }

    public void run() {
        try {
            WebReader webReader = new WebReader(url, id);
            webReader.generate();
            webReader.appendToList(links);
            System.out.println(links.size());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class MultiThreadedCrawler {
    MultiThreadedCrawler() {

        String url = "https://news.ycombinator.com";
        List<String> links = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<?>> futures = new ArrayList<>();

        // The crawler runs like so -> url + i = https://baseurl/i
        // links is a shared resource
        for(int i=1;i<=10;i++) {
            futures.add(executorService.submit(new Crawler(url, i, links)));
        }

        for(Future<?> future: futures) {
            try {
                future.get();
            } catch(InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        }
        for(String link: links) {
            System.out.println(link);
        }
        System.out.println(links.size());

        executorService.shutdown();
    }
}
