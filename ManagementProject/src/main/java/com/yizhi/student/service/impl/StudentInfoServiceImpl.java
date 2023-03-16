package com.yizhi.student.service.impl;

import com.yizhi.system.domain.MenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;



@Service
public class StudentInfoServiceImpl implements StudentInfoService {



	@Autowired
	private StudentInfoDao studentInfoDao;

	@Override
	public StudentInfoDO get(Integer id){
		System.out.println("======service层中传递过来的id参数是：" + id + "======");
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){
		 String currPage1 = (String) map.get("currPage");
		String pageSize1 = (String)map.get("pageSize");
		int currPage= Integer.parseInt(currPage1);
		int pageSize = Integer.parseInt(pageSize1);
		int begin=(currPage-1)*pageSize;
		int size=pageSize;
		List<StudentInfoDO> students = studentInfoDao.list(begin,size);
		return students;
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}

	@Override
	public int save(StudentInfoDO studentInfo){
		return studentInfoDao.save(studentInfo);
	}

	@Override
	public int update(StudentInfoDO studentInfo){
		return studentInfoDao.update(studentInfo);
	}

	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}

	@Override
	public int batchRemove(Integer[] ids){
		return studentInfoDao.batchRemove(ids);
	}

}
