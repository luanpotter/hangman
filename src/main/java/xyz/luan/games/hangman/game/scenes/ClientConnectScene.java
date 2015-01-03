package xyz.luan.games.hangman.game.scenes;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import xyz.luan.games.hangman.client.Client;
import xyz.luan.games.hangman.game.MainGameStatus;
import xyz.luan.games.hangman.game.I18n;
import xyz.luan.games.hangman.game.forms.DialogHelper;
import xyz.luan.games.hangman.game.forms.FormUtils;

public class ClientConnectScene extends DefaultScene {

    private GridPane grid;
    private TextField serverIp;

    @Override
    protected Pane generatePane() {
        grid = FormUtils.defaultGrid();

        addTitle();
        addClientConfigButton();
        addServerIpBox();
        addBottomButtons();

        return grid;
    }

    private void addBottomButtons() {
        Button connectButton = new Button(I18n.t("client.connect"));
        connectButton.setOnAction(buttonHandler());
        grid.add(connectButton, 0, 3);
        grid.add(new StateChangeButton("common.cancel", MainGameStatus.MAIN_MENU), 1, 3);
    }

    private EventHandler<ActionEvent> buttonHandler() {
        return e -> {
            String ip = serverIp.getText();
            try {
                Client handler = new Client(ip);
                mainRef.connect(handler);
                handler.start();
            } catch (IOException ex) {
                DialogHelper.show("Error connecting!", "Could not connect to the requested server. Error: " + ex.getMessage());
            }
        };
    }

    private void addClientConfigButton() {
        grid.add(new StateChangeButton("client.config", MainGameStatus.CLIENT_CONFIG), 0, 1, 2, 1);
    }

    private void addServerIpBox() {
        serverIp = new TextField();
        grid.add(new Label(I18n.t("client.serverIp")), 0, 2);
        grid.add(serverIp, 1, 2);
    }

    private void addTitle() {
        Text sceneTitle = new Text(I18n.t("client.connect"));
        sceneTitle.getStyleClass().add("title");
        grid.add(sceneTitle, 0, 0, 2, 1);
    }

}
