package com.example.check.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DepSecend {

    private Integer id;
    private Integer depSecendId;
    private String depSecendName;
    private Integer depId;
    private String responsibility;

}
