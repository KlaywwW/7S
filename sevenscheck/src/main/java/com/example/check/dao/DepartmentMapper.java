package com.example.check.dao;

import com.example.check.pojo.DepSecend;
import com.example.check.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    DepSecend getSecend(@Param("depId") Integer depId,@Param("depSecendId") Integer depSecendId);
}
