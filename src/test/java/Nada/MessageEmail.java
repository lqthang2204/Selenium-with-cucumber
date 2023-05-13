package Nada;

import Nada.Message;

import java.util.Arrays;
import java.util.List;

public class MessageEmail {
    public long last;
    public long total;
    public Message[] msgs;


    public Message[] getMsgs() {
        return msgs;
    }

    public void setMsgs(Message[] msgs) {
        this.msgs = msgs;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
