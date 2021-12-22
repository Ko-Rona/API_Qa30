package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.DeleteDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpDeleteContactById {

    final String URL = "https://contacts-telran.herokuapp.com/api/";
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imtvcm9uYTE1MDRAZ21haWwuY29tIn0.ntJem4O2Jk_NXiuKbaqXbTBni35L8JhUb0_ThLJ_iRI";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    int id;

    @BeforeMethod
    public void prRequest() throws IOException {

        ContactDto requestContact = ContactDto.builder()
                .address("address89")
                .description("description88")
                .email("email95@mail.com")
                .id(0)
                .lastName("lastName88")
                .name("name88")
                .phone("11223395")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestContact), JSON);

        Request request = new Request.Builder()
                .url(URL + "contact")
                .addHeader("Authorization", TOKEN)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ContactDto contactDto = gson.fromJson(response.body().string(), ContactDto.class);
        id = contactDto.getId();
    }

    @Test
    public void deleteByIdTest() throws IOException {
        Request request = new Request.Builder()
                .url(URL + id)
                .delete()
                .addHeader("Authorization", TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.message());
        Assert.assertTrue(response.isSuccessful());
        DeleteDto deleteDto = gson.fromJson(response.body().string(), DeleteDto.class);
        Assert.assertEquals(deleteDto.getStatus(), "Contact was deleted!");

    }

    @Test
    public void deleteContactTestByIdPositive() throws IOException {
        String id = "21342";
        Request request = new Request.Builder()
                .url(URL + "contact/" + id)
                .addHeader("Authorization", TOKEN)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        DeleteDto deleteDto = gson.fromJson(response.body().string(), DeleteDto.class);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(deleteDto.getStatus(), "Contact was deleted!");
    }

    @Test
    public void deleteContactTestByIdNegative404() throws IOException {
        String id = "21342";
        Request request = new Request.Builder()
                .url(URL + "contact/" + id)
                .addHeader("Authorization", TOKEN)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 404);
        Assert.assertEquals(errorDto.getMessage(), "Contact with id: " + id + " not found!");
    }

    @Test
    public void deleteContactTestByIdNegative401() throws IOException {
        String id = "21342";
        Request request = new Request.Builder()
                .url(URL + "contact/" + id)
                .addHeader("Authorization", "")
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong authorization!"));
    }

    @Test
    public void deleteContactTestByIdNegative401_2() throws IOException {
        String id = "21342";
        Request request = new Request.Builder()
                .url(URL + "contact/" + id)
                .addHeader("Authorization", "TOKEN")
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getCode(), 401);
        Assert.assertTrue(errorDto.getMessage().contains("Wrong token format!"));
    }

}
