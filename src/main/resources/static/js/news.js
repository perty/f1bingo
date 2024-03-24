
const options = {
    year: 'numeric', month: 'numeric', day: 'numeric',
    hour: '2-digit', minute: '2-digit', hour12: false
};


// En funktion som hämtar nyheter och lägger till de 5 senaste i DOM:en.
function fetchNews(maxNews) {
    fetch('/news')
        .then(response => response.json())
        .then(news => {
            let newsCounter = maxNews
            const newsContainer = document.getElementById('news');
            newsContainer.innerHTML = '';
            for (const newsItem of news) {
                const newsTimestamp = document.createElement('div');
                const date = new Date(newsItem.timestamp);
                newsTimestamp.textContent = date.toLocaleDateString(undefined, options);
                const newsContent = document.createElement('div');
                newsContent.textContent = newsItem.content;
                const newsElement = document.createElement('div');
                newsElement.appendChild(newsTimestamp);
                newsElement.appendChild(newsContent);
                newsContainer.appendChild(newsElement);
                newsCounter--;
                if (newsCounter === 0) {
                    break;
                }
            }
        })
        .catch(error => console.error('Error:', error));
}
