$(document).ready(function(){
    $('a[href="#login"]').click(function(){

        var login = function(){
            alert("Weeee");
            var user = $(this).children('form').children('input[type="text"]').val();
            var pass = $(this).children('form').children('input[type="password"]').val();
            if(user.length < 1 || pass.length < 1){
                alert('Invalid!\nPlease fill all required forms');
            } else {
                alert('username: '+user+'\npassword: '+pass);
                $.fallr('hide');
            }
        }

        $.fallr('show', {
            width       : '320px',
            content     : '<h4>Sign in</h4>'
                + '<form>'
                +     '<input placeholder="username" type="text"/'+'>'
                +     '<input placeholder="password" type="password"/'+'>'
                + '</form>',
            buttons : {
                button1 : {text: 'Submit', onclick: login},
                button4 : {text: 'Cancel'}
            }
        });
    });
});