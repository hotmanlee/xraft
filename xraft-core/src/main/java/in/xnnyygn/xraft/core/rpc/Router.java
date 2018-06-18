package in.xnnyygn.xraft.core.rpc;

import in.xnnyygn.xraft.core.node.AbstractNode;
import in.xnnyygn.xraft.core.node.NodeGroup;
import in.xnnyygn.xraft.core.node.NodeId;

// TODO rename to Connector
public class Router {

    private final NodeGroup nodeGroup;
    private final NodeId selfNodeId;

    public Router(NodeGroup nodeGroup, NodeId selfNodeId) {
        this.nodeGroup = nodeGroup;
        this.selfNodeId = selfNodeId;
    }

    public void sendRpc(Object rpc) {
        for (AbstractNode node : nodeGroup) {
            if (node.getId() != this.selfNodeId) {
                node.getRpcChannel().write(rpc, this.selfNodeId);
            }
        }
    }

    public void sendResult(Object result, NodeId destination) {
        AbstractNode node = this.nodeGroup.find(destination);
        node.getRpcChannel().write(result, this.selfNodeId);
    }

}