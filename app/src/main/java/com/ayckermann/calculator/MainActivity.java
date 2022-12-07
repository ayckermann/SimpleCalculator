package com.ayckermann.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<History> listHistory;
    private RecyclerView recHistory;
    Adapter adapter;

    EditText edtValue1, edtValue2;
    Button btnEnter;
    TextView txtResult;
    RadioGroup rgOperators;
    RadioButton rbPlus, rbMinus, rbTimes, rbDivide;

    SharedPreferences sharedPreferences;

    int id =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listHistory = new ArrayList<>();
        initComponents();
        showArray();

        if(listHistory.size()==0){
            id=1;
        }
        else{
            id = (listHistory.get(listHistory.size()-1).getId())+1;
        }


        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Double v1 = Double.parseDouble(edtValue1.getText().toString());
                    Double v2 = Double.parseDouble(edtValue2.getText().toString());
                    insertItem(v1, v2);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Inputan salah", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void initComponents() {
        edtValue1 = findViewById(R.id.edtValue1);
        edtValue2 = findViewById(R.id.edtValue2);

        btnEnter = findViewById(R.id.btnEnter);
        rgOperators = findViewById(R.id.radioGroup);
        rbPlus = findViewById(R.id.radPlus);
        rbMinus = findViewById(R.id.radMinus);
        rbTimes = findViewById(R.id.radTimes);
        rbDivide = findViewById(R.id.radDivide);

        txtResult = findViewById(R.id.txtResult);


        sharedPreferences = this.getSharedPreferences("History", Context.MODE_PRIVATE);
        recHistory = findViewById(R.id.rvHistory);
        adapter = new Adapter(listHistory,sharedPreferences);
        recHistory.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recHistory.setLayoutManager(linearLayoutManager);
    }

    public void insertItem(Double value1, Double value2){
        String operasi= "";
        double hasil =0;
        if(rbPlus.isChecked()){
            hasil = value1 + value2;
            operasi = " + ";
        }
        else if(rbMinus.isChecked()){
            hasil = value1 - value2;
            operasi = " - ";
        }
        else if(rbTimes.isChecked()){
            hasil = value1 * value2;
            operasi = " x ";
        }
        else if(rbDivide.isChecked()){
            hasil = value1 / value2;
            operasi = " / ";
        }

        String history = value1 + operasi + value2  +" = "+hasil;
        saveToShared(Integer.toString(id), history);

        txtResult.setText(Double.toString(hasil));
        adapter.notifyDataSetChanged();
    }

    private  Boolean validate() {
        if (edtValue1.getText().toString().equals("") || edtValue1.getText() == null) {
            return false;
        } else if (edtValue2.getText().toString().equals("") || edtValue2.getText() == null) {
            return false;
        } else if (rgOperators.getCheckedRadioButtonId() == -1) {
            return false;
        }

        return true;
    }

    public void showArray() {
        Map<String, ?> entries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: entries.entrySet()) {
            getArray(entry.getKey(), entry.getValue().toString());
        }
    }

    public void saveToShared(String id, String history){
        try {
            sharedPreferences.edit().putString(id, history).apply();
            String value =sharedPreferences.getString(id,"");
            getArray(id,value);
            this.id++;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getArray(String id, String history){
        try{
            listHistory.add(new History(Integer.parseInt(id),history ));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("gagal tambah array");
        }
    }

//    public void saveToLocal(Double value1, Double value2, Double hasil, String operasi){
//        String strListHistory = sharedPreferences.getString(getString(R.string.history),"[]");
//        listHistory = gson.fromJson(strListHistory, new TypeToken<ArrayList<History>>() {}.getType());
//
//        if(listHistory == null){
//            listHistory  = new ArrayList<>();
//        }
//
//        listHistory.add(new History(id,value1,value2,hasil,operasi));
//
//        strListHistory = gson.toJson(listHistory);
//        sharedPreferences.edit().putString(getString(R.string.history), strListHistory).apply();
//
//    }


}