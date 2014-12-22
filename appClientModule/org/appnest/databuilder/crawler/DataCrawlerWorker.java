package org.appnest.databuilder.crawler;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.appnest.databuilder.DataBuilder;
import org.appnest.databuilder.DataBuilderModule;
import org.appnest.databuilder.appdata.RecipeCooker;
import org.appnest.databuilder.cooker.DataCooker;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class DataCrawlerWorker extends WebCrawler {
	
	private Logger logger = Logger.getLogger(DataCrawlerWorker.class);
	
	/**
	 * Outgoing links to exclude
	 */
	private final static Pattern FILTERS = Pattern
	.compile(".*(\\.(css|js|bmp|gif|jpe?g"
			+ "|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	
	private RecipeCooker recipeCooker;
	
	private static int visitCounter = 0;
	private List<String> myCrawlDomains;
	
	
	public DataCrawlerWorker() {
		super();
		// TODO check this if i could do better
		Injector injector = Guice.createInjector(new DataBuilderModule());		
		this.recipeCooker = injector.getInstance(RecipeCooker.class);
	}

	@SuppressWarnings("unchecked")
	@Override
    public void onStart() {
            this.myCrawlDomains = (List<String>) this.myController.getCustomData();
	}
	
	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		if (FILTERS.matcher(href).matches()) return false;
		for (String crawlDomain : myCrawlDomains) {
            if (href.startsWith(crawlDomain)) return true;
		}
		return false;
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@SuppressWarnings("static-access")
	@Override
	public void visit(Page page) {
		this.visitCounter++;
		
		String url = page.getWebURL().getURL();
		String domain = page.getWebURL().getDomain();
		String subDomain = page.getWebURL().getSubDomain();
		logger.info("URL: " + url);

		if (StringUtils.isNotBlank(subDomain)) domain = subDomain + "." + domain;
		
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			String title = htmlParseData.getTitle();
			logger.info("Title: " + title);
			
			boolean possibleRecipe = this.recipeCooker.checkIfPossibleRecipe(html, domain);
			logger.info("Check if possible recipe: " + possibleRecipe);
			
			if(possibleRecipe){
				this.recipeCooker.cookPage(page);
				
			}else{
				logger.info("Skipped element: " + title);
			}
		}else{
			logger.info("Skipped URL: " + url);
		}
		
		logger.info("=============");
	}

	
	
	@SuppressWarnings("static-access")
	@Override
	public void onBeforeExit() {
		super.onBeforeExit();
		logger.info("Visited URLs: " + this.visitCounter);
//		logger.info("Recipes found: " + this.recipeCooker.getRecipes().size());
	}
	
	
}
