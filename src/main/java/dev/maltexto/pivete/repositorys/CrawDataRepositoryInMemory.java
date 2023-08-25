package dev.maltexto.pivete.repositorys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.maltexto.pivete.entities.CrawlData;

public class CrawDataRepositoryInMemory implements ICrawlDataRepository {
    private final Map<String, CrawlData> inMemoryDB;

    public CrawDataRepositoryInMemory() {
        this.inMemoryDB = new ConcurrentHashMap<>();
    }

    @Override
    public CrawlData findById(String id) {
        return inMemoryDB.get(id);
    }

    @Override
    public void save(CrawlData data) {
        String id = data.getId();
        inMemoryDB.put(id, data);
    }

    @Override
    public void update(CrawlData data) {
        save(data);
    }
}
