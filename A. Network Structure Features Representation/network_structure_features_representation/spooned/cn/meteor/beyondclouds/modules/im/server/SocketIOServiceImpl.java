package cn.meteor.beyondclouds.modules.im.server;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class SocketIOServiceImpl implements cn.meteor.beyondclouds.modules.im.server.SocketIOService {
    /**
     * 用来存已连接的客户端
     */
    private static final java.util.Map<java.lang.String, java.util.Set<com.corundumstudio.socketio.SocketIOClient>> CLIENT_MAP = new java.util.concurrent.ConcurrentHashMap<>();

    private com.corundumstudio.socketio.SocketIOServer socketIOServer;

    private cn.meteor.beyondclouds.core.redis.TokenManager tokenManager;

    @org.springframework.beans.factory.annotation.Autowired
    public void setTokenManager(cn.meteor.beyondclouds.core.redis.TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setSocketIOServer(com.corundumstudio.socketio.SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     *
     * @throws Exception
     */
    @javax.annotation.PostConstruct
    private void autoStartup() throws java.lang.Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     * @throws Exception
     */
    @javax.annotation.PreDestroy
    private void autoStop() throws java.lang.Exception {
        stop();
    }

    @java.lang.Override
    public void start() throws java.lang.Exception {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            java.lang.String token = client.getHandshakeData().getSingleUrlParam("token");
            cn.meteor.beyondclouds.modules.im.server.log.debug("new websocket connection, token is {}", token);
            // 若没有携带token， 则直接关闭webscoket连接
            if (null == token) {
                client.disconnect();
                return;
            }
            // 校验token
            cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = tokenManager.getTokenInfo(token);
            if (null == tokenInfo) {
                client.disconnect();
                return;
            }
            // 将session存入内存
            java.lang.String userId = tokenInfo.getUserId();
            putClient(userId, client);
            cn.meteor.beyondclouds.modules.im.server.log.debug("user {} [{},{},{}] connected to the im server", userId, client.getSessionId(), client.getRemoteAddress(), client.getNamespace());
            // client.sendEvent(PUSH_EVENT, "hello, 欢迎连接服务器!");
        });
        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            cn.meteor.beyondclouds.modules.im.server.log.info("断开连接.");
            java.lang.String token = client.getHandshakeData().getSingleUrlParam("token");
            if (null != token) {
                cn.meteor.beyondclouds.modules.user.dto.TokenInfo tokenInfo = tokenManager.getTokenInfo(token);
                if (null != tokenInfo) {
                    Set<com.corundumstudio.socketio.SocketIOClient> clients = CLIENT_MAP.get(tokenInfo.getUserId());
                    if (!org.springframework.util.CollectionUtils.isEmpty(clients)) {
                        cn.meteor.beyondclouds.modules.im.server.log.debug("before remove{}", clients);
                        cn.meteor.beyondclouds.modules.im.server.log.debug("remove{}", client);
                        clients.remove(client);
                        cn.meteor.beyondclouds.modules.im.server.log.debug("after remove{}", clients);
                    }
                }
            }
        });
        socketIOServer.start();
    }

    @java.lang.Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @java.lang.Override
    public void pushMessage(java.lang.String userId, java.lang.Object message) {
        java.util.Set<com.corundumstudio.socketio.SocketIOClient> clients = cn.meteor.beyondclouds.modules.im.server.SocketIOServiceImpl.CLIENT_MAP.get(userId);
        log.info("send message to {}", userId);
        if (!org.springframework.util.CollectionUtils.isEmpty(clients)) {
            clients.forEach(socketIOClient -> {
                socketIOClient.sendEvent(cn.meteor.beyondclouds.modules.im.server.SocketIOService.PUSH_EVENT, message);
            });
        }
    }

    private void putClient(java.lang.String userId, com.corundumstudio.socketio.SocketIOClient socketIOClient) {
        java.util.Set<com.corundumstudio.socketio.SocketIOClient> clients = cn.meteor.beyondclouds.modules.im.server.SocketIOServiceImpl.CLIENT_MAP.get(userId);
        if (null == clients) {
            clients = new java.util.HashSet<>();
            cn.meteor.beyondclouds.modules.im.server.SocketIOServiceImpl.CLIENT_MAP.put(userId, clients);
        }
        clients.add(socketIOClient);
    }
}