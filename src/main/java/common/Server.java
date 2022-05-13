package common;

public class Server {
    public String ipAddress;
    public String serverName;

    public Server(String virtualNodeName, String ipAddress) {
        this.serverName = virtualNodeName;
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Server setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getServerName() {
        //return serverName;

        return this.getServerName().substring(0, this.getServerName().indexOf("&&"));
    }

    public Server setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    @Override
    public String toString() {
        return "Server{" +
                "ipAddress='" + ipAddress + '\'' +
                ", serverName='" + serverName + '\'' +
                '}';
    }
}
