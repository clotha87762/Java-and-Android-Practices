var host = "http://localhost:54320";

$(document).ready(function () {

    // get message
    function updateMsg() {
        var request = $.ajax({
            url: host + "/messages",
            type: 'GET',
            success: function (data, status) {

                $('#loader').addClass('hide');

				$('#msg-list').empty();
				
                var msgArray = eval(data);
				
                var i;
                for (i = 0; i < msgArray.length; i++) {
                    var msg = msgArray[i];
					var time = new Date(msg['time']).toLocaleString();
					var cont = msg['content'].toString();
					cont = cont.replace(/&/g, "&amp;")
								.replace(/</g, "&lt;")
								.replace(/>/g, "&gt;")
								.replace(/"/g, "&quot;")
								.replace(/'/g, "&#039;");

					$("#msg-list").append('<div class="list-group-item">' + '<h4 class="list-group-item-heading">[' + escape(msg['sender']['username'].toString()) + '] says </h4><p>' + cont + '</p><p class="text-right small">' + time + '</p></div>');
                }

            },
            dataType: 'json'
        });

    }

    updateMsg();


    // login
    $('#login-btn').on('click', function () {

        var username = $('#username').val();
        var password = $('#password').val();

        var request = $.ajax({
            url: host + "/session",
            type: 'POST',
            data: JSON.stringify({
                "username": username,
                "password": password
            }),
            dataType: 'json',
			contentType: 'application/json',
            statusCode: {
                201: function () {
                    $('#login-navbtn').addClass('hide');
                    $('#logout-navbtn').removeClass('hide');
                    $('#post-navbtn').removeClass('hide');

                    $('#login-dialog').modal('hide');
                },
                400: function () {
                    $('#loginError').show();
                }
            }
        });

    });


    // logout
    $('#logout-navbtn').on('click', function () {

        var request = $.ajax({
            url: host + "/session",
            type: 'DELETE',
			success:
			function () {
				$('#login-navbtn').removeClass('hide');
				$('#logout-navbtn').addClass('hide');
				$('#post-navbtn').addClass('hide');
			}
        });


    });


    // post message
    $('#post-btn').on('click', function () {

        $('post-success').alert();

        var msg = $('#message-text').val();

        $.ajax({
            url: host + "/messages",
            type: 'POST',
            success: function () {
                $('#post-dialog').modal('hide');
				updateMsg();
            },
            data: JSON.stringify({
                "content": msg
            }),
            contentType: 'application/json'
        });

    });




});