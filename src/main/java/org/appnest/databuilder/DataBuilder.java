package org.appnest.databuilder;

import java.util.Date;

import org.apache.log4j.Logger;
import org.appnest.databuilder.cooker.DataCooker;
import org.appnest.databuilder.crawler.DataCrawler;
import org.springframework.beans.factory.annotation.Autowired;

public class DataBuilder{

	private Logger logger = Logger.getLogger(DataBuilder.class);
	
	@Autowired
	private DataCrawler dataCrawler;
	
	@Autowired
	private DataCooker dataCooker;
	
	
	public void init(){
		logger.info("in DataBuilder init()");
		dataCrawler.init();
//		dataCooker.init();
	}

	
	public void buildData(){
		// crawling session
		logger.info("starting crawling session " + new Date());
		dataCrawler.crawlData();
		logger.info("stopping crawling session " + new Date());
		
		// cooking session
//		logger.info("starting cooking session " + new Date());
//		dataCooker.cookData();
//		logger.info("stopping cooking session " + new Date());
	}
	
}
