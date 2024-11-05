package config;

public class Config {

	public static final String BaseUrlDefault = "https://qaplayground.dev/";
	public static final String BaseURL = System.getenv("BASE_URL") == null ? Config.BaseUrlDefault : System.getenv("BASE_URL");
	public static final long Timeout = 3;

	public class DynamicTable {
		public static final String UserSuperHero = "Spider-Man";
		public static final String UserRealName = "Peter Parker";
	}
}
