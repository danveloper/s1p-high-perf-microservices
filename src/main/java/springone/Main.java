package springone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pgasync.ConnectionPoolBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ratpack.registry.Registry;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import ratpack.spring.Spring;
import springone.config.DbConfig;

import static ratpack.jackson.Jackson.json;

@SpringBootApplication
public class Main {

  @Bean
  public RatpackPgConnectionPool ratpackPgConnectionPool(DbConfig dbConfig) {
    System.out.println("Captured keys: " + dbConfig.keySet());
    ConnectionPoolBuilder.PoolProperties props = new ObjectMapper().convertValue(dbConfig, ConnectionPoolBuilder.PoolProperties.class);
    return new RatpackPgConnectionPool(props);
  }

  public static void main(String[] args) throws Exception {
    RxRatpack.initialize();

    Registry registry = Spring.spring(Main.class);
    RatpackServer.start(spec -> spec
        .registry(registry)
        .handlers(chain -> chain
            .get(ctx -> {
              ProductService productService = ctx.get(ProductService.class);
              RxRatpack.promise(productService.getProducts()).then(products -> ctx.render(json(products)));
            })
        )
    );
  }
}
