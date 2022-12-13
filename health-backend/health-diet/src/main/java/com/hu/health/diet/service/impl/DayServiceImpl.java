package com.hu.health.diet.service.impl;

import com.alibaba.fastjson.JSON;
import com.hu.health.common.utils.R;
import com.hu.health.diet.dao.DietMenuDao;
import com.hu.health.diet.entity.*;
import com.hu.health.diet.openClient.UserClient;
import com.hu.health.diet.util.CalculateAge;
import com.hu.health.diet.util.MyListFound;
import com.hu.health.diet.util.getAge;
//import com.hu.health.sport.entity.*;
import com.hu.health.diet.util.getWeek;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.Query;

import com.hu.health.diet.dao.DayDao;
import com.hu.health.diet.service.DayService;
import org.springframework.stereotype.Service;


@Service("dayService")
public class DayServiceImpl extends ServiceImpl<DayDao, DayEntity> implements DayService {
     @Autowired
    DietMenuDao dietMenuDao;

     @Autowired
     DayDao dayDao;

     @Autowired
    UserClient userClient;


   @Override
     public HighAndLow getExc(Long id) {
//       R r = userClient.findById(id);
//       System.out.println(r);
//       MemberEntity byId = r.getData(MemberEntity.class);
       HighAndLow highAndLow=new HighAndLow();
//       if(byId.getGender()==1){
           highAndLow.setHigh("90");
           highAndLow.setLow("70");
//       }else {
//           highAndLow.setHigh("80");
//           highAndLow.setLow("60");
//       }
       return highAndLow;
//       R r = userClient.findById(id);
//       System.out.println(r);
//       MemberEntity byId = r.getData(MemberEntity.class);
//    Long age= Long.valueOf(0);
//             System.out.println(byId);
////       try {
//
//
//          age= Long.valueOf(getAge.getAge(byId.getBirth()));
//       } catch (Exception e) {
//           e.printStackTrace();
//       }
//       if (byId.getGender() == 1 && byId.getExercise().equals("0")) {
//             return CalculateAge.MenAndNoExe(age);
//         } else if (byId.getGender() == 1 && byId.getExercise().equals("1")) {
//             return CalculateAge.MenAndLittleExe(age);
//         } else if (byId.getGender() == 1 && byId.getExercise().equals("2")) {
//            return CalculateAge.MenAndMoreExe(age);
//         }else if (byId.getGender() == 0 && byId.getExercise().equals("0")) {
//             return CalculateAge.WoMenAndNoExe(age);
//         }else if (byId.getGender() == 0 && byId.getExercise().equals("1")) {
//             return CalculateAge.WoMenAndLittleExe(age);
//         }else if (byId.getGender() == 0 && byId.getExercise().equals("2")) {
//             return CalculateAge.WoMenAndMoreExe(age);
//         }else {
//             return null;
//         }
//       try {
//
//
//          age= Long.valueOf(getAge.getAge(byId.getBirth()));
//       } catch (Exception e) {
//           e.printStackTrace();
//       }
//       if (byId.getGender() == 1) {
//             return CalculateAge.MenAndNoExe(age);
//         } else if (byId.getGender() == 1 ) {
//             return CalculateAge.MenAndLittleExe(age);
//         } else if (byId.getGender() == 1) {
//            return CalculateAge.MenAndMoreExe(age);
//         }else if (byId.getGender() == 0 ) {
//             return CalculateAge.WoMenAndNoExe(age);
//         }else if (byId.getGender() == 0 ) {
//             return CalculateAge.WoMenAndLittleExe(age);
//         }else if (byId.getGender() == 0 ) {
//             return CalculateAge.WoMenAndMoreExe(age);
//         }else {
//             return null;
//         }
     }

    @Override
    public HighAndLow getProportion(Long id) {
        R r = userClient.findById(id);
        System.out.println(r);
        MemberEntity byId = r.getData(MemberEntity.class);
        HighAndLow highAndLow=new HighAndLow();
        if(byId.getGender()==1){
             highAndLow.setHigh("90");
             highAndLow.setLow("70");
        }else {
            highAndLow.setHigh("80");
            highAndLow.setLow("60");
        }
        return highAndLow;
    }

    @Override
    public HighAndLow getFat(Long id) {
        R r = userClient.findById(id);
        System.out.println(r);
        MemberEntity byId = r.getData(MemberEntity.class);
        HighAndLow highAndLow=new HighAndLow();
            highAndLow.setHigh("80");
            highAndLow.setLow("50");

        return highAndLow;
    }

    @Override
    public HighAndLow getCatrion(Long id) {
        R r = userClient.findById(id);
        System.out.println(r);
        MemberEntity byId = r.getData(MemberEntity.class);
        HighAndLow highAndLow=new HighAndLow();
        highAndLow.setHigh("150");
        highAndLow.setLow("100");
        return highAndLow;
    }

    @Override
    public HighAndLow getWeekExc(Long id) {
        return getExc(id);
    }

    @Override
    public HighAndLow getWeekFat(Long id) {
        return getFat(id);
    }

    @Override
    public HighAndLow getWeekProportion(Long id) {
        return getProportion(id);
    }

    @Override
    public HighAndLow getWeekCatrion(Long id) {
       return getCatrion(id);
    }

    @Override
    public HighAndLow getMonthExc(Long id) {
        return getExc(id);
    }

    @Override
    public HighAndLow getMonthFat(Long id) {
        return getFat(id);
    }

    @Override
    public HighAndLow getMonthProportion(Long id) {
        return getProportion(id);
    }

    @Override
    public HighAndLow getMonthCatrion(Long id) {
        return getCatrion(id);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DayEntity> page = this.page(
                new Query<DayEntity>().getPage(params),
                new QueryWrapper<DayEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public DayEntity ShowMeal(Long id,String data) {
        QueryWrapper<DayEntity> dayEntityQueryWrapper=new QueryWrapper<>();
        dayEntityQueryWrapper.eq("user_id",id).eq("day",data);
        DayEntity dayEntity = dayDao.selectOne(dayEntityQueryWrapper);
        return dayEntity;
    }

    @Override
    public Map<DietMenuEntity,String> showBreakfast(Long id, String data) {
        String brekfast = this.ShowMeal(id, data).getBrekfast();
        List<Food> integers = JSON.parseArray(brekfast, Food.class);
        if(integers.size()==0)return null;
        Map<DietMenuEntity,String> dietMenuEntities=new HashMap<>();
        for (Food food:integers) {
            DietMenuEntity breakfast = dietMenuDao.showbreakfast(food.getFood_id());
            dietMenuEntities.put(breakfast,food.getHundred_g());
        }
        return dietMenuEntities;
    }

    @Override
    public Map<DietMenuEntity,String> showLunch(Long id, String data) {
        String lunch = this.ShowMeal(id, data).getLunch();
        List<Food> integers = JSON.parseArray(lunch, Food.class);
        if(integers.size()==0)return null;
        Map<DietMenuEntity,String> dietMenuEntities=new HashMap<>();
        for (Food food:integers) {
            DietMenuEntity lunchs =dietMenuDao.showlunch(food.getFood_id());
            dietMenuEntities.put(lunchs,food.getHundred_g());
        }
        return dietMenuEntities;
    }

    @Override
    public Map<DietMenuEntity,String> showDinner(Long id, String data) {
        String dinner = this.ShowMeal(id, data).getDinner();
        List<Food> integers = JSON.parseArray(dinner, Food.class);
        if(integers.size()==0)return null;
        Map<DietMenuEntity,String> dietMenuEntities=new HashMap<>();
        for (Food food:integers) {
            DietMenuEntity dinners = dietMenuDao.showdinner(food.getFood_id());
            dietMenuEntities.put(dinners,food.getHundred_g());
        }
        return dietMenuEntities;
    }

    @Override
    public Map<DietMenuEntity,String> showAdds(Long id, String data) {
        String add = this.ShowMeal(id, data).getAdds();
        List<Food> integers = JSON.parseArray(add, Food.class);
        if(integers.size()==0)return null;
        Map<DietMenuEntity,String> dietMenuEntities=new HashMap<>();
        for (Food food:integers) {
            DietMenuEntity adds = dietMenuDao.showadds(food.getFood_id());
            dietMenuEntities.put(adds,food.getHundred_g());
        }
        return dietMenuEntities;
    }

    @Override
    public void AddBreakfast(Long user_id, String data, Food food) {
        System.out.println(food);
        String breakfast = this.ShowMeal(user_id, data).getBrekfast();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
          if(integers.size()==0||!MyListFound.isFood(integers,food)) {
              integers.add(food);
          }
          else {
              Food food1 = MyListFound.findFood(integers, food);
              food1.setHundred_g(String.valueOf(Double.valueOf(food.getHundred_g())+Double.valueOf(food1.getHundred_g())));
          }
        dayDao.updateBreakfast(user_id, data, JSON.toJSONString(integers));
    }

    @Override
    public void AddLunch(Long id, String data, Food food) {
        System.out.println(food);
        String breakfast = this.ShowMeal(id, data).getLunch();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        if(integers.size()==0||!MyListFound.isFood(integers,food)) {
            integers.add(food);
        }
        else {
            Food food1 = MyListFound.findFood(integers, food);
            food1.setHundred_g(String.valueOf(Double.valueOf(food.getHundred_g())+Double.valueOf(food1.getHundred_g())));
        }
        dayDao.updateLunch(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void AddDinner(Long id, String data, Food food) {
        System.out.println(food);
        String breakfast = this.ShowMeal(id, data).getDinner();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        if(integers.size()==0||!MyListFound.isFood(integers,food)) {
            integers.add(food);
        }
        else {
            Food food1 = MyListFound.findFood(integers, food);
            food1.setHundred_g(String.valueOf(Double.valueOf(food.getHundred_g())+Double.valueOf(food1.getHundred_g())));
        }
        dayDao.updateDinner(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void AddAdds(Long id, String data, Food food) {
        System.out.println(food);
        String breakfast = this.ShowMeal(id, data).getAdds();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        if(integers.size()==0||!MyListFound.isFood(integers,food)) {
            integers.add(food);
        }
        else {
            Food food1 = MyListFound.findFood(integers, food);
            food1.setHundred_g(String.valueOf(Double.valueOf(food.getHundred_g())+Double.valueOf(food1.getHundred_g())));
        }
        dayDao.updateAdds(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void DecBreakfast(Long id, String data, Food food) {
        String breakfast = this.ShowMeal(id, data).getBrekfast();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        Food food1 = MyListFound.findFood(integers, food);
        integers.remove(food1);
        dayDao.updateBreakfast(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void DecLunch(Long id, String data, Food food) {
        String breakfast = this.ShowMeal(id, data).getLunch();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        Food food1 = MyListFound.findFood(integers, food);
        integers.remove(food1);
        dayDao.updateLunch(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void DecDinner(Long id, String data, Food food) {
        String breakfast = this.ShowMeal(id, data).getDinner();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        Food food1 = MyListFound.findFood(integers, food);
        integers.remove(food1);
        dayDao.updateDinner(id, data, JSON.toJSONString(integers));
    }

    @Override
    public void DecAdds(Long id, String data, Food food) {
        String breakfast = this.ShowMeal(id, data).getAdds();
        List<Food> integers = JSON.parseArray(breakfast, Food.class);
        Food food1 = MyListFound.findFood(integers, food);
        integers.remove(food1);
        dayDao.updateAdds(id, data, JSON.toJSONString(integers));
    }

    @Override
    public Map<DietMenuEntity, String> getAllMeal(Long id, String date) {
        Map<DietMenuEntity,String> allMeal=new HashMap<>();
        Map<DietMenuEntity, String> dietMenuEntityStringMap = showBreakfast(id, date);
        System.out.println(dietMenuEntityStringMap);
        Map<DietMenuEntity, String> dietMenuEntityStringMap1 = showLunch(id, date);
        System.out.println(dietMenuEntityStringMap1);
        Map<DietMenuEntity, String> dietMenuEntityStringMap2 = showDinner(id, date);
        System.out.println(dietMenuEntityStringMap2);
        Map<DietMenuEntity, String> dietMenuEntityStringMap3 = showAdds(id, date);
        System.out.println(dietMenuEntityStringMap3);

        if (dietMenuEntityStringMap != null) {
            allMeal.putAll(dietMenuEntityStringMap);
        }
        if(dietMenuEntityStringMap1!=null) {
                allMeal.putAll(dietMenuEntityStringMap1);
            }
        if(dietMenuEntityStringMap2!=null) {
                allMeal.putAll(dietMenuEntityStringMap2);
            }
        if(dietMenuEntityStringMap3!=null) {
                allMeal.putAll(dietMenuEntityStringMap3);
            }
            return allMeal;


    }

    @Override
    public Double getCalorie(Long id, String date) {
        double sum=0;
        Map<DietMenuEntity, String> allMeal = getAllMeal(id, date);
        System.out.println(allMeal);
        Set<DietMenuEntity> dietMenuEntities = allMeal.keySet();
        for (DietMenuEntity dietMenu:dietMenuEntities) {
            double count = Double.valueOf(allMeal.get(dietMenu));
            System.out.println(count);
            sum+=count*Double.valueOf(dietMenu.getQuantityOfHeat());
            System.out.println(sum);
        }
        return sum;
    }

    @Override
    public Double getProtein(Long id, String date) {
        double sum=0;
        Map<DietMenuEntity, String> allMeal = getAllMeal(id, date);
        System.out.println(allMeal);
        Set<DietMenuEntity> dietMenuEntities = allMeal.keySet();
        for (DietMenuEntity dietMenu:dietMenuEntities) {
            double count = Double.valueOf(allMeal.get(dietMenu));
            System.out.println(count);
            sum+=count*Double.valueOf(dietMenu.getProportionOfProtein());
            System.out.println(sum);
        }
        return sum;
    }

    @Override
    public Double getFat(Long id, String date) {
        double sum=0;
        Map<DietMenuEntity, String> allMeal = getAllMeal(id, date);
        System.out.println(allMeal);
        Set<DietMenuEntity> dietMenuEntities = allMeal.keySet();
        for (DietMenuEntity dietMenu:dietMenuEntities) {
            double count = Double.valueOf(allMeal.get(dietMenu));
            System.out.println(count);
            sum+=count*Double.valueOf(dietMenu.getProportionOfFat());
            System.out.println(sum);
        }
        return sum;
    }

    @Override
    public Double getCarbonWater(Long id, String date) {
        double sum=0;
        Map<DietMenuEntity, String> allMeal = getAllMeal(id, date);
        System.out.println(allMeal);
        Set<DietMenuEntity> dietMenuEntities = allMeal.keySet();
        for (DietMenuEntity dietMenu:dietMenuEntities) {
            double count = Double.valueOf(allMeal.get(dietMenu));
            System.out.println(count);
            sum+=count*Double.valueOf(dietMenu.getCarbonWaterRatio());
            System.out.println(sum);
        }
        return sum;
    }

    @Override
    public Double getAllNutrition(long id, String date) {
        return getProtein(id,date)+getCarbonWater(id,date)+getFat(id,date);
    }

    @Override
    public Double getProteinProportion(Long id, String date) {
        return getProtein(id,date)/getAllNutrition(id,date);
    }

    @Override
    public Double getFatProportion(Long id, String date) {
        return getFat(id,date)/getAllNutrition(id,date);
    }

    @Override
    public Double getCatrionWaterProportion(Long id, String date) {
        return getCarbonWater(id,date)/getAllNutrition(id,date);
    }
}