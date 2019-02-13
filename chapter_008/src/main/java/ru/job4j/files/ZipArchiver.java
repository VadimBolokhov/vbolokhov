package ru.job4j.files;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip archiver.
 * @author Vadim Bolokhov
 */
public class ZipArchiver implements Archiver {
    /** File search */
    private Search fileSearch = new Search();

    @Override
    public void pack(String source, String dest, List<String> exclude) throws IOException {
        Path input = this.getAbsolutePath(source);
        List<File> searchResult = this.fileSearch.files(input.toString(), new LinkedList<>());
        if (!searchResult.isEmpty()) {
            List<File> filesToZip = this.excludeExtensions(searchResult, exclude);
            File zipFile = this.createTargetDirectory(dest);
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile))) {
                Path root = getZipRoot(input);
                for (File fileToZip : filesToZip) {
                    String entryName = this.getEntryName(root, fileToZip);
                    if (fileToZip.isDirectory()) {
                        zipOut.putNextEntry(new ZipEntry(entryName + "/"));
                        zipOut.closeEntry();
                    } else {
                        try (FileInputStream fis = new FileInputStream(fileToZip)) {
                            zipOut.putNextEntry(new ZipEntry(entryName));
                            this.writeBytes(fis, zipOut);
                            zipOut.closeEntry();
                        }
                    }
                }
            }
        }
    }

    private Path getZipRoot(Path source) {
        return Files.isDirectory(source) ? source : source.getParent();
    }

    private Path getAbsolutePath(String source) {
        Path result = Paths.get(source);
        if (!result.isAbsolute()) {
            Path current = Paths.get(System.getProperty("user.dir"));
            result = current.resolve(result);
        }
        return result;
    }

    private File createTargetDirectory(String dest) throws FileSystemException {
        File zipFile = this.getAbsolutePath(dest).toFile();
        File parent = zipFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new FileSystemException("Unable to create directory: " + parent.toString());
        }
        return zipFile;
    }

    private List<File> excludeExtensions(List<File> fileList, List<String> extensions) {
        List<File> result = fileList;
        if (!extensions.isEmpty()) {
            String regex = String.format(".+\\.(%s)\\b", String.join("|", extensions));
            result = result.stream()
                    .filter(file -> !file.getName().matches(regex))
                    .collect(Collectors.toList());
        }
        return result;
    }

    private String getEntryName(Path source, File fileToZip) {
        Path relativePath = source.relativize(fileToZip.toPath());
        return relativePath.toString();
    }

    private void writeBytes(FileInputStream fis, ZipOutputStream zipOut) throws IOException {
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }
}
