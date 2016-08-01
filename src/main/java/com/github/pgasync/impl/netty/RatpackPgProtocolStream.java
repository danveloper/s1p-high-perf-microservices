package com.github.pgasync.impl.netty;

import com.github.pgasync.SqlException;
import com.github.pgasync.impl.message.ErrorResponse;
import com.github.pgasync.impl.message.Message;
import com.github.pgasync.impl.message.StartupMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import ratpack.util.internal.ChannelImplDetector;
import rx.Observable;
import rx.Subscriber;

import java.net.SocketAddress;

public class RatpackPgProtocolStream extends NettyPgProtocolStream {

  public RatpackPgProtocolStream(EventLoopGroup group, SocketAddress address, boolean useSsl, boolean pipeline) {
    super(group, address, useSsl, pipeline);
  }

  @Override
  public Observable<Message> connect(StartupMessage startup) {
    return Observable.create(subscriber -> {

      pushSubscriber(subscriber);
      new Bootstrap()
          .group(group)
          .channel(ChannelImplDetector.getSocketChannelImpl())
          .handler(newProtocolInitializer(newStartupHandler(startup)))
          .connect(address)
          .addListener(onError);

    }).flatMap(this::throwErrorResponses);
  }

  private void pushSubscriber(Subscriber<? super Message> subscriber) {
    if(!subscribers.offer(subscriber)) {
      throw new IllegalStateException("Pipelining not enabled " + subscribers.peek());
    }
  }

  private Observable<Message> throwErrorResponses(Object message) {
    return message instanceof ErrorResponse
        ? Observable.error(toSqlException((ErrorResponse) message))
        : Observable.just((Message) message);
  }

  private static SqlException toSqlException(ErrorResponse error) {
    return new SqlException(error.getLevel().name(), error.getCode(), error.getMessage());
  }
}
