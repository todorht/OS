package JavaIO;

import java.io.*;
import java.text.DecimalFormat;

public class Javaio3 {



    public static void rekurzija(String in) throws IOException {
        File file = new File(in);
        File[] files = file.listFiles();
        if(files.length !=0){
            for (File f:files){
                if(f.isFile()){
                    byte[] b = f.getName().getBytes("UTF-8");
                    byte[] b1 = f.getParent().getBytes("UTF-8");
                    String permisii = "";
                    if(f.canRead()){
                        permisii+="r";
                    }else{
                        permisii+="-";
                    }
                    if(f.canWrite()){
                        permisii+="w";
                    }else {
                        permisii+="-";
                    }
                    if(f.canExecute()){
                        permisii+="x";
                    }else {
                        permisii+="-";
                    }
                    File zapishi = new File("E:\\FINKI\\Leten\\OS\\Vezbi\\outD\\izles.bin");
                    DataOutputStream dos = null;
                    try {
                        dos = new DataOutputStream( new FileOutputStream(zapishi, true));
                        DecimalFormat format = new DecimalFormat("00");
                        DecimalFormat format1 = new DecimalFormat("000");
                        String out = format.format(b.length) + f.getName() + format1.format(b1.length) + f.getParent() + permisii + "\n";
                        dos.writeBytes(out);
//                        dos.writeUTF(out);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        dos.flush();
                        dos.close();
                    }

                }
                if(f.isDirectory()){
                    rekurzija(f.getAbsolutePath());
                }
            }
        }
    }

    public static void pomini(String inDir, String outDir) throws IOException {
        File in = new File(inDir);
        File out = new File(outDir);
        if(!in.exists()){
            System.exit(0);
        }
        if(!out.exists()){
            out.mkdirs();
        }
        File[] files = out.listFiles();
        if(files.length!=0) {
            for (File f : files) {
                f.delete();
            }
        }
        rekurzija(in.getAbsolutePath());

    }

    public static void main(String[] args) throws IOException {
        pomini("E:\\FINKI\\Leten\\OS\\Vezbi\\in","E:\\FINKI\\Leten\\OS\\Vezbi\\outD");
    }
}
