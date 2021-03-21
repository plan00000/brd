package com.zzy.brd.algorithm.generateNo;

public interface IGenerateNo {
	
	/***
	 * 生成用户no
	 * @param id
	 * @return
	 */
	public String generateUserNo(long id);
	
	/***
	 * 生成产品no
	 * @param id
	 * @return
	 */
	public String generateProductNo(long id);
	
	/***
	 * 生成订单no
	 * @param id
	 * @return
	 */
	public String generateOrderformNo(long id);
	
	/**
	 * 提现no
	 * @param id
	 * @return
	 */
	public String generateFlowno(long id);
	
	/***
	 * 生成佣金no
	 * @param id
	 * @return
	 */
	public String generateBrokerageNo(long id);
}
