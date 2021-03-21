package com.zzy.brd.algorithm.generateNo;


public class SimpleGenerateNoAlgorithm implements IGenerateNo {

	private static SimpleGenerateNoAlgorithm instance = new SimpleGenerateNoAlgorithm();

	private SimpleGenerateNoAlgorithm() {

	}

	@Override
	public String generateUserNo(long id) {
		return No.USER.getNo() + 10000 + id;
	}

	@Override
	public String generateProductNo(long id) {
		
		return No.PRODUCT.getNo() + 10000 + id;
	}

	@Override
	public String generateOrderformNo(long id) {
		return No.ORDERFORM.getNo() + 10000 + id;
	}

	@Override
	public String generateFlowno(long id) {
		return No.FLOWITHDRAW.getNo() + 10000 + id;
	}
	
	@Override
	public String generateBrokerageNo(long id) {
		// TODO Auto-generated method stub
		return No.Brokerage.getNo() + 10000 + id;
	}
	
	public static SimpleGenerateNoAlgorithm getInstance() {
		return instance;
	}

	
}
