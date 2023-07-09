package ru.antonsibgatulin.stockmarketoftaskclient.websocket;

import android.content.Context;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;

public class ClientWebSocket extends WebSocketClient {

    private Context context;



    public ClientWebSocket() throws URISyntaxException {
        super(new URI("ws://"+ Constant.EMPTY_HOST));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        String [] arr = message.split(";");
        JSONObject jsonObject = null;
        if(arr[1].equals("json")){
            try {
                jsonObject = new JSONObject(message.replace(arr[0]+";"+arr[1]+";",""));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }




    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }


}
