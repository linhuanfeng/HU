package com.hu.health.diet.entity;

import lombok.Data;

@Data
public class Food {
    private long food_id;
    private String hundred_g;

    @Override
    public boolean equals(Object o) {
        if(o==null) {
            return true;
        }
       Food food= (Food) o;
        if(food.getFood_id()==this.getFood_id()) {
            return true;
        }
        return false;
    }
}
