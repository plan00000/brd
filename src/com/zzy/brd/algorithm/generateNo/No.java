package com.zzy.brd.algorithm.generateNo;

public enum No {
	/** 用户 */
	USER("u"),
	/** 产品 */
	PRODUCT("p"),
	/** 订单 */
	ORDERFORM("o"),
	/** 提现 */
	FLOWITHDRAW("f"),
	/** 佣金 */
	Brokerage("b"), ;
	private String no;

	private No(String no) {
		this.setNo(no);
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
