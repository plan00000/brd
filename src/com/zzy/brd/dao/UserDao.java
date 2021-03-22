package com.zzy.brd.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO;
import com.zzy.brd.entity.Role;
import com.zzy.brd.entity.User;
import com.zzy.brd.entity.User.State;
import com.zzy.brd.entity.User.UserType;
//import com.zzy.brd.mobile.web.dto.rep.apprentice.RepMyApprenticeDTO;


public interface UserDao extends BaseDao<User>{
	
	User findByUsername(String username);
	
	@Query("select u from User u where u.username=?1 and u.state !=2")
	List<User> findByUsername1(String username);
	
	@Query("select u from User u where u.realname = ?1 and u.userType = ?2")
	User findByRealname(String realname, UserType type);
	
	List<User> findByRole(Role role);
	/** 查询 ：根据手机号 */
	@Query("select u from User u where u.mobileno=?1 and u.state != 2")
	User findByMobileno(String mobileno);
	
	@Query("select u from User u where u.mobileno=?1 and u.state != 2")
	List<User> findByMobileno1(String mobileno);
	
	@Query("select u from User u where u.mobileno=?1 and  u.userType in ?2  and u.state != 2")
	List<User> findByMobileno2(String mobileno,List<UserType> userTypeList);
	
	/*@Query("select u from User u where u.userInfoBoth.recommendCode=?1 and u.state != 2")
	User findByAskperson(String recommendCode);*/
	
	@Query("select u from User u where (u.userType='3' or u.userType='4' or u.userType='6'  ) and u.state!='2'  and  u.userInfoEmployee.department.id=?1 ")
	List<User> findByDepartment(long departmentId);
	
	@Query("select u from User u where (u.userType='0' or u.userType='1' or u.userType='2' or u.userType='4' ) and u.state !='2' ")
	List<User> findStatisticsUser();
	
	/**
	 * 删除一个员工
	 * 
	 * @param @param deleteId
	 * @param @param del
	 * @return void
	 */
	@Modifying
	@Query(value="update User set state = ?2 where id = ?1")
	void delUser(Long deleteId,State del);
	
	/**
	 * 会员、员工停用或启用
	 * @param userId
	 * @param state
	 * @return
	 */
	@Modifying
	@Query(value = "update User set state = ?2 where id = ?1")
	boolean updateWithState(long userId,State state);


	/***
	 *推荐码查找用户 
	 * 
	 */
	/*@Query("select count(*) from User u where u.userInfoBoth.recommendCode=?1")
	int findUserByRecommended(String iCode);*/
	/**
	 * 待审核
	 * Pending audit
	 */                 
	/*@Query("select count(*)  from UserRemark ur right join ur.user u where u.userType in(0,1,2,4) and ur.id=null")
	int pendingAudit();*/
	
	/*@Query("select u from User u where u.userInfoBoth.recommendCode=?1 ")
	User findUserByRecommended2(String iCode);*/
	

	/**
	 * 通过userType查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType=?1")
	int findUserCount(User.UserType userType);
	/**
	 * 通过userTypeh 和创建时间 查找会员数
	 * @param 
	 * @return
	 */
	@Query("select count(*) from User u where u.userType=?1 and u.createdate between ?2 and ?3")
	int findUserCount(User.UserType userType,Date start,Date end);
	
	/**
	 * 根据用户类型查找非删除的所有用户
	 * @param type
	 * @return
	 */
	@Query("select u from User u where u.state = ?1 ")
	List<User> findUsersNoDel(State state);
	
	/**
	 *通过部门查询用户信息
	 * 
	 * @param @param department
	 * @param @return
	 * @return List<User>
	 */
	@Query(value="select u from User u left join u.userInfoEmployee uie left join uie.department d where d.id=?1 and u.state in ?2")
	List<User> findByDepartmentlistAndStates(Long departmentid,List<State> states);
	
	/**
	 *通过各个部门的用户id去查找业务员发展的对应的商家融资和融资经理
	 * 
	 * @param @param department
	 * @param @return
	 * @return List<User>
	 */
	/*@Query(value="select u from User u left join u.userInfoBoth uib where uib.salesman in ?1")
	List<User> findUser(List<User> users);*/
	/**
	 *通过各个部门的用户id去查找业务员发展的对应的商家融资和融资经理
	 * 
	 * @param @param department
	 * @param @return
	 * @return List<User>
	 */
	/*@Query(value="select u from User u left join u.userInfoBoth uib where uib.salesman in ?1 and u.userType =?2")
	List<User> findUser(List<User> users,User.UserType userType);*/
	
	/****
	 * 获取指定用户组昨天注册的徒弟,返回徒弟数组
	 */
	/*@Query("select id from User u where u.userInfoBoth.parent.id in ?3 and u.createdate between ?1 and ?2  ")
	List<Long> findSonIds(Date startTime,Date endTime,List<Long> ids);*/
	
	/**
	 * @param mobileno 
	 * @param realname 
	 * @param username 
	 * @return
	 */
	@Query("select u from User u where u.state = 1 and u.userType in (0,1,2,4) and (u.username like %?1% "
			+ "and u.realname like %?2% and u.mobileno like %?3%) order by u.id")
	List<User> listAllUsers(String username, String realname, String mobileno);
	
	
	/**
	 * @param user
	 *
	 */
	/*@Query("select u from User u where u.userInfoBoth.grandParent =?1  ")
	List<User> findGrandSonsByUser(User user);*/
	/**通过用户类型找到会员列表
	 * @param user
	 *
	 */
	@Query("select u from User u where u.userType =?1  ")
	List<User> findUserByUsertype(UserType userType);
	
	/** 
	 * 统计业务员有多少个融资经理，商家
	 */
	/*@Query("select count(*) from User u where u.userType = ?1 and u.userInfoBoth.parent.id = ?2 and u.state = 1")
	int countsByUserType(UserType userType,long id);*/
	
	
	/**
	 *获取收徒记录 
	 *
	 **/
	/*@Query("select new com.zzy.brd.dto.rep.admin.user.RepApprenticesRecordDTO(u "
			+ " ) from User u where (?1 is null or u.userInfoBoth.parent = ?1) ")
	public Page<RepApprenticesRecordDTO> pageApprenticesRecord(User user,
			Pageable pageable);*/
	
	
	/***
	 * 获取所有的徒弟
	 */
	/*@Query(" select u from User u where u.state!=2 and u.userInfoBoth.parent=?1")
	List<User> findAllSons(User user);*/
	/**
	 * 获取所有的徒孙
	 * 
	 * */
	/*@Query(" select u from User u where u.state!=2 and u.userInfoBoth.grandParent=?1")
	 List<User> findAllGrandSon(User user);*/
	 /**
	   * 获取所有的徒孙孙
	   * 
	   * */
	/*@Query("select u from User u where u.state!=2 and u.userInfoBoth.parent.id in (?1)")
	 List<User> findAllGGrandSon(List<Long> ids);*/
	/****
	 * 根据 号码和用户类型
	 * 
	 */
	@Query("select u from User u where u.mobileno=?1 and u.userType in ( ?2 ) and u.state!='2' ")
	User findByMobileAndUerType(String mobile,List<UserType> typeList);
	
	/**
	 * 根据用户名查询
	 * 
	 **/
	@Query("select u from User u where u.username = ?1 and u.userType in ( ?2 ) and u.state!='2' ")
	User findByUserNameAndUserType(String username,List<UserType> typeList);
	/**
	 * 收徒列表
	 * @return
	 */
	/*@Query(value = "select new com.zzy.brd.mobile.web.dto.rep.apprentice.RepMyApprenticeDTO(u,s.brokerage)  from User u left join UserInfoBoth ub "
			+ "on u.userInfoBoth.id = ub.id  left join (select r.user.id userid,sum(r.haveBrokerage) brokerage "
			+ "from RecordBrokerage r where r.user.id = ?1 and r.relate =1 group by r.gainUser.id) s "
			+ "on u.id = s.userid where u.state = 1 and ub.parent.id = ?1")*/
	/*@Query("select u from User u where u.id = userId")
	List<RepMyApprenticeDTO> getMyApprentices(long userId);*/
	
	//查询商家为某个人的全部会员
	/*@Query("select u from User u where u.userInfoBoth.seller.id =?1 and u.state!='2'")
	List<User> findUserByBussiness(long userId);*/
	
	/*@Query("select u from User u where u.userType = ?1 and u.userInfoBoth.parent.id = ?2 and u.state!='2' ")
	List<User> findUserByUserTypeAndSalesmanId(UserType userType,long salesmanId);*/
	
	/*@Query("select u from User u where u.userInfoBoth.parent.id = ?1 and u.state!='2' ")
	List<User> findSonUsers(long userId);*/
	
	/*@Query("select count(*) from User u where u.userInfoBoth.grandParent.id = ?1 and u.state!='2' ")
	int countGrandSonsForBussiness(long userId);	*/
}
