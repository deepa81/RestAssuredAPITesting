package util;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class APIGetRequest {


    private static RequestSpecification requestWithoutParam(String url){
        return RestAssured
                                                        .given()
                                                        .contentType(ContentType.JSON)
                                                        .baseUri(url);

    }
    private static RequestSpecification requestWithParameter(String url, HashMap<String, String> paramMap){
        return RestAssured
                                                        .given()
                                                        .contentType(ContentType.JSON)
                                                        .queryParams(paramMap)
                                                        .baseUri(url);
    }
    private static void logRequest(RequestSpecification reqSpec){
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(reqSpec);
        ExtentReportManager.logInfoDetails("End point is  :"+queryableRequestSpecification.getBaseUri());

    }
    private static void logResponse(Response Response){
        ExtentReportManager.logInfoDetails("Response Status code :"+Response.statusCode());
        ExtentReportManager.logInfoDetails("Response body :"+Response.body().prettyPrint());

    }
    public Response getAPIWithNoParam(String url){

        RequestSpecification requestSpecification = APIGetRequest.requestWithoutParam(url);
        APIGetRequest.logRequest(requestSpecification);
        Response res= requestSpecification.get()
                .then()
                    .extract().response();
        APIGetRequest.logResponse(res);
        return res;
    }
    public Response getAPIWithQueryParamAndStatusCode(String url, HashMap<String, String> paramMap, int statuscode){

        RequestSpecification requestSpecification = APIGetRequest.requestWithParameter(url,paramMap);
        APIGetRequest.logRequest(requestSpecification);
        Response res= requestSpecification.get()
                            .then()
                                .statusCode(statuscode)
                                .extract().response();
        APIGetRequest.logResponse(res);
        return res;
    }
}
