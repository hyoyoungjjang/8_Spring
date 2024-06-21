package com.kh.openData.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class testController {

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/earthquake-shelters")
    public String getEarthquakeShelters(Model model) {
        String url = "http://apis.data.go.kr/1741000/TsunamiShelter4";
        
        // Decode the API key
        String decodedApiKey = "	\r\n" + 
        		"XAXI2HBQTWMef6laPKHyY4czlXT8sqALvqpzwh6Ay3KKowKGPjtJ9Q+sOeii4so9TR+ik0FKAWmlQyYPtVAy8A==";
        String params = "?serviceKey=" + decodedApiKey + "&pageNo=1&numOfRows=2";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url + params, String.class);

        model.addAttribute("response", response.getBody());
        return "test";
    }
}
