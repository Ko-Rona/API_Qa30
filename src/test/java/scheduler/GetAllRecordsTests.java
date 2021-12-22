package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.GetAllRecordsDto;
import schedulerdto.GetRecordsRequestDto;
import schedulerdto.RecordDto;

import static com.jayway.restassured.RestAssured.given;

public class GetAllRecordsTests {

    String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImhvZ0BnbWFpbC5jb20ifQ.64EpedlrKYcpAoxm05uAZfH0GD9fw7O7QhGF4wAEvdU";
    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void getAllRecords(){
        GetRecordsRequestDto requestDto = GetRecordsRequestDto.builder()
                .monthFrom(5)
                .monthTo(12)
                .yearFrom(2021)
                .yearTo(2021)
                .build();

        GetAllRecordsDto records = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when()
                .post("records")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(GetAllRecordsDto.class);

        for (RecordDto record : records.getRecords() ) {
            System.out.println(record.getId());
            System.out.println("=====");
        }
    }

}
