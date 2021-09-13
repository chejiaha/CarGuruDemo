from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

driver = webdriver.Firefox()
# driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/")
# #driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test.html")
# web_title = driver.find_element_by_class_name("hero-title__header--overview")
# #ele = driver.find_element_by_class_name("widget-title")

# web_title = driver.find_element_by_class_name("hero-title__header--overview")
# web_cost = driver.find_element_by_class_name("bpp-widget__avg-price-paid")
# web_horsepower = driver.find_element_by_id("hp")
# web_drivetrain = driver.find_element_by_id("drivetrainInfo")
# web_seats = driver.find_element_by_id("seatingInfo")

# # Navigation link to link to get more detailed information about the car.
# web_detailed_info = driver.find_element_by_class_name("vwo-profile-nav-specs")

# print(web_title.text)
# print(web_cost.text)
# print(web_horsepower.text)
# print(web_drivetrain.text)
# print(web_seats.text)

# Click the 'Configuration' Tab
#web_detailed_info.click()
# time.sleep(8)

# driver.close()

#driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/specs")
driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test2.html")

#Once you get the specific make, model and year
web_specPage = driver.find_element_by_class_name("hide-for-small-only")

#Test: All L4 Headers
all_headers = driver.find_elements_by_tag_name('h4')
# A dict to hold all trim's and the links 
car_dict = {}
# {"Trim": "href for car specs information"}
info_dict = {}

# 1. Set the make and model variables they will not change until next iteration
#   We are making    {"Make": {Model: {Trim: {Year: { Category: xyz, Doors:2 ...}}}}}
web_title = driver.find_element_by_class_name("hero-title__header").text
# Set the year (2021/ BMW 5-Series Configurations)
year = web_title[:4]
print("Year " + year)
# Set the make (BMW 5-Series)
web_title = web_title[4:-14].strip()
# Split the Make from the Model
split_title = str.split(web_title)
if ("Alpha Romeo" in web_title or "Austin Martin" in web_title or "Land Rover" in web_title):
    make = split_title[0] + split_title[1]
    model = split_title[2]
    print("Make " + make)
    print("Model " + model)
else:
    make = split_title[0]
    model = split_title[1]
    print("Make " + make)
    print("Model " + model)
    

# Find the first space and split the item
print(" WEB TITLE: " + web_title)
# 2. Find the trim for the make and year 
# element_list is a list to hold all elements for easy extraction of name of trim and href
element_list = []
#Going through each link and checking for class
for element in all_headers:    
    element_code = element.get_attribute('innerHTML')
    #If there is an a tag, put the a tag in the element list for later use.
    if ("<a" in element_code):
        # Get the a tag element
        a_tag_element = element.find_element_by_tag_name('a')
        element_list.append(a_tag_element)


# 3. Go through the list and set the trim name and link to the trim information 
for a_tags in element_list:
    # Get the trim and href from the tag
    trim = a_tags.get_attribute('innerHTML').strip()
    html_link = a_tags.get_attribute('href')
    
    # Put them in the dict
    info_dict = {trim:html_link}
    print ("The trim is %s and the link is %s" % (trim, html_link))

# 4. go to the specs page of each trim and populate the car_dict 
for trim, href in info_dict.items:
    # Start the page.
    driver.get(href)
    #
    

# 4. So Now I got a list of all trims and need to open the href for 
# each one and get all of the vehicle information based on that car
for trim in car_dict:
    '''
    This will populate the 'car_dict' dictionary.
    This dictionary is set up to have a dict with each item below as a dict within the car_dict
    The dictionary would have these populated Make->Model->Trim->Year:{Doors:2......}
    We will get the :
        •	Doors
        •	MPG
        •	Horsepower
        •	Engine
        •	Seats
        •	Price
        •	Cylinders
        •	Torque
        •	(Convertible)
        •	(Category)
        •	(Car type [sedan, converable…])
        •	(Common Problems)
        •	(Recalls)
        •	(Description)
        •	(Drivetrain)
    car_dict = trim1: {Doors:"xyz", MPG: "xyz"...}, trim2: {Doors:"xyz", MPG: "xyz"...}
    '''
    
    car_dict[make][model][trim][year]["Doors"] = 2
    car_dict[make][model][trim][year]["MPG"] = 2
    car_dict[make][model][trim][year]["Horsepower"] = 2
    car_dict[make][model][trim][year]["Engine"] = 2
    car_dict[make][model][trim][year]["Seats"] = 2
    car_dict[make][model][trim][year]["Cylinders"] = 2
    car_dict[make][model][trim][year]["Torque"] = 2

        #web_trimName = element.text()
        #html_link = element.get_attribute('href')
       # print(" THE href is %s and the trim is % " % (html_link, web_trimName))




# web_title = driver.find_element_by_class_name("hero-title__header--overview")
# web_cost = driver.find_element_by_class_name("bpp-widget__avg-price-paid nowrap")
# web_horsepower = driver.find_element_by_id("hp")
# web_drivetrain = driver.find_element_by_id("drivetrainInfo")
# web_seats = driver.find_element_by_id("seatingInfo")

print("This is done now")

driver.close()