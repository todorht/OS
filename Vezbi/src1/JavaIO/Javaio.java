package JavaIO;

import java.io.*;

public class Javaio {
    public static void main(String[] args) throws IOException {
        File in = new File("in");
        File out = new File("out");
        manage(in.getAbsolutePath(),out.getAbsolutePath());
    }

    public static void manage(String in, String out) throws IOException {
        File filein = new File(in);
        File fileout = new File(out);

        if(fileout.exists()){
            File[] filesout = fileout.listFiles();
            if(filesout.length != 0) {
                for (File f : filesout) {
                    f.delete();
                }
            }
        }
        File resources = new File("resources");
        File[] filesin = filein.listFiles();
        if(filesin.length != 0){
            for(File f:filesin) {
                if (f.getAbsolutePath().endsWith(".dat")) {
                    if (f.canWrite() && !f.isHidden()) {
                        File moveFile = new File(fileout, f.getName());
                        f.renameTo(moveFile);
                        System.out.println("premestuvam");
                    }else if(!f.canWrite()){
                        File zapishi = new File(resources.getAbsolutePath(),"writable-content.txt");
                        BufferedWriter writer = null;
                        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new FileReader(f));
                            writer = new BufferedWriter(new FileWriter(zapishi, true));
                            String line = null;
                            while ((line = reader.readLine())!= null){
                                writer.write(line);
                                writer.newLine();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(writer != null){
                                writer.flush();
                                writer.close();
                                reader.close();
                            }
                        }
                    } else if(f.isHidden()){
                        f.delete();
                        System.out.println("Zbunet sum");
                    }

                }
            }
        }

        if(!filein.exists()){
            System.out.println("Ne postoi");
        }





    }
}
