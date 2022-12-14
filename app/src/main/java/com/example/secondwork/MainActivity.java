package com.example.secondwork;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private Adapter pAdapter;
    private List<Persons> listPerson = new ArrayList<>();
    EditText editTextSort;
    Spinner spinner;
    ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            listView = findViewById(R.id.listViewEmployees);
            String[]items = {"<по умолчанию>","Код","Имя", "Фамилия"};
            spinner = findViewById(R.id.spinSort);

            editTextSort = findViewById(R.id.editTextSortBy);
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        //Person tempProduct = new Person(1,"asd","asd");
        //listPerson.add(tempProduct);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (editTextSort.getText().toString().isEmpty()) {
                        Sort(listPerson);
                    } else {
                        Search();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            editTextSort.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        public void SetAdapter(List<Persons> list){
            pAdapter = new Adapter(MainActivity.this,list);
            listView.setAdapter(pAdapter);
            pAdapter.notifyDataSetInvalidated();

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void Search(){
            List<Persons> listFilter = listPerson.stream().filter(x-> (x.lname.toLowerCase(Locale.ROOT).contains(editTextSort.getText().toString().toLowerCase(Locale.ROOT)))).collect(Collectors.toList());
            Sort(listFilter);
        }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(List<Persons> list){
        listView.setAdapter(null);

        switch(spinner.getSelectedItemPosition()){
            case 0:
                if(editTextSort.getText().toString().isEmpty()){

                    listPerson.clear();
                    new GetPerson().execute();
                }
                break;
            case 1:
                Collections.sort(list, Comparator.comparing(Persons::getID));
                break;
            case 2:
                Collections.sort(list, Comparator.comparing(Persons::getFname));
                break;
            case 3:
                Collections.sort(list, Comparator.comparing(Persons::getLname));
                break;
            default:
                break;
        }
        SetAdapter(list);
    }

    public void GoAddPerson(View v){
        startActivity(new Intent(this, AddPerson.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Clear(View v) {
        editTextSort.setText("");
        spinner.setSelection(0);
    }




        private class GetPerson extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("https://ngknn.ru:5001/ngknn/КонстантиновАС/api/Persons");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception exception) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try
                {
                    JSONArray tempArray = new JSONArray(s);
                    for (int i = 0;i<tempArray.length();i++)
                    {
                        JSONObject perJson = tempArray.getJSONObject(i);
                        Persons tempProduct = new Persons(
                                perJson.getInt("id"),
                                perJson.getString("fname"),
                                perJson.getString("lname"),
                                perJson.getString("img")
                        );
                        listPerson.add(tempProduct);
                        pAdapter.notifyDataSetInvalidated();


                    }
                } catch (Exception ignored) {
                    Log.e(ignored.toString(), ignored.getMessage());
                }
            }
        }
    }