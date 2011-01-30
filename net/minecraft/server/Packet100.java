package net.minecraft.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Packet100 extends Packet {

    public int a;
    public int b;
    public String c;
    public int d;

    public Packet100() {}

    public Packet100(int i, int j, String s, int k) {
        this.a = i;
        this.b = j;
        this.c = s;
        this.d = k;
    }

    public void a(NetHandler nethandler) {
        nethandler.a(this);
    }

    public void a(DataInputStream datainputstream) {
        this.a = datainputstream.readByte();
        this.b = datainputstream.readByte();
        this.c = datainputstream.readUTF();
        this.d = datainputstream.readByte();
    }

    public void a(DataOutputStream dataoutputstream) {
        dataoutputstream.writeByte(this.a);
        dataoutputstream.writeByte(this.b);
        dataoutputstream.writeUTF(this.c);
        dataoutputstream.writeByte(this.d);
    }

    public int a() {
        return 3 + this.c.length();
    }
}
