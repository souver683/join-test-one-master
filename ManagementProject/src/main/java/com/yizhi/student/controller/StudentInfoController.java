package com.yizhi.student.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizhi.common.annotation.Log;
import com.yizhi.common.controller.BaseController;
import com.yizhi.common.utils.*;
import com.yizhi.student.domain.ClassDO;
import com.yizhi.student.service.ClassService;
import com.yizhi.student.service.CollegeService;
import com.yizhi.student.service.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;

import javax.servlet.http.HttpServletRequest;

/**
 * 生基础信息表
 */

@Controller
@RequestMapping("/student/studentInfo")
public class StudentInfoController {
	@Autowired
	private StudentInfoService studentInfoService;
    //
	@Log("学生信息保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("student:studentInfo:add")
	public R save(StudentInfoDO studentInfoDO, HttpServletRequest request){
		if (studentInfoDO==null||request==null){
			return R.error("学生信息不能为空！");
		}
		Date date=new Date();
		Timestamp addTime=new Timestamp(date.getTime());
		Timestamp editTime=new Timestamp(date.getTime());
		studentInfoDO.setEditTime(editTime);
		studentInfoDO.setAddTime(addTime);
		String studentId = studentInfoDO.getStudentId();
		String certify = studentInfoDO.getCertify();
		String political = studentInfoDO.getPolitical();
		if(StringUtils.isAllBlank(studentId,certify,political)) {
			return R.error("请检查是否填写学号、身份证号、和一卡通账号！");
		}
		int save = studentInfoService.save(studentInfoDO);
		if (save<=0){
			return R.error("系统异常，保存失败！");
		}
		return new  R();
	}

	/**
	 * 可分页 查询
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("student:studentInfo:studentInfo")
	public PageUtils list(@RequestParam Map<String, Object> map){
		List<StudentInfoDO> list = studentInfoService.list(map);
		int count = studentInfoService.count(map);
		return new PageUtils(list,count);
	}


	/**
	 * 修改
	 */
	@Log("学生基础信息表修改")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("student:studentInfo:edit")
	public R update(StudentInfoDO studentInfo){
		Date date=new Date();
		Timestamp editTime=new Timestamp(date.getTime());
		studentInfo.setEditTime(editTime);
		studentInfoService.update(studentInfo);
		return new R();
	}

	/**
	 * 删除
	 */
	@Log("学生基础信息表删除")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:remove")
	public R remove( Integer id){
		studentInfoService.remove(id);
		return new R();
	}

	/**
	 * 批量删除
	 */
	@Log("学生基础信息表批量删除")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		studentInfoService.batchRemove(ids);
		return new R();
	}


	//前后端不分离 客户端 -> 控制器-> 定位视图
	/**
	 * 学生管理 点击Tab标签 forward页面
	 */
	@GetMapping()
	@RequiresPermissions("student:studentInfo:studentInfo")
	String StudentInfo(){
		return "student/studentInfo/studentInfo";
	}

	/**
	 * 更新功能 弹出View定位
	 */
	@GetMapping("/edit/{id}")
	@RequiresPermissions("student:studentInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		StudentInfoDO studentInfo = studentInfoService.get(id);
		model.addAttribute("studentInfo", studentInfo);
		return "student/studentInfo/edit";
	}

	/**
	 * 学生管理 添加学生弹出 View
	 */
	@GetMapping("/add")
	@RequiresPermissions("student:studentInfo:add")
	String add(){
	    return "student/studentInfo/add";
	}

}//end class
