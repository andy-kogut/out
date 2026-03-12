import java.util.Map;

public class TopicConfig {

    // 1️⃣ Define the record (immutable data holder)
    public record Route(String origin, String dest) {}

    // 2️⃣ Hardcoded configuration map
    private static final Map<String, Route> ROUTES = Map.of(
            "t1", new Route("service1", "service2"),
            "t2", new Route("service3", "service4")
    );

    // 3️⃣ Access method
    public static Route getRoute(String topic) {
        return ROUTES.get(topic);
    }

    public static String getOrigin(String topic) {
        return ROUTES.get(topic).origin();
    }

    public static String getDest(String topic) {
        return ROUTES.get(topic).dest();
    }
}
