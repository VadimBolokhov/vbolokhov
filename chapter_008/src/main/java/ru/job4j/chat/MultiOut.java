package ru.job4j.chat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Multiple output.
 * @author Vadim Bolokhov
 */
public class MultiOut implements ChatOut {
    /** List of outputs */
    private List<ChatOut> outs = new LinkedList<>();

    /**
     * Adds the specified output to the list.
     * @param out specified output
     */
    public void addOut(ChatOut out) {
        this.outs.add(out);
    }

    /**
     * Removes the specified output from the list.
     * @param out specified output
     */
    public void removeOut(ChatOut out) {
        this.outs.remove(out);
    }

    @Override
    public void display(String message) throws IOException {
        for (ChatOut out: this.outs) {
            out.display(message);
        }
    }
}
