package net.j7.system.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class MyApplicationConfig implements ServerApplicationConfig {

	  @Override
	  public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
	    return new HashSet<ServerEndpointConfig>() {
			private static final long serialVersionUID = 1L;

		{
	        add(ServerEndpointConfig.Builder
	            .create(EchoServer.class, "/echo2")
	            .build());
	      }
	    };
	  }

	  @Override
	  public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		  return scanned;
//	       // Deploy all WebSocket endpoints defined by annotations in the examples
//		  	        // web application. Filter out all others to avoid issues when running
//		  	        // tests on Gump
//		  	        Set<Class<?>> results = new HashSet<>();
//		  	        for (Class<?> clazz : scanned) {
//		  	            if (clazz.getPackage().getName().startsWith("websocket.")) {
//		  	                results.add(clazz);
//		  	            }
//		  	        }
//		  	        return results;
	  }
	}
//public class MyApplicationConfig {}