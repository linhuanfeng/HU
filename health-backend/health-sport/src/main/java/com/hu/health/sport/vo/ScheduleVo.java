package com.hu.health.sport.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleVo implements Serializable {
    Long userId;
    Integer plan;
    Integer finish;
    Integer unFinish;
}
