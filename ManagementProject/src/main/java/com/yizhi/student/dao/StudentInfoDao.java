package com.yizhi.student.dao;

import com.yizhi.student.domain.StudentInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 生基础信息表
 * @author dunhf
 * @email 499345515@qq.com
 * @date 2019-08-01 09:45:46
 */
@Mapper
public interface StudentInfoDao {

	StudentInfoDO get(Integer id);

	List<StudentInfoDO> list(int begin,int size,String name,String tocollegeId,String tomajorId,String classId);

	int count(Map<String,Object> map);

	int save(StudentInfoDO studentInfo);

	int update(StudentInfoDO studentInfo);

	int remove(Integer id);

	int batchRemove(Integer[] ids);
}
