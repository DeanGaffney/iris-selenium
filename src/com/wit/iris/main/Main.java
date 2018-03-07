package com.wit.iris.main;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wit.iris.functional.pages.Page;
import com.wit.iris.functional.pages.guitar.MusicStorePage;
import com.wit.iris.functional.pages.guitar.ThomannPage;
import com.wit.iris.functional.webdrivers.DriverFactory;


public class Main {
	private static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTdjBcL2JRQlIrRG9rb1FpcFFpVW9NZENuZGtDUEJtS21ndEJLeVFrV2FoVXF0THZiREhKenZ6TjBaa2dWbGdpRURxQzFTSlZaR1wvaE5ZK2dkVVpXQmw3dHAzaHVEQWdyakpmdmY1K1wvVjhmZ01WbzJFeDFvd0w0NmNpaTduMFRhcTVqQTJHbWVhMjYyY0dkWVEyUjN6TWdTMmF3TzN4U3VBRlVPS1JoVmZCRnR0bFZjRmtYRjF0YjJGb2F4ME5DMHJIZDR3Ym1pVzRwXC9TMmY4OGRLbzBQQkFwcTc2d0VvK3N3eGNKUVpkSTJsS3gzVXE0eFdvZkpZaGFvY051TnBrTzZRV2s1RTJZWU9vcVN0UVZHQVl5enpHNHFVdVZvTEV6Y21zMHNGOVVtMmxvQUwxSm1ETGw3bEtScG5YVjM3MnhLU3JBRCsxRHVwQjRkNnU2ZGdcL3FPeDE5V1FsQnFycVNaYThsRVJYeURPM0hpNzgxK1wvMzEwMm11VkFLaVQrYWVcL0tlWXpTOUM3K1BydlRWNjBGMXA0UFdTOWdOVTZLYm1aS3BnXC9hM1RLZjM1OStuRnljXC9obGhKUWQ0c1B6OXpIM1wvcTY1N3JKS1VxYVpWVU03SXRxOXNuc204cVdueVFkYjZQcE5ucVFDNlkrU0ZxTjdpWUtZNHBhMUVvTytMWXl0clFiMWI2MW1mYzI5VlZpVWNFbWlMXC9QTWJsbCtvR2hWXC9ldmp5Nk8zZjRsZ0JTcTdUR1JJbFU4V29FYVd0RkVmbkpcL01qdis4NnVjQkJqXC96ZjFtZmFkb1FBd0FBIiwic3ViIjoiYWRtaW4iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNTIwNDI2NDM4LCJpYXQiOjE1MjA0MjI4Mzh9.D-Ky6RgRQZrbfd4I5W642cV2ymJXwlWSIUG-rVWBo3o";

	public static void main(String[] args) {
	
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
					HttpPost request = new HttpPost(getAgentUrl());
					StringEntity jsonBody = new StringEntity(page.getJson());
					request.setEntity(jsonBody);
					request.setHeader("Content-type", "application/json");
					request.setHeader("X-Auth-Token", JWT);
					HttpResponse response = httpClient.execute(request);
					System.out.println(getJsonResponse(response));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

		}catch(Throwable e) {
			System.out.println(e);
		}finally {
			close();
		}
	}

	static String getAgentUrl() throws Exception{
		String agentAddress = "http://ec2-52-16-53-220.eu-west-1.compute.amazonaws.com:8080/iris/schema/getAgentUrl";
		JsonObject json = new JsonObject();
		json.addProperty("name", "selenium_agent");
		String url = "";
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(agentAddress);
		StringEntity jsonBody = new StringEntity(json.toString());
		request.setEntity(jsonBody);
		request.setHeader("Content-type", "application/json");
		request.setHeader("X-Auth-Token", JWT);
		HttpResponse response = httpClient.execute(request);
		JsonObject responseObj = getJsonResponse(response);
		url = responseObj.get("url").getAsString();
		return url;
	}

	static JsonObject getJsonResponse(HttpResponse response) throws Exception{
		HttpEntity responseEntity = response.getEntity();
		String responseBody;
		JsonObject o = null;
		if(responseEntity!=null) {
			responseBody = EntityUtils.toString(responseEntity);
			JsonParser parser = new JsonParser();
			o = parser.parse(responseBody).getAsJsonObject();
		}
		return o;
	}
}
