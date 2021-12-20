package contact;

import com.google.gson.Gson;
import dto.DeleteDto;
import dto.GetAllContactsDto;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpDeleteContact {

    final String URL = "https://contacts-telran.herokuapp.com/api/";
    final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imtvcm9uYTE1MDRAZ21haWwuY29tIn0.ntJem4O2Jk_NXiuKbaqXbTBni35L8JhUb0_ThLJ_iRI";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void deleteContactTest() throws IOException {
        Request request = new Request.Builder()
                .url(URL+"clear")
                .addHeader("Authorization", TOKEN)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        DeleteDto deleteDto = gson.fromJson(response.body().string(), DeleteDto.class);

        Assert.assertEquals(deleteDto.getStatus(), "Your contacts was deleted!");
    }
}
