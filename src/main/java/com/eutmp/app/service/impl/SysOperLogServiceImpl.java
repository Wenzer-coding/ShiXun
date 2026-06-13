package com.eutmp.app.service.impl;

import com.eutmp.app.bean.SysOperLog;
import com.eutmp.app.mapper.SysOperLogMapper;
import com.eutmp.app.service.SysOperLogService;
import com.eutmp.app.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统操作日志表Service实现类
 */
@Slf4j
@Service
public class SysOperLogServiceImpl implements SysOperLogService {
    
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    
    @Override
    public SysOperLog selectById(Long id) {
        log.debug("根据ID查询操作日志: {}", id);
        return sysOperLogMapper.selectById(id);
    }
    
    @Override
    public List<SysOperLog> selectAll() {
        log.debug("查询所有操作日志");
        return sysOperLogMapper.selectAll();
    }
    
    @Override
    public PageResult<SysOperLog> selectByPage(Integer pageNum, Integer pageSize, String operModule, String username, String createTime) {
        log.info("分页查询操作日志: pageNum={}, pageSize={}, operModule={}, username={}, createTime={}", 
                pageNum, pageSize, operModule, username, createTime);
        
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 查询数据
        List<SysOperLog> list = sysOperLogMapper.selectByCondition(operModule, username, createTime);
        
        // 包装成PageInfo
        PageInfo<SysOperLog> pageInfo = new PageInfo<>(list);
        
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
    public List<SysOperLog> selectByUserId(Long userId) {
        log.debug("根据用户ID查询操作日志: {}", userId);
        return sysOperLogMapper.selectByUserId(userId);
    }
    
    @Override
    public int insert(SysOperLog sysOperLog) {
        log.info("新增操作日志: {} - {}", sysOperLog.getOperModule(), sysOperLog.getOperDesc());
        return sysOperLogMapper.insert(sysOperLog);
    }
    
    @Override
    public int update(SysOperLog sysOperLog) {
        log.info("更新操作日志: {}", sysOperLog.getId());
        return sysOperLogMapper.update(sysOperLog);
    }
    
    @Override
    public int deleteById(Long id) {
        log.info("删除操作日志: {}", id);
        return sysOperLogMapper.deleteById(id);
    }
    
    @Override
    public int deleteBatch(Long[] ids) {
        log.info("批量删除操作日志: ids={}", java.util.Arrays.toString(ids));
        if (ids == null || ids.length == 0) {
            return 0;
        }
        return sysOperLogMapper.deleteBatch(ids);
    }
}
