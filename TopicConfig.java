import java.util.Map;

public class TopicConfig {

    record Route(String origin, String dest) {}

    private static final Map<String, Route> ROUTES = Map.of(
            "t1", new Route("service1", "service2"),
            "t2", new Route("service3", "service4")
    );

    public static String getOrigin(String topic) {
        return ROUTES.get(topic).origin();
    }

    public static String getDest(String topic) {
        return ROUTES.get(topic).dest();
    }
}
