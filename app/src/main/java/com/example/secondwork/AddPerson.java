package com.example.secondwork;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddPerson extends AppCompatActivity {

    ImageView Img;
    Bitmap bitmap = null, b;
    EditText ID, Fname, Lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        Img = findViewById(R.id.addPersonImg);
       b = BitmapFactory.decodeResource(AddPerson.this.getResources(), R.drawable.e);

        Fname = findViewById(R.id.addPersonFname);
        Lname = findViewById(R.id.addPersonLname);
        Img.setImageBitmap(b);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    Img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void DataPost(View v){

        EncodeImg ei = new EncodeImg();
        String fname = Fname.getText().toString();
        String lname = Lname.getText().toString();
        String image = ei.Image(bitmap);

        postData(fname, lname, image, v);
    }

    private void postData(String fName, String lName, String image, View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/КонстантиновАС/api/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);


        Persons modal = new Persons(null, fName, lName,  image);
        Call<Persons> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Persons>() {
            @Override
            public void onResponse(Call<Persons> call, Response<Persons> response) {
                Toast.makeText(AddPerson.this, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }

            @Override
            public void onFailure(Call<Persons> call, Throwable t) {
                Toast.makeText(AddPerson.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}







