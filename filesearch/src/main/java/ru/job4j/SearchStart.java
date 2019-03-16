package ru.job4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * File search application main class.
 * @author Vadim Bolokhov
 */
public class SearchStart {
    /** Command line arguments */
    private String[] args;

    public SearchStart(String[] args) {
        this.args = args;
    }

    /**
     * The main cycle
     */
    public void start() {
        FileSearchArgs parser = new FileSearchArgs(this.args);
        try {
            Path output = Paths.get(parser.getDestination());
            List<String> searchResult = this.performSearch(parser);
            new FileOutput(output).writeStrings(searchResult);
        } catch (ArgsException e) {
            this.showHelp(e.getErrorCode().getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> performSearch(FileSearchArgs parser) throws ArgsException, IOException {
        Path searchDir = Paths.get(parser.getSearchDir());
        if (!Files.exists(searchDir)) {
            throw new FileNotFoundException("Cannot find file or directory: "
                    + searchDir.toString());
        }
        Predicate<Path> matcher = parser.getSearchCondition();
        FileSearch search = new FileSearch(matcher);
        List<Path> searchResult = search.search(searchDir);
        return this.getStringList(searchResult);
    }

    private List<String> getStringList(List<Path> paths) throws FileNotFoundException {
        if (paths.isEmpty()) {
            throw new FileNotFoundException("No files found.");
        }
        return paths.stream()
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    private void showHelp(String message) {
        String usage = new StringJoiner(System.lineSeparator())
                .add("Usage: find -d <search directory> -o <output> -n <pattern> [-options]")
                .add("where possible options include:")
                .add("\t-m\t\tFind files by wildcard")
                .add("\t-r\t\tFind files by regular expression")
                .add("\t-f\t\tFind files by full match")
                .toString();
        System.out.println(message);
        System.out.println(usage);
    }

    public static void main(String[] args) {
        new SearchStart(args).start();
    }
}
