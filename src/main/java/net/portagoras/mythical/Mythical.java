package net.portagoras.mythical;

public class Mythical {

    String apiToken;

    /**
     * Starts a new Instance of the Mythical API
     * @param apiToken API token is required for the connection with the Mythical Bot List
     */
    public Mythical(String apiToken) {

        if(apiToken == null || apiToken.equals("")) {
            throw new IllegalArgumentException("You must deliver a valid API token");
        }

        this.apiToken = apiToken;
    }

    public void postServers(long id, int count){

    }

    public void postUsers(long id, int count){

    }


    public void postServers(String id, int count){
        postServers(Long.valueOf(id), count);
    }
    public void postUsers(String id, int count){
        postUsers(Long.valueOf(id), count);
    }

}
