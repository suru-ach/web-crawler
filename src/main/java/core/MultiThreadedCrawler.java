package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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

        // # approach 1
        // The crawler runs like so -> url + i = https://baseurl/i
        // links is a shared resource

        // # approach 2
        // add new links that you get from a crawled link , so you end up nesting many links (add a limit to not crawl many links)

        // # approach 1 taken
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

    public static void main(String[] args) {
        MultiThreadedCrawler crawler = new MultiThreadedCrawler();
    }
}