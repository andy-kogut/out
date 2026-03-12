import lombok.Value;
import java.util.Map;

public class TopicConfig {

    @Value
    static class Route {
        String origin;
        String dest;
    }

    private static final Map<String, Route> ROUTES = Map.of(
            "t1", new Route("service1", "service2"),
            "t2", new Route("service3", "service4")
    );

    public static Route get(String topic) {
        return ROUTES.get(topic);
    }
}
