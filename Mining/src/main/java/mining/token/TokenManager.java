package mining.token;

public class TokenManager {
    public static String masterToken = ""; //判断请求是否由主机发出。
    // 当接受到主机发出的接受要求时（即自己即将接受其他机器发送的数据时），masterToken修改为发送机的accessToken
    public static String accessToken = "";

    public static boolean registered() {
        return !masterToken.equals("");
    }
}
