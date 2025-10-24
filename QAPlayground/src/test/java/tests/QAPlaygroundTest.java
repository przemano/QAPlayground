package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import config.Config;
import pages.*;

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
		new HomePage(driver).goTo(TagsInputBoxPage.URL);
		
		TagsInputBoxPage tib = new TagsInputBoxPage(driver);

		//CRUD method - Create, read, update, delete
		tib.addTag(Config.TagInputBox.Tags.get(0));
		var lastTag = tib.takeLastTag();
		var remainingTagsNo = tib.takeReainingTagsNo();
		assertEquals(Config.TagInputBox.Tags.get(0), lastTag);
		assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo - 1, remainingTagsNo, TagsInputBoxPage.AssertMsg_DiscrepancyTagsNumber);

		tib.removeLastTag();
		lastTag = tib.takeLastTag();
		remainingTagsNo = tib.takeReainingTagsNo();
		assertNotEquals(Config.TagInputBox.Tags.get(0), lastTag);
		assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo, remainingTagsNo, TagsInputBoxPage.AssertMsg_DiscrepancyTagsNumber);
	}
	
	@DisplayName("Tags Input Box - Add maximum number of tags and remove all and assert tag's presence and count")
	@Test
	void tagsInputBox2Test()
	{
		new HomePage(driver).goTo(TagsInputBoxPage.URL);



		TagsInputBoxPage tib = new TagsInputBoxPage(driver);

		IntStream.range(0, Config.TagInputBox.Tags.size())
		.forEach(iTag ->
		{
			tib.addTag(Config.TagInputBox.Tags.get(iTag));
			var lastTag = tib.takeLastTag();
			var remainingTagsNo = tib.takeReainingTagsNo();
			assertEquals(Config.TagInputBox.Tags.get(iTag), lastTag);
			assertEquals(Config.TagInputBox.MaxTagsNo - Config.TagInputBox.InitTagsNo - (iTag + 1), remainingTagsNo, TagsInputBoxPage.AssertMsg_DiscrepancyTagsNumber);
		});

		tib.removeAll();
		assertEquals(Config.TagInputBox.MaxTagsNo, tib.takeReainingTagsNo(), TagsInputBoxPage.AssertMsg_DiscrepancyTagsNumber);

	}

	@DisplayName("Multi Level Dropdown - Navigate into the sub-menus and assert menu items text and link")
	@ParameterizedTest(name = "#{index} Menu item {0} / {1} link to {2}")
	@CsvSource({
		"My Profile, ,#undefined",
		"Settings, ,#settings",
		"Settings,My Tutorial,#main",
		"Settings,HTML,#!HTML",
		"Settings,CSS,#!CSS",
		"Settings,JavaScript,#!JavaScript",
		"Settings,Awesome!,#!Awesome",
		"Animals, ,#animals",
		"Animals,Animals,#main",
		"Animals,Kangaroo,#!Kangaroo",
		"Animals,Frog,#!Frog",
		"Animals,Horse,#!Horse",
		"Animals,Hedgehog,#!Hedgehog"}) 
	void multiLevelDropdownTest(String menu1stLevel, String menu2ndLevel, String linkURL)
	{
		new HomePage(driver).goTo(MultiLevelDropdownPage.URL);

		MultiLevelDropdownPage mld = 
		new MultiLevelDropdownPage(driver)
		.openDropDownMenu()
		.clickMenu1stLevel(menu1stLevel)
		.clickMenu2ndLevel(menu2ndLevel);

		assertEquals(linkURL, mld.takeLastPartURL(), MultiLevelDropdownPage.AssertMsg_DiscrepancyLinkURL);
	}
	
	@DisplayName("New Tab - Open new tab by clicking on the button and assert text on the opened new page")
	@Test
	void newTabTest()
	{
		new HomePage(driver).goTo(NewTabPage.URL);

		 NewTabPage nt = new NewTabPage(driver);
		
		nt
		.clickButtonOpenNewTab()
		.switchToSecondTab();
		
		 String newTabLabel  = nt.takeLabelInNewTab();
		 
		 nt
		 .closeSecondTab()
		 .switchToFirstTab();

		assertEquals(Config.NewTab.NewTabLabel, newTabLabel, NewTabPage.AssertMsg_DiscrepancyLabel);

	}

	@DisplayName("Pop-Up Window - Open pop-up and click on the button in it and assert text on the main window")
	@Test
	void PopUpWindowTest()
	{
		new HomePage(driver).goTo(PopUpWindowPage.URL);

		PopUpWindowPage puw = new PopUpWindowPage(driver);
		
		puw
		.clickButtonOpen()
		.switchToPopup()
		.clickSubmitInPopupAndClosePopup()
		.switchToMainPage();

		String buttonClickedLabel  = puw.takeButtonClickedLabel();

		assertEquals(Config.PopUpWindow.ButtonClickedLabel, buttonClickedLabel, PopUpWindowPage.AssertMsg_DiscrepancyLabel);

	}

	@DisplayName("Nested Iframe - Click on the button in the iframe that is in another iframe and assert a success message")
	@Test
	void nestedIframeTest()
	{
		new HomePage(driver).goTo(NestedIframePage.URL);
		
		String buttonClickedLabel = 
		new NestedIframePage(driver)
		.clickClickMeButton()
		.takeButtonClickedLabel();
		
		assertEquals(Config.NestedIframe.ButtonClickedLabel, buttonClickedLabel);
	}

	@DisplayName("Shadow DOM - Click on the button and assert that progress is on the 95 percent")
	@Test
	void shadowDOMPage_Test()//int place, String person)
	{
		new HomePage(driver).goTo(ShadowDOMPage.URL);

		String progressbarValue =
				new ShadowDOMPage(driver)
						.clickBoost()
						.waitForProgressbarValue(Config.ShadowDOM.progressbarValue)
						.takeProgressbarValue();



		assertEquals(Config.ShadowDOM.progressbarValue, progressbarValue);
	}

	@DisplayName("Stars Rating Widget - Set each available rate value and assert by image, text, and number")
	@ParameterizedTest(name = "stars: {0} - {1} | {2} ")
	@CsvSource({
			"1, I just hate it, emojis/emoji-1.png",
			"2, I don't like it, emojis/emoji-2.png",
			"3, This is awesome, emojis/emoji-3.png",
			"4, I just like it, emojis/emoji-4.png",
			"5, I just love it, emojis/emoji-5.png",
	})
	void startsRatingWidget_Test(int stars, String description, String image)
	{
		//System.out.println("a");
		new HomePage(driver).goTo(StarsRatingWidgetPage.URL);

		StarsRatingWidgetPage srw = new StarsRatingWidgetPage(driver);
		srw.clickStars(stars); //FIXME Try to avoid Thread.sleep
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

		Assertions.assertEquals(description, srw.takeDescription() );
		Assertions.assertEquals(stars, srw.takeNumber() );
		Assertions.assertTrue(srw.takeImage().endsWith(image) );

	}

	@DisplayName("Covered Elements - Click on the hidden button and assert hidden message")
	@Test
	void coveredElements_Test()
	{
		new HomePage(driver).goTo(CoveredElementsPage.URL);
		CoveredElementsPage ce = new CoveredElementsPage(driver);
		String label = ce.clickButton().readLabel();
		Assertions.assertEquals(CoveredElementsPage.EXPECTED_LABEL, label);
	}
}
