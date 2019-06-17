package com.sqlitedatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.ViewHolder> {

    Context mcontext;
    List<UserDetail> userDetails;
    EditText edtName, edtEmail, edtPhone, edtCity, edtState;
    TextView txtId;
    SQLiteDataBaseDb db;

    public UserDetailAdapter(Context context, List<UserDetail> userDetails) {
        this.mcontext = context;
        this.userDetails = userDetails;
        db = new SQLiteDataBaseDb(mcontext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.item_user_data, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtId.setText(userDetails.get(position).getId() + "");
        holder.txtName.setText(userDetails.get(position).getName());
        holder.txtEmail.setText(userDetails.get(position).getEmail());
        holder.txtPhone.setText(userDetails.get(position).getPhone());
        holder.txtCity.setText(userDetails.get(position).getCity());
        holder.txtState.setText(userDetails.get(position).getState());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete data using userId
                db.deleteByUserId(userDetails.get(position).getId());
                ((BaseActivity) mcontext).showToast(mcontext.getResources().getString(R.string.delete_successfully));
                userDetails.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, userDetails.size());
            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppCompatDialog dialog = new AppCompatDialog(mcontext, R.style.theme_dialog);
                dialog.setTitle(mcontext.getResources().getString(R.string.updateuser));
                dialog.setContentView(R.layout.dialog_update_user_data);
                dialog.setCancelable(false);

                txtId = dialog.findViewById(R.id.txtId);
                edtName = dialog.findViewById(R.id.edtName);
                edtEmail = dialog.findViewById(R.id.edtEmail);
                edtPhone = dialog.findViewById(R.id.edtPhoneNumber);
                edtCity = dialog.findViewById(R.id.edtCity);
                edtState = dialog.findViewById(R.id.edtState);

                txtId.setText("User Id :- " + userDetails.get(position).getId());
                edtName.setText(userDetails.get(position).getName());
                edtEmail.setText(userDetails.get(position).getEmail());
                edtPhone.setText(userDetails.get(position).getPhone());
                edtCity.setText(userDetails.get(position).getCity());
                edtState.setText(userDetails.get(position).getState());

                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
                TextView txtCancel = dialog.findViewById(R.id.txtCancle);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // update user data
                        db.updateUserDetailById(userDetails.get(position).getId(), edtName.getText().toString(), edtEmail.getText().toString(), edtPhone.getText().toString(), edtCity.getText().toString(), edtState.getText().toString());
                        ((BaseActivity) mcontext).showToast(mcontext.getResources().getString(R.string.updatesucessfully));
                        dialog.dismiss();
                        ((ShowUserDetail) mcontext).getUserData();

                    }
                });
                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtId, txtName, txtEmail, txtPhone, txtCity, txtState;
        ImageView imgDelete, imgUpdate;

        ViewHolder(View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtIDValue);
            txtName = itemView.findViewById(R.id.txtUserName);
            txtEmail = itemView.findViewById(R.id.txtEmailValue);
            txtPhone = itemView.findViewById(R.id.txtPhoneValue);
            txtCity = itemView.findViewById(R.id.txtCityValue);
            txtState = itemView.findViewById(R.id.txtStateValue);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
        }
    }
}
