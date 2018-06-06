package ru.job4j.search;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Поиск текста в файловой системе.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ParallelSearch {
    /** Путь до папки, откуда надо осуществлять поиск */
    private final String root;
    /** Заданный текст */
    private final String text;
    /** Список расширений файлов, в которых нужно делать поиск */
    private final List<String> exts;
    /** {@code true} - если обход файловой системы зовершен, {@code false} - в противном случае */
    private volatile boolean finish = false;
    /** {@code true} - если поиск завершен, {@code false} - в противном случае */
    private volatile boolean ready = false;
    /** Очередь файлов с заданными разрешениями */
    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();
    /** Список файлов, содержащих заданный текст */
    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param root путь до папки, откуда надо осуществлять поиск
     * @param text заданный текст
     * @param exts список расширений файлов
     */
    ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Создание и запуск потоков
     */
    public void init() {
        Thread search = new Thread("Search") {
            @Override
            public void run() {
                try {
                    Files.walkFileTree(Paths.get(root), new FileSearch());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        search.start();
        Thread read = new Thread("Read") {
            @Override
            public void run() {
                synchronized (ParallelSearch.this) {
                    String currentFile;
                    Scanner scan;
                    while (!finish && !this.isInterrupted()) {
                        try {
                            while (files.isEmpty()) {
                                ParallelSearch.this.wait();
                            }
                            currentFile = files.poll();
                            scan = new Scanner(Paths.get(currentFile));
                            while (scan.hasNextLine()) {
                                if (scan.nextLine().contains(text)) {
                                    paths.add(currentFile);
                                    break;
                                }
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ready = true;
                    ParallelSearch.this.notifyAll();
                }
            }
        };
        read.start();
    }

    /**
     * Возвращает результат поиска
     * @return список файлов, содержащих заданный текст
     */
    synchronized List<String> result() {
        while (!this.ready) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.paths;
    }

    /**
     * Класс, осуществляющий обход файловой системы
     */
    private class FileSearch implements FileVisitor<Path> {
        String getExtension(Path file) {
            String fileName = file.toString();
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            synchronized (ParallelSearch.this) {
                for (String ext : exts) {
                    if (ext.equals(this.getExtension(file))) {
                        files.offer(file.toString());
                        ParallelSearch.this.notify();
                        break;
                    }
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            FileVisitResult result = FileVisitResult.CONTINUE;
            synchronized (ParallelSearch.this) {
                if (Files.isSameFile(dir, Paths.get(root))) {
                    finish = true;
                    result = FileVisitResult.TERMINATE;
                    ParallelSearch.this.notifyAll();
                }
            }
            return result;
        }
    }
}
