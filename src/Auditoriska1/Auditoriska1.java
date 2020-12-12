package Auditoriska1;

import java.io.*;

public class Auditoriska1 {

    public static String readFromFileText(File f) throws IOException { //cita od file
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
        String line = null;

        StringBuilder sb = new StringBuilder();
        sb.end

        while ((line = br.readLine())!= null){
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    public static void stdinRead() throws IOException{ //cita od tastatura
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String s;

        System.out.println("Vnesi neshto: ");
        while ((s = stdin.readLine())!=null && s.length()!=0 ){
            System.out.println(s);
        }
    }

    public static void writeInFile(File f, String s, boolean append) throws IOException { //dadem String "s" go zapisuva vo file
        BufferedWriter br = new BufferedWriter(new FileOut(f, append));
        br.write
        br.write(s + "\n");
        br.close();
    }

    public static void fromTo(File from, File to) throws IOException { // kopiranje od eden file vo drug
        BufferedWriter bw = new BufferedWriter(new FileWriter(to));
        BufferedReader br = new BufferedReader(new FileReader(from));

        String line = null;

        while ((line = br.readLine())!=null){
            bw.write(line + "\n");
        }

        br.close();
        bw.close();


    }

    public static void fileOutputLineNumbers(File f) throws IOException{ //od postoecki file zapishuva vo nov file FileOutputLineNumbers.out
        String outFile = "Folder\\FileOutputLineNumbers.out";
        BufferedReader in = null;
        PrintWriter out = null;

        try{
            in = new BufferedReader(new FileReader(f));
            out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

            int lineCount = 1;
            String s;
            while ((s=in.readLine())!= null){
                out.println(lineCount++ + ": " + s);
            }
        }finally {
            if(out!=null) out.close();
            if(in!=null) in.close();
        }
    }
}
