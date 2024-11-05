package tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Config;
import pages.DynamicTablePage;
import pages.HomePage;
@DisplayName("QA Playground with Selenium 4")
public class QAPlaygroundTest extends BaseTest {
	
	@DisplayName("Dynamic Table -  Find the '" + Config.DynamicTable.UserSuperHero + "' and assert his real name")
	@Test
	public void dynamicTable()
	{
		DynamicTablePage dynamicTablePage = new DynamicTablePage(driver);
		
		new HomePage(driver).goTo(dynamicTablePage);
		
		var userRealName = dynamicTablePage.takeRealNameOfUser(Config.DynamicTable.UserSuperHero);
		
		Assertions.assertEquals(Config.DynamicTable.UserRealName, userRealName);
		
	}
}
