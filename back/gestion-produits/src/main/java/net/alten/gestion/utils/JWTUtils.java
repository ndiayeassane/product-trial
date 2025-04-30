package net.alten.gestion.utils;

public class JWTUtils {
	public static final String SECRET="codesecret";
	public static final String HEADER_AUTH="Authorization";
	public static final String PREFIX="Bearer ";
	public static final long EXPIRE_ACCESS_TOKEN=3*60*1000;
	public static final long EXPIRE_REFRESH_TOKEN=15*60*1000;
}



