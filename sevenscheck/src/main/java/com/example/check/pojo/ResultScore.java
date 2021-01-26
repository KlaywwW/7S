package com.example.check.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultScore {

    private String item;
    private Integer checkId;

    private double score;

    private List<Deduct> deduct;
    private Department department;
    private DepSecend depSecend;
    private Checkitems checkitems;
}
