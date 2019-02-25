package ru.job4j.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zip archiver.
 * @author Vadim Bolokhov
 */
public class ZipArchiver {

    /**
     * Returns an iterable set of files and directories.
     * @param source source directory
     * @param exts list of extensions to be excluded
     * @return {@code PathEntryList} object containing files and directories
     * @throws IOException if an I/O error occurs
     */
    public PathEntryList getPathEntryList(String source, List<String> exts) throws IOException {
        Path input = Paths.get(source);
        PathEntryList list = new PathEntryList(input);
        if (Files.exists(input)) {
            String regex = String.format(".+\\.(%s)\\b", String.join("|", exts));
            Queue<Path> pathQueue = new LinkedList<>();
            pathQueue.offer(input);
            while (!pathQueue.isEmpty()) {
                Path file = pathQueue.poll();
                if (Files.isDirectory(file)) {
                    try (Stream<Path> pathStream = Files.list(file)) {
                        pathStream.forEach(pathQueue::offer);
                    }
                    list.addPath(file);
                } else if (!file.toString().matches(regex)) {
                    list.addPath(file);
                }
            }
        }
        return list;
    }

    /**
     * Compresses files and directories into destination zip file
     * @param filesToZip files and directories to be compressed
     * @param dest destination file
     * @throws IOException if an I/O error occurs
     */
    public void pack(PathEntryList filesToZip, Path dest) throws IOException {
        if (filesToZip.containsFiles()) {
            this.createTargetDirectory(dest);
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(dest.toFile()))) {
                for (PathEntryList.PathEntry entry : filesToZip) {
                    this.putEntry(entry, zipOut);
                }
            }
        }
    }

    private void createTargetDirectory(Path dest) throws IOException {
        Path zipFileParent = dest.getParent();
        if (zipFileParent != null) {
            Files.createDirectories(zipFileParent);
        }
    }

    private void putEntry(PathEntryList.PathEntry entry, ZipOutputStream zipOut) throws IOException {
        String entryName = entry.getEntryName();
        Path fileToZip = entry.getPath();
        if (Files.isDirectory(fileToZip)) {
            zipOut.putNextEntry(new ZipEntry(entryName + "/"));
            zipOut.closeEntry();
        } else {
            try (FileInputStream fis = new FileInputStream(fileToZip.toFile())) {
                zipOut.putNextEntry(new ZipEntry(entryName));
                this.writeBytes(fis, zipOut);
                zipOut.closeEntry();
            }
        }
    }

    private void writeBytes(FileInputStream fis, ZipOutputStream zipOut) throws IOException {
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }
}
