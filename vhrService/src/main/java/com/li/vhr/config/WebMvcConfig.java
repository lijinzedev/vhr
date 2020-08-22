package com.li.vhr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LiJinZe
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

//    /**
//     * 配置全局时间格式化
//     * @return
//     */
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//        return builder -> {
//            builder.simpleDateFormat(dateTimeFormat);
//            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
//            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
//        };
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToData());
    }

    /**
     * 这里需要注意，因为默认的convert是String，如果方法返回的是String，那么将会被提前返回，所以需要做一下兼容。
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringOrMappingJackson2HttpMessageConverter messageConverter = new StringOrMappingJackson2HttpMessageConverter();
        //       ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString("");
//            }
//        });
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Collection.class, new CollectionIgnoreNullSerializer());
//        objectMapper.registerModule(module);
        //   messageConverter.setObjectMapper(objectMapper);
        converters.add(0, messageConverter);
    }

    public static class StringToData implements Converter<String, Date> {
        @Override
        public Date convert(String source) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            try {
                Date date = simpleDateFormat.parse(source);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    /**
     * 兼容String和对象类型
     */
    public static class StringOrMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

        private static final StringHttpMessageConverter shmc = new StringHttpMessageConverter();


        @Override
        public boolean canWrite(Class<?> clazz, MediaType mediaType) {
            boolean canWrite = super.canWrite(clazz, mediaType);
            if (!canWrite) {
                canWrite = clazz.isAssignableFrom(String.class);
            }
            return canWrite;
        }

        @Override
        protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            if (object != null && object instanceof String) {
                outputMessage.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                shmc.write((String) object, MediaType.TEXT_PLAIN, outputMessage);
                return;
            }
            outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            super.writeInternal(object, object != null ? object.getClass() : null, outputMessage);
        }
    }
}

