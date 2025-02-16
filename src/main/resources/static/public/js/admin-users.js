document.addEventListener("DOMContentLoaded", function () {
    function fetchUsers() {
        fetch('/users/all')
            .then(response => response.json())
            .then(data => {
                const users = data.users || [];
                const table = document.getElementById('users');
                table.innerHTML = ''; // Clear the table body
                users.forEach(user => {
                    const row = table.insertRow(-1);
                    row.insertCell(-1).innerText = user.name;
                    row.insertCell(-1).innerText = user.roles;
                });
            })
            .catch(error => {
                console.error('Error fetching users:', error);
            });
    }

    fetchUsers();

    document.getElementById('add-user-form').addEventListener('submit', function (event) {
        event.preventDefault();
        const email = document.getElementById('email').value;
        const roles = document.getElementById('roles').value;
        const password = document.getElementById('initial-pw').value;

        fetch('/users/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email, roles, password})
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text) });
                }
                return response.json();
            })
            .then(user => {
                alert('Fan tillagd  ' + user.email);
                document.getElementById('add-user-form').reset();
                fetchUsers();
            })
            .catch(error => {
                alert('Problem: ' + error.message);
                console.error('Error adding user:', error)
            });
    });
});

document.getElementById('reset-password-form').addEventListener('submit', function (event) {
    event.preventDefault();
    const email = document.getElementById('reset-email').value;
    const newPassword = document.getElementById('new-password').value;

    fetch('/users/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, newPassword})
    })
        .then(response => {
            if (response.status === 404) {
                alert('Error: User not found');
                return Promise.reject('Not Found');
            }
            return response.text();
        })
        .then(message => {
            alert(message);
        })
        .catch(error => console.error('Error resetting password:', error));
});
