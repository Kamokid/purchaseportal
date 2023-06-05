$(document).ready(function(){
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
		
	});
	
	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});
	
	$(".linkRemove").on("click", function(evt) {
		evt.preventDefault();
		removeProduct($(this));
	});
});

function decreaseQuantity(link){
		productId = link.attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val())-1;
		
		if(newQuantity > 4){
			quantityInput.val(newQuantity);
			updateQuantity(productId,newQuantity);
		}else{
			showWarningModal("Minimum quantity is 5")
		}
}

function increaseQuantity(link){
		productId = link.attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val())+1;
		
		if(newQuantity <= 20){
			quantityInput.val(newQuantity);
			updateQuantity(productId,newQuantity);
		}else{
			showWarningModal("Maximum quantity is 20")
		}
}

function updateQuantity(productId, quantity){
	url = contextPath + "cart/update/" + productId + "/" + quantity;

	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		updateSubtotal(response, productId);
		updateTotal();
	}).fail(function() {
		showErrorModal("Error while updating product.");
	});
}

function updateSubtotal(updatedSubtotal, productId){
	formatedSubtotal = $.number(updatedSubtotal, 0);
	$("#subtotal" + productId).text(formatedSubtotal);
}

function updateTotal(){
	total = 0.0;
	productCount = 0;
	
	$(".subtotal").each(function(index,element) {
		productCount++;
		total += parseFloat(element.innerHTML.replaceAll(",",""));
	});
	
	if(productCount < 1){
		showEmptyShoppingCart();
	}else{
		formattedTotal = $.number(total, 0);
		$("#total").text(formattedTotal);
	}
}

function removeProduct(link){
	url = link.attr("href");
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
		removeProductHTML(rowNumber);
		updateTotal();
		updateCountNumbers();
		showModalDialog("Shopping Cart", response);
;	}).fail(function() {
		showErrorModal("Error while deleting product.");
	});
}

function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
}

function updateCountNumbers(){
	$(".divCount").each(function(index,element){
		element.innerHTML = ""+ (index + 1);
	});
}

function showEmptyShoppingCart(){
	$("#sectionTotal").hide();
	$("#emptyCart").removeClass("d-none");
}