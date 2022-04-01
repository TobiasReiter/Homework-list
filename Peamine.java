import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Peamine {

    public static void main(String[] args) throws IOException {

        System.out.println("Tere tulemast kalendrisse, siit näed enda tegemist vajavaid kodutöid");
        System.out.println("Sisestusjuhised:");
        System.out.println("Kodutööd sisesta formaadis: TNT_Kontrolltöö");
        System.out.println("Tähtajad sisesta formaadis: pp.kk.aa");

        List<Kodutööd> kalender= järjenditäitmine();

        FileWriter fw = new FileWriter("kalender.txt");

        for (int i = 0; i < kalender.size(); i++) {
            Kodutööd kodutöö2= kalender.get(i);
            String kodutöö_id= String.valueOf(kodutöö2.getId());
            String kodutöö_töö = kodutöö2.getTöö();
            String kodutöö_kuupäev = kodutöö2.getKuupäev();
            String rida= kodutöö_id+"&"+kodutöö_töö+ "&" + kodutöö_kuupäev;
            fw.write(rida+ "\n");
        }
        fw.close();

        System.out.println("Sinu kalendris on järgmised tegevused:");
        for (int i = 0; i < kalender.size(); i++)
            System.out.print(kalender.get(i)+"\n");
        System.out.println();
    }

    public static  ArrayList<Kodutööd> järjenditäitmine() {
        int id = 0;
        ArrayList<Kodutööd> kalender = new ArrayList<>();

        try {
            java.io.File fail = new java.io.File("kalender.txt");
            {
                try {
                    java.util.Scanner scanner = new java.util.Scanner(fail, "UTF-8");
                    while (scanner.hasNextLine()) {
                        String rida = scanner.nextLine();
                        String[] tükid = rida.split("&");
                        kalender.add(new Kodutööd(Integer.parseInt(tükid[0]), tükid[1], tükid[2]));
                        id= Integer.parseInt(tükid[0]);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        tsükkel:while (true){
            System.out.println("Sisesta kodutöö");
            String kodutöö = scan.next();
            System.out.println("Sisesta tähtaeg");
            String tähtaeg = scan.next();
            kalender.add(new Kodutööd(id + 1, kodutöö, tähtaeg));

            System.out.println("Kas soovid lõpetada? Siis siseta Jah");
            if (scan.next().equals("Jah")) {
                break tsükkel;
            }


        }
        return kalender;
    }
}
