package doctorhoai.learn.proxy_client.jwt.service;

public interface TokenService {
    void saveToken( String token , String username);
    boolean findToken( String token );
    void deleteToken( String token );
}
