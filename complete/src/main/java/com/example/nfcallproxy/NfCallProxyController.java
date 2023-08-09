package com.example.nfcallproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import java.util.HashMap;

@Controller
@RequestMapping(path="/")
@CrossOrigin(origins = "*")
public class NfCallProxyController {

    private static final Logger log = LoggerFactory.getLogger(NfCallProxyController.class);

    @PostMapping(path="/prod-info-recv", consumes="application/json")
    public @ResponseBody HashMap getProdInfoRecv(
        @RequestBody HashMap map 
    ) {

        log.info("map = " + map);

	HashMap respMap = new HashMap();
	respMap.put("result", "OK");

        return respMap;
    }

    @GetMapping(path="/get-product", produces="application/json")
    public @ResponseBody String getProduct(@RequestParam(value = "productCode") String productCode) {

        log.info("getProduct..");

        String url = "https://openapis-loan-dev.pay.naver.com/re-lease"
            + "/api/hf/product/" + productCode;

	log.info("url = " + url);

	try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("partner-key", "hf");
            headers.set("api-key", "9gJCr9N5R9Ur66vaCyzP3Q==");

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	    RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, String.class);

	    String rspsStr = response.getBody();

            log.info(rspsStr.toString());

            return rspsStr;

        } catch (HttpClientErrorException e) {
            return "" + e;
	} catch (HttpServerErrorException e) {
            return "" + e;
        }
    }
}
