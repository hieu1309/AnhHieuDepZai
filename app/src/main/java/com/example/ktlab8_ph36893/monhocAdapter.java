package com.example.ktlab8_ph36893;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class monhocAdapter extends RecyclerView.Adapter<monhocAdapter.viewholer> {
    private final Context context;
    private final ArrayList<monhoc> list;
    monhocDAO mhDao;

    public monhocAdapter(Context context, ArrayList<monhoc> list) {
        this.context = context;
        this.list = list;
        mhDao = new monhocDAO(context);
    }

    @NonNull
    @Override
    public viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_mh, null);
        return new viewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholer holder, int position) {
        holder.txtmamh.setText("Mã MH: " + list.get(position).getMamh());
        holder.txttenmh.setText("Tên MH: " + list.get(position).getTenmh());
        holder.txtsotiet.setText("Số tiết: " + list.get(position).getSotiet());
        monhoc mh = list.get(position);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");//set tiêu đề
                builder.setIcon(R.drawable.warning);//set icon
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mhDao.delete(mh.getMamh())) {
                            list.clear();
                            list.addAll(mhDao.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogsua(mh);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholer extends RecyclerView.ViewHolder {
        TextView txtmamh, txttenmh, txtsotiet;
        ImageButton btnupdate, btndelete;

        public viewholer(@NonNull View itemView) {
            super(itemView);
            txtmamh = itemView.findViewById(R.id.txtmamh);
            txttenmh = itemView.findViewById(R.id.txttenmh);
            txtsotiet = itemView.findViewById(R.id.txtsotiet);
            btndelete = itemView.findViewById(R.id.btndelete);
            btnupdate = itemView.findViewById(R.id.btnupdate);
        }
    }
        private void opendialogsua(monhoc mh) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            LayoutInflater inflater=((Activity) context).getLayoutInflater();
            View view=inflater.inflate(R.layout.item_update,null);
            builder.setView(view);
            Dialog dialog=builder.create();
            dialog.show();

            EditText edtmamh = view.findViewById(R.id.edtmamh_ud);
            EditText edttenmh=view.findViewById(R.id.edttenmh_ud);
            EditText edtsotiet =view.findViewById(R.id.edtsotiet_ud);
            Button btnsua=view.findViewById(R.id.btnupdate_ud);

            //gán dữ liệu lên các widget
            edtmamh.setText(mh.getMamh());
            edttenmh.setText(mh.getTenmh());
            edtsotiet.setText(String.valueOf(mh.getSotiet()));

            btnsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(edtmamh.getText().toString())||TextUtils.isEmpty(edttenmh.getText().toString())|TextUtils.isEmpty(edtsotiet.getText().toString())){
                        Toast.makeText(context, "Yêu cầu nhập đầy ddur", Toast.LENGTH_SHORT).show();
                    }else {
                        mh.setMamh(edtmamh.getText().toString());
                        mh.setTenmh(edttenmh.getText().toString());
                        mh.setSotiet(Integer.parseInt(edtsotiet.getText().toString()));
                        int sotiet1;
                        try {
                            sotiet1 = Integer.parseInt(edtsotiet.getText().toString());
                            if (sotiet1 <= 15) {
                                Toast.makeText(context, "Số tiết phải lớn hơn 15", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (mhDao.update(mh)) {
                                list.clear();
                                list.addAll(mhDao.selectAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, "Tiet phai la so", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
}
