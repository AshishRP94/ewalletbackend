package ewalletbackend.kefka;

import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;



@Configuration
public class TKafkaConsumerConfig
{	
	
    @Bean
    Map<String , Object> ConsumerProperties(){
        Map<String,Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    

	
	@Bean
	DefaultKafkaConsumerFactory<String,String> consumerfactory()
	{
		return new DefaultKafkaConsumerFactory<>(ConsumerProperties());
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory  <String,String> cklcf()
	{
		ConcurrentKafkaListenerContainerFactory<String,String>obj=new ConcurrentKafkaListenerContainerFactory<String,String>();
		obj.setConsumerFactory(consumerfactory());
		return obj;
	}

}
