package com.example.projekti_graafika;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Kalendri_graafika extends Application {
    String Lause;
    int ID;
    List<Kodutööd> kalender = loetööd("kalender.txt");
    ListView<Kodutööd> list = new ListView<Kodutööd>();


    public Kalendri_graafika() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage peaLava) throws Exception, IOException{
        Label juhised= new Label(" ---------------------------------------------------------------- \n Tere tulemast kalendrisse, siin on sulle kasutusjuhised: \n \n *Kodutöö lisamisel sisesta tekst formaadis: sinu_kodutöö;dd.mm.yyyy \n *Kodutöö vaatamiseks ära sisesta teksti, vaid vajuta nuppu! \n *Kustutamiseks sisesta kodutöö ees olev number, mida soovid kustutada \n *Enne lõpetamist ära unusta vajutada nuppu salvesta! \n  ---------------------------------------------------------------- ");
        TextField tekstiRida = new TextField();
        // nupud:
        Button nuppLisa = new Button("Lisa");
        Button nuppVaata = new Button("Vaata");
        Button nuppKustuta= new Button("Kustuta");
        Button nuppSalvesta= new Button("Salvesta");


        nuppLisa.setOnMouseClicked(event -> {
            Lause = tekstiRida.getText();
            try {
                lisamine(Lause, kalender);
                vaatamine(kalender);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tekstiRida.setText("");
        });

        nuppSalvesta.setOnMouseClicked(event -> {
            try {
                salvesta(kalender);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tekstiRida.setText("");
        });


        nuppKustuta.setOnMouseClicked(event -> {
            ID = Integer.parseInt(tekstiRida.getText());
            try {
                kustutamine(kalender,ID);
                vaatamine(kalender);

            } catch (IOException e) {
                e.printStackTrace();
            }
            tekstiRida.setText("");
        });

        nuppVaata.setOnMouseClicked(event ->{
            vaatamine(kalender);
        });


        HBox nupudall = new HBox();
        nupudall.setSpacing(2);
        nupudall.setPadding(new Insets(3, 3, 3, 3));
        nuppVaata.setTextFill(Color.BLACK);
        nuppKustuta.setTextFill(Color.BLACK);
        nuppSalvesta.setTextFill(Color.BLACK);
        nupudall.getChildren().addAll(nuppLisa, nuppVaata, nuppKustuta, nuppSalvesta);


        tekstiRida.setMinWidth(450);

        VBox alumine = new VBox();
        alumine.setSpacing(2);
        alumine.setPadding(new Insets(3, 3, 3, 3));
        alumine.getChildren().addAll(nupudall, list);



        peaLava.setOnHiding(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                Stage kusimus = new Stage();
                Label label = new Label("Kas sa soovid kindlasti lõoetada?");
                Button okButton = new Button("Jah");
                Button cancelButton = new Button("Ei");


                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        try {
                            salvesta(kalender);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        kusimus.hide();
                    }
                });


                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        peaLava.show();
                        kusimus.hide();
                    }
                });


                FlowPane pane = new FlowPane(10, 10);
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().addAll(okButton, cancelButton);


                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(label, pane);


                Scene stseen2 = new Scene(vBox);
                kusimus.setScene(stseen2);
                kusimus.show();
            }
        });

        BorderPane juurpaigutus = new BorderPane();
        juurpaigutus.setTop(juhised);
        juurpaigutus.setCenter(tekstiRida);
        juurpaigutus.setBottom(alumine);

        Scene scene = new Scene(juurpaigutus, 500,400);
        peaLava.setScene(scene);
        peaLava.setResizable(false);
        peaLava.setTitle("Kodutööd");
        peaLava.show();
    }
    private static void lisamine(String lause, List<Kodutööd> kalender) throws IOException {
        int viimaneindeks = kalender.get(kalender.size() - 1).getId() + 1;
        kalender.add(new Kodutööd(viimaneindeks, lause));

    }
    private static List<Kodutööd> loetööd(String failinimi) throws IOException {
        List<Kodutööd> kalender = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(failinimi), "UTF-8"))) {
            while (true) {
                String rida = br.readLine();
                if (rida == null)
                    return kalender;
                String[] osad = rida.split("&");
                kalender.add(new Kodutööd(Integer.parseInt(osad[0]), osad[1]));
            }
        }
    }

    private void vaatamine(List<Kodutööd> kalender) {
        ObservableList<Kodutööd> items = FXCollections.observableArrayList (kalender);
        list.setItems(items);
        list.setPrefWidth(100);
        list.setPrefHeight(200);
    }

    private static void salvesta(List<Kodutööd> kalender) throws IOException {
        try (BufferedWriter w = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("kalender.txt"),
                        "UTF-8"))) {
            for (Kodutööd kodutöö : kalender) {
                w.write(kodutöö.getId() + "&" + kodutöö.getTöö() + ";" + kodutöö.getKuupäev());
                w.newLine();
            }
        }
        System.out.println("Salvestatud ja lõpetatud!");

    }

    private static void kustutamine(List<Kodutööd> kalender, int ID) throws IOException {
        for (int i = 0; i < kalender.size(); i++) {
            if (kalender.get(i).getId() == ID) {
                kalender.remove(i);
                break;
            }
        }
        System.out.println("kustutatud");

    }
}
