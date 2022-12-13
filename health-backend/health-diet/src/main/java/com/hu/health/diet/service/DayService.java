package com.hu.health.diet.service;

import com.baomidou.mybatisplus.extension.service.IService;
//import com.hu.health.common.utils.PageUtils;
import com.hu.health.common.utils.PageUtils;
import com.hu.health.diet.entity.DayEntity;
import com.hu.health.diet.entity.DietMenuEntity;
import com.hu.health.diet.entity.Food;
import com.hu.health.diet.entity.HighAndLow;

import java.util.Map;

/**
 * 
 *
 * @author lhf
 * @email i@lhf.com
 * @date 2022-02-17 06:09:21
 */
public interface DayService extends IService<DayEntity> {

    PageUtils queryPage(Map<String, Object> params);

  //获取当日的菜单情况
    DayEntity ShowMeal(Long id, String date);

    //获取早餐菜单
    Map<DietMenuEntity,String> showBreakfast(Long id,String data);

    //获取午餐菜单
  Map<DietMenuEntity,String> showLunch(Long id,String data);

    //获取晚餐菜单
  Map<DietMenuEntity,String> showDinner(Long id,String data);

    //获取加餐数据
    Map<DietMenuEntity,String> showAdds(Long id,String data);

    //新增早餐
    void AddBreakfast(Long user_id, String data, Food food);

    //新增午餐
  void AddLunch(Long user_id, String data, Food food);

  //新增晚餐
  void AddDinner(Long user_id, String data, Food food);

  //加餐
  void AddAdds(Long user_id, String data, Food food);


  //新增早餐
  void DecBreakfast(Long id, String data, Food food);

  //新增午餐
  void DecLunch(Long id, String data, Food food);

  //新增晚餐
  void DecDinner(Long id, String data, Food food);

  //加餐
  void DecAdds(Long id, String data, Food food);

  //获取所有
  Map<DietMenuEntity,String> getAllMeal(Long id,String date);


  //获取摄入卡路里
  Double getCalorie(Long id,String date);

  //获取摄入蛋白质
  Double getProtein(Long id,String date);

  //获取摄入脂肪
  Double getFat(Long id,String date);

  //获取摄入碳水
  Double getCarbonWater(Long id,String date);

  //获取所有
  Double getAllNutrition(long id,String date);

  //获取蛋白质占比
  Double getProteinProportion(Long id,String date);

  //获取脂肪占比
  Double getFatProportion(Long id,String date);

  //获取碳水占比
  Double getCatrionWaterProportion(Long id,String date);

  //获取日应获取的热量
  HighAndLow getExc(Long id);

  //获取日应获取的蛋白质
  HighAndLow getProportion(Long id);

  //获取日应获取的脂肪
  HighAndLow getFat(Long id);

  //获取日应获取的碳水
  HighAndLow getCatrion(Long id);

  //获取周平均应获取的热量
  HighAndLow getWeekExc(Long id);

  //获取周平均应获取的脂肪
  HighAndLow getWeekFat(Long id);

  //获取周平均应获取的蛋白质
  HighAndLow getWeekProportion(Long id);

  //获取周平均应获取的碳水
  HighAndLow getWeekCatrion(Long id);



  //获取月平均应获取的热量
  HighAndLow getMonthExc(Long id);

  //获取月平均应获取的脂肪
  HighAndLow getMonthFat(Long id);

  //获取月平均应获取的蛋白质
  HighAndLow getMonthProportion(Long id);

  //获取月平均应获取的碳水
  HighAndLow getMonthCatrion(Long id);
}

