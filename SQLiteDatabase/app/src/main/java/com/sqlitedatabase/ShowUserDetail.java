package com.sqlitedatabase;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowUserDetail extends BaseActivity {

    private RecyclerView rcyUserDetail;
    private UserDetailAdapter adapter;
    SQLiteDataBaseDb db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_detail);

        rcyUserDetail = findViewById(R.id.rcyUserDetail);
        rcyUserDetail.setHasFixedSize(true);
        rcyUserDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        db = new SQLiteDataBaseDb(ShowUserDetail.this);
        // Get User Data from DataBase
        getUserData();
    }

    public void getUserData() {
        new GetData().execute();
    }

    public class GetData extends AsyncTask<Void, Void, List<UserDetail>> {

        ProgressDialog dialog;
        List<UserDetail> userDetailList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ShowUserDetail.this, "", getString(R.string.loading));
            userDetailList = new ArrayList<>();
        }

        @Override
        protected List<UserDetail> doInBackground(Void... voids) {
            userDetailList = db.getUserDetail();
            return userDetailList;
        }

        @Override
        protected void onPostExecute(List<UserDetail> userDetails) {
            super.onPostExecute(userDetails);
            dialog.dismiss();
            if (userDetails != null && userDetails.size() != 0) {
                adapter = new UserDetailAdapter(ShowUserDetail.this, userDetails);
                rcyUserDetail.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
