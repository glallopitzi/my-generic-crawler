package org.appnest.databuilder;

import org.appnest.databuilder.appdata.RecipeCooker;
import org.appnest.databuilder.cooker.DataCooker;

import com.google.inject.AbstractModule;

public class DataBuilderModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataCooker.class).to(RecipeCooker.class);
		
	}

}
