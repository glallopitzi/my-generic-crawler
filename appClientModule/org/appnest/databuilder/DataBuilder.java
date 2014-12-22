package org.appnest.databuilder;

import java.util.Date;

import org.apache.log4j.Logger;
import org.appnest.databuilder.appdata.RecipeCooker;
import org.appnest.databuilder.cooker.DataCooker;
import org.appnest.databuilder.crawler.DataCrawler;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DataBuilder{

	private DataCrawler dataCrawler;
	private DataCooker dataCooker;
	
	private Logger logger = Logger.getLogger(DataBuilder.class);
	
	@Inject
	public DataBuilder(DataCrawler dataCrawler, DataCooker dataCooker){
		this.dataCrawler = dataCrawler;
		this.dataCooker = dataCooker;
	}
	
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
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Injector injector = Guice.createInjector(new DataBuilderModule());
		DataBuilder dataBuilder = injector.getInstance(DataBuilder.class);
		dataBuilder.init();
		dataBuilder.buildData();

	}
	
}
