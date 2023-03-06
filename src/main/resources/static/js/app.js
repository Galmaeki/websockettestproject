const stompClient = Stomp.over(new SockJS('/ws/chat'));

stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat', function(message) {
        console.log('Received: ' + message);
        showMessage(JSON.parse(message.body));
    });
});

function sendMessage() {
    const message = $('#message').val();
    const username = $('#username').val();
    stompClient.send('/app/chat', {}, JSON.stringify({
        type: 'message',
        username: username,
        message: message
    }));
    $('#message').val('');
}

function showMessage(message) {
    if (message.type === 'join') {
        $('#chat').append('<div><b>' + message.username + ' has joined the chat.</b></div>');
    } else if (message.type === 'message') {
        $('#chat').append('<div><b>' + message.username + ':</b> ' + message.message + '</div>');
    } else if (message.type === 'leave') {
        $('#chat').append('<div><b>' + message.username + ' has left the chat.</b></div>');
    }
}
