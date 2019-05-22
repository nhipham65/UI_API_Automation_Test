import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

'Open web site to test'
WebUI.openBrowser(GlobalVariable.baseURLTest)

'Wait for iFrame loaded'
WebUI.waitForElementPresent(findTestObject('Page Main/Page iFrame/iFrameLive'), 5)

'Navigate to iFrame'
WebUI.switchToFrame(findTestObject('Page Main/Page iFrame/iFrameLive'), 5)

'VERIFY TEST CASE 3'

'Enter search text for search textbox'
WebUI.sendKeys(findTestObject('Page Main/Page Search/Search Catalog'), searchText)

'Click Search icon'
WebUI.click(findTestObject('Page Main/Page Search/Search Icon'))

'Wait for search result loaded'
WebUI.waitForElementVisible(findTestObject('Page Main/Page Search/Product Search List'), 5)

'Get total of Product loaded and check all the items contain "Mug" in their names in the search result'
def text = WebUI.getText(findTestObject('Page Main/Page Search/Total of Product'))
println(text)

if (text != null) {
    def numberProduct = text.replaceAll('[^0-9]', '')

    result = Integer.parseInt(numberProduct)

    println(result)

    for (def index : (1..result)) {
        
		WebUI.verifyElementVisible(findTestObject('Page Main/Page Search/Title of Search List', [('index') : index]))
       
		 def titleName = WebUI.getText(findTestObject('Page Main/Page Search/Title of Search List', [('index') : index]))

        println(titleName)

        WebUI.verifyEqual(titleName.toLowerCase().contains(searchText.toLowerCase()), true)
    }
} 
else {
    println('There is no available product')
}

'VERIFY TEST CASE 4'

'Click on Sort By dropdown'
WebUI.click(findTestObject('Page Main/Page Sort/Sort By of dropdown'))

'Click on Menu Item - Price, low to high'
WebUI.click(findTestObject('Page Main/Page Sort/Menu Item of dropdown'))

'Verify Sorted Item result loaded'
WebUI.verifyElementVisible(findTestObject('Page Main/Page Sort/Sorted Item List'))

'Wait for sorted result loaded'
WebUI.delay(5)

'Float list to store price list'
List<Float> priceList = new ArrayList<Float>()

'Loop to get price list'
for (def index : (1..result)) {
	
	def price = WebUI.getText(findTestObject('Page Main/Page Sort/Product Item Price', [('index') : index]))
	
	priceList.add(Float.valueOf(price.replace('$', '')))
	
	println(price)
}

'Check price list is sorted by price low to high'
for (def index = 0; index < (priceList.size() - 1); index++) {
	
	//WebUI.verifyLessThanOrEqual(priceList[index], priceList[(index + 1)])
	
	WebUI.verifyEqual(priceList[index] <= priceList[(index + 1)], true)
		
}

'Go out of iFrame'
WebUI.switchToDefaultContent()

'Close browser'
WebUI.closeBrowser()

