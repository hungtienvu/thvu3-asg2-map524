package com.example.stevevu.noclookup;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class NOCDetails extends Activity {

    private static ArrayList<NOC> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nocdetails);

        //initialize
        JSONHelper jsh = new JSONHelper(this);
        jsh.open();
        try {
            list = jsh.readJsonStream();
        }catch (IOException e){
            e.printStackTrace();
        }

        String noc_code = new String();
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
            noc_code = extras.getString("NOC_CODE");

        if(noc_code!=null) {

            NOC n = NOC.getNOCByCode(noc_code,list);

            TextView title = (TextView) findViewById(R.id.nocTitle);
            TextView noc = (TextView) findViewById(R.id.nocCode);
            TextView skill = (TextView) findViewById(R.id.nocSkillLevel);
            TextView status = (TextView) findViewById(R.id.nocStatus);

            title.setText(n.getDesc());
            noc.setText(n.getCode());
            skill.setText(n.getSkillLevel());

            if (n.getSkillLevel().equals("0")
                    || n.getSkillLevel().equals("A")
                    || n.getSkillLevel().equals("B")) {
                status.setText("Eligible");
            } else {
                status.setText("Not eligible for skilled workers");
            }

        }
    }

}
