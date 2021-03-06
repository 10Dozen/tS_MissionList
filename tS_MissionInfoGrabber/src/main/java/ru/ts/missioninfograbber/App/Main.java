package ru.ts.missioninfograbber.App;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ru.ts.missioninfograbber.logic.MissionInfoGrabber;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 475;

    private static final String DEFAULT_FOLDER = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private static final String DEFAULT_OUTPUT_FILENAME = "MissionInfo.txt";

    private static final String APPNAME = "tS Mission Info Grabber";
    private static final String VERSION = "v0.3";
    private static final String BROWSE_BTN = "Browse";
    private static final String BROWSE_LBL = "Select mission folder:";
    private static final String BROWSE_DIRECTORY_TEXT = "Browse folder...";
    private static final String OUTPUT_FILENAME_PROMT_TEXT = "Output filename";
    private static final String APPLY_BTN = "Grab!";
    private static final String OVERWRITE_CHBX = "Overwrite output file";
    private static final String WRITE_DATAFILE_CHBX = "Write as datafile";

    private static final String STATUS_TEXT = "Status: %s";
    private static final String STATUS_ERR_TEXT = "Status: Error! %s";
    private static final String STATUS_RDY_TEXT = "Ready";
    private static final String STATUS_INPROGRESS_TEXT = "Grabbing...";
    private static final String STATUS_DONE_TEXT = "All done in %d ms!";

    @Override
    public void start(Stage stage) throws Exception {

        // Folder browse // ------------------------
        Label folderLbl = new Label(BROWSE_LBL);
        folderLbl.setPadding(new Insets(15, 0, 0, 0));

        TextField folderField = new TextField(DEFAULT_FOLDER);
        folderField.setMinWidth(340);
        folderField.setPromptText(BROWSE_DIRECTORY_TEXT);

        Button browseBtn = new Button(BROWSE_BTN);
        browseBtn.setMinSize(50, 20);

        FlowPane group1 = new FlowPane(folderField, browseBtn);

        // Folder list // --------------------------
        ListView listView = new ListView();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setPrefSize(400, 200);

        FlowPane groupListView = new FlowPane(listView);

        // Output settings // ---------------------
        Label outputFileLabel = new Label(OUTPUT_FILENAME_PROMT_TEXT.concat(":"));
        TextField outputFileField = new TextField();
        outputFileField.setMinWidth(340);
        outputFileField.setText(DEFAULT_OUTPUT_FILENAME);
        outputFileField.setPromptText(OUTPUT_FILENAME_PROMT_TEXT.concat("..."));
        FlowPane group2 = new FlowPane(outputFileLabel, outputFileField);

        CheckBox overwriteChbx = new CheckBox(OVERWRITE_CHBX);
        CheckBox asDataFileChbx = new CheckBox(WRITE_DATAFILE_CHBX);
        asDataFileChbx.setDisable(true);

        // Do/Reset Buttons // ---------------------
        Button applyBtn = new Button(APPLY_BTN);
        applyBtn.setPrefSize(150, 20);
        FlowPane group3 = new FlowPane(applyBtn);

        // Status // -------------------------------
        Label statusLbl = new Label(String.format(STATUS_TEXT, STATUS_RDY_TEXT));

        // --------------------
        // Panes
        // --------------------
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.setVgap(8);
        pane.setHgap(15);
        pane.setOrientation(Orientation.VERTICAL);
        pane.getChildren().addAll(folderLbl, group1, groupListView, group2, overwriteChbx, asDataFileChbx, group3, statusLbl);

        // --------------------
        // Events
        // --------------------
        browseBtn.setOnAction(event -> {
            DirectoryChooser chooser = new DirectoryChooser();

            if (!folderField.getText().isEmpty()) {

                Path path = Paths.get(folderField.getText());
                File initFolder = null;

                if (Files.isDirectory(path)) {
                    initFolder = path.toFile();
                } else {
                    initFolder = path.getParent().toFile();
                }

                if (initFolder.exists()) {
                    chooser.setInitialDirectory(initFolder);
                }
            }

            File selectedDirectory = chooser.showDialog(stage);
            if (selectedDirectory != null) {
                folderField.setText(selectedDirectory.getAbsolutePath());
                updateListView(listView, selectedDirectory);
            }
        });
        folderField.setOnKeyReleased(event -> {
            if (!event.getCode().equals(KeyCode.ENTER)) { return; }
            if (folderField.getText().isEmpty()) { return; }

            Path path = Paths.get(folderField.getText());
            if (!Files.isDirectory(path)) { return; }

            updateListView(listView, path.toFile());
        });
        overwriteChbx.setOnAction(event -> {
            if (overwriteChbx.isSelected()) {
                asDataFileChbx.setDisable(false);
            } else {
                if (asDataFileChbx.isSelected()) {
                    asDataFileChbx.setSelected(false); // Disable as Data file when not overwriting
                }
                asDataFileChbx.setDisable(true);
            }
        });
        applyBtn.setOnAction(event -> {
            long start = System.currentTimeMillis();
            long timeSpent = -1;
            statusLbl.setText(STATUS_INPROGRESS_TEXT);
            statusLbl.setTextFill(Color.OLIVEDRAB);

            boolean completed = false;
            boolean isSingleMission = pane.isDisable();
            pane.setDisable(true);

            try {
                String rootDirectory = folderField.getText();
                ObservableList subdirectories = listView.getSelectionModel().getSelectedItems();
                String exportPath = outputFileField.getText();
                boolean doAppend = !overwriteChbx.isSelected();
                boolean asDataFile = asDataFileChbx.isSelected();

                MissionInfoGrabber grabber = new MissionInfoGrabber();
                grabber.grab(rootDirectory, subdirectories, exportPath, doAppend, asDataFile);

                completed = true;
                timeSpent = System.currentTimeMillis() - start;
            } catch (IOException e) {
                statusLbl.setText(String.format(STATUS_ERR_TEXT, e.getLocalizedMessage()));
                statusLbl.setTextFill(Color.RED);
                folderLbl.setTextFill(Color.RED);
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                statusLbl.setText(String.format(STATUS_ERR_TEXT, e.getLocalizedMessage()));
                statusLbl.setTextFill(Color.RED);
                e.printStackTrace();
            } catch (Exception e) {
                statusLbl.setText(String.format(STATUS_ERR_TEXT, e.getLocalizedMessage()));
                e.printStackTrace();
            }

            if (completed) {
                statusLbl.setText(String.format(STATUS_TEXT, String.format(STATUS_DONE_TEXT, timeSpent)));
                statusLbl.setTextFill(Color.OLIVEDRAB);
                folderLbl.setTextFill(Color.BLACK);
            }
            pane.setDisable(isSingleMission);
        });

        // --------------------
        // Stage building
        // --------------------
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle(String.format("%s %s", APPNAME, VERSION));
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.show();
    }

    private void updateListView(ListView view, File path) {
        view.getItems().clear();

        File missionFile = new File(path.getAbsolutePath(), "mission.sqm");
        if (missionFile.exists()) {
            // -- Single mission folder is selected
            view.getItems().addAll(new String[] { path.getName() });
            view.setDisable(true);
        } else {
            // -- Folder with multiple missions is selected
            String[] directories = path.list(new FilenameFilter() {
                @Override
                public boolean accept(File item, String name) {
                    return new File(item, name).isDirectory();
                }
            });
            view.getItems().addAll(directories);
            view.setDisable(false);
        }
        view.refresh();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
