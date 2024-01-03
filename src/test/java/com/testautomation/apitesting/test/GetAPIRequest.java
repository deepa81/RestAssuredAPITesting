package com.testautomation.apitesting.test;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.messages.internal.com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.junit.Assert;
import org.testng.annotations.Test;
import util.APIGetRequest;
import util.BaseTest;
import util.ExtentReportManager;

import java.util.LinkedHashMap;

public class GetAPIRequest extends BaseTest {
    APIGetRequest apiGetRequest= new APIGetRequest();
    String url="https://www.alphavantage.co/query";

    /*
Given API, pls write a test which ensures that totalRevenue of the quarterlyReport for fiscalDateEnding 2022-06-30 equals to 15535000000.
Please note, that test should fail if the respective quarterlyReport for the date is not present in the response.

https://www.alphavantage.co/query?function=INCOME_STATEMENT&symbol=IBM&apikey=demo

  * */
@Test
    public void getReq(){
    System.out.println("Test 1");
    LinkedHashMap<String, String > paramlist = new LinkedHashMap<>();
    paramlist.put("function","INCOME_STATEMENT");
    paramlist.put("symbol","IBM");
    paramlist.put("apikey","demo");

    Response res=apiGetRequest.getAPIWithQueryParamAndStatusCode(url, paramlist,200);

  //  System.out.println(res.getBody().asString());
    JSONArray fiscalDateEnding=JsonPath.read(res.body().asString(),"$.quarterlyReports..[?(@.fiscalDateEnding ==\"2022-06-30\")].totalRevenue");
    String Actual = (String)fiscalDateEnding.get(0);
    ExtentReportManager.logResultDetails(Actual.equals("15535000000"), "Acutal = "+Actual + " Expected = 15535000000");

    }

    @Test
    public void actualRequest(){


        Response res= RestAssured
                .given()
                .contentType(ContentType.JSON)
                .param("function","INCOME_STATEMENT")
                .param("symbol","IBM")
                .param("apikey","demo")
                .baseUri(url)
                .when()
                .get()
                .then()
                .extract().response();
        System.out.println("Test 2");
    }
}
