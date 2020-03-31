package Auditoriska1;

import java.io.*;

public class Tester extends Auditoriska1 {
    private static String FilePath = "Folder";

    public static void main(String[] args) throws IOException {

          File newFile = new File(FilePath, "todor.txt");
//        if(!newFile.exists()) System.out.println(newFile.createNewFile());
//
//        File dir = new File(FilePath, "Folder1");
//        if(!dir.exists()) System.out.println(dir.mkdir());
//
          File f1 = new File("Folder\\Folder1", "todor1.txt");
//        if(!f1.exists()) System.out.println(newFile.renameTo(f1));
//        String [] files = dir.list();
//        if (!(files ==null)) {
//            for (String s : files)
//                System.out.println(s);
//        }
//
//       writeInFile(newFile, "Todor krastavica", true);

 //       fromTo(newFile, f1);
        fileOutputLineNumbers(newFile);

    }
}
