package JavaIO;

import javax.xml.crypto.Data;
import java.io.*;

public class Javaio4 {

    public static void findRW(String in, String out) throws IOException {
        File file = new File(in);
        File[] files = file.listFiles();
        for(File f: files) {
            if (f.isDirectory()) {
                findRW(file.getAbsolutePath(), out);
            }
            if (f.isFile()) {
                if(f.getAbsolutePath().endsWith(".xlsx") || f.getAbsolutePath().endsWith(".docx")){
                    if(f.canWrite() && f.canRead()){
                        File outF = new File(out + f.getName());
                        DataInputStream writer = null;
                        DataOutputStream reader = null;
                        if(f.getAbsolutePath().endsWith(".docx")){
                            try {
                                reader = new DataOutputStream(new FileOutputStream(f.getAbsoluteFile()));
                                writer = new DataInputStream(new FileInputStream(outF));
                                String line = null;
                                while ((line = writer.readUTF()).equals(null)){
                                   reader.writeUTF(line);

                                }
                            } finally {
                                assert reader != null;
                                reader.close();
                                assert writer != null;
                                writer.close();
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        findRW("E:\\FINKI\\Leten\\OS\\Vezbi\\in", "E:\\FINKI\\Leten\\OS\\Vezbi\\outD");
    }
}
