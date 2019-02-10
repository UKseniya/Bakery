// $("#hidden").click(function () {
//
//     if ($("#details").is(":visible")) $("#details").hide()
//     else $("#details").show();
// });

// $('#hidden').bind('click', function(){
//     $(this).hide();
//     $('#details').show();
// });

// function toggle_visibility(id) {
//     var e = document.getElementById(id);
//     if(e.style.display == 'block')
//         e.style.display = 'none';
//     else
//         e.style.display = 'block';
// }

function unhide(divID) {
    var item = document.getElementById(divID);
    if (item) {
        item.className=(item.className=='hidden')?'unhidden':'hidden';
    }
}