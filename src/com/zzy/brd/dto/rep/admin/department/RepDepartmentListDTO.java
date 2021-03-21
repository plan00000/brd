package com.zzy.brd.dto.rep.admin.department;

import java.util.ArrayList;
import java.util.List;

import com.zzy.brd.entity.Department;

/**
 * @author:xpk
 *    2016年10月13日-下午4:29:00
 **/
public class RepDepartmentListDTO {
	
	
	
	private List<RepDepartmentDetailDTO> firstDepartments = new ArrayList<RepDepartmentDetailDTO>();
	
	private List<RepDepartmentDetailDTO> secondDepartments = new ArrayList<RepDepartmentDetailDTO>();
	
	private List<RepDepartmentDetailDTO> thirdDepartment = new ArrayList<RepDepartmentDetailDTO>();

	public List<RepDepartmentDetailDTO> getFirstDepartments() {
		return firstDepartments;
	}

	public void setFirstDepartments(List<RepDepartmentDetailDTO> firstDepartments) {
		this.firstDepartments = firstDepartments;
	}

	public List<RepDepartmentDetailDTO> getSecondDepartments() {
		return secondDepartments;
	}

	public void setSecondDepartments(List<RepDepartmentDetailDTO> secondDepartments) {
		this.secondDepartments = secondDepartments;
	}

	public List<RepDepartmentDetailDTO> getThirdDepartment() {
		return thirdDepartment;
	}

	public void setThirdDepartment(List<RepDepartmentDetailDTO> thirdDepartment) {
		this.thirdDepartment = thirdDepartment;
	}

}
