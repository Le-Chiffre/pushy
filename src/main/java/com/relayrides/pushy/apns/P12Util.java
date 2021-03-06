package com.relayrides.pushy.apns;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

class P12Util {

    public static PrivateKeyEntry getPrivateKeyEntryFromP12InputStream(final InputStream p12InputStream, final String password) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, CertificateException, IOException {
        final KeyStore keyStore = KeyStore.getInstance("PKCS12");

        keyStore.load(p12InputStream, password != null ? password.toCharArray() : null);

        if (keyStore.size() != 1) {
            throw new KeyStoreException("Key store must contain exactly one entry, and that entry must be a private key entry.");
        }

        final String alias = keyStore.aliases().nextElement();
        final KeyStore.Entry entry = keyStore.getEntry(alias, password != null ? new KeyStore.PasswordProtection(password.toCharArray()) : null);

        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            throw new KeyStoreException("Key store must contain exactly one entry, and that entry must be a private key entry.");
        }

        return (PrivateKeyEntry) entry;
    }
}
