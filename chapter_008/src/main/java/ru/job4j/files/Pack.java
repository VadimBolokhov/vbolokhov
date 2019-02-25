package ru.job4j.files;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;

/**
 * Archiver console application.
 * @author Vadim Bolokhov
 */
public class Pack {

    public static void main(String[] args) throws IOException {
        Args arguments = new Args(args);
        try {
            String sourceArg = arguments.directory();
            String destArg = arguments.output();
            List<String> extensions = arguments.exclude();
            ZipArchiver archiver = new ZipArchiver();
            PathEntryList list = archiver.getPathEntryList(sourceArg, extensions);
            archiver.pack(list, Paths.get(destArg));
        } catch (FileNotFoundException e) {
            System.out.println("Source directory does not exist.");
        } catch (IllegalArgumentException e) {
            String usage = new StringJoiner(System.lineSeparator())
                    .add("Usage: pack -d <source directory> -o <output> [-options]")
                    .add("where possible options include:")
                    .add("\t-e <extensions to exclude>\t\tExclude files with specified extensions.")
                    .toString();
            System.out.println(e.getMessage() + System.lineSeparator() + usage);
        } catch (FileSystemException e) {
            System.out.println(e.getMessage());
        }
    }
}
