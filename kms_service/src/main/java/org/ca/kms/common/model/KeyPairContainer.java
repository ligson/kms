package org.ca.kms.common.model;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ligson on 2016/5/13.
 */
public class KeyPairContainer {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String type;
    private int keySize;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
}
