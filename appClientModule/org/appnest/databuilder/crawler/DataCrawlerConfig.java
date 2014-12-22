package org.appnest.databuilder.crawler;

import org.appnest.databuilder.utils.DataBuilderConstants;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;

public class DataCrawlerConfig extends CrawlConfig{

	public void getConfigFromConstants(){
		setCrawlStorageFolder(DataBuilderConstants.CRAWLED_DATA_BASE_FOLDER);
		setPolitenessDelay(DataBuilderConstants.CRAWLER_POLITENESS);
		setMaxDepthOfCrawling(DataBuilderConstants.CRAWLER_MAX_DEPTH);
		setMaxPagesToFetch(DataBuilderConstants.CRAWLER_MAX_PAGES);
		setResumableCrawling(DataBuilderConstants.CRAWLER_RESUMABLE);
		setUserAgentString(DataBuilderConstants.CRAWLER_USERAGENT);
	}
	
	public void getConfigFromPropertiesFile(String filename){
		// TODO read a properties file and set relative config
	}
}
