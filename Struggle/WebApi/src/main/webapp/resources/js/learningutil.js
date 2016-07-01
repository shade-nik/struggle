/**
 * for testing and learning
 */

function addBorder() {
	$("div").addClass("bordered");
	alert("In the addBorder.");
}

/* Heh.... not worked because not wait until page becomes ready**/
/* addBorder(); */

$().ready(function() {
	console.log("ready");
	addBorder();
});
