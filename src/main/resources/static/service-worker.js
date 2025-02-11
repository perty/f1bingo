const CACHE_NAME = 'v72'; // Change in misc-info.html as well.

self.addEventListener('install', event => {
    self.skipWaiting();
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => {
            return cache.addAll([
                '/index.html',
                '/home.html',
                '/public/help.html',
                '/public/calendar.html',
                '/public/misc-info/team-info.html',
                '/public/misc-info/points-info.html',
                '/public/misc-info/driver-info.html',
                '/public/misc-info/tyre-info.html',
                '/public/misc-info/grid-2025.html',
                '/public/misc-info/qual-info.html',
                '/public/points.html',
                '/public/misc-info.html',
                '/public/team/ferrari.html',
                '/public/team/haas.html',
                '/public/team/aston-martin.html',
                '/public/team/mclaren.html',
                '/public/team/rb.html',
                '/public/team/mercedes.html',
                '/public/team/sauber.html',
                '/public/team/williams.html',
                '/public/team/alpine.html',
                '/public/team/red-bull.html',
                '/private/reset.html',
                '/private/weekend.html',
                '/private/admin.html',
                '/private/create-news.html',
                '/private/fan.html',
                '/private/verify.html',
                '/private/chat.html',
                '/public/css/misc-info.css',
                '/public/css/team.css',
                '/public/css/fan.css',
                '/public/css/login.css',
                '/public/css/admin.css',
                '/public/css/chat.css',
                '/public/css/verify.css',
                '/public/css/style.css',
                '/public/css/bingo.css',
                '/public/css/points.css',
                '/public/css/calendar.css',
                '/public/images/flag/hungary.png',
                '/public/images/flag/china.png',
                '/public/images/flag/argentina.png',
                '/public/images/flag/saudi-arabia.png',
                '/public/images/flag/abu-dhabi.png',
                '/public/images/flag/great-britain.png',
                '/public/images/flag/usa.png',
                '/public/images/flag/monaco.png',
                '/public/images/flag/australia.png',
                '/public/images/flag/belgium.png',
                '/public/images/flag/brazil.png',
                '/public/images/flag/switzerland.png',
                '/public/images/flag/canada.png',
                '/public/images/flag/qatar.png',
                '/public/images/flag/azerbaijan.png',
                '/public/images/flag/germany.png',
                '/public/images/flag/austria.png',
                '/public/images/flag/spain.png',
                '/public/images/flag/bahrain.png',
                '/public/images/flag/finish.png',
                '/public/images/flag/france.png',
                '/public/images/flag/thailand.png',
                '/public/images/flag/italy.png',
                '/public/images/flag/new-zealand.png',
                '/public/images/flag/denmark.png',
                '/public/images/flag/singapore.png',
                '/public/images/flag/netherlands.png',
                '/public/images/flag/japan.png',
                '/public/images/flag/finland.png',
                '/public/images/flag/mexico.png',
                '/public/images/car/alpine.png',
                '/public/images/car/red-bull.png',
                '/public/images/car/mercedes.png',
                '/public/images/car/aston-martin.png',
                '/public/images/car/mclaren.png',
                '/public/images/car/rb.png',
                '/public/images/car/ferrari.png',
                '/public/images/car/williams.png',
                '/public/images/car/haas.png',
                '/public/images/car/sauber.png',
                '/public/images/f1-bg.png',
                '/public/images/track/monza.png',
                '/public/images/track/gilles-villeneuve.png',
                '/public/images/track/sakhir.png',
                '/public/images/track/silverstone.png',
                '/public/images/track/shanghai.png',
                '/public/images/track/red-bull.png',
                '/public/images/track/suzuka.png',
                '/public/images/track/cotas.png',
                '/public/images/track/monaco.png',
                '/public/images/track/lusail.png',
                '/public/images/track/las-vegas.png',
                '/public/images/track/barcelona.png',
                '/public/images/track/melbourne.png',
                '/public/images/track/jedda.png',
                '/public/images/track/hermanos.png',
                '/public/images/track/marina-bay.png',
                '/public/images/track/spa.png',
                '/public/images/track/baku.png',
                '/public/images/track/enzo.png',
                '/public/images/track/yas-marina.png',
                '/public/images/track/track1.png',
                '/public/images/track/zandvoort.png',
                '/public/images/track/miami.png',
                '/public/images/track/interlagos.png',
                '/public/images/track/hungaroring.png',
                '/public/images/logo.png',
                '/public/images/tyres/yellow.png',
                '/public/images/tyres/blue.png',
                '/public/images/tyres/green.png',
                '/public/images/tyres/white.png',
                '/public/images/tyres/red.png',
                '/public/images/flag2.png',
                '/public/ikon/ikon-512x512.png',
                '/public/ikon/ikon-192x192.png',
                '/public/images/chat.svg',
                '/public/images/screenshot.jpg',
                '/public/images/screenshot2.jpg',
                '/public/js/chat-notification.js',
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
