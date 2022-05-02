package com.bummer4;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;

import javax.imageio.ImageIO;

public class FootballController {

    private List<Competition> attributes;
    private int competitionIdx;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField areaCodeBut;

    @FXML
    private TextField areaIdBut;

    @FXML
    private TextField areaNameBut;

    @FXML
    private TextField compIdBut;

    @FXML
    private TextField compNameBut;

    @FXML
    private Button nextBut;

    @FXML
    private Button previousBut;

    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imgView;

    @FXML
    void nextClicked(ActionEvent event) throws MalformedURLException {
        this.competitionIdx++;
        updateCompetitionDisplay();
    }

    @FXML
    void preClicked(ActionEvent event) throws MalformedURLException {
        this.competitionIdx--;
        updateCompetitionDisplay();
    }

    @FXML
    void initialize() throws MalformedURLException {
        String endpoint = "competitions";
        APIManager manager = new APIManager();
        String body = manager.request(endpoint);

        this.attributes = processJsonCompetitions(body);
        this.competitionIdx = 0;
        updateCompetitionDisplay();
    }

    public static List<Competition> processJsonCompetitions(String body) {

        String endpoint = "competitions";
        Gson gson = new Gson();
        JsonObject jsonObject;
        jsonObject = gson.fromJson(body, JsonObject.class);
        Type competitionListType = new TypeToken<ArrayList<Competition>>() {
        }.getType();
        List<Competition> competitions = gson.fromJson((jsonObject.get(endpoint)), competitionListType);
        return competitions;
    }

    public void updateCompetitionDisplay() throws MalformedURLException {
        //It can only be used for competitions.
        if (competitionIdx < 0) {
            competitionIdx = attributes.size() - 1;
        } else if (competitionIdx > attributes.size() - 1) {
            competitionIdx = 0;
        }
        Competition actualComp = attributes.get(this.competitionIdx);
        this.areaIdBut.setText(Integer.toString(actualComp.getArea().getId()));
        this.compIdBut.setText(Integer.toString(actualComp.getId()));
        this.areaCodeBut.setText(actualComp.getArea().getCountryCode());
        this.areaNameBut.setText(actualComp.getArea().getName());
        this.compNameBut.setText(actualComp.getName());
        this.imgView.setImage(null);
        URL image_url;
        try {
            image_url = new URL(actualComp.getEmblemUrl());
        } catch (MalformedURLException e) {
            image_url = null;
        }
        if (image_url != null) {
            try {
                BufferedImage imagelag = ImageIO.read(image_url);
                Image image = SwingFXUtils.toFXImage(imagelag, null);
                this.imgView.setImage(image);
            } catch (RuntimeException | IOException e) {
                BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
                try (InputStream file = image_url.openStream();) {
                    TranscoderInput transIn = new TranscoderInput(file);
                    try {
                        transcoder.transcode(transIn, null);
                        Image img = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);
                        imgView.setImage(img);
                    } catch (TranscoderException ex) {
                        ;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}