package DictionaryGUI;
/**
 * class Controller handle event
 * @author Do Thi Hong Ngat
 * @version 1.0
 * @since 2018-10-10
 */
import  DictionaryCmL.*;
import com.voicerss.tts.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class Controller implements Initializable {
    @FXML TextField  targetSearch, targetAdd, targetEdit, targetRemove, soundAdd;
    @FXML TextArea explainAdd, explainRemove, explainEdit,explainSearch;
    @FXML
    ListView<String> listViewSearch = new ListView<String>(), listViewRecent = new ListView<String>(), listViewFavorite = new ListView<String>();
    @FXML ListView<String> listViewAdd = new ListView<String>(), listViewRemove = new ListView<String>(), listViewEdit = new ListView<String>();
    private static DictionaryCommandline dictionary = new DictionaryCommandline();
    @FXML
    Label  targetShow, warningAdd, warningRemove, warningEdit;
    @FXML
    Button buttonSound, buttonAdd, buttonRemove, buttonEdit;
    @FXML
    ToggleButton buttonFavor;
    @FXML TabPane tabpane;

    /**
     * action for buttonSound
     * @param event
     * @throws Exception
     */
    public void clickForSound(MouseEvent event) throws Exception{
        String word = targetShow.getText();
        if (word.trim().equals("English - Vietnamese")) return;
        VoiceProvider tts = new VoiceProvider("1d7d26040c284a6ba91ecb53977ee3f0");
        VoiceParameters params = new VoiceParameters(word, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream("output_mp3/voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        String gongFile = "output_mp3/voice.mp3";
        InputStream in = new FileInputStream(gongFile);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }

    /**
     * Lookup when searching
     */
    public void lookupSearch()
    {
        String target = targetSearch.getText();
        Word word = dictionary.dictionaryLookup(target);
        if(dictionary.recentWord.contains(target)) dictionary.recentWord.remove(target);
        dictionary.recentWord.addFirst(target);
        targetShow.setText(target);
        explainSearch.setText(word.toString());
        buttonFavor.setSelected(word.isFavorite());
    }

    /**
     * Lookup when removing words
     */
    public void lookupRemove()
    {
        String target = targetRemove.getText();
        Word word = dictionary.dictionaryLookup(target);
        explainRemove.setText(word.getWord_explain());
    }

    /**
     * Lookup when editing words
     */
    public void lookupEdit()
    {
        String target = targetEdit.getText();
        Word word = dictionary.dictionaryLookup(target);
        explainEdit.setText(word.getWord_explain());
    }

    /**
     * suggestion words when searching
     */
    public void searchHelper()
    {
        String target = targetSearch.getText();
        listViewSearch.getItems().clear();
        if (target.equals("")) return;
        ArrayList<String> list = dictionary.dictionarySearcher(target);
        ObservableList<String> data = FXCollections.observableArrayList(list);

        listViewSearch.getItems().addAll(data);
    }

    /**
     * suggestion words when adding word
     */
    public void addHelper()
    {
        String target = targetAdd.getText();
        target.trim();
        listViewAdd.getItems().clear();
        if (target.equals(""))
        {
            warningAdd.setText("");
            return;
        }
        ArrayList<String> list = dictionary.dictionarySearcher(target);
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewAdd.getItems().addAll(data);
        if (!list.isEmpty() && list.get(0).trim().equals(target)) warningAdd.setText("This word is already existed");
        else warningAdd.setText("This word can be added");
    }

    /**
     * suggestion words when removing word
     */
    public void removeHelper()
    {
        String target = targetRemove.getText();
        target.trim();
        listViewRemove.getItems().clear();
        if (target.equals(""))
        {
            warningRemove.setText("");
            return;
        }
        ArrayList<String> list = dictionary.dictionarySearcher(target);
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewRemove.getItems().addAll(data);
        if (list.isEmpty() || !list.get(0).trim().equals(target)) warningRemove.setText("This word is not already existed");
        else warningRemove.setText("This word can be removed");
    }
    /**
     * suggestion words when editing word
     */
    public void editHelper()
    {
        String target = targetEdit.getText();
        target.trim();
        listViewEdit.getItems().clear();
        if (target.equals(""))
        {
            warningEdit.setText("");
            return;
        }
        ArrayList<String> list = dictionary.dictionarySearcher(target);
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewEdit.getItems().addAll(data);
        if (list.isEmpty() || !list.get(0).trim().equals(target)) warningEdit.setText("This word is not already existed");
        else warningEdit.setText("This word can be edited");
    }

    /**
     * Add words
     */
    public void AddWord()
    {
        String target = targetAdd.getText();
        String sound = soundAdd.getText();
        String explain = explainAdd.getText();
        warningAdd.setText(dictionary.addWord(target,sound,explain));

    }
    /**
     * Remove words
     */
    public void RemoveWord()
    {
        String target = targetRemove.getText();
        warningRemove.setText(dictionary.removeWord(target));
        explainRemove.setText("");
    }
    /**
     * Edit words
     */
    public void EditWord()
    {
        String target = targetEdit.getText();
        String explain = explainEdit.getText();
        warningEdit.setText(dictionary.editWord(target,explain));
        explainEdit.setText("");
    }
    /**
     * print recent words
     */
    public void printRecentWord()
    {
        listViewRecent.getItems().clear();
        for(int i = 0; i< dictionary.recentWord.size(); i++) {
            listViewRecent.getItems().add(dictionary.recentWord.get(i));

        }
    }
    /**
     * print favorite words
     */
    public void printFavoriteWord()
    {
        listViewFavorite.getItems().clear();
        for(int i = 0; i< dictionary.favoriteWord.size(); i++)
            listViewFavorite.getItems().add(dictionary.favoriteWord.get(i));
    }
    /**
     * Reset this dictionary to default dictionary
     */
    public void ResetToDeFaultDictionary()
    {
        dictionary.resetToDefaultDictionary();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reset to default Dictionary");
        alert.setHeaderText("Reset successfully");
        alert.show();
    }

    /**
     * export custom dictionary when close window to save data
     */
    public static void  ExportCustomDictionary(){
        try {
            dictionary.exportCustomDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * init variables
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonFavor.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            String target = targetShow.getText();
            if (!target.trim().equals("English - Vietnamese"))
            {
                Word word = dictionary.dictionaryLookup(target);
                word.setFavorite(newValue);
                dictionary.listWord.remove(word);
                dictionary.listWord.add(word);
                if (newValue)
                {
                    if (!dictionary.favoriteWord.contains(target)) dictionary.favoriteWord.addFirst(target);
                }
                else
                {
                    dictionary.favoriteWord.remove(target);
                }
            }
            else buttonFavor.setSelected(false);
        }));
        listViewRecent.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        listViewFavorite.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        targetSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookupSearch();
                }
                if (ke.getCode() == KeyCode.DOWN){
                    listViewSearch.requestFocus();
                    listViewSearch.getSelectionModel().selectFirst();
                }
            }
        });
        listViewSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String target =  listViewSearch.getSelectionModel().getSelectedItem();
                targetSearch.setText(target);
                lookupSearch();
            }
        });
        listViewSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target =  listViewSearch.getSelectionModel().getSelectedItem();
                    targetSearch.setText(target);
                    lookupSearch();
                }
                if (ke.getCode() == KeyCode.UP){
                    if (listViewSearch.getSelectionModel().getSelectedIndex() == 0)
                    targetSearch.requestFocus();

                }
            }
        });
        targetAdd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.DOWN){
                    listViewAdd.requestFocus();
                    listViewAdd.getSelectionModel().selectFirst();
                }
            }
        });
        listViewAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String target = listViewAdd.getSelectionModel().getSelectedItem();
                        targetSearch.setText(target);
                        lookupSearch();
                        tabpane.getSelectionModel().select(0);
                    }
            }
            }
        });
        listViewAdd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target =  listViewAdd.getSelectionModel().getSelectedItem();
                    targetAdd.setText(target);
                    lookupSearch();
                }
                if (ke.getCode() == KeyCode.UP){
                    if (listViewAdd.getSelectionModel().getSelectedIndex() == 0)
                        targetAdd.requestFocus();

                }
            }
        });
        targetRemove.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookupRemove();
                }
                if (ke.getCode() == KeyCode.DOWN){
                    listViewRemove.requestFocus();
                    listViewRemove.getSelectionModel().selectFirst();
                }
            }
        });
        listViewRemove.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String target =  listViewRemove.getSelectionModel().getSelectedItem();
                targetRemove.setText(target);
                lookupRemove();
            }
        });
        listViewRemove.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target =  listViewRemove.getSelectionModel().getSelectedItem();
                    targetRemove.setText(target);
                    lookupRemove();
                }
                if (ke.getCode() == KeyCode.UP){
                    if (listViewRemove.getSelectionModel().getSelectedIndex() == 0)
                        targetRemove.requestFocus();
                }
            }
        });
        targetEdit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookupEdit();
                }
                if (ke.getCode() == KeyCode.DOWN){
                    listViewEdit.requestFocus();
                    listViewEdit.getSelectionModel().selectFirst();
                }
            }
        });
        listViewEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String target =  listViewEdit.getSelectionModel().getSelectedItem();
                targetEdit.setText(target);
                lookupEdit();
            }
        });
        listViewEdit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target =  listViewEdit.getSelectionModel().getSelectedItem();
                    targetEdit.setText(target);
                    lookupEdit();
                }
                if (ke.getCode() == KeyCode.UP){
                    if (listViewEdit.getSelectionModel().getSelectedIndex() == 0)
                        targetEdit.requestFocus();
                }
            }
        });
        listViewFavorite.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String target =  listViewFavorite.getSelectionModel().getSelectedItem();
                        targetSearch.setText(target);
                        lookupSearch();
                        tabpane.getSelectionModel().select(0);
                    }
                }
            }
        });
        listViewRecent.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        String target =  listViewRecent.getSelectionModel().getSelectedItem();
                        targetSearch.setText(target);
                        lookupSearch();
                        tabpane.getSelectionModel().select(0);
                    }
                }
            }
        });
        try {
            dictionary.importCustomDictionary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * class Xcel customize list cell
     */
    static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        ToggleButton button = new ToggleButton();
        String lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            label.setMaxWidth(500);
            label.setMaxHeight(90);
            label.setWrapText(true);
            button.selectedProperty().addListener(((observable, oldValue, newValue) -> {
                String target = lastItem;
                Word word = dictionary.dictionaryLookup(target);
                word.setFavorite(newValue);
                dictionary.listWord.remove(word);
                dictionary.listWord.add(word);
                if (newValue) {
                    if (!dictionary.favoriteWord.contains(target)) dictionary.favoriteWord.addFirst(target);
                }
                else {
                    dictionary.favoriteWord.remove(target);
                }
            }));
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                if (item!=null) {
                    Word word = dictionary.dictionaryLookup(item);
                    button.setSelected(word.isFavorite());
                    label.setText(word.toString2());
                }
                setGraphic(hbox);
            }
        }
    }
}
