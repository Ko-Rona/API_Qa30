package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.GetAllContactsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpGetAllContacts {

    final String URL = "https://contacts-telran.herokuapp.com/api/";
    final String  TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imtvcm9uYTE1MDRAZ21haWwuY29tIn0.ntJem4O2Jk_NXiuKbaqXbTBni35L8JhUb0_ThLJ_iRI";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void getAllContactTest() throws IOException {

        Request request = new Request.Builder()
                .url(URL+"contact")
                .addHeader("Authorization", TOKEN)
                .build();

        Response response = client.newCall(request).execute();

        GetAllContactsDto contacts = gson.fromJson(response.body().string(), GetAllContactsDto.class);
        Assert.assertTrue(response.isSuccessful());

        for(ContactDto contact: contacts.getContacts()){
            System.out.println(contact.getId());
            System.out.println(contact.getEmail());
            System.out.println("================");
        }
    }
}
