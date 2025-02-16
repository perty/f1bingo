document.addEventListener("DOMContentLoaded", function() {
    fetch('/users/single')
        .then(response => response.json())
        .then(data => {
            document.getElementById('user-name').textContent = data.name;
            document.getElementById('user-roles').textContent = data.roles;
            if (data.roles.split(',').includes('ADMIN')) {
                document.getElementById('adminBtn').style.display = 'block';
            }
        })
        .catch(error => console.error('Error fetching user details:', error));

    const newPasswordField = document.getElementById('new-password');
    const confirmPasswordField = document.getElementById('confirm-password');
    const passwordMatchMessage = document.getElementById('password-match-message');
    const changePasswordForm = document.getElementById('change-password-form');

    function checkPasswordsMatch() {
        if (newPasswordField.value !== confirmPasswordField.value) {
            passwordMatchMessage.textContent = 'Olika lösenord';
        } else {
            passwordMatchMessage.textContent = '';
        }
    }

    newPasswordField.addEventListener('input', checkPasswordsMatch);
    confirmPasswordField.addEventListener('input', checkPasswordsMatch);

    changePasswordForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const currentPassword = document.getElementById('current-password').value;
        const newPassword = newPasswordField.value;
        const confirmPassword = confirmPasswordField.value;

        if (newPassword !== confirmPassword) {
            alert('New passwords do not match');
            return;
        }

        fetch('/users/change-password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ currentPassword, newPassword })
        })
            .then(response => {
                if (response.ok) {
                    alert('Password changed successfully');
                    changePasswordForm.reset();
                    passwordMatchMessage.textContent = '';
                } else {
                    alert('Error changing password');
                }
            })
            .catch(error => console.error('Error changing password:', error));
    });
});
