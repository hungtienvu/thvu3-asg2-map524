package com.example.stevevu.noclookup;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchNOC extends Activity {

    private static ArrayList<NOC> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_noc);
        list = new ArrayList<NOC>();
        //initialize
        JSONHelper jsh = new JSONHelper(this);
        jsh.open();
        try {
            list = jsh.readJsonStream();
        }catch (IOException e){
            e.printStackTrace();
        }


        AutoCompleteTextView ac = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
            ac.setThreshold(2);

        //note
        ArrayAdapter<NOC> adapter = new ArrayAdapter<NOC>(ac.getContext(),R.layout.single_tv,getListDesc(list));

        ac.setAdapter(adapter);

        ac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String desc = new String();
                desc =  parent.getAdapter().getItem(position).toString();
                NOC n = NOC.getNOCByDesc(desc,list);
                Log.d("bug", n.toString());

                Intent nocdetails = new Intent(view.getContext(), NOCDetails.class);
                nocdetails.putExtra("NOC_CODE", n.getCode());
                startActivity(nocdetails);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_noc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //only level 4 NOC will be show up
    public List getListDesc(List<NOC> noc){
        ArrayList<String> list = new ArrayList<>();
        for(NOC n: noc)
            if(n.getCode().length()==4)
                list.add(n.getDesc());
        return list;
    }


}
