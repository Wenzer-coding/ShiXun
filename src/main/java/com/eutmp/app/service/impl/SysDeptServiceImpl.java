package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysDept;
import com.eutmp.app.bean.SysUser;
import com.eutmp.app.mapper.SysDeptMapper;
import com.eutmp.app.mapper.SysUserMapper;
import com.eutmp.app.service.SysDeptService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eutmp.app.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门表Service实现类
 */
@Slf4j
@Service
public class SysDeptServiceImpl implements SysDeptService {
    
    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysDept selectById(Long id) {
        log.debug("根据ID查询部门: {}", id);
        return sysDeptMapper.selectById(id);
    }
    
    @Override
    public List<SysDept> selectAll() {
        log.debug("查询所有部门");
        return sysDeptMapper.selectAll();
    }
    
    @Override
    public PageResult<SysDept> selectByPage(Integer pageNum, Integer pageSize, String deptName) {
        log.info("分页查询部门: pageNum={}, pageSize={}, deptName={}", pageNum, pageSize, deptName);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据（根据是否有搜索条件选择查询方法）
        List<SysDept> list;
        if (deptName != null && !deptName.trim().isEmpty()) {
            list = sysDeptMapper.selectByDeptName(deptName.trim());
        } else {
            list = sysDeptMapper.selectAll();
        }
        
        // 包装成PageInfo
        PageInfo<SysDept> pageInfo = new PageInfo<>(list);
        
        log.info("分页查询结果: total={}, pages={}", pageInfo.getTotal(), pageInfo.getPages());
        
        // 返回统一的分页结果
        return PageResult.of(
            pageInfo.getPageNum(),
            pageInfo.getPageSize(),
            pageInfo.getTotal(),
            pageInfo.getList()
        );
    }
    
    @Override
    public List<SysDept> selectByParentId(Long parentId) {
        log.debug("根据父部门ID查询: {}", parentId);
        return sysDeptMapper.selectByParentId(parentId);
    }
    
    @Override
    public List<SysDept> selectByDeptName(String deptName) {
        log.debug("根据部门名称模糊查询: {}", deptName);
        return sysDeptMapper.selectByDeptName(deptName);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(SysDept sysDept) {
        log.info("新增部门: {}", sysDept.getDeptName());
        
        // 设置默认值
        if (sysDept.getParentId() == null) {
            sysDept.setParentId(0L);
        }
        if (sysDept.getSort() == null) {
            sysDept.setSort(0);
        }
        if (sysDept.getStatus() == null) {
            sysDept.setStatus(1);
        }
        
        // 设置时间
        String now = DateUtils.now();
        sysDept.setCreateTime(now);
        sysDept.setUpdateTime(now);
        
        return sysDeptMapper.insert(sysDept);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SysDept sysDept) {
        log.info("更新部门: {}", sysDept.getId());
        
        // 设置更新时间
        sysDept.setUpdateTime(DateUtils.now());
        
        return sysDeptMapper.update(sysDept);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        log.info("开始删除部门: id={}", id);
        
        // 检查部门是否存在
        SysDept dept = sysDeptMapper.selectById(id);
        if (dept == null) {
            log.error("部门不存在: id={}", id);
            throw new RuntimeException("部门不存在");
        }
        
        log.info("部门信息: id={}, name={}, parentId={}", id, dept.getDeptName(), dept.getParentId());
        
        // 检查是否有子部门
        List<SysDept> children = sysDeptMapper.selectByParentId(id);
        if (children != null && !children.isEmpty()) {
            log.warn("删除失败 - 存在子部门: id={}, 子部门数量={}", id, children.size());
            children.forEach(child -> 
                log.warn("  子部门: id={}, name={}", child.getId(), child.getDeptName())
            );
            throw new RuntimeException("该部门下还有 " + children.size() + " 个子部门，请先删除或转移子部门");
        }
        
        // 检查部门下是否有用户
        List<SysUser> users = sysUserMapper.selectByDeptId(id);
        if (users != null && !users.isEmpty()) {
            throw new RuntimeException("该部门下还有 " + users.size() + " 个用户，请先转移用户后再删除");
        }

        log.info("子部门检查通过，准备删除: id={}, name={}", id, dept.getDeptName());
        
        // 执行删除
        try {
            int result = sysDeptMapper.deleteById(id);
            if (result > 0) {
                log.info("部门删除成功: id={}, name={}", id, dept.getDeptName());
            } else {
                log.error("部门删除失败 - 影响行数为0: id={}", id);
                throw new RuntimeException("删除失败，请检查数据完整性");
            }
            return result;
        } catch (Exception e) {
            log.error("部门删除异常: id={}, name={}, error={}", id, dept.getDeptName(), e.getMessage(), e);
            throw new RuntimeException("删除部门失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> deptList) {
        log.debug("构建部门树形结构");
        
        if (deptList == null || deptList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 找出所有根节点（parentId = 0）
        List<SysDept> rootList = deptList.stream()
                .filter(dept -> dept.getParentId() == null || Long.valueOf(0).equals(dept.getParentId()))
                .collect(Collectors.toList());
        
        // 为每个根节点递归设置子节点
        for (SysDept root : rootList) {
            setChildren(root, deptList);
        }
        
        log.debug("构建完成，根节点数量: {}", rootList.size());
        return rootList;
    }
    
    /**
     * 递归设置子节点
     */
    private void setChildren(SysDept parent, List<SysDept> allDepts) {
        // 找出当前节点的所有子节点
        List<SysDept> children = allDepts.stream()
                .filter(dept -> parent.getId().equals(dept.getParentId()))
                .collect(Collectors.toList());
        
        // 如果有子节点，设置并递归
        if (!children.isEmpty()) {
            parent.setChildren(children);
            for (SysDept child : children) {
                setChildren(child, allDepts);
            }
        }
    }
}
