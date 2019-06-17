package com.sqlitedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btnAdd, btnRead, btnDelete, btnUpdate;
    private EditText edtName, edtEmail, edtPhone, edtCity, edtState;
    String name, email, phone, city, state;
    SQLiteDataBaseDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        edtCity = findViewById(R.id.edtCity);
        edtState = findViewById(R.id.edtState);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        db = new SQLiteDataBaseDb(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                checkValidation();
                break;
            case R.id.btnRead:
                if (db.getUserDetail().size() != 0)
                    startActivity(new Intent(MainActivity.this, ShowUserDetail.class));
                else
                    showToast(getResources().getString(R.string.no_user_data));
                break;
            case R.id.btnDelete:
                break;
            case R.id.btnUpdate:
                break;
        }
    }


    public void checkValidation() {
        if (!edtName.getText().toString().isEmpty()) {
            name = edtName.getText().toString();
            if (!edtEmail.getText().toString().isEmpty()) {
                email = edtEmail.getText().toString();
                if (!edtPhone.getText().toString().isEmpty()) {
                    phone = edtPhone.getText().toString();
                    if (!edtCity.getText().toString().isEmpty()) {
                        city = edtCity.getText().toString();
                        if (!edtState.getText().toString().isEmpty()) {
                            state = edtState.getText().toString();
                            insertData(name, email, phone, city, state);
                        } else
                            showToast(getResources().getString(R.string.please_enter_state));
                    } else
                        showToast(getResources().getString(R.string.please_enter_city));
                } else
                    showToast(getResources().getString(R.string.please_enter_phone));
            } else
                showToast(getResources().getString(R.string.please_enter_email));
        } else
            showToast(getResources().getString(R.string.please_enter_name));
    }

    private void insertData(String name, String email, String phone, String city, String state) {
        UserDetail userDetail = new UserDetail();
        userDetail.setName(name);
        userDetail.setEmail(email);
        userDetail.setPhone(phone);
        userDetail.setCity(city);
        userDetail.setState(state);

        db.insertUserData(name, email, phone, city, state);
        showToast(getResources().getString(R.string.insert_successfully));
        clearEditText();
    }

    private void clearEditText() {
        edtName.setText("");
        edtEmail.setText("");
        edtPhone.setText("");
        edtCity.setText("");
        edtState.setText("");
    }
}
