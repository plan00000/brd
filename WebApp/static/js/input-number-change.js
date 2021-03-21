function checktext(text){
    allValid = true;
    for (i = 0;  i < text.length;  i++)
    {
      if (text.charAt(i) != " ")
      {
        allValid = false;
        break;
      }
    }
	return allValid;
}

function gbcount(message,used,max){
  if (message.value.length > max) {
  	message.value = message.value.substring(0,max);
 	 used.value = max+"/"+max;
	 alert("输入框最大值为"+max);
	  }
 	 else {
  	used.value = message.value.length+"/"+max;
 	}
}

function gbcount1(message,used,max,mes){
	  if (message.value.length > max) {
	  	message.value = message.value.substring(0,max);
	 	 used.value = max+"/"+max;
		 alert(mes);
		  }
	 	 else {
	  	used.value = message.value.length+"/"+max;
	 	}
	}
//佣金订单里备注提示
function gbcount2(message,used,max){
	if (message.value.length > max) {
	  	message.value = message.value.substring(0,max);
	 	used.value = max+"/"+max;
	 	showMsg("输入框最大值为"+max);
		  }
	 	 else {
	  	used.value = message.value.length+"/"+max;
	 	}
}

