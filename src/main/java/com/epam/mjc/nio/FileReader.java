
package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = accessFile.getChannel()) {

            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();
            StringBuilder fileContent = new StringBuilder();
            while (buffer.hasRemaining()){
                fileContent.append((char) buffer.get());
            }
            String[] lines = fileContent.toString().split("\n");
            for (String line : lines){
                String[] substrings = line.split(": ");
                String key = substrings[0];
                String value = substrings[1].trim();

                switch (key){
                    case "Name":
                        profile.setName(value);
                        break;
                    case "Age":
                        try {
                            System.out.println("Parsing age: " + value); // Print the value
                            profile.setAge(Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "Email":
                        profile.setEmail(value);
                        break;
                    case "Phone":
                        try {
                            System.out.println("Parsing phone: " + value); // Print the value
                            profile.setPhone(Long.parseLong(value));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;
    }
}