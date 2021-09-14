from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

driver = webdriver.Firefox()
# driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/")
# #driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test.html")

# A dict to hold all trim's and the links 
car_dict = {}
# {"Trim": "href for car specs information"}
info_dict = {}
# The make of the vehicle
make = ""
# The Model of the vehicle
model = ""
# The Year of the vehicle
year = ""
# The Trim of the vehicle
trim = ""

#driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/specs")
driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test2.html")


# Go through each make and go through the list one by one until you get all vehicle models, makes, and trims information

# List of all makes in selector.
# make_selector = driver.find_element_by_class_name("auto-finder__select--make")
# make_options = make_selector.find_elements_by_tag_name("option")


# List of all models

# List of all years



# 1. Set the make and model variables they will not change until next iteration
#   We are making    {"Make": {Model: {Trim: {Year: { Category: xyz, Doors:2 ...}}}}}
web_title = driver.find_element_by_class_name("hero-title__header").text
# Set the year (2021/ BMW 5-Series Configurations)
year = web_title[:4]
print("Year " + year)
# Set the make (BMW 5-Series)
web_title = web_title[4:-14].strip()
# Split the Make from the Model
split_title = web_title.split()
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

#Once you get the specific make, model and year
# 2. Find the trim for the make and year 
# The Links are in <h4> elements
#Test: All L4 Headers
all_headers = driver.find_elements_by_tag_name('h4')


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
    info_dict[trim] = html_link
    #debug
    print ("The trim is %s and the link is %s" % (trim, html_link))
    print ("make: %s, model: %s year: %s, trim: %s" % (make,model,year,trim))
#debug  
print ("InfoDict: %s" % info_dict)

#Get the Transmission and Drivetrain from the /specs page
# Get all list elements in the spec sheet.
#all_li = driver.find_elements_by_tag_name("li")
all_spec_item = driver.find_elements_by_class_name("card__spec-item")
for spec, trim in zip(all_spec_item, info_dict.keys()):
    spec_text = spec.text
    print(spec_text)
    print ("make: %s, model: %s year: %s, trim: %s" % (make,model,year,trim))
    #Transmission: Manual
    if ("Transmission" in spec_text):
        car_dict[make][model][trim][year]["Transmission"] = spec_text[14:]
    elif ("Drivetrain" in spec_text):
        #Drivetrain: Front Wheel Drive 
        car_dict[make][model][trim][year]["Drivetrain"] = spec_text[12:]
print(car_dict)
    

#close the page
driver.close()

def test ():
    time.sleep(5)
    # 4. go to the specs page of each trim and populate the car_dict 
    for trim, href in info_dict.items:
        # Start the page.
        driver.get(href)
        # 4. So Now I got a list of all trims and need to open the href for 
        # each one and get all of the vehicle information based on that car
        '''
        This will populate the 'car_dict' dictionary.
        This dictionary is set up to have a dict with each item below as a dict within the car_dict
        The dictionary would have these populated Make->Model->Trim->Year:{Doors:2......}
        We will get the :
            •	Doors ** Not in this parser.
            •	MPG
            •	Horsepower
            •	Engine
            •	Seats
            •	Price
            •	Cylinders
            •	Torque
            •	Transmission
            •	Torque
            •	(Car type [sedan, converable…])
            •	TODO (Description) ** Need to go to review page to get this 
            •	TODO (Drivetrain) 
            •	(Category) ** Added with 'Category Algorithm'
            •	(Common Problems)** Another Parser
            •	(Recalls)** Another parser

        car_dict = trim1: {Doors:"xyz", MPG: "xyz"...}, trim2: {Doors:"xyz", MPG: "xyz"...}
        '''
        # Get all list elements in the spec sheet.
        all_li = driver.find_elements_by_tag_name("li")
        # 5. Find all Fields listed above and set the values
        for item in all_li:
            item_html = item.get_attribute('innerHTML')
            #Getting the number of Doors
            if ("Door" in item_html):
                car_dict[make][model][trim][year]["Doors"] = item.text
            elif ("MPG" in item_html): 
                # MPG: 15 City / 21 Hwy
                split_mpg = item.text.split("/").strip()
                # Splitting the city and higway mpg
                city_mpg = split_mpg[0][0:2]
                highway_mpg = split_mpg[1][0:2]
                car_dict[make][model][trim][year]["City_MPG"] = city_mpg
                car_dict[make][model][trim][year]["Highway_MPG"] = highway_mpg
            elif ("Horsepower" in item_html):
                #Horsepower (Net @ RPM): 600 @ 6000
                car_dict[make][model][trim][year]["Horsepower"] = item.text[22:-7]
            elif ("Engine Type" in item_html):
                #Engine Type: Twin Turbo Premium Unleaded V-8
                car_dict[make][model][trim][year]["Engine"] = item.text[13:]
            elif ("Passenger Capacity" in item_html):
                car_dict[make][model][trim][year]["Seats"] = item.text[20:]
            elif ("Cylinders" in item_html):
                car_dict[make][model][trim][year]["Cylinders"] = item.text[:1]
            elif ("Torque" in item_html):
                #Torque (Net @ RPM): 553 @ 1800
                car_dict[make][model][trim][year]["Torque"] = item.text[18:-7]
            elif ("Body Style" in item_html):
                # Body Style: Sedan
                car_dict[make][model][trim][year]["Type"] = item.text[12:]
            else:
                continue
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