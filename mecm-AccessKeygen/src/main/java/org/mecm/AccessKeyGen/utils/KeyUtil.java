package org.edge.mep.keygenerator.AKSKGenerator.utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class KeyUtil {
        //Using
        public static String convertToBase64(javax.crypto.KeyGenerator generator, int keylength)
        {
            generator.init(keylength);
            return Base64.encodeBase64String(generator.generateKey().getEncoded());
        }
}