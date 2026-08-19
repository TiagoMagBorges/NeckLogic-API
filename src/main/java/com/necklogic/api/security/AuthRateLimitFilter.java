package com.necklogic.api.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthRateLimitFilter extends OncePerRequestFilter {

    private record LimitedRoute(String method, String path, int capacity, Duration window) {}

    private static final LimitedRoute[] LIMITED_ROUTES = {
            new LimitedRoute("POST", "/auth/login", 8, Duration.ofMinutes(1)),
            new LimitedRoute("POST", "/auth/register", 5, Duration.ofMinutes(5)),
            new LimitedRoute("POST", "/auth/forgot-password", 3, Duration.ofMinutes(5)),
            new LimitedRoute("POST", "/auth/verify-account", 8, Duration.ofMinutes(5)),
            new LimitedRoute("POST", "/auth/reset-password", 5, Duration.ofMinutes(5)),
    };

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LimitedRoute route = matchRoute(request);

        if (route != null) {
            String key = route.path() + "|" + clientIp(request);
            Bucket bucket = buckets.computeIfAbsent(key, k -> newBucket(route));

            if (!bucket.tryConsume(1)) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\":\"Muitas tentativas. Tente novamente em instantes.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private LimitedRoute matchRoute(HttpServletRequest request) {
        for (LimitedRoute route : LIMITED_ROUTES) {
            if (route.method().equalsIgnoreCase(request.getMethod()) && route.path().equals(request.getRequestURI())) {
                return route;
            }
        }
        return null;
    }

    private Bucket newBucket(LimitedRoute route) {
        Bandwidth limit = Bandwidth.classic(route.capacity(), Refill.intervally(route.capacity(), route.window()));
        return Bucket.builder().addLimit(limit).build();
    }

    private String clientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}