package dev.maltexto.pivete.services;

import static dev.maltexto.pivete.utils.JsonUtils.toJson;

import dev.maltexto.pivete.entities.CrawlData;
import dev.maltexto.pivete.repositorys.CrawDataRepositoryInMemory;
import dev.maltexto.pivete.repositorys.ICrawlDataRepository;

public class CrawlerService {

    ICrawlDataRepository crawlDataDB;

    public CrawlerService() {
        this.crawlDataDB = new CrawDataRepositoryInMemory();
    }

    public String crawl(String keyword) {
        CrawlData crawlData = new CrawlData(keyword);

        crawlDataDB.save(crawlData);

        String crawlDataID = crawlData.getId();

        Worker worker = new Worker(crawlDataDB, crawlDataID);

        Thread workerThread = new Thread(worker);
        workerThread.start();

        return crawlData.getId();
    }

    public String getCrawlResultByID(String id) {
        CrawlData crawlData = crawlDataDB.findById(id);
        return toJson(crawlData);
    }
}