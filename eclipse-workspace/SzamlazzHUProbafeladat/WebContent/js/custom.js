$(document).ready(function() {

	$("#add").click(function(e) {
		event.preventDefault()
		$('#termekinfo').append('\<br><br><br><br><br><div id="termekinfo">\
				<input class="tf" type="text" name="termeknev[]" placeholder="  terméknév"/><br><br>\
				<input class="tf" type="text" name="ar[]" placeholder="  ár"/><br><br>\
				<input class="mini-add" type="button" value="+" id="add"/>\
				<input class="mini-torol" type="button" value="X" id="delete"/>\
				</div><br>');
		});

	$('body').on('click', '#delete', function(e) {
		$(this).parent('div').remove();
	});
});