package org.fok.tools.rlp;

public class FokRLPItem implements FokRLPElement {

    private final byte[] rlpData;

    public FokRLPItem(byte[] rlpData) {
        this.rlpData = rlpData;
    }

    public byte[] getRLPData() {
        if (rlpData.length == 0)
            return null;
        return rlpData;
    }
}
