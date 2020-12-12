package JavaIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Javaio2 {


    public static void metod(String  in) throws IOException {
        File file = new File(in);

        File[] files = file.listFiles();
        if (files.length != 0) {
            for (File f : files) {
                if (f.isFile()) {
                    if (f.getAbsolutePath().endsWith(".txt") || f.getAbsolutePath().endsWith(".dat")) {
                        long size = f.length();
                        long sizeKb = size / 1024;
                        if (sizeKb < 512) {
                            BufferedWriter writer = null;
                            try {
                                File zapisi = new File("E:\\FINKI\\Leten\\OS\\Vezbi\\resources\\files.csv");
                                writer = new BufferedWriter(new FileWriter(zapisi, true));
                                Date lastModified = new Date(f.lastModified());
                                writer.write(f.getAbsolutePath() + ", " + size + ", " + lastModified);
                                writer.newLine();
                            } finally {
                                writer.flush();
                                writer.close();
                            }


                        }
                    }
                }
                if (f.isDirectory()) {
                    metod(f.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        metod("E:\\FINKI\\Leten\\OS\\Vezbi\\in");
    }
}
