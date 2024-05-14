package com.kh.openData.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Run {
	
	public static final String SERVICE_KEY = "NJjR8SW1hUotqk3FaHIe6FsoyxT20yVMBE3JAvqiP3yIoaJam8KmZcCw8%2BAVx%2FKUY5h%2FCxIDnkSVy3bkUJfCTg%3D%3D";
	
	public static void main(String[] args) throws IOException {
		
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICE_KEY;
		url += "&returnType=json";
		url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8"); //요청시 전달값에 한글이 있으면 인코딩 작업을 해줘야한다.
		
//		System.out.println(url);
		
		//자바코드로 서버에서 서버로 요청을 보내야함
		// ** HTTpURLConnection객체를 이용해서 요청을 보내자
		
		//1. 요청하고자하는 url을 전달하면서 java.net.URL객체 생성
		URL requestURL = new URL(url);
		//2. 만들어진 URL객체를 가지고 HTTpURLConnection객체 생성
		HttpURLConnection urlConnection = (HttpURLConnection)requestURL.openConnection();
		//3. 요청에 필요한 Header설정하기
		urlConnection.setRequestMethod("GET");
		//4. 해당 api서버로 요청 보낸후 입력데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine()) != null ) {
			responseText += line;
		}
		
		//각각의 item 정보 -> JSON형식으로 변환
		//JSONObject, JSONArray -> json라이브러러리에서 제공하는 객체들
		//JsonObject, JsonArray -> Gson에서 제공
		
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		JsonObject responseObj = totalObj.getAsJsonObject("response");
		JsonObject bodyObj = responseObj.getAsJsonObject("body");
		System.out.println(bodyObj);
		
		int totalCount = bodyObj.get("totalCount").getAsInt();		
		JsonArray itemArr = bodyObj.getAsJsonArray("items");
		System.out.println(itemArr);
		
		for(int i = 0; i < itemArr.size(); i++) {
			JsonObject item = itemArr.get(i).getAsJsonObject(); // 0번째 구의 미세먼지 정보를 Json객체
			System.out.println("측정소명 : " + item.get("stationName").getAsString());
			System.out.println("측정일시 : " + item.get("dataTime").getAsString());
			System.out.println("미세먼지 농도 : " + item.get("pm10Value").getAsString());
		}
	}
}
