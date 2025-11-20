// Handle login form
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            const data = await response.json();

            if (!response.ok) {
                document.getElementById('errorMsg').textContent = data.message || 'Login failed';
                return;
            }

            localStorage.setItem('token', data.accessToken);
            window.location.href = 'index.html';
        } catch (err) {
            console.error(err);
            document.getElementById('errorMsg').textContent = 'Error connecting to server';
        }
    });
}

// Fetch books on index.html
async function fetchBooks() {
    const bookList = document.getElementById('bookList');
    if (!bookList) return;

    const token = localStorage.getItem('token');
    if (!token) {
        alert('Please login first');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/book?pageNumber=0&pageSize=10', {
            headers: { 'Authorization': 'Bearer ' + token }
        });

        if (!response.ok) {
            alert('Failed to fetch books. Maybe token expired.');
            localStorage.removeItem('token');
            window.location.href = 'login.html';
            return;
        }

        const data = await response.json();
        data.content.forEach(book => {
            const li = document.createElement('li');
            li.textContent = `${book.title} by ${book.author} - $${book.price}`;
            bookList.appendChild(li);
        });

    } catch (err) {
        console.error(err);
    }
}

fetchBooks();

// Logout button
const logoutBtn = document.getElementById('logoutBtn');
if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('token');
        window.location.href = 'login.html';
    });
}
