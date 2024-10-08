// Funktion som visar pop-upen
function showNewMessagePopup(message) {
    const popUp = document.getElementById('chatPopup');
    if (popUp) {
        popUp.style.display = 'block';
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        popUp.appendChild(messageElement);
    }
}

function goToChatPage() {
    const popUp = document.getElementById('chatPopup');
    if (popUp) {
        popUp.style.display = 'none';
        window.location.href = '/chat.html';
    }
}

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

document.addEventListener('DOMContentLoaded', () => {
    // Kontrollera om det finns nya meddelanden vid sidladdning
    const fanId = localStorage.getItem('selectedFan');
    if (fanId) {
        checkForNewMessages(fanId);
    }

    connect(); // Anslut till WebSocket
});

function checkForNewMessages(fanId) {
    fetch('/chat/new-messages/' + fanId, {
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
