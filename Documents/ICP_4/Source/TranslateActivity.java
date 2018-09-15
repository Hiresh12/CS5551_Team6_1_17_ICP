package tutorial.cs5551.com.translateapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TranslateActivity extends AppCompatActivity {

    String API_URL = "https://api.fullcontact.com/v2/person.json?";
    String API_KEY = "b29103a702edd6a";
    String sourceText;
    TextView outputTextView;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        outputTextView = (TextView) findViewById(R.id.txt_Result);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
          //      R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayList<Language> lst=new ArrayList<>();
        lst.add(new Language("English","en"));
        lst.add(new Language("Spanish","es"));
        lst.add(new Language("Tamil","ta"));
        lst.add(new Language("Telugu","te"));
        lst.add(new Language("Malayalam","ml"));
        lst.add(new Language("German","de"));
        ArrayAdapter<Language> adapter= new ArrayAdapter<Language>(this,android.R.layout.simple_spinner_dropdown_item, lst);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
    }
    public void Logout(View v) {
        //This code redirects the from Translate page to the Login page.
        Intent redirect = new Intent(TranslateActivity.this, LoginActivity.class);
        startActivity(redirect);
    }
    public void translateText(View v) {
        TextView sourceTextView = (TextView) findViewById(R.id.txt_Email);

        sourceText = sourceTextView.getText().toString();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Language FromLang=(Language)spinner.getSelectedItem();
        String fromLang=FromLang.Languagecode;

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Language ToLang =(Language)spinner2.getSelectedItem();
        String toLang=ToLang.Languagecode;
        //toLang=toLang.substring(toLang.length()-2);
        //outputTextView.setText(spinner.getSelectedItem().toString());
        String getURL = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=trnsl.1.1.20151023T145251Z.bf1ca7097253ff7e." +
                "c0b0a88bea31ba51f72504cc0cc42cf891ed90d2&text=" + sourceText +"&" +
                "lang="+fromLang+"-"+toLang+"&[format=plain]&[options=1]&[callback=set]";//The API service URL
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        JSONArray convertedTextArray = jsonResult.getJSONArray("text");
                        final String convertedText = convertedTextArray.get(0).toString();
                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                outputTextView.setText(convertedText);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());

        }

    }
}
 class Language {
    public String LanguageName;
    public String Languagecode;
     public Language(String LanguageName, String Languagecode) {
         this.LanguageName = LanguageName;
         this.Languagecode = Languagecode;
     }
    @Override
    public String toString() {
        return LanguageName;
    }
}