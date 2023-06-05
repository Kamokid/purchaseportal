$(document).ready(function(){
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val())-1;
		
		if(newQuantity > 4){
			quantityInput.val(newQuantity);
		}else{
			showWarningModal("Minimum quantity is 5")
		}
	});
	
	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val())+1;
		
		if(newQuantity <= 20){
			quantityInput.val(newQuantity);
		}else{
			showWarningModal("Maximum quantity is 20")
		}
	});
});