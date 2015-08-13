package com.example.stevevu.noclookup;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    private InputStream in = null;
    private Context context;

    public JSONHelper(Context context){
        this.context = context;
    }

    //open JSON before reading
    //closing on readJsonStream
    public void open(){
        try {
            in = context.getAssets().open("NOCLevel11.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //http://developer.android.com/reference/android/util/JsonReader.html
    public ArrayList<NOC> readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in,"UTF-8"));
        ArrayList<NOC> list = new ArrayList<>();
        String code = "" ;
        String desc = "" ;
        String level = "" ;
        //try read at array level, mean loop through each object
        try{
            reader.beginArray();
            while (reader.hasNext()){
                //try to read inside JSON object
                reader.beginObject();
                while (reader.hasNext()){
                    String name = reader.nextName();
                    if(name.equals("FIELD1")){
                        code = reader.nextString();
                    }else if(name.equals("FIELD2")){
                        desc = reader.nextString();
                    }else{
                        reader.skipValue();
                    }
                }
                reader.endObject();
                //initialize new NOC obj and add to List
                NOC item = new NOC(code,desc);
                list.add(item);
            }
            reader.endArray();
        }finally {
            reader.close();
        }

        return list;
    }


}
