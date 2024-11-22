package config;

import java.util.List;
import java.util.Set;

public class Config {
	/** Default home page of the tested website. Can be overwritten with variables from CI/CD tools */
	public static final String BaseUrlDefault = "https://qaplayground.dev/";
	/** Default home page of the tested website - from CI/CD tools or from BaseUrlDefault*/
	public static final String BaseURL = System.getenv("BASE_URL") == null ? Config.BaseUrlDefault : System.getenv("BASE_URL");
	/** Timeout in seconds - page loading time and other actions for the wait object of the WebDriverWait class*/
	public static final long Timeout = 3;



	public class DynamicTable {
		/**Searched superhero*/
		public static final String UserSuperHero = "Spider-Man";
		/**Searched superhero real name*/
		public static final String UserRealName = "Peter Parker";
	}
	
	public class VerifyAccount {
		
		/**Valid code to go to the 'Success' page */
		public static final String ValidCode = "999999";
		/**'Success' page message*/
		public static final Object SuccessMsg = "Success"; 
	}
	
	public class TagInputBox
	{
		/** Tags to add in the test */
		public static final List<String> Tags = List.of("przemano", "github", "com", "qaplayground", "selenium", "java", "junit", "maven");
		/** The initial number of tags added when the page was opened*/
		public static final int InitTagsNo = 2;
		/** The maximum number of tags that can be added*/
		public static final int MaxTagsNo = 10;
		
	}
	
	public class NewTab
	{
		/** Tags to add in the test */
		public static final String NewTabLabel = "Welcome to the new page!";

		
	}
}
