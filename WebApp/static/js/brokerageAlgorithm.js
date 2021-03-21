
function getDifferenceMonthBrokerage(loanAmount,monthlyInterest,month){
	if (!CommValidate.isNullStr(loanAmount)) {
		var b = loanAmount * monthlyInterest * month/30 * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}
/** 赚差价-按日*/
function getDifferenceDayBrokerage(loanAmount,dayInterest,day){
	if (!CommValidate.isNullStr(loanAmount)) {
		if(day > 15){
			day = 15;
		}
		var b = loanAmount * dayInterest * day * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}
/** 赚差价-收益金*/
function getDifferenceProfitBrokerage(loanAmount,monthlyInterest){
	if (!CommValidate.isNullStr(loanAmount)){
		var b = loanAmount * monthlyInterest * 10000;
		var d = Number((b).toFixed(2));
		return d;
	}else{
		return loanAmount;
	}
}
/** 赚提成-按月*/
function getPercentMonthBrokerage(loanAmount,monthlyInterest,month,royaltyRate){
	if (!CommValidate.isNullStr(loanAmount)) {
		var b = loanAmount * monthlyInterest * month/30 * royaltyRate * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}
/** 赚提成-按日*/
function getPercentByDayBrokerage(loanAmount,dayInterest,day,royaltyRate){
	if (!CommValidate.isNullStr(loanAmount)) {
		if(day > 15){
			day = 15;
		}
		var b = loanAmount * dayInterest * day * royaltyRate * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}
/** 赚提成-收益金*/
function getPercentProfitBrokerage(loanAmount,pa,pb){
	if (!CommValidate.isNullStr(loanAmount)){
		var b = loanAmount * pa * pb * 10000;
		var d = Number((b).toFixed(2));
		return d;
	}else{
		return loanAmount;
	}
}
/** 赚提成-特殊模式*/
function getPercentSpecialBrokerage(loanAmount,month,pa,pb,pc,pd,pe,pf,pg,ph){
	
	if (!CommValidate.isNullStr(loanAmount)) {
		var rate;
		var rate2;
		if(parseInt(month/30) == 6){
			rate = pa;
			rate2 = pb;
		}else if(parseInt(month/30) == 12){
			rate = pc;
			rate2 = pd;
		}else if(parseInt(month/30) == 24){
			rate = pe;
			rate2 = pf;
		}else if(parseInt(month/30) == 36){
			rate = pg;
			rate2 = ph;
		}
		var b = loanAmount * rate2 * rate * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}
/** 赚提成-特殊模式(后台可修改)*/
function getPercentSpecialBrokerageByMonthly(loanAmount,month,pa,pb,pc,pd,pe,pf,pg,ph,monthlyInterest){
	
	if (!CommValidate.isNullStr(loanAmount)) {
		var rate;
		var rate2 = monthlyInterest;
		if(parseInt(month/30) == 6){
			rate = pa;
		}else if(parseInt(month/30) == 12){
			rate = pc;
		}else if(parseInt(month/30) == 24){
			rate = pe;
		}else if(parseInt(month/30) == 36){
			rate = pg;
		}
	
		var b = loanAmount * rate2 * rate * 10000;
		var d = Number((b).toFixed(2));
		return d;
	} else {
		return loanAmount;
	}
}

