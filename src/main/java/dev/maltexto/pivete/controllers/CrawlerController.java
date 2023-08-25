package dev.maltexto.pivete.controllers;

import static dev.maltexto.pivete.utils.ControllerUtils.*;
import static dev.maltexto.pivete.utils.JsonUtils.*;
import static spark.Spark.*;

import dev.maltexto.pivete.services.CrawlerService;

public class CrawlerController {
    CrawlerService crawlerService;

    public CrawlerController() {
        this.crawlerService = new CrawlerService();
    }

    public void setupRoutes() {
        before("/crawl", (req, res) -> {
            if (!isValidKeyword(req.body())) {
                halt(400, "Bad Request: Invalid request body.");
            }
        });

        before("/crawl/:id", (req, res) -> {

            if (!isAlphanumericAndEightChars(req.params("id"))) {
                halt(400, "Bad Request: Invalid ID.");
            }
        });

        get("/crawl/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return crawlerService.getCrawlResultByID(id);
        });

        post("/crawl", (req, res) -> {
            res.type("application/json");

            String keyword = getValueFromJsonString("keyword", req.body());

            String crawlResultID = crawlerService.crawl(keyword);

            res.status(200);
            return toJson("id", crawlResultID);
        });
    }
}
