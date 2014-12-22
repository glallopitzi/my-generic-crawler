package org.appnest.databuilder.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appnest.databuilder.utils.DataBuilderConfigFileReader;
import org.appnest.databuilder.utils.DataBuilderConstants;

import com.google.inject.Inject;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class DataCrawler{
	
	@SuppressWarnings("unused")
	private DataCrawlerWorker dataCrawlerWorker;
	private DataCrawlerConfig dataCrawlerConfig;
	private List<String> seedList;
	
	private Logger logger = Logger.getLogger(DataCrawler.class);
	
	
	@Inject
	public DataCrawler(DataCrawlerWorker dataCrawlerWorker,
			DataCrawlerConfig dataCrawlerConfig) {
		super();
		this.dataCrawlerWorker = dataCrawlerWorker;
		this.dataCrawlerConfig = dataCrawlerConfig;
	}


	public void init(){
		dataCrawlerConfig.getConfigFromConstants();
		
		seedList = new ArrayList<String>();
		try {
			seedList = DataBuilderConfigFileReader.readListFromFile(DataBuilderConstants.SEEDS_LIST_FILE_PATH);
		} catch (IOException e) {
			logger.error("some error during seed parsing", e);
			e.printStackTrace();
		}
	}
	
	
	public void crawlData(){
		PageFetcher pageFetcher = new PageFetcher(dataCrawlerConfig);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		
		CrawlController controller = null;
		try {
			controller = new CrawlController(dataCrawlerConfig, pageFetcher, robotstxtServer);
		} catch (Exception e) {
			logger.error("some error during controller initialization", e);
			e.printStackTrace();
		}
		
		if (seedList.size() > 0) {
			for (String seedItem : seedList) {
				controller.addSeed(seedItem);
				controller.setCustomData(seedList);
				logger.info("added seed: " + seedItem);
			}
		}
		
		controller.start(DataCrawlerWorker.class, DataBuilderConstants.CRAWLER_THREAD);
	}
	
}
