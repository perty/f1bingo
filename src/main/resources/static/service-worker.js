const CACHE_NAME = 'v72'; // Change in misc-info.html as well.

self.addEventListener('install', event => {
    self.skipWaiting();
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => {
            return cache.addAll([
                '/index.html',
                '/admin.html',
                '/calendar.html',
                '/chat.html',
                '/fan.html',
                '/help.html',
                '/home.html',
                '/login.html',
                '/misc-info.html',
                '/points.html',
                '/reset.html',
                '/verify.html',
                '/weekend.html',
                '/css/admin.css',
                '/css/bingo.css',
                '/css/calendar.css',
                '/css/chat.css',
                '/css/fan.css',
                '/css/login.css',
                '/css/misc-info.css',
                '/css/points.css',
                '/css/style.css',
                '/css/team.css',
                '/css/verify.css',
                '/images/logo.png',
                '/images/f1-bg.png',
                '/images/car/alpine.png',
                '/images/car/aston-martin.png',
                '/images/car/ferrari.png',
                '/images/car/haas.png',
                '/images/car/mclaren.png',
                '/images/car/mercedes.png',
                '/images/car/rb.png',
                '/images/car/red-bull.png',
                '/images/car/sauber.png',
                '/images/car/williams.png',
                '/images/flag/finish.png',
                '/misc-info/driver-info.html',
                '/misc-info/grid-2025.html',
                '/js/chat-notification.js',
            ]);
        }).catch(error => {
            console.error('Failed to cache resources during install:', error);
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
        fetch(event.request)
            .then(response => {
                // Om nätverksförfrågan lyckas, uppdatera cachen
                return caches.open(CACHE_NAME).then(cache => {
                    cache.put(event.request, response.clone());
                    return response;
                });
            })
            .catch(() => {
                // Om nätverksförfrågan misslyckas, använd cache
                return caches.match(event.request);
            })
    );
});

self.addEventListener('controllerchange', () => {
    window.location.reload();
});
