package com.wit.iris.functional.pages.guitar;

import com.codeborne.selenide.Condition;
import com.wit.iris.functional.pages.Page;
import com.wit.iris.functional.pages.Wait;
import static com.codeborne.selenide.Selenide.$;

public class ThomannPage extends Page{
	
	public static final String MATT_HEAFY_URL = "https://www.thomann.de/ie/epiphone_les_paul_matt_heafy_6_string.htm?ref=search_rslt_matt+heafy_306944_1";
	private static final String IDENTIFIER = "span[itemprop='price']";
	private static final String PRICE_BOX = IDENTIFIER;
	
	public ThomannPage() {
		super(IDENTIFIER, Condition.visible, Wait.SHORT);
		setSiteName("Thomann");
	}
	
	public ThomannPage setPriceFromPage() {
		String priceStr = $(PRICE_BOX).text().trim().replace("€", "");
		setPrice(Double.parseDouble(priceStr));
		return this;
	}

}
