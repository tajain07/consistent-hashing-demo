package without_virtual_nodes;

import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.*;

public class ConsistentHash {
    private SortedMap<Integer, Server> hashRing;

    public ConsistentHash(List<Server> servers) throws NoSuchAlgorithmException {
        hashRing = new TreeMap<>();

        if (servers != null) {
            for (Server server : servers) {
                this.addServerToHashRing(server);
            }
        }

    }

    public void addServerToHashRing(Server server) throws NoSuchAlgorithmException {

        String serverIdentity = server.ipAddress;
        int hashKey = FNVHash.To32BitFnv1aHashInt(serverIdentity);
        this.hashRing.put(hashKey, server);
        System.out.println(server.ipAddress + " join the set, and its Hash value is " + hashKey);

    }

    public void removeServerFromHashRing(Server server) throws NoSuchAlgorithmException {
        String serverIdentity = server.ipAddress;
        int hashKey = FNVHash.To32BitFnv1aHashInt(serverIdentity);
        this.hashRing.remove(hashKey);
    }

    //Get the node that should be routed to
    Server getServer(String key) throws NoSuchAlgorithmException {
        //Get the hash value of the key
        int hash = FNVHash.To32BitFnv1aHashInt(key);
        System.out.println("For Key " + key + ", Hash " + hash);
        //Get all Map s that are larger than the Hash value
        SortedMap<Integer, Server> subMap = hashRing.tailMap(hash);
        if (subMap.isEmpty()) {
            //If there is no one larger than the hash value of the key, start with the first node
            Integer i = hashRing.firstKey();
            //Return to the corresponding server
            return hashRing.get(i);
        } else {
            //The first Key is the nearest node clockwise past the node.
            Integer i = subMap.firstKey();
            //Return to the corresponding server
            return subMap.get(i);
        }
    }

}