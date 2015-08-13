package com.example.stevevu.noclookup;
/*
    NOC:
    code = 0-> level1cat
    code = 00 -> level2 cat
    code = 001 -> level3
    code = 0011 -> final level4 ( get detail from HTTP request)
    **01-05 => (group of NOC level 2) --skip? cast to level 2
 */

import java.util.ArrayList;
import java.util.List;

public class NOC {
    private String code;
    private String desc;

    public NOC(String code,String desc){
        this.code = code;
        this.desc = desc;

    }

    public String getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }



    public int getCatLevel(){
        //return length of code which is level of NOC
        int level = code.length();
        if(level<=4){
            return level;
        }else if(level==5){
            return 2;
        }
        //false
        return 0;
    }

    public String getParent(){
        if(this.getCatLevel()>1)
            return this.getCode().substring(0,this.getCatLevel()-1);
        return null;
    }

    public String getSkillLevel(){
        //check first character and then the second one
        //only with 4 letter
        //get 1st letter
        //0 -> 0
        //1 -> A
        //2-3 -> B
        //4-5 -> C
        //6 -> D
        String first = code.substring(0,1);
        String second = code.substring(1,2);

        if(code.length()==4) {
            if(first.equals("0")){
                return "0";

            }else{
                switch (second) {
                    case "0": case "1":
                        return "A";
                    case "2":
                    case "3":
                        return "B";
                    case "4":
                    case "5":
                        return "C";
                    case "6":
                        return "D";
                }
            }

        }else {
            System.out.println("Invalid NOC code!! must have 4 characters!");
        }
        return null;
    }

    //helper function to get NOC object by code
    public static NOC getNOCByCode(String c,ArrayList<NOC> list){
        for(NOC noc : list)
            if(noc.getCode().equals(c))
                return noc;
        return null;
    }

    //helper function to get NOC object by code
    public static NOC getNOCByDesc(String c,ArrayList<NOC> list){
        for(NOC noc : list)
            if(noc.getDesc().equals(c))
                return noc;
        return null;
    }


    @Override
    public String toString() {
        return "NOC{" +
                "code: " + code +
                "; desc: " + desc +
                "}\n";

    }


}

