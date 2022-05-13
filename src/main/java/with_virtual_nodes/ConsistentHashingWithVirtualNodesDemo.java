package with_virtual_nodes;

import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsistentHashingWithVirtualNodesDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true); // this will also round numbers, 3

        List<String> rackServers = new ArrayList<>();
        rackServers.add("10.0.0.1");
        rackServers.add("10.0.0.2");

        int noOfVirtualNodes = 2;

        ConsistentHashWithVirtualNodes serverDistributor = new ConsistentHashWithVirtualNodes(noOfVirtualNodes, rackServers);

        serverDistributor.addServerToHashRing("10.0.0.3");

        String key = "Sun";
        Server serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey + " holds key: " + key + "\n"));

        key = "Moon";
        serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey + " holds key: " + key + "\n"));
        //serverDistributor.removeServerFromHashRing(serverForKey);

        key = "facebookfacebookfacebookzzzz";
        serverForKey = serverDistributor.getServer(key);
        System.out.println(("without_virtual_nodes.Server: " + serverForKey + " holds key: " + key + "\n"));

    }
}