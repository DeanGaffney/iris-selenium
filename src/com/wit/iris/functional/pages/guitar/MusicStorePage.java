package com.wit.iris.functional.pages.guitar;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;
import com.wit.iris.functional.pages.Page;
import com.wit.iris.functional.pages.Wait;

public class MusicStorePage extends Page{
	
	public static final String MATT_HEAF_URL = "https://www.musicstore.de/en_IE/EUR/Epiphone-Matt-Heafy-Les-Paul-Custom-/art-GIT0026992-000";
	
	private static final String IDENTIFIER = ".kor-priceContainer > meta[itemprop='price']";
	private static final String PRICE_BOX = IDENTIFIER;

	public MusicStorePage() {
		super(IDENTIFIER, Condition.exist, Wait.SHORT);
		setSiteName("Music Store");
	}
	
	public MusicStorePage setPriceFromPage() {
		String priceStr = $(PRICE_BOX).attr("content").trim();
		setPrice(Double.parseDouble(priceStr));
		return this;
	}

}
