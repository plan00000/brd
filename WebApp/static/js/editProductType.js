head.ready('jquery', function(){
    head.ready(document, function() {
    	$(".edit_productName").blur(editLostBlur);
    });
});
/** 编辑产品类型*/
function editProductType(object){
	var $this = $(object);
	var id = $this.data('value');
	var name = $this.data('name');
	/* var a = $(".edittypename").find("input").length;
	
		editLostBlur;
		 */
	editLostBlur;
	tdproduct = "#td_productname"+id;
	$(tdproduct).html("<input type = 'text' class = 'edit_productName' data-id ='"+id+"' value = '"+name+"' name = 'productName'>");
	$(tdproduct).find("input").focus();
	
}
function editLostBlur(){
	if($(".edit_productName").length>0 ){
		var editproductname = $(".edit_productName").val().trim();
		
		if(editproductname.length ==0){
			return ;
		}else{
			var data = {};
			data.productTypeId = $(".edit_productName").data('id');
			data.productName = editproductname;
			$.post("${ctx}/admin/product/productType/edit;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
				if(res.code ==1){
					location.reload();
				}else{
					alert(res.mes);
				}
			})
		}
	}
}