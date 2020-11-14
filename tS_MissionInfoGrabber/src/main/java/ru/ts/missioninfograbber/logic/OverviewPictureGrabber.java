package ru.ts.missioninfograbber.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class OverviewPictureGrabber {
    private final String overviewImageFilename = "overview.jpg";
    private final String targetDirectoryName = "imgs";
    private final String outputImageFileSuffix = "_overview.jpg";

    public String getOverviewImage(String path, String exportPath) throws IOException {
        Path originalImgPath = Paths.get(path, overviewImageFilename);
        if (!isOverviewFileExists(originalImgPath)) { return ""; }

        Path exportImgPath = prepareImageDirectory(exportPath);
        return copyOverviewImage(originalImgPath, exportImgPath);
    }

    private Path prepareImageDirectory(String exportPath) throws IOException {
        Path imageDir = Paths.get(exportPath, targetDirectoryName);
        if (imageDir.toFile().exists()) { return imageDir; }

        return Files.createDirectory(imageDir);
    }

    private boolean isOverviewFileExists(Path path) {
        return path.toFile().exists();
    }

    private String copyOverviewImage(Path path, Path exportPath) throws IOException {
        String targetFileName = path.getParent().getFileName().toString().concat(outputImageFileSuffix);
        Path targetPath = exportPath.resolve(targetFileName);

        Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);

        return Paths.get(targetDirectoryName, targetFileName).toString();
    }
}
