package app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import facade.TravelBookingFacade;
import factory.AdventurePackageFactory;
import factory.BeachPackageFactory;
import model.Trip;
import observer.BookingConfirmationObserver;
import observer.BookingCounterObserver;
import observer.BookingNotifier;
import strategy.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/** Local website and Java API; uses the existing six design patterns. */
public final class TravelWebServer {
    private TravelWebServer() {}

    public static HttpServer start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", Integer.getInteger("travel.port", 8765)), 0);
        BookingNotifier notifier = new BookingNotifier();
        notifier.subscribe(new BookingConfirmationObserver());
        BookingCounterObserver counter = new BookingCounterObserver();
        notifier.subscribe(counter);
        TravelBookingFacade facade = new TravelBookingFacade(notifier);
        Path frontend = Path.of("frontend").toAbsolutePath().normalize();

        server.createContext("/api/quote", exchange -> {
            if (!"GET".equals(exchange.getRequestMethod())) { reply(exchange, 405, "application/json", "{\"error\":\"Method not allowed\"}"); return; }
            try {
                Map<String, String> p = parse(exchange.getRequestURI().getRawQuery());
                Trip trip = createTrip(p, null);
                reply(exchange, 200, "application/json", result(trip, false, 0));
            } catch (IllegalArgumentException e) { reply(exchange, 400, "application/json", error(e.getMessage())); }
        });
        server.createContext("/api/book", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) { reply(exchange, 405, "application/json", "{\"error\":\"Method not allowed\"}"); return; }
            try {
                byte[] bytes = exchange.getRequestBody().readNBytes(4097);
                if (bytes.length > 4096) throw new IllegalArgumentException("Request is too large");
                Map<String, String> p = parse(new String(bytes, StandardCharsets.UTF_8));
                String name = p.getOrDefault("name", "").trim();
                String email = p.getOrDefault("email", "").trim();
                if (name.length() < 2 || name.length() > 80 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
                    throw new IllegalArgumentException("Please enter a valid name and email");
                Trip trip = createTrip(p, facade);
                reply(exchange, 200, "application/json", result(trip, true, counter.getBookingCount()));
            } catch (IllegalArgumentException e) { reply(exchange, 400, "application/json", error(e.getMessage())); }
        });
        server.createContext("/", exchange -> {
            if (!"GET".equals(exchange.getRequestMethod())) { reply(exchange, 405, "text/plain", "Method not allowed"); return; }
            String url = exchange.getRequestURI().getPath();
            String file = url.equals("/") ? "index.html" : url.substring(1);
            Path target = frontend.resolve(file).normalize();
            if (!target.startsWith(frontend) || !Files.isRegularFile(target)) { reply(exchange, 404, "text/plain", "Not found"); return; }
            String type = file.endsWith(".css") ? "text/css" : file.endsWith(".js") ? "text/javascript" : "text/html";
            byte[] content = Files.readAllBytes(target);
            exchange.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
            exchange.getResponseHeaders().set("Cache-Control", "no-store");
            exchange.sendResponseHeaders(200, content.length);
            try (var out = exchange.getResponseBody()) { out.write(content); }
        });
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        return server;
    }

    private static Trip createTrip(Map<String, String> p, TravelBookingFacade facade) {
        String type = p.getOrDefault("trip", "beach");
        PriceCalculator calc = new PriceCalculator(strategy(p.getOrDefault("strategy", "regular")));
        boolean insurance = "true".equals(p.get("insurance"));
        boolean luggage = "true".equals(p.get("luggage"));
        if (facade != null) {
            if ("beach".equals(type)) return facade.bookBeachTrip(calc, insurance, luggage);
            if ("adventure".equals(type)) return facade.bookAdventureTrip(calc, insurance, luggage);
        } else {
            // Quote uses the same factories and decorators without notifying observers.
            Trip trip;
            if ("beach".equals(type)) trip = new BeachPackageFactory().createPackage(calc);
            else if ("adventure".equals(type)) trip = new AdventurePackageFactory().createPackage(calc);
            else throw new IllegalArgumentException("Unknown trip");
            if (insurance) trip = new decorator.TravelInsuranceDecorator(trip);
            if (luggage) trip = new decorator.ExtraLuggageDecorator(trip);
            return trip;
        }
        throw new IllegalArgumentException("Unknown trip");
    }

    private static PricingStrategy strategy(String s) {
        return switch (s) {
            case "regular" -> new RegularPricingStrategy();
            case "seasonal" -> new SeasonalDiscountStrategy(15);
            case "longstay" -> new LongStayDiscountStrategy();
            case "lastminute" -> new LastMinuteSurchargeStrategy();
            default -> throw new IllegalArgumentException("Unknown pricing strategy");
        };
    }

    private static Map<String, String> parse(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isEmpty()) return result;
        for (String item : query.split("&")) {
            String[] pair = item.split("=", 2);
            result.put(URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.length > 1 ? pair[1] : "", StandardCharsets.UTF_8));
        }
        return result;
    }

    private static String result(Trip trip, boolean booked, int count) {
        return "{\"destination\":\"" + esc(trip.getDestination()) + "\",\"description\":\"" + esc(trip.getDescription()) +
                "\",\"price\":" + trip.getTotalPrice() + ",\"booked\":" + booked + ",\"bookingCount\":" + count + "}";
    }
    private static String error(String message) { return "{\"error\":\"" + esc(message) + "\"}"; }
    private static String esc(String s) { return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", " "); }
    private static void reply(HttpExchange ex, int status, String type, String value) throws IOException {
        byte[] data = value.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", type + "; charset=utf-8");
        ex.sendResponseHeaders(status, data.length);
        try (var out = ex.getResponseBody()) { out.write(data); }
    }
}
