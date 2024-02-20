self.addEventListener('install', event => {
    event.waitUntil(
        caches.open('v1').then(cache => {
            return cache.addAll([
                '/index.html',
                '/css/style.css',
                '/images/car/rb.png',
                '/images/car/haas.png',
                '/images/car/sauber.png',
                '/images/car/mclaren.png',
                '/images/car/alpine.png',
                '/images/car/mercedes.png',
                '/images/car/aston-martin.png',
                '/images/car/red-bull.png',
                '/images/car/ferrari.png',
                '/images/car/williams.png',
            ]);
        })
    );
});

self.addEventListener('fetch', event => {
    event.respondWith(caches.match(event.request).then(response => {
        return response || fetch(event.request);
    }));
});
