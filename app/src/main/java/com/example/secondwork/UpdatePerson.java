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
import android.widget.TextView;
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

public class UpdatePerson extends AppCompatActivity {

    Bundle arg;
    Persons person;
    EditText fname, lname;
    TextView id;
    ImageView img;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);

        arg = getIntent().getExtras();
        person = arg.getParcelable(Persons.class.getSimpleName());

        id = findViewById(R.id.editTextUpId);
        fname = findViewById(R.id.editTextUpFname);
        lname = findViewById(R.id.editTextUpLname);
        img = findViewById(R.id.upImg);

        id.setText(Integer.toString(person.getID()));
        fname.setText(person.getFname());
        lname.setText(person.getLname());

        DecodeImg decodeImageClass = new DecodeImg(UpdatePerson.this);
        Bitmap userImage = decodeImageClass.getImg(person.getImage());
        img.setImageBitmap(userImage);
        if (!person.getImage().equals("null")) {
            bitmap = userImage;
        }
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void SelectPhoto(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void Update(View v) {
        person.setID(Integer.parseInt(id.getText().toString()));
        person.setFname(fname.getText().toString());
        person.setLname(lname.getText().toString());


        EncodeImg encodeImageClass = new EncodeImg();
        person.setImg(encodeImageClass.Image(bitmap));
        DataPutDelete(person, 0, "Данные успешно изменены", v);
    }

    public void Delete(View v) {
        DataPutDelete(person, 1, "Запись успешно удалена", v);
    }

    public void GoViewData(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void DataPutDelete(Persons person, int number, String str, View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/КонстантиновАС/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Persons> call = null;
        switch (number) {
            case 0:
                RetrofitApiPut retrofitAPI = retrofit.create(RetrofitApiPut.class);
                call = retrofitAPI.createPut(person, person.getID());
                break;
            case 1:
                RetrofitApiDelete retrofitAPIs = retrofit.create(RetrofitApiDelete.class);
                call = retrofitAPIs.createDelete(person.getID());
                break;
            default:
                break;
        }
        call.enqueue(new Callback<Persons>() {
            @Override
            public void onResponse(Call<Persons> call, Response<Persons> response) {
                Toast.makeText(UpdatePerson.this, str, Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }

            @Override
            public void onFailure(Call<Persons> call, Throwable t) {
                Toast.makeText(UpdatePerson.this, "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

