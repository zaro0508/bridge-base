package org.sagebionetworks.bridge.crypto;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.testng.annotations.Test;

public class PemUtilsTest {

    @Test
    public void testCert() throws Exception {
        CertificateFactory certFactory = new BcCertificateFactory();
        KeyPair keyPair = KeyPairFactory.newRsa2048();
        X509Certificate cert = certFactory.newCertificate(keyPair, new CertificateInfo.Builder().build());
        String pem = PemUtils.toPem(cert);
        assertNotNull(pem);
        cert = PemUtils.loadCertificateFromPem(pem);
        cert.checkValidity();
        cert.verify(keyPair.getPublic(), BcCmsConstants.PROVIDER);
    }

    @Test
    public void testPrivateKey() throws Exception {
        KeyPair keyPair = KeyPairFactory.newRsa2048();
        String pem = PemUtils.toPem(keyPair.getPrivate());
        assertNotNull(pem);
        PrivateKey privateKey = PemUtils.loadPrivateKeyFromPem(pem);
        assertTrue(privateKey.getAlgorithm().equalsIgnoreCase(BcCmsConstants.KEY_PAIR_ALGO));
    }
}
