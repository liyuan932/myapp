package com.mycompany.myapp.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration("classpath:spring-bean.xml")
public class BaseTest extends AbstractTestNGSpringContextTests {
	public static void main(String[] args) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet("http://www.biqugezw.com/3_3096/");

			System.out.println("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity, "gb2312") : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(html2Text(responseBody));
			// System.out.println(responseBody);

			Matcher requstMatcher = Pattern.compile("<dd><a href=\"(.*)\">(.*)</a></dd>").matcher(responseBody);
			while (requstMatcher.find()) {
				System.out.println(requstMatcher.group(1));
				System.out.println(requstMatcher.group(2));
			}

			FileWriter writer = new FileWriter("c:/a.txt");
			writer.write(responseBody);
			writer.flush();
			writer.close();

		} finally {
			httpclient.close();
		}
	}

	public static String html2Text(String html) {
		try {
			Parser parser = Parser.createParser(html, "utf-8");
			TextExtractingVisitor visitor = new TextExtractingVisitor();
			parser.visitAllNodesWith(visitor);
			return visitor.getExtractedText();
		} catch (Exception ex) {
			return null;
		}
	}
}
