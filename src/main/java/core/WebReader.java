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

// Just Scraps the links from href tag,
public class WebReader {

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
