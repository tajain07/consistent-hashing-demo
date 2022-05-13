package with_virtual_nodes;


import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashWithVirtualNodes {
    NumberFormat myFormat = NumberFormat.getInstance();

    private List<String> realNodes;
    private SortedMap<Integer, Server> hashRing;
    private final int VIRTUAL_NODES;

    public ConsistentHashWithVirtualNodes(int numberOfVitualNodes, List<String> serverIPs) throws NoSuchAlgorithmException {
        this.realNodes = new LinkedList<String>();

        this.VIRTUAL_NODES = numberOfVitualNodes;
        hashRing = new TreeMap<>();

        if (serverIPs != null) {
            for (String server : serverIPs) {
                this.addServerToHashRing(server);
            }
        }
        myFormat.setGroupingUsed(true); // this will also round numbers, 3

    }

    public void addServerToHashRing(String serverIp) throws NoSuchAlgorithmException {

        realNodes.add(serverIp);

        //Adding virtual nodes makes traversing LinkedList more efficient using foreach loops
        //for (String serverIPItr : realNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String virtualNodeName = serverIp + "&&VN" + String.valueOf(i);
                int hashKey = FNVHash.To32BitFnv1aHashInt(virtualNodeName);
                System.out.println("Virtual node[" + virtualNodeName + "]Be added, hash The value is " + myFormat.format(hashKey));
                hashRing.put(hashKey, new Server(virtualNodeName, serverIp));
            }
        //}
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
        System.out.println("For Key " + key + ", Hash " + myFormat.format(hash));
        //Get all Map s that are larger than the Hash value
        SortedMap<Integer, Server> subMap = hashRing.tailMap(hash);

        Server virtualNode;

        if (subMap.isEmpty()) {
            //If there is no one larger than the hash value of the key, start with the first node
            Integer i = hashRing.firstKey();
            //Return to the corresponding server
            virtualNode = hashRing.get(i);
        } else {
            //The first Key is the nearest node clockwise past the node.
            Integer i = subMap.firstKey();
            //Return to the corresponding server
            virtualNode = subMap.get(i);
        }

        //The virtual Node virtual node name needs to be intercepted
        /*if (StringUtils.isNotBlank(virtualNode.getServerName())) {
            return virtualNode.getServerName().substring(0, virtualNode.getServerName().indexOf("&&"));
        }*/
        return virtualNode;
    }

}