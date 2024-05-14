package com.kh.openData.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {
	public static final String SERVICE_KEY = "NJjR8SW1hUotqk3FaHIe6FsoyxT20yVMBE3JAvqiP3yIoaJam8KmZcCw8%2BAVx%2FKUY5h%2FCxIDnkSVy3bkUJfCTg%3D%3D";
	
	@ResponseBody
	@RequestMapping(value="air", produces="application/json; charset=UTF-8")
	public String getAirInfo(String location) throws IOException {
		
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&returnType=json";
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		
		URL requestURL = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
		urlConnection.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null ) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();
		
		return responseText;
	}
	
	
}
