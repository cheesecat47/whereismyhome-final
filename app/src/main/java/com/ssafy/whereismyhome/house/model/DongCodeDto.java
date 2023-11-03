package com.ssafy.whereismyhome.house.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DongCodeDto {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;
}
