package without_virtual_nodes;

import common.Server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ConsistentHashingDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {


        List<Server> rackServers = new ArrayList<>();
        Server server1 = new Server("node1", "10.0.0.1");
        Server server2 = new Server("node2", "10.0.0.2");
        rackServers.add(server1);
        rackServers.add(server2);


        ConsistentHash serverDistributor = new ConsistentHash(rackServers);

        Server newServer = new Server("node3", "10.0.0.3");
        serverDistributor.addServerToHashRing(newServer);

        String[] keys = new String[]{"twitter", "discord", "facebookfacebookfacebookzzzzz"};


        for (String key : keys) {
            Server serverForKey = serverDistributor.getServer(key);
            System.out.println(("without_virtual_nodes.Server: " + serverForKey + " holds key: " + key + "\n"));
        }

    }
}