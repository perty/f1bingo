
self.addEventListener('install', event => {
    self.skipWaiting();
});

const CACHE_NAME = 'v2026.1'; // Change in misc-info.html as well.

// Define which requests should be cached
const CACHEABLE_REQUESTS = [
    /\.(?:html|css|js|woff2|png|jpg|jpeg|svg|gif)$/
];

// Function to check if a request should be cached
function shouldCache(request) {
    // Don't cache POST/PUT/DELETE requests
    if (request.method !== 'GET') {
        return false;
    }

    // Check URL against cacheable patterns
    const url = new URL(request.url);
    return CACHEABLE_REQUESTS.some(pattern => pattern.test(url.pathname));
}

// Function to handle network requests with timeout
async function timeoutFetch(request, timeoutMs = 5000) {
    return Promise.race([
        fetch(request),
        new Promise((_, reject) =>
            setTimeout(() => reject(new Error('Request timeout')), timeoutMs)
        )
    ]);
}

// Main fetch event handler
self.addEventListener('fetch', event => {
    event.respondWith(handleFetch(event.request));
});

async function handleFetch(request) {
    // Try to get from cache first
    try {
        const cache = await caches.open(CACHE_NAME);
        const cachedResponse = await cache.match(request);

        if (cachedResponse) {
            // If we have a cached response, return it and update cache in background
            // This implements a stale-while-revalidate pattern
            if (navigator.onLine && shouldCache(request)) {
                updateCache(request, cache).catch(err => console.warn('Background cache update failed:', err));
            }
            return cachedResponse;
        }

        // If not in cache, make network request
        const response = await timeoutFetch(request);

        // Only cache successful responses that meet our criteria
        if (response.ok && shouldCache(request)) {
            await cache.put(request, response.clone());
        }

        return response;
    } catch (error) {
        console.error('Service worker fetch error:', error);

        // Try to return something from cache as fallback
        const cache = await caches.open(CACHE_NAME);
        const cachedResponse = await cache.match(request);

        if (cachedResponse) {
            return cachedResponse;
        }

        // For navigation requests, return the offline page
        if (request.mode === 'navigate') {
            return caches.match('/public/offline.html');
        }

        // Otherwise, return a simple error response
        return new Response('Network error occurred', {
            status: 503,
            statusText: 'Service Unavailable',
            headers: new Headers({
                'Content-Type': 'text/plain'
            })
        });
    }
}

// Function to update cache in background
async function updateCache(request, cache) {
    try {
        const freshResponse = await fetch(request);
        if (freshResponse.ok) {
            await cache.put(request, freshResponse);
        }
    } catch (err) {
        throw err; // Let caller handle this
    }
}

// Cache cleanup - run periodically
self.addEventListener('activate', event => {
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.map(cacheName => {
                    if (cacheName !== CACHE_NAME) {
                        return caches.delete(cacheName);
                    }
                })
            );
        })
    );
});

self.addEventListener('controllerchange', () => {
    window.location.reload();
});
