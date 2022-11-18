package com.ssafy.apartment.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.json.XML;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apartment.model.HouseDealDto;
import com.ssafy.apartment.model.SidoGugunCodeDto;
import com.ssafy.apartment.model.service.HousedealService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/house")
@Api("아파트 컨트롤러  API")
@CrossOrigin(origins= "*")
public class HousedealController {

	private HousedealService housedealService; // 관심지역

	@Autowired
	public HousedealController(HousedealService housedealService) {
		this.housedealService = housedealService;
	}

	private static final Logger logger = LoggerFactory.getLogger(HousedealController.class);

	@ApiOperation(value = "시도 정보", notes = "전국의 시도를 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.info("sido - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(housedealService.getSido(), HttpStatus.OK);
	}

	@ApiOperation(value = "구군 정보", notes = "선택한 시도의 구군을 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SidoGugunCodeDto>>(housedealService.getGugunInSido(sido), HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 목록", notes = "구군코드를 기준으로 아파트 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/gugunAptList/{gugunCode}")
	public ResponseEntity<?> gugunAptList(@PathVariable("gugunCode") String gugunCode) throws Exception {
		List<HouseDealDto> list = housedealService.gugunAptList(gugunCode);
		return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트 거래 목록", notes = "선택한 아파트의 2021년 거래 목록을 반환한다.", response = List.class)
	@GetMapping(value = "/AptDealList/{aptCode}")
	public ResponseEntity<?> AptDealList(@PathVariable("aptCode") String aptCode) throws Exception {
		List<HouseDealDto> list = housedealService.AptDealList(aptCode);
		return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.OK);
	}

//	@ApiOperation(value = "아파트 거래 목록", notes = "지역코드와 매매계약월을 기준으로 아파트 목록을 반환한다.", response = List.class)
//	@GetMapping(value = "/aptlist/{gugunCode}/{ymd}", produces = "application/json;charset=utf-8")
//	public ResponseEntity<String> aptList(@PathVariable("gugunCode") String gugunCode, @PathVariable("ymd") String ymd) throws IOException {
//		logger.info("sido - 호출");
//		String serviceKey = "4HyUqpw9G2XYzMYieXUVJGZ78J6OHIllmLEfvofTQ4cxYHg%2Bk8ICFXxpcHDa1qJxCwMFNEBR7ReV4Dbvn%2FBJ3Q%3D%3D";
//		StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); 
//		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
//		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
//		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /* 한 페이지 결과 수 */
//		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "=" + URLEncoder.encode(gugunCode, "UTF-8")); /* 지역코드 */
//		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "=" + URLEncoder.encode(ymd, "UTF-8")); /* 계약월 */
//		
//		URL url = new URL(urlBuilder.toString());
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-type", "application/json");
//		System.out.println("Response code: " + conn.getResponseCode());
//		BufferedReader rd;
//		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		} else {
//			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		}
//		StringBuilder sb = new StringBuilder();
//		String line;
//		while ((line = rd.readLine()) != null) {
//			sb.append(line);
//		}
//		rd.close();
//		conn.disconnect();
//		System.out.println(sb.toString());
//		JSONObject json = XML.toJSONObject(sb.toString());
//		String jsonStr = json.toString();
//
//		return new ResponseEntity<String>(jsonStr, HttpStatus.OK);
//	}
	
}
