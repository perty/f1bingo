function showNewMessagePopup(message) {
    const popUp = document.getElementById('chatPopup');
    if (popUp) {
        popUp.style.display = 'block';
        const messageElement = document.createElement('div');
        messageElement.textContent = message.length > 120 ? message.substring(0, 120) + '...' : message;
        popUp.appendChild(messageElement);

        // Hide the popup after 15 seconds
        setTimeout(() => {
            popUp.style.display = 'none';
            popUp.removeChild(messageElement);
        }, 15000);
    }
}

function goToChatPage() {
    const popUp = document.getElementById('chatPopup');
    if (popUp) {
        popUp.style.display = 'none';
        window.location.href = '/private/chat.html';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const host = location.hostname + ":" + location.port;
    const stompClient = new StompJs.Client({
        brokerURL: 'wss://' + host + '/chat-ws'
    });

    stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', (message) => {
            console.log('Received message: ' + message);
            showNewMessagePopup(JSON.parse(message.body).message);
        });
    };

    stompClient.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    function connect() {
        stompClient.activate();
    }

    connect(); // Anslut till WebSocket
});

checkForNewMessages();

function checkForNewMessages() {
    fetch('/chat/new-messages', {
        method: 'GET',
    })
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                data.forEach(message => {
                    showNewMessagePopup(message.message);
                });
            }
        })
        .catch(error => console.error('Error fetching new messages:', error));
}
