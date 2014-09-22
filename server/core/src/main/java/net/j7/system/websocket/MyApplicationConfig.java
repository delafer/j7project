package net.j7.system.websocket;

//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.websocket.Endpoint;
//import javax.websocket.server.ServerApplicationConfig;
//import javax.websocket.server.ServerEndpointConfig;
//
//public class MyApplicationConfig implements ServerApplicationConfig {
//
//	  @Override
//	  public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
//	    return new HashSet<ServerEndpointConfig>() {
//			private static final long serialVersionUID = 1L;
//
//		{
//	        add(ServerEndpointConfig.Builder
//	            .create(EchoServer.class, "/echo")
//	            .build());
//	      }
//	    };
//	  }
//
//	  @Override
//	  public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
//	    return Collections.emptySet();
//	  }
//	}
public class MyApplicationConfig {}