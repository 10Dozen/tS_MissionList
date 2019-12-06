package ru.ts.missionlist.grabber.App;

import javafx.stage.Stage;

public class Controller {
    private Stage stage;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public void initEvents() {
/*
        stage.getScene().getRoot().
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
            }
        });

 */
    }
}
