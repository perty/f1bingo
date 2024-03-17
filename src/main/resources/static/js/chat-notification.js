// Funktion som visar pop-upen
function showNewMessagePopup(message) {
    const popUp = document.getElementById('chatPopup');
    if(popUp) {
        popUp.style.display = 'block';
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        popUp.appendChild(messageElement);
    }
}

function goToChatPage() {
    const popUp = document.getElementById('chatPopup');
    if(popUp) {
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

connect();