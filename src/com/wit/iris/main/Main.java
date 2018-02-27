package com.wit.iris.main;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.wit.iris.functional.pages.Page;
import com.wit.iris.functional.pages.guitar.MusicStorePage;
import com.wit.iris.functional.pages.guitar.ThomannPage;
import com.wit.iris.functional.webdrivers.DriverFactory;

public class Main {
	
	public static void main(String[] args) {
		
		final String URL = "http://ec2-52-16-53-220.eu-west-1.compute.amazonaws.com:8080/iris/schema/route/20";
		try {
			DriverFactory.setFirefoxDriver();
			
			open(ThomannPage.MATT_HEAFY_URL);
			ThomannPage thomannPage = new ThomannPage().setPriceFromPage();
			
			open(MusicStorePage.MATT_HEAF_URL);
			MusicStorePage musicStore = new MusicStorePage().setPriceFromPage();
			
			List<Page> pages = Arrays.asList(thomannPage, musicStore);
			
			pages.forEach(page -> {
				try {
					HttpClient httpClient = HttpClientBuilder.create().build();
					HttpPost request = new HttpPost(URL);
					StringEntity jsonBody = new StringEntity(page.getJson());
					request.setEntity(jsonBody);
					request.setHeader("Content-type", "application/json");
					HttpResponse response = httpClient.execute(request);
					System.out.println(response.toString());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		}catch(Throwable e) {
			System.out.println(e);
		}finally {
			close();
		}
	}
}
