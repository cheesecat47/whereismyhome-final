package com.ssafy.whereismyhome.house.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "DongCodeDto (동 코드 정보)", description = "동 코드를 관리할 Dto")
public class DongCodeDto {
    private String dongCode;
    private String sidoName;
    private String gugunName;
    private String dongName;
}
