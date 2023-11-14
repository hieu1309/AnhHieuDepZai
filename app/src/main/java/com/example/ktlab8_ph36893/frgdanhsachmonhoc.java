package com.example.ktlab8_ph36893;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class frgdanhsachmonhoc extends Fragment {
    RecyclerView rcvmonhoc;
    FloatingActionButton fltadd;
    monhocDAO mhDao;
    monhocAdapter adapter;
    private ArrayList<monhoc> list = new ArrayList<>();


    public frgdanhsachmonhoc() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frgdanhsachmonhoc, container, false);
        rcvmonhoc = view.findViewById(R.id.rcvmonhoc);
        fltadd = view.findViewById(R.id.fltadd);

        mhDao = new monhocDAO(getActivity());
        list = mhDao.selectAll();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rcvmonhoc.setLayoutManager(layout);
        adapter = new monhocAdapter(getActivity(), list);
        rcvmonhoc.setAdapter(adapter);
        fltadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogthem();
            }
        });
        return view;
    }

    private void opendialogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add, null);
        builder.setView(view);//gán view vào hôp thoại
        Dialog dialog = builder.create();//tạo hộp thoại
        dialog.show();

        EditText edtmamh = view.findViewById(R.id.edtmamh_add);
        EditText edttenmh = view.findViewById(R.id.edttenmh_add);
        EditText edtsotiet = view.findViewById(R.id.edtsotiet_add);

        Button btnadd = view.findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mamh = edtmamh.getText().toString();
                String tenmh = edttenmh.getText().toString();
                String sotiet = edtsotiet.getText().toString();
                if (TextUtils.isEmpty(edtmamh.getText().toString()) || TextUtils.isEmpty(edttenmh.getText().toString()) || TextUtils.isEmpty(edtsotiet.getText().toString())) {
                    Toast.makeText(getActivity(), "Yêu cầu nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else {

                    int sotiet1;
                    try {
                        sotiet1 = Integer.parseInt(edtsotiet.getText().toString());
                        if (sotiet1 <= 15) {
                            Toast.makeText(getActivity(), "Số tiết phải lớn hơn 15", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        monhoc mh = new monhoc(mamh, tenmh, Integer.parseInt(sotiet));

                        if (mhDao.insert(mh)) {
                            list.clear();
                            list.addAll(mhDao.selectAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "insert succ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "insert fail", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "số tiết phải là số", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}