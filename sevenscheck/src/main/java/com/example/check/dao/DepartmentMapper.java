package com.example.check.dao;

import com.example.check.pojo.DepSecend;
import com.example.check.pojo.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
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
