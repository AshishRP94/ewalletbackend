package ewalletbackend.kefka;

import java.util.*;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;


@Configuration
@EnableKafka
public class TKafkaProducerConfig
{
	
	
	   @Bean
	    Map<String, Object> properties(){
	        Map<String, Object> properties = new HashMap<>();
	        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        return properties;
	    }
	
	
	@Bean
	DefaultKafkaProducerFactory<String,String> producerfactory()
	{
		return new DefaultKafkaProducerFactory<>(properties());
	}
	
	
	@Bean
	KafkaTemplate<String,String> kefkatemplate()
	{
		return new  KafkaTemplate<>(producerfactory());
	}
	

}
