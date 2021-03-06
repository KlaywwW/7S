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
    /**
     * 获取对应部门名称
     * @param depId
     * @return
     */
    Department getDep(Integer depId);

    /**
     * 获取对应二级部门名称
     * @param depId
     * @param depSecendId
     * @return
     */
    DepSecend getSecend(Integer depId,Integer depSecendId);
}
