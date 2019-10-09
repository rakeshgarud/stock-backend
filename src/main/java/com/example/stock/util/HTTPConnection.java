package com.example.stock.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HTTPConnection {

	public static JSONObject send(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpGet httpGet = new HttpGet(url);

		// Make the request.
		HttpResponse response = httpClient.execute(httpGet);
		JSONObject jsonObject = null;
		// Process the result
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			String response_string = EntityUtils.toString(response.getEntity());
			jsonObject = (JSONObject) new JSONTokener(response_string).nextValue();
		}
		return jsonObject;
	}
}
