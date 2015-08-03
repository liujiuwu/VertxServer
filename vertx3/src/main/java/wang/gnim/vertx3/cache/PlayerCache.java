package wang.gnim.vertx3.cache;

import wang.gnim.vertx3.model.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wanggnim on 2015/8/3.
 */
public class PlayerCache {

    private Map<String, Player> loginPlayers = new ConcurrentHashMap<>();

    public Player getLoginPlayer(String playerID) {
        return loginPlayers.get(playerID);
    }

    public int addLoginPlayer(String ip, Player player) {
        loginPlayers.put(ip, player);
        return loginPlayers.size();  // XXX 可能会导致不一致的结果
    }

    public int removeLoginPlayer(String ip) {
        loginPlayers.remove(ip);
        return loginPlayers.size();    // XXX 可能会导致不一致的结果
    }
}
