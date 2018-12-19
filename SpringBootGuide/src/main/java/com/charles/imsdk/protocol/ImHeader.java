package com.charles.imsdk.protocol;

public class ImHeader {
    private int version;
    private int magicNumber;
    private int cmd;
    private int length;

    public enum Command {
        handshake(1, "handshake"),
        keepalive(2, "keepalive");

        private int type;
        private String name;

        Command(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
