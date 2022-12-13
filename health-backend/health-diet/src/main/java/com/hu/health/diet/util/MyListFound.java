package com.hu.health.diet.util;

import com.hu.health.diet.entity.Food;

import java.util.List;

public class MyListFound {

    public static boolean isFood(List<Food> list, Food food){
        for (Food myFood:list) {
            if(myFood.getFood_id()==food.getFood_id())
                return true;
        }
        return false;

    }

    public static Food findFood(List<Food> list,Food food){
        for (Food myFood:list) {
            if(myFood.getFood_id()==food.getFood_id())
                return myFood;
        }
        return null;
    }
}
