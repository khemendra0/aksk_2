/*
 *  Copyright 2020 Huawei Technologies Co., Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.edge.mep.keygenerator.AKSKGenerator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.util.Closeable;
import org.edge.mep.keygenerator.AKSKGenerator.constants.ConstantsRepo;
import org.edge.mep.keygenerator.AKSKGenerator.model.AkSk;
import org.edge.mep.keygenerator.AKSKGenerator.model.AuthDetail;
import org.edge.mep.keygenerator.AKSKGenerator.model.AuthInfo;
import org.edge.mep.keygenerator.AKSKGenerator.model.Credentials;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.edge.mep.keygenerator.AKSKGenerator.utils.*;

@CrossOrigin
@RestController
public class KeyGenController {
    @GetMapping("mecm/access/v1/applications/{appId}")
    public ResponseEntity<AuthDetail> KeyGen(@PathVariable final String appId) {
        Credentials credentialsData = new Credentials();
        AuthInfo authInfoResponse = new AuthInfo();
        try {
            javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("HMACSHA1");
            //Generate Access Key
            credentialsData.setAccessKeyId(KeyUtil.convertToBase64(generator, ConstantsRepo.AccessKeyLength));
            //Generate Secret Key
            credentialsData.setSecretKey(KeyUtil.convertToBase64(generator,ConstantsRepo.SecretKeyLength));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }

        authInfoResponse.setCredentials(credentialsData);
        AuthDetail authDetail = new AuthDetail();
        authDetail.setAuthDetail(authInfoResponse);

       /* CloseableHttpClient client = HttpClients.createDefault();
        //String url = ConstantsRepo.httpBegin. + ConstantsRepo.MEPSERVERPORT + ConstantsRepo.AKSKPushURL;
        HttpPut httpPut = new HttpPut("http://localhost:8070//mep/auth/v1/applications/{applicationId]/confs");
        httpPut.setEntity(new UrlEncodedFormEntity(authInfoResponse));

        CloseableHttpResponse response = client.execute(httpPost);

        client.close();
*/


        return new ResponseEntity<AuthDetail>(authDetail,HttpStatus.OK);
    }
}
