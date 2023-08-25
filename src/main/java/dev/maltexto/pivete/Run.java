package dev.maltexto.pivete;

import dev.maltexto.pivete.controllers.CrawlerController;

public class Run {
    public static void main(String[] args) {
        CrawlerController crawlerController = new CrawlerController();
        crawlerController.setupRoutes();
    }
}
