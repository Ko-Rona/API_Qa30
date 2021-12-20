package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpAddContact {

    final String URL = "https://contacts-telran.herokuapp.com/api/";
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imtvcm9uYTE1MDRAZ21haWwuY29tIn0.ntJem4O2Jk_NXiuKbaqXbTBni35L8JhUb0_ThLJ_iRI";
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void addContactTestPositive() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address89")
                .description("description88")
                .email("email94@mail.com")
                .id(0)
                .lastName("lastName88")
                .name("name88")
                .phone("11223394")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", TOKEN)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ContactDto contactDto = gson.fromJson(response.body().string(), ContactDto.class);

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(contactDto.getEmail(), "email94@mail.com");
        Assert.assertEquals(contactDto.getPhone(), "11223394");

    }

    @Test
    public void addContactTestNegative409_500() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address88")
                .description("description88")
                .email("email90@mail.com")
                .id(22)
                .lastName("lastName88")
                .name("name88")
                .phone("11223390")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", TOKEN)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 500);
        Assert.assertTrue(errorDto.getMessage().contains("could not execute statement"));
    }

    @Test
    public void addContactTestNegative400() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address88")
                .description("description88")
                .email("")
                .id(91)
                .lastName("lastName88")
                .name("name88")
                .phone("4555455")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", TOKEN)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong contact format!"));
    }

    @Test
    public void addContactTestNegative401() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address88")
                .description("description88")
                .email("")
                .id(0)
                .lastName("lastName88")
                .name("name88")
                .phone("4555455")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", "hdhdfhdh")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong token format!"));
    }

    @Test
    public void addContactTestNegative401_2() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address88")
                .description("description88")
                .email("")
                .id(0)
                .lastName("lastName88")
                .name("name88")
                .phone("4555455")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", "")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong authorization!"));
    }

}
