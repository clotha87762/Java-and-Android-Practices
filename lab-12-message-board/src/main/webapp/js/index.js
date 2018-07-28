$(document).ready(function () {

    // get message
    function updateMsg() {
        var request = $.ajax({
            url: "messages",
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

					$("#msg-list").append('<div class="list-group-item"><p>' + cont + '</p><p class="text-right small">' + time + '</p></div>');
                }

            },
            dataType: 'json'
        });

    }

    updateMsg();

    
    // post message
    $('#post-btn').on('click', function () {

        $('post-success').alert();

        var msg = $('#message-text').val();

        $.ajax({
            url: "messages",
            type: 'POST',
            success: function () {
                $('#post-dialog').modal('hide');
                $('#message-text').val("");
				updateMsg();
            },
            data: JSON.stringify({
                "content": msg
            }),
            contentType: 'application/json'
        });

    });




});