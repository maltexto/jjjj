package dev.maltexto.pivete.services;

import static dev.maltexto.pivete.utils.ConnectionUtils.*;
import static dev.maltexto.pivete.utils.UrlUtils.isSameDomain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.maltexto.pivete.entities.CrawlData;
import dev.maltexto.pivete.entities.enums.CrawlStatus;
import dev.maltexto.pivete.repositorys.ICrawlDataRepository;

public class Worker implements Runnable {
    // BFS

    private Queue<URI> urlQueue;
    private List<URI> visitedURLs;
    private URI baseURL;

    private ICrawlDataRepository crawlDataDB;

    private String keyword;
    private CrawlData crawlDataBuffer;
    private Logger logger;

    public Worker(ICrawlDataRepository crawlDataRepository, String crawlDataID) {
        this.crawlDataDB = crawlDataRepository;
        this.crawlDataBuffer = crawlDataRepository.findById(crawlDataID);

        this.urlQueue = new LinkedList<>();
        this.visitedURLs = new ArrayList<>();
        this.baseURL = getVarEnvBaseURL();

        this.keyword = crawlDataBuffer.getKeyword();
        this.logger = LoggerFactory.getLogger(Worker.class);
    }

    private URI getVarEnvBaseURL() {
        String varEnvBaseURL = System.getenv("BASE_URL");
        URI uri = null;

        try {
            uri = new URI(varEnvBaseURL);
        } catch (URISyntaxException exception) {
            crawlDataBuffer.setStatus(CrawlStatus.ERROR);
            crawlDataDB.update(crawlDataBuffer);
        }
        return uri;
    }

    private boolean containsKeyword(String html) {
        Pattern pattern = Pattern.compile("(?i).*" + keyword + ".*");
        Matcher matcher = pattern.matcher(html);
        return matcher.find();
    }

    private Set<URI> extractUrlsWithSameDomain(String domain, String html) {
        Set<URI> uris = new HashSet<>();

        String regex = "<a[^>]*\\bhref\\s*=\\s*\"([^\"]*)\"[^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            String matchingStr = matcher.group(1);

            try {
                URI uri = new URI(matchingStr);

                if (uri.isAbsolute()) {
                    if (isSameDomain(domain, uri)) {
                        uris.add(uri);
                    } else {
                        URI absoluteURI = baseURL.resolve(uri);
                        uris.add(absoluteURI);
                    }
                }
            } catch (URISyntaxException exception) {
                logger.info("Invalid URI: {}", matchingStr);
            }
        }
        return uris;
    }

    public void crawl(URI rootURL) {
        String response = "";

        urlQueue.add(rootURL);

        while (!urlQueue.isEmpty()) {
            URI currentURI = urlQueue.poll();

            if (crawlDataBuffer.getStatus() == CrawlStatus.ACTIVE) {

                try {
                    response = get(currentURI);

                } catch (IOException | InterruptedException exception) {
                    crawlDataBuffer.setStatus(CrawlStatus.ERROR);
                }

                if (containsKeyword(response)) {
                    updateURLFieldOnCrawlResponse(currentURI.toString());
                }

                String domain = currentURI.getHost();
                Set<URI> urisFounded = extractUrlsWithSameDomain(domain, response);

                for (URI uri : urisFounded) {
                    if (!urlQueue.contains(uri)) {
                        urlQueue.add(uri);
                    }
                }

                visitedURLs.add(currentURI);
            }
        }
    }

    private void updateURLFieldOnCrawlResponse(String url) {
        crawlDataBuffer.getUrls().add(url);
        crawlDataDB.update(crawlDataBuffer);
    }

    @Override
    public void run() {
        crawl(baseURL);
        if (crawlDataBuffer.getStatus() == CrawlStatus.ACTIVE) {
            crawlDataBuffer.setStatus(CrawlStatus.DONE);
        }
    }
}
