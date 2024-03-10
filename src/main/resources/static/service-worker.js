const CACHE_NAME = 'v15';

self.addEventListener('install', event => {
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => {
            return cache.addAll([
                '/index.html',
                '/admin.html',
                '/calendar.html',
                '/fan.html',
                '/help.html',
                '/login.html',
                '/misc-info.html',
                '/points.html',
                '/reset.html',
                '/verify.html',
                '/weekend.html',
                '/css/admin.css',
                '/css/bingo.css',
                '/css/calendar.css',
                '/css/fan.css',
                '/css/login.css',
                '/css/misc-info.css',
                '/css/points.css',
                '/css/style.css',
                '/css/team.css',
                '/css/verify.css',
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

self.addEventListener('activate', event => {
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.filter(cacheName => cacheName !== CACHE_NAME)
                    .map(cacheName => caches.delete(cacheName))
            );
        }).then(() => self.clients.claim())
    );
});

self.addEventListener('fetch', event => {
    event.respondWith(
        caches.match(event.request)
            .then(response => {
                return response || fetch(event.request);
            }));
});
