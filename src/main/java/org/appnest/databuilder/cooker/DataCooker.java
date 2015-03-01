package org.appnest.databuilder.cooker;

import edu.uci.ics.crawler4j.crawler.Page;

public interface DataCooker {
	public void cookPage(Page page);
	public void cookData();
}
