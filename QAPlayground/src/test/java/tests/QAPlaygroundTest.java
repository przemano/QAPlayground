package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import config.Config;
import pages.DynamicTablePage;
import pages.HomePage;
import pages.TagsInputBox;
import pages.VerifyAccountPage;
@DisplayName("QA Playground with Selenium 4")
class QAPlaygroundTest extends BaseTest {
	
	@DisplayName("Dynamic Table -  Find the '" + Config.DynamicTable.UserSuperHero + "' and assert his real name")
	@Test
	void dynamicTableTest()
	{
		new HomePage(driver).goTo(DynamicTablePage.URL);

		DynamicTablePage dynamicTablePage = new DynamicTablePage(driver);
		var userRealName = dynamicTablePage.takeRealNameOfUser(Config.DynamicTable.UserSuperHero);

		Assertions.assertEquals(Config.DynamicTable.UserRealName, userRealName);
	}
	
	@DisplayName("Verify Account -  Enter valid code '" + Config.VerifyAccount.ValidCode + "' typing number and assert 'Success'")
	@Test
	void verifyAccount1Test()
	{
		new HomePage(driver).goTo(VerifyAccountPage.URL);
		
		VerifyAccountPage vap = new VerifyAccountPage(driver);
		vap.enterCodeTyping(Config.VerifyAccount.ValidCode);
		String successLabel = vap.takeSuccessMsg();
		
		assertEquals(Config.VerifyAccount.SuccessMsg, successLabel);
		
	}
	@DisplayName("Verify Account -  Enter valid code '" + Config.VerifyAccount.ValidCode + "' by pressing the key-up button and assert 'Success'")
	@Test
	void verifyAccount2Test()
	{
		new HomePage(driver).goTo(VerifyAccountPage.URL);
		
		String successLabel = 
		new VerifyAccountPage(driver)
		.enterCodePressingKeyUp(Config.VerifyAccount.ValidCode)
		.takeSuccessMsg();
		
		assertEquals(Config.VerifyAccount.SuccessMsg, successLabel);
	}

	@DisplayName("Tags Input Box - Add and remove tag and assert tag presence and count - CRUD method")
	@Test
	void tagsInputBoxTest()
	{
		new HomePage(driver).goTo(TagsInputBox.URL);
		
		TagsInputBox tib = new TagsInputBox(driver);

		//CRUD method - Create, read, update, delete
		tib.addTag(Config.TagInputBox.Tags.get(0));
		var lastTag = tib.takeLastTag();
		var remainingTagsNo = tib.takeReainingTagsNo();
		assertEquals(Config.TagInputBox.Tags.get(0), lastTag);
		assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo - 1, remainingTagsNo, TagsInputBox.AssertMsg_DiscrepancyTagsNumber);

		tib.removeLastTag();
		lastTag = tib.takeLastTag();
		remainingTagsNo = tib.takeReainingTagsNo();
		assertNotEquals(Config.TagInputBox.Tags.get(0), lastTag);
		assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo, remainingTagsNo, TagsInputBox.AssertMsg_DiscrepancyTagsNumber);
	}
	
	@DisplayName("Tags Input Box - Add maximum number of tags and remove all and assert tag's presence and count")
	@Test
	void tagsInputBox2Test()
	{
		new HomePage(driver).goTo(TagsInputBox.URL);



		TagsInputBox tib = new TagsInputBox(driver);

		IntStream.range(0, Config.TagInputBox.Tags.size())
		.forEach(iTag ->
		{
			tib.addTag(Config.TagInputBox.Tags.get(iTag));
			var lastTag = tib.takeLastTag();
			var remainingTagsNo = tib.takeReainingTagsNo();
			assertEquals(Config.TagInputBox.Tags.get(iTag), lastTag);
			assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo - (iTag + 1), remainingTagsNo, TagsInputBox.AssertMsg_DiscrepancyTagsNumber);
		});

		tib.removeAll();
		assertEquals(Config.TagInputBox.MaxTagsNo, tib.takeReainingTagsNo(), TagsInputBox.AssertMsg_DiscrepancyTagsNumber);

	}

	
}
