package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Tip {

    private URL url;

    private JSONArray jsonArray;
    private JSONObject jsonObject;

    private String tipJsonString;

    public Tip(URL url){
        this.url = url;
    }

    public String getTip() throws IOException {
        return new ServiceConnection(this.url).getJsonString();
    }

    public String getPubKey() throws IOException {
        this.tipJsonString = getTip();
        this.jsonArray = new JSONArray(tipJsonString);
        String publicKey=null;
        for (int i=0; i<jsonArray.length(); i++){
            jsonObject = jsonArray.getJSONObject(i);
            boolean inTipChain = jsonObject.getBoolean("InTipChain");
            if (inTipChain){
                return "DID Revoked";
            }else {
                String asm = jsonObject.getString("asm");          //Scriptsig asm
                String[] values = asm.split("\\s*");
                publicKey = values[1];
                return publicKey;
            }
        }
        return publicKey;
    }

}