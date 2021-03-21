package com.zzy.brd.dto.rep.admin.department;

import com.zzy.brd.entity.Department;

/**
 * @author:xpk
 *    2016年10月13日-下午5:04:20
 **/
public class RepDepartmentDetailDTO {
	
	private long id ;
	
	private String name;
	
	public RepDepartmentDetailDTO(){
		
	}
	
	public RepDepartmentDetailDTO(Department department){
		this.id = department.getId();
		this.name = department.getName();		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
