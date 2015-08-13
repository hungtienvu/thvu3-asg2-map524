package com.example.stevevu.noclookup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CatListing extends Activity {

    private static ArrayList<NOC> list;
    private Intent intent;
    private String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        parent = intent.getStringExtra("PARENT");

        //Log.d("bug",parent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_listing);

        //initialize
        JSONHelper jsh = new JSONHelper(this);
        jsh.open();
        try {
            list = jsh.readJsonStream();
        }catch (IOException e){
            e.printStackTrace();
        }

        //get cat by level
        ArrayList al = new ArrayList();

        //get root category, only when startup Browsing code// root = 1
        if(parent.equals("root")) {
            al = getCatByLevel(1);
        }else{
            //get subcategory of parent category
            al = getSubCategory(NOC.getNOCByCode(parent,list));
        }

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setItemsCanFocus(false);

        CustomAdapter ca = new CustomAdapter(this,al);

        lv.setAdapter(ca);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                NOC n = (NOC) parent.getAdapter().getItem(position);
                LinearLayout ll = (LinearLayout) view;
                TextView code = (TextView) ll.findViewById(R.id.noc_code);
                TextView desc = (TextView) ll.findViewById(R.id.noc_desc);
                String t = code.getText().toString() + ". " + desc.getText().toString();
                Toast.makeText(getApplicationContext(),
                        t, Toast.LENGTH_SHORT).show();

                if(n.getCatLevel()<4) {
                    //continue listing
                    intent.putExtra("PARENT", n.getCode());
                    startActivity(intent);
                }else{
                    //stop listing
                    Intent nocdetails = new Intent(view.getContext(),NOCDetails.class);
                    nocdetails.putExtra("NOC_CODE", n.getCode());
                    startActivity(nocdetails);
                }

            }
        });

    }

    @Override
    public void onBackPressed(){
        //TODO: check this one swich back to intent, can not get privious Clicked obj
        if(!parent.equals("root")) {
            //if clicked item is not root --> go back
            if (NOC.getNOCByCode(parent, list).getCatLevel() > 1) {
                intent.putExtra("PARENT", NOC.getNOCByCode(parent, list).getParent());

            } else {
                //if clicked item is root --> back to root
                intent.putExtra("PARENT", "root");
            }
            startActivity(intent);

        }else if(parent.equals("root")){

            Intent home = new Intent(this,MainActivity.class);
            //clear top activity and back to home
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);

        }

    }

    public ArrayList<NOC> getCatByLevel(int level){
        ArrayList sorted = new ArrayList<NOC>();
        for(NOC noc : list){
            if(noc.getCatLevel()==level){
                sorted.add(noc);
            }
        }
        return sorted;
    }

    public ArrayList<NOC> getSubCategory(NOC n){

        int cat_level = n.getCatLevel() + 1 ;//next subcat
        ArrayList subCat = new ArrayList<NOC>();
        String from = new String("");
        String to = new String("");

        if(n.getCode().length()==5) {
            String temp[] = n.getCode().split("-");
            ArrayList temp2 = new ArrayList();

            if (temp.length == 2)
                for(int i=Integer.parseInt(temp[0]);i<= Integer.parseInt(temp[1]);i++)
                    temp2.add("0"+i);
            for(NOC noc : list)
                if(noc.getCatLevel()==cat_level)
                    if(temp2.contains(noc.getCode().substring(0,n.getCatLevel())))
                        subCat.add(noc);


        }else{
            for(NOC noc : list)
                if(noc.getCatLevel()==cat_level
                        && noc.getCode().substring(0,n.getCatLevel()).equals(n.getCode()))
                    subCat.add(noc);
        }

        return subCat;


    }

}
