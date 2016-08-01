package springone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import springone.model.Product;

import java.math.BigDecimal;

@Service
public class ProductService {

  private final RatpackPgConnectionPool pool;

  @Autowired
  public ProductService(RatpackPgConnectionPool pool) {
    this.pool = pool;
  }

  public Observable<Product> getProducts() {
    return pool.queryRows("select * from product").map(row -> {
      Long id = row.getLong("product_id");
      String name = row.getString("name");
      String description = row.getString("description");
      Boolean available = row.getBoolean("is_available");
      BigDecimal price = row.getBigDecimal("price");
      return new Product(id, name, description, available, price);
    }).doOnError(Throwable::printStackTrace);
  }
}
