package com.zzy.brd.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.zzy.brd.entity.interfaces.IdEntity;

/***
 * 部门表
 * 
 * @author wwy
 *
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
public class Department extends IdEntity {
	/**
	 * 
	 */
	
	public enum State{
		DEL,NOMAL,
	}
	
	
	private static final long serialVersionUID = 7735029174310618152L;
	/** 部门名称 */
	private String name;

	/** 父级部门 */
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid", referencedColumnName = "id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Department parent;

	/** 子级部门 */
	@OneToMany(targetEntity = Department.class, mappedBy = "parent", fetch = FetchType.LAZY)
	@OrderBy("id asc ")
	@Where(clause = "state=1 ")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Department> sons = new ArrayList<>();
	
	/**部门人数*/
	private int departmentNum ;
	
	/**部门等级*/
	private int level;
	
	/**累计发放佣金*/
	private BigDecimal addBrokerage;
	
	/**部门状态*/
	private State state;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public List<Department> getSons() {
		return sons;
	}

	public void setSons(List<Department> sons) {
		this.sons = sons;
	}

	public int getDepartmentNum() {
		return departmentNum;
	}

	public void setDepartmentNum(int departmentNum) {
		this.departmentNum = departmentNum;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BigDecimal getAddBrokerage() {
		return addBrokerage;
	}

	public void setAddBrokerage(BigDecimal addBrokerage) {
		this.addBrokerage = addBrokerage;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
