package com.example.den.bigdig;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentTest extends Fragment {
    private EditText editText;
    private Resources res;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test, container, false);//создаем вид
        Button button = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.editText);
        res = getResources();//доступ к ресерсам
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkText = editText.getText().toString();

                if (android.util.Patterns.WEB_URL.matcher(linkText).matches()
                        && linkText.matches(".*\\.(jpg|png|bmp|jpeg|gif)$")) {
                    Intent intent = new Intent();
                    intent.putExtra("AUTHORITY", ContractLinks.AUTHORITY);
                    intent.putExtra("PATH", ContractLinks.PATH_LINKS_DATA);
                    intent.putExtra("linkText", linkText);//передаем ссылку
                    intent.setClassName("com.example1.den.bigdig2", "com.example1.den.bigdig2.App2");
                    startActivity(intent);
                    getActivity().finish();
                } else
                    Toast.makeText(getContext(), res.getString(R.string.enterUrlString), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }//onCreateView
}