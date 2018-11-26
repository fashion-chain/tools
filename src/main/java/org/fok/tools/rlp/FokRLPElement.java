package org.fok.tools.rlp;

import java.io.Serializable;

public interface FokRLPElement extends Serializable {

    byte[] getRLPData();
}
