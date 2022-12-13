package com.hu.health.community.to;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberEntityTo {

    private Long memberId;
    private Integer count;
}
