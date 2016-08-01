package springone;

import com.github.pgasync.ConnectionPoolBuilder;
import com.github.pgasync.impl.PgConnectionPool;
import com.github.pgasync.impl.PgProtocolStream;
import com.github.pgasync.impl.netty.RatpackPgProtocolStream;
import io.netty.channel.EventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ratpack.exec.Execution;
import ratpack.service.Service;
import ratpack.service.StartEvent;

import java.net.InetSocketAddress;

@Component
public class RatpackPgConnectionPool extends PgConnectionPool implements Service {

  private final boolean useSsl;
  private final boolean usePipeline;

  private EventLoopGroup eventLoopGroup;

  @Autowired
  public RatpackPgConnectionPool(ConnectionPoolBuilder.PoolProperties poolProperties) {
    super(poolProperties);
    this.useSsl = poolProperties.getUseSsl();
    this.usePipeline = poolProperties.getUsePipelining();
  }

  @Override
  public void onStart(StartEvent e) {
    this.eventLoopGroup = Execution.current().getEventLoop();
  }

  @Override
  protected PgProtocolStream openStream(InetSocketAddress address) {
    return new RatpackPgProtocolStream(eventLoopGroup, address, useSsl, usePipeline);
  }

}
