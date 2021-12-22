package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthRequestDto;
import schedulerdto.AuthResponseDto;
import schedulerdto.ErrorDto;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class LoginRegistrationRestAssered {

    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginSuccess() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("hog@gmail.com")
                .password("12345hoSg!")
                .build();

        AuthResponseDto responseDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println(responseDto.getToken());
        System.out.println(responseDto.getStatus());
    }


    @Test
    public void wrongPasswordTest() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("hog@gmail.com")
                .password("12345hoS")
                .build();

        ErrorDto errorDto = given().body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);

        Assert.assertEquals(errorDto.getMessage(), "Wrong email or password");
    }

    @Test
    public void wrongPasswordTest2() {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("hog@gmail.com")
                .password("12345hoS")
                .build();

        String message = given().body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().path("message");

        Assert.assertEquals(message, "Wrong email or password");

    }

    @Test
    public void logTest() {

        AuthRequestDto auth = AuthRequestDto.builder()
                .email("hog@gmail.com")
                .password("12345hoSg!")
                .build();
        String token = given().contentType(ContentType.JSON).body(auth)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status", containsString("Login success"))
                .assertThat().body("registration", equalTo(false))
                .extract().path("token");

        System.out.println(token);
    }


}
