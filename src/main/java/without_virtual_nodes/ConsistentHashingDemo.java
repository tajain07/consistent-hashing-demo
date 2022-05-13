package without_virtual_nodes;

import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsistentHashingDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {


        List<Server> rackServers = new ArrayList<>();
        Server server1 = new Server("10.0.0.1");
        Server server2 = new Server("10.0.0.2");
        rackServers.add(server1);
        rackServers.add(server2);


        ConsistentHash serverDistributor = new ConsistentHash(rackServers);

        Server newServer = new Server("10.0.0.3");
        serverDistributor.addServerToHashRing(newServer);

        String key = "twitter";
        Server serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey.ipAddress + " holds key: " + key + "\n"));

        key = "discord";
        serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey.ipAddress + " holds key: " + key + "\n"));
        //serverDistributor.removeServerFromHashRing(serverForKey);

        key = "facebookfacebookfacebookzzzzz";
        serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey.ipAddress + " holds key: " + key + "\n"));

    }
}