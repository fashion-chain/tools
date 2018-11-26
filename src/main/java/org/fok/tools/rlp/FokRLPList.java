package org.fok.tools.rlp;

import java.util.ArrayList;

import org.fok.tools.bytes.BytesHelper;

public class FokRLPList extends ArrayList<FokRLPElement> implements FokRLPElement {

    byte[] rlpData;

    public void setRLPData(byte[] rlpData) {
        this.rlpData = rlpData;
    }

    public byte[] getRLPData() {
        return rlpData;
    }

    public static void recursivePrint(FokRLPElement element) {

        if (element == null)
            throw new RuntimeException("RLPElement object can't be null");
        if (element instanceof FokRLPList) {

            FokRLPList rlpList = (FokRLPList) element;
            for (FokRLPElement singleElement : rlpList)
                recursivePrint(singleElement);
        } else {
            String hex = BytesHelper.toHexString(element.getRLPData());
        }
    }
}
