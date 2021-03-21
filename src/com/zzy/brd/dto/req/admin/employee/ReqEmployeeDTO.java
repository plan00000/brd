package com.zzy.brd.dto.req.admin.employee;
/**
 * @author:xpk
 *    2016年10月13日-上午9:33:49
 **/
public class ReqEmployeeDTO {
	
	
	private long firstDepartmentId;
	
	private long secondDepartmentId;
	
	private long thirdDepartmentId;
	
	private long roleId;
	
	private String userno;
	
	private String name;
	
	private String password;
	
	private String phone;

	public long getFirstDepartmentId() {
		return firstDepartmentId;
	}

	public void setFirstDepartmentId(long firstDepartmentId) {
		this.firstDepartmentId = firstDepartmentId;
	}

	public long getSecondDepartmentId() {
		return secondDepartmentId;
	}

	public void setSecondDepartmentId(long secondDepartmentId) {
		this.secondDepartmentId = secondDepartmentId;
	}

	public long getThirdDepartmentId() {
		return thirdDepartmentId;
	}

	public void setThirdDepartmentId(long thirdDepartmentId) {
		this.thirdDepartmentId = thirdDepartmentId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
