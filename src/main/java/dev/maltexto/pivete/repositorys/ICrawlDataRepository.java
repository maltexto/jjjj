package dev.maltexto.pivete.repositorys;

import dev.maltexto.pivete.entities.CrawlData;

public interface ICrawlDataRepository {
    CrawlData findById(String id);

    void save(CrawlData data);

    void update(CrawlData data);
}
