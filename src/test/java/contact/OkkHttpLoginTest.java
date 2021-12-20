package contact;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkkHttpLoginTest {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    final String URL = "https://contacts-telran.herokuapp.com/api/";

    @Test
    public void loginTestPositive() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("korona1504@gmail.com")
                .password("KoronA10$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url(URL + "login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertTrue(responseDto.toString().contains("token"));

    }

    @Test
    public void loginTestNegativeWrongEmail400() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("korona1504gmail.com")
                .password("KoronA10$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url(URL + "login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format!"));
    }

    @Test
    public void loginTestNegativeWrongPassword400() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("korona1504@gmail.com")
                .password("KoronA10")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url(URL + "login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertTrue(errorDto.getMessage().contains("Password must contain at least one special symbol"));
    }

    @Test
    public void loginTestNegative401() throws IOException {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("uoty@nas.com")
                .password("Qq123456$")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url(URL + "login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password"));
    }

}
