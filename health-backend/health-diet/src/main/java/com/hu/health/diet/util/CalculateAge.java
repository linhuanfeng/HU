package com.hu.health.diet.util;

import com.hu.health.diet.entity.HighAndLow;
import org.springframework.stereotype.Component;


public class CalculateAge {


    public static HighAndLow MenAndNoExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
       highAndLow.setHigh("2400");
       highAndLow.setLow("2000");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("2800");
            highAndLow.setLow("2400");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("2400");
            highAndLow.setLow("2200");
        }else if(age>=51){
            highAndLow.setHigh("2200");
            highAndLow.setLow("2000");
        }
        return highAndLow;
    }

    public static HighAndLow MenAndLittleExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
            highAndLow.setHigh("2800");
            highAndLow.setLow("2400");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("2800");
            highAndLow.setLow("2600");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("2600");
            highAndLow.setLow("2400");
        }else if(age>=51){
            highAndLow.setHigh("2400");
            highAndLow.setLow("2200");
        }
        return highAndLow;
    }
    public static HighAndLow MenAndMoreExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
            highAndLow.setHigh("3200");
            highAndLow.setLow("2800");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("4000");
            highAndLow.setLow("3000");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("3000");
            highAndLow.setLow("2800");
        }else if(age>=51){
            highAndLow.setHigh("2800");
            highAndLow.setLow("2400");
        }
        return highAndLow;
    }

    public static HighAndLow WoMenAndNoExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
            highAndLow.setHigh("1800");
            highAndLow.setLow("0");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("2000");
            highAndLow.setLow("1800");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("1800");
            highAndLow.setLow("0");
        }else if(age>=51){
            highAndLow.setHigh("2200");
            highAndLow.setLow("2000");    //无数据
        }
        return highAndLow;
    }
    public static HighAndLow WoMenAndLittleExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
            highAndLow.setHigh("2000");
            highAndLow.setLow("0");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("2200");
            highAndLow.setLow("2000");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("2000");
            highAndLow.setLow("0");
        }else if(age>=51){
            highAndLow.setHigh("1800");
            highAndLow.setLow("0");
        }
        return highAndLow;
    }

    public static HighAndLow WoMenAndMoreExe(Long age){
        HighAndLow highAndLow=new HighAndLow();
        if(age>=14&&age<=18){
            highAndLow.setHigh("2400");
            highAndLow.setLow("0");
        }else if(age>=19&&age<=30){
            highAndLow.setHigh("2400");
            highAndLow.setLow("0");
        }else if (age>=31&&age<=50){
            highAndLow.setHigh("2200");
            highAndLow.setLow("0");
        }else if(age>=51){
            highAndLow.setHigh("2200");
            highAndLow.setLow("2000");
        }
        return highAndLow;
    }
}
