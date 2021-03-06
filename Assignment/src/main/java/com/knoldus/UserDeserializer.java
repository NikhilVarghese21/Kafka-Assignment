package com.knoldus;

import java.nio.ByteBuffer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UserDeserializer implements Deserializer<User> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Nothing to configure
    }

    @Override
    public User deserialize(String topic, byte[] data) {

        try {
            if (data == null) {
                System.out.println("Null received at deserialize");
                return null;
            }

            ByteBuffer buf = ByteBuffer.wrap(data);
            int userid = buf.getInt();

            int sizeOfName = buf.getInt();
            byte[] nameBytes = new byte[sizeOfName];
            buf.get(nameBytes);
            String deserializedName = new String(nameBytes, encoding);

            int age = buf.getInt();

            int sizeOfCourse = buf.getInt();
            byte[] courseBytes = new byte[sizeOfCourse];
            buf.get(courseBytes);
            String deserializedCourse = new String(courseBytes, encoding);


            return new User(userid, deserializedName, age, deserializedCourse);

        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to User");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}
