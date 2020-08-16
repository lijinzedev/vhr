package com.li.vhr.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Collection;

public class CollectionIgnoreNullSerializer extends StdSerializer<Collection> {
    public CollectionIgnoreNullSerializer() {
        this(null);
    }

    public CollectionIgnoreNullSerializer(Class<Collection> t) {
        super(t);
    }

    @Override
    public void serialize(Collection list, JsonGenerator jsonGenerator, SerializerProvider unused) throws IOException {
        jsonGenerator.writeStartArray();

        list.forEach(e -> {
            try {
                if (e != null) {
                    jsonGenerator.writeObject(e);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        jsonGenerator.writeEndArray();
    }
}