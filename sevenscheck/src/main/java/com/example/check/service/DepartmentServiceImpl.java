package com.example.check.service;

import com.example.check.dao.DepartmentMapper;
import com.example.check.pojo.DepSecend;
import com.example.check.pojo.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDep() {
        return departmentMapper.getAllDep();
    }

    @Override
    public List<DepSecend> getDepSecend(Integer depId) {
        return departmentMapper.getDepSecend(depId);
    }

    @Override
    public Department getDep(Integer depId) {
        return departmentMapper.getDep(depId);
    }

    @Override
    public DepSecend getSecend(Integer depId, Integer depSecendId) {
        return departmentMapper.getSecend(depId,depSecendId);
    }
}
