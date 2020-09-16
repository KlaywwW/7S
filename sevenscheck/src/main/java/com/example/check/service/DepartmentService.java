package com.example.check.service;

import com.example.check.pojo.DepSecend;
import com.example.check.pojo.Department;

import java.util.List;

public interface DepartmentService {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDep();

    /**
     * 获取对应的二级部门
     * @param depId 部门id
     * @return
     */
    List<DepSecend> getDepSecend(Integer depId);
}
