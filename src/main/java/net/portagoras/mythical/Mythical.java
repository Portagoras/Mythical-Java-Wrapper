package net.portagoras.mythical;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.*;
import org.json.JSONObject;

import java.io.IOException;

public class Mythical {

    private String apiToken;
    private AsyncHttpClient httpsClient;
    private boolean verbose;

    private static final String INVALID_KEY = "Invalid API Key";
    private static final String ACCEPTED_SERVERS = "Valid API Key, settting servers...";
    private static final String ACCEPTED_USERS = "Valid API Key, setting user count...";

    /**
     * Starts a new Instance of the Mythical API
     * @param apiToken API token is required for the connection with the Mythical Bot List
     */
    public Mythical(String apiToken) {
        this(apiToken, false);
    }

    public Mythical(String apiToken, boolean verbose) {

        if(apiToken == null || apiToken.equals("")) {
            throw new IllegalArgumentException("You must deliver a valid API token");
        }

        this.apiToken = apiToken;
        this.verbose = verbose;

        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config()
                .setConnectTimeout(500);
        httpsClient = Dsl.asyncHttpClient(clientBuilder);
    }


    /**
     * Updates the Servercount on Mythical Bot List
     * @param id    ID of your bot
     * @param count Amount of servers the bot is currently on.
     */
    public void postServers(long id, int count){

        String query = "https://mythicalbots.xyz/api?" +
                "key=" + apiToken +
                "?postServers?" + id + "/" + count;
        BoundRequestBuilder getRequest = httpsClient.prepareGet(query);
        getRequest.setHeader("Content-Type","application/json");

        getRequest.execute(new AsyncHandler<Object>() {

            public State onStatusReceived(HttpResponseStatus httpResponseStatus) {return null;}

            public State onHeadersReceived(HttpHeaders httpHeaders) {return null;}

            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) {
                JSONObject json = new JSONObject(new String(httpResponseBodyPart.getBodyPartBytes()));
                String err = json.getString("err");
                if(err.toLowerCase().equals(INVALID_KEY.toLowerCase())){
                    errln("Mythical: Invalid API Key ("+apiToken+")");
                }else if(err.toLowerCase().equals(ACCEPTED_SERVERS.toLowerCase())){
                    println("Mythical: Updated Servers");
                }
                return null;
            }

            public void onThrowable(Throwable throwable) {}

            public Object onCompleted() {return null;}
        });

    }

    /**
     * Updates the Usercount on Mythical Bot List
     * @param id    ID of your Bot
     * @param count Amount of users that are currently connected to the servers your bot is on.
     */
    public void postUsers(long id, int count){

        String query = "https://mythicalbots.xyz/api?" +
                "key=" + apiToken +
                "?postUsers?" + id + "/" + count;
        BoundRequestBuilder getRequest = httpsClient.prepareGet(query);
        getRequest.setHeader("Content-Type","application/json");

        getRequest.execute(new AsyncHandler<Object>() {

            public State onStatusReceived(HttpResponseStatus httpResponseStatus) {return null;}

            public State onHeadersReceived(HttpHeaders httpHeaders) {return null;}

            public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) {
                JSONObject json = new JSONObject(new String(httpResponseBodyPart.getBodyPartBytes()));
                String err = json.getString("err");
                if(err.toLowerCase().equals(INVALID_KEY.toLowerCase())){
                    errln("Mythical: Invalid API Key ("+apiToken+")");
                }else if(err.toLowerCase().equals(ACCEPTED_USERS.toLowerCase())){
                    println("Mythical: Updated Users");
                }
                return null;
            }

            public void onThrowable(Throwable throwable) {}

            public Object onCompleted() {return null;}
        });

    }

    /**
     * Updates the Servercount on Mythical Bot List
     * @param id    ID of your bot
     * @param count Amount of servers the bot is currently on.
     */
    public void postServers(String id, int count){
        postServers(Long.valueOf(id), count);
    }

    /**
     * Updates the Usercount on Mythical Bot List
     * @param id    ID of your Bot
     * @param count Amount of users that are currently connected to the servers your bot is on.
     */
    public void postUsers(String id, int count){
        postUsers(Long.valueOf(id), count);
    }

    /**
     * Closes all Sockets, latest packages may be lost.
     */
    public void close(){
        try {
            httpsClient.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void println(String str){
        if(verbose)
            System.out.println(str);
    }

    private void errln(String str){
        if(verbose)
            System.err.println(str);
    }

}
