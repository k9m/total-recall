package org.ing.hackathon.totalrecall.api.config.elastic;

import lombok.Setter;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;


@Configuration
@ConfigurationProperties(prefix = "elastic")
@Setter
public class ElasticConfiguration {

  private String clusterName;
  private String host;
  private Integer port;


  @Bean
  TransportClient createClient(){
    Settings.Builder builder = Settings.builder();
    Settings settings = builder
            .put("cluster.name", clusterName)
            .put("client.transport.sniff", false)
            .build();

    TransportAddress[] addresses = { new TransportAddress(new InetSocketAddress(host.trim(), port)) };
    return new PreBuiltTransportClient(settings).addTransportAddresses(addresses);
  }

}