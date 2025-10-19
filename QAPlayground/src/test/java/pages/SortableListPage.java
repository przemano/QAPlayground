package pages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class SortableListPage extends Page {

	public static final String URL = "/apps/sortable-list/";
	public static final String AssertMsg_DiscrepancyExpectedStatus = "Discrepancy in expected status";
	
	//private static final String XPATH_PERSONNAME = ".//*[contains(@class,'person-name')]"; 
		
//	@FindBy(xpath = "//*[@id = 'draggable-list']//li/*[contains(@class,'draggable')]")
//	private List<WebElement> RichestPeopleDrapAndDropList;
			
	@FindBy(xpath = "//*[@id = 'draggable-list']//li") //"//*[contains(@class,'person-name')]")
	private List<WebElement> RichestPeopleList;
	
	@FindBy(id = "check")
	private WebElement CheckOrderButton; 
	
	private static final String XPATH_PEOPLE_NUMBER =".//*[contains(@class,'number')]";
	private static final String XPATH_PEOPLE_DRAGABLE_AREA =".//*[contains(@class,'draggable')]";
	private static final String XPATH_PEOPLE_NAME =".//*[contains(@class,'person-name')]";
	public static final String Class_Right = "right";
	
	
	public SortableListPage(WebDriver driver) {
		super(driver);
		this.url = URL;
	}

	
	
	public SortableListPage dragAndDropAllPersons(Map<Integer, String> richestpeoplelist2) {

		richestpeoplelist2.forEach((place,person) ->
		{
			dragAndDropPersonOnPlace(person, place);
		});
		
		return this;
	}

	public SortableListPage checkOrder() {
		CheckOrderButton.click();
		return this;
	}	
	
	
	public boolean areAllPersonsGreen() {
		long rightCount = RichestPeopleList
			.stream()
			.filter(personItem -> personItem.getAttribute("class").contains(Class_Right))
			.count();
		
		return rightCount == RichestPeopleList.size();
		
	}
	
	
	private SortableListPage dragAndDropPersonOnPlace(String person, int place) {
		int currentIndex = findPersonAndTakeIndex(person);
		int placeIndex = place -1;
		dragAndDropElement(currentIndex, placeIndex); 
		assertEquals(person, takePersonName(placeIndex));
		assertEquals(place, takePersonPlace(placeIndex));
		return this;
	}
	
	private int findPersonAndTakeIndex(String person)
	{
		return  	
		IntStream.range(0, RichestPeopleList.size())
		.filter(i -> takePersonName(i).equalsIgnoreCase(person))
		.findFirst()
		.orElseThrow(() -> new NoSuchElementException("There is no person '"+person+"' on list."));
	}
	
	private void dragAndDropElement(int currentPlaceIndex, int placeIndex) 
	{
		//int placeIndex = place -1;
		new Actions(driver)
			.dragAndDrop(takePersonDraggableArea(currentPlaceIndex), takePersonDraggableArea(placeIndex))
			.perform();

		try { //FIXME - replace Thread.sleep with wait.until
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

	public String takePersonOrderStatus(int placeIndex) {
		
		return RichestPeopleList.get(placeIndex).getAttribute("class");
	}
	
	private String takePersonName(int placeIndex)
	{
		return RichestPeopleList.get(placeIndex).findElement(By.xpath(XPATH_PEOPLE_NAME)).getText();
	}

	private int takePersonPlace(int placeIndex)
	{
		String currentPlace = RichestPeopleList.get(placeIndex).findElement(By.xpath(XPATH_PEOPLE_NUMBER)).getText();
		return Integer.parseInt(currentPlace);
	}
	
	private WebElement takePersonDraggableArea(int place)
	{
		//place=-1; //need index of element
		return RichestPeopleList.get(place).findElement(By.xpath(XPATH_PEOPLE_DRAGABLE_AREA));
		
	}





	
}