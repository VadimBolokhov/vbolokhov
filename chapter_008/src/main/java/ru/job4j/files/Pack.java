package ru.job4j.files;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Archiver console application.
 * @author Vadim Bolokhov
 */
public class Pack {
    /** Archiver */
    private Archiver archiver;

    public Pack(Archiver archiver) {
        this.archiver = archiver;
    }

    public static void main(String[] args) throws IOException {
        Args arguments = new Args(args);
        try {
            String sourceArg = arguments.directory();
            String destArg = arguments.output();
            List<String> extensions = arguments.exclude();
            Pack pack = new Pack(new ZipArchiver());
            pack.pack(sourceArg, destArg, extensions);
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

    private void pack(String source, String dest, List<String> extensions) throws IOException {
        this.archiver.pack(source, dest, extensions);
    }
}
