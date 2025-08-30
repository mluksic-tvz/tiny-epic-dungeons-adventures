package hr.game.tinyepicdungeonsadventures.jndi;

public enum ConfigurationKey {

    RMI_HOST("rmi.host"),
    RMI_PORT("rmi.port"),
    SERVICE_LOBBY_NAME("service.lobby.name"),
    SERVICE_CHAT_NAME("service.chat.name");

    private final String key;

    ConfigurationKey(String key) { this.key = key; }

    public String getKey() { return key; }
}
