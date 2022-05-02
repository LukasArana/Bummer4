package com.bummer4;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.spi.CollatorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class footbalClient extends Application {
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(footbalClient.class.getResource("footbal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Rest client for competitions");
        stage.setScene(scene);
        stage.show();
    }
}
