package com.example.den.bigdig;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentTest extends Fragment {
    private EditText editText;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test, container, false);//создаем вид
        Button button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkText = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("AUTHORITY", ContractLinks.AUTHORITY);
                intent.putExtra("PATH", ContractLinks.PATH_LINKS_DATA);
                intent.putExtra("linkText", linkText);//передаем ссылку
                intent.setClassName("com.example1.den.bigdig2", "com.example1.den.bigdig2.MainActivity");
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }//onCreateView
}