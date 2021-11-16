from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import requests
import shutil # save img locally
#C:\Users\luka1\AppData\Local\Programs\Python\Python39\Lib
import vehicle_scrapper_data
#Loading manually entered data for all make's, year's and models.
import car_make_year_data
#checking if the picture is already in the picture folder.
import os

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

#setting car_dict and info_dict
car_dict = vehicle_scrapper_data.car_dict
# {"Trim": "href for car specs information"}
info_dict = vehicle_scrapper_data.info_dict

# Creating the web Driver object
#driver = webdriver.Firefox()

# # driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/")
# driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test.html")

#driver.get("https://cars.usnews.com/cars-trucks/ford/focus/2014/specs")
#driver.get("file:///D:/Sheridan%202021%20Semester%205/Capstone%20Prototype/Semester5/PythonScripts/WebScrappers/test2.html")


'''
"BMW" : {
      "3-series" : {
        "320i" : {
          "2016" : {
            "Category" : "Commuter",
            "CommonProblems" : [ "Left Light Fails", "Blinker Stops Working" ],
            "Convertible" : "Both",
            "Cylinders" : 4,
            "Description" : "A commuter car that is from 2016",
            "Doors" : 4,
            "Drivetrain" : "AWD",
            "EV" : "No",
            "Engine" : 2,
            "GroundClearance" : 5,
            "Horsepower" : 210,
            "MPG" : 30,
            "Price" : 11000,
            "Recalls" : [ "R1", "R2", "R3" ],
            "Seats" : 5,
            "Torque ft-lb" : 200,
            "Weight" : 3510
          },
          "2017" : {
            "Cylinders" : 4,
            "Description" : "BMW 320 A quicker commuter car from 2016",
            "Doors" : 4,
            "Drivetrain" : "AWD",
            "EV" : "No",
            "Engine" : 2,
            "Horsepower" : 210,
            "MPG" : 28,
            "Price" : 13000,
            "Seats" : 5,
            "Torque ft-lb" : 200,
            "Weight" : 3541
          }
        },
    }
}


INFO_dict (one car model and one year)
{'BMW': {'5-Series': {'4dr Sdn 528i': {'2016': {}}, '4dr Sdn 320i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379533'},
'4dr Sdn 320i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379532'},
'4dr Sdn 328i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379804'},
'4dr Sdn 328d RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379808'},
'4dr Sdn 328d xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379809'},
'4dr Sports Wgn 328i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sports-wagon-379807'},
'4dr Sdn 330e Plug-In Hybrid RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-382983'},
'4dr Sports Wgn 328d xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sports-wagon-379810'},
'4dr Sdn 340i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379811'}, 
'4dr Sdn 340i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379813'}, 
'5dr 335i xDrive Gran Turismo AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-wagon-379766'},
'M3 4dr Sdn': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-m3-sedan-379326'}}
}}

'''


makeList = ["Acura","Alfa Romeo","Aston Martin","Audi","BMW","Buick","Cadillac","Chevrolet","Chrysler","Dodge","Ferrari",
            "FIAT","Ford","Genesis","GMC","Honda","HUMMER","Hyundai","Infiniti","Isuzu","Jaguar","Jeep","Kia",
            "Lamborghini","Land Rover","Lexus","Lincoln","Lotus","Maserati","Mazda","Mercedes-Benz","Mercury","MINI","Mitsubishi","Nissan",
            "Polestar","Pontiac","Porsche","RAM", "Saab","Saturn","Scion","Smart","Subaru","Suzuki","Tesla","Toyota","Volkswagen","Volvo"]

modelList = [car_make_year_data.AcuraModel ,car_make_year_data.AlfaRomeoModel, car_make_year_data.AstonMartinModel,car_make_year_data.AudiModel,car_make_year_data.BMWModel,car_make_year_data.BuickModel,car_make_year_data.CadillacModel,car_make_year_data.ChevroletModel,car_make_year_data.ChryslerModel,
             car_make_year_data.DodgeModel,car_make_year_data.FerrariModel,car_make_year_data.FIATModel,car_make_year_data.FordModel,car_make_year_data.GenesisModel,car_make_year_data.GMCModel,car_make_year_data.HondaModel,car_make_year_data.HummerModel, car_make_year_data.hyundiModel, car_make_year_data.infinityModel, car_make_year_data.isuzuModel,
             car_make_year_data.jaguarModel, car_make_year_data.jeepModel, car_make_year_data.kiaModel, car_make_year_data.lamboModel, car_make_year_data.landRoverModel, car_make_year_data.lexusModel, car_make_year_data.linconModel, car_make_year_data.lotusModel, car_make_year_data.mazarattiModel, car_make_year_data.mazdaModel,
             car_make_year_data.mercedesModel, car_make_year_data.mercuryModel, car_make_year_data.miniModel, car_make_year_data.mitsubishiModel, car_make_year_data.nissanModel, car_make_year_data.polestarModel, car_make_year_data.pontiacModel, car_make_year_data.porsheModel, car_make_year_data.ramModel,
             car_make_year_data.saabModel, car_make_year_data.saturnModel, car_make_year_data.scionModel, car_make_year_data.smartModel, car_make_year_data.subaruModel, car_make_year_data.suzukiModel, car_make_year_data.teslaModel, car_make_year_data.toyotaModel, car_make_year_data.volwagenModel, car_make_year_data.volvoModel
             ]
yearList = [car_make_year_data.AcuraYear,car_make_year_data.AlfaRomeoYear,car_make_year_data.AstonMartinYear,car_make_year_data.AudiYear,car_make_year_data.BMWYear,car_make_year_data.BuickYear,car_make_year_data.CadillacYear,car_make_year_data.ChevroletYear,car_make_year_data.ChryslerYear,car_make_year_data.DodgeYear,car_make_year_data.FerrariYear,car_make_year_data.FIATYear,car_make_year_data.FordYear,car_make_year_data.GenesisYear,car_make_year_data.GMCYear,car_make_year_data.HondaYear,car_make_year_data.HummerYear,car_make_year_data.hyundiYear, car_make_year_data.infinityYear, car_make_year_data.isuzuYear, car_make_year_data.jaguarYear, car_make_year_data.jeepYear,car_make_year_data.kiaYear, car_make_year_data.lamboYear, car_make_year_data.landRoverYear, car_make_year_data.lexusYear, car_make_year_data.linconYear, car_make_year_data.lotusYear, car_make_year_data.mazarattiYear, car_make_year_data.mazdaYear, car_make_year_data.mercedesYear, car_make_year_data.mercuryYear, car_make_year_data.miniYear, car_make_year_data.mitsubishiYear, car_make_year_data.nissanYear, car_make_year_data.polestarYear, car_make_year_data.pontiacYear, car_make_year_data.porsheYear, car_make_year_data.ramYear, car_make_year_data.saabYear, car_make_year_data.saturnYear, car_make_year_data.scionYear, car_make_year_data.smartYear, car_make_year_data.subaruYear, car_make_year_data.suzukiYear, car_make_year_data.teslaYear, car_make_year_data.toyotaYear, car_make_year_data.volwageYear, car_make_year_data.volvoYear]


'''
  This method is created to populate car_dict & info_dict
  This takes each make and model and puts creates a dictionary inside of each one.
  
  This function will populate the dictionary you pass in our case 'dictionary'.
  The function will create the make and models as empty key/values based on the lists above
  This function is used to create the base dictionary = {make1:{model1:{}, model2:{}...}} dictionary. 
    
  {'Acura': {'ILX': {}, 'ILX Hybrid': {}, 'MDX': {}..}, 'BMW': {'5-series':{},.....}
  
  returns dictionary
'''
def setupJson (dictionary):
  #Setting up the initial Json
  for index, make in enumerate(makeList):
      # Create the json
      dictionary[make] = {}
      
      #debug
      #print("make : %s " % make)
        
      #Go through the models of each make and add the models
      #modelList is a list that contains the list of models of each make (makeList)[acuraModels]
      for model in modelList[index]:
        #debug
        #print("model : %s " % model)
        dictionary[make][model] = {}
  return dictionary

#Setting up Dictionaries
car_dict = setupJson(car_dict)
info_dict = setupJson(info_dict)


def downloadVehiclePic(make,model,year, listOfImgTag):
  #Check if the file is already downloaded
  file_name = "vehicle_pictures/%s_%s_%s.jpg" % (make, model, year)
  if (os.path.isfile(file_name) == False):
    try:
      #img = driver.find_element_by_xpath('//div[@id="recaptcha_image"]/img')
      #seperate year, make and model as a string so we can find the picture.
      make = make.lower()
      model = model.lower()
      model = model.lower()
      make = make.replace(" ","_" )
      model = model.replace(" ","_" )
      model = model.replace("-","_")
      
      # Creating a custom header to allow a reply to my client
      headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36', "Upgrade-Insecure-Requests": "1","DNT": "1","Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8","Accept-Language": "en-US,en;q=0.5","Accept-Encoding": "gzip, deflate"}
      file_name = "vehicle_pictures/%s_%s_%s.jpg" % (make, model, year)
      
      for imgTag in listOfImgTag:
        
        url = ""
        try:
          url = imgTag.get_attribute('src')
        except:
          print("this image did not have a src")
      
        # url = "https://cars.usnews.com/pics/size/350x262/images/Auto/izmo/i2314308/2016_bmw_3_series_angularfront.jpg"
        
        # url = "https://smartcdn.prod.postmedia.digital/driving/wp-content/uploads/2021/05/chrome-image-414927.png"
        
            
        if("%s_%s_%s" % (year, make, model) in url and "images/Auto" in url):
        # if(make in url):
          #urllib.request.urlretrieve(src, "%s_%s_%s.jpg" % (year, make, model))
          res = requests.get(url, stream = True, headers=headers)
          if res.status_code == 200:
              with open(file_name,'wb') as f:
                  shutil.copyfileobj(res.raw, f) 
              print('Image sucessfully Downloaded: ',file_name)
              break
          else:
              print('Image Couldn\'t be retrieved')
          break
    except Exception as err:
      print("Your Vehicle had an issue while getting Image. \n Make:%s, Model:%s,  Year:%s \n error:%s" % (make,model,year,err))
      error_file = open("ImageError.txt", "a")
      error_file.write("Your Vehicle had an issue populating the Description.  Make:%s, Model:%s,Year:%s error:%s\n" % (make,model,year,err))
      error_file.close()
  else:
    print("The file is already populated.")
    
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
      •	(Drivetrain) 
      •	(Category) ** Added with 'Category Algorithm'  /Not in this parser.
      •	(Common Problems)** Another Parser Not in this parser.
      •	(Recalls)** Another parser /Not in this parser.

  populates the car_dict description= make:{model:{trim:{year:{Doors:"xyz", MPG: "xyz"...}}}}
  

  '''
def getVehicleDescription (make, model, trim, year, href):
  try:
    # 4. go to the specs page of each trim and populate the car_dict 
    # Start the page.
    # Creating the web Driver object
    driver = webdriver.Firefox()
    driver.get(href)
    # 4. So Now I got a list of all trims and need to open the href for 
    # each one and get all of the vehicle information based on that car
    # Get all list elements in the spec sheet.
    all_li = driver.find_elements_by_tag_name("li")
    # 5. Find all Fields listed above and set the values
    # A flag to signal if the vehicle has leather seats
    doIhaveLeatherSeats = False
    # A flag to signal if the vehicle is a convertible
    amIConvertible = False
    for item in all_li:
      item_html = item.get_attribute('innerHTML')
      #Getting the number of Doors
      # if ("Door" in item_html):
      #     car_dict[make][model][trim][year]["Doors"] = item.text
      if ("MPG" in item_html and "/" in item_html): 
          # MPG: 15 City / 21 Hwy
          split_mpg = item.text.split(":")
          split_mpg = split_mpg[1].split("/")
          # Splitting the city and higway mpg
          city_mpg = split_mpg[0].strip().split(" ")
          city_mpg = city_mpg[0]
          int_city = float(city_mpg.strip())
          highway_mpg = split_mpg[1].strip().split(" ")
          highway_mpg = highway_mpg[0]
          int_highway = float(highway_mpg.strip())
          car_dict[make][model][trim][year]["City_MPG"] = int_city
          car_dict[make][model][trim][year]["Highway_MPG"] = int_highway
      #Since this did not get the MPG all the time for some reason get it using other value just incase
      #EPA Fuel Economy Est - Hwy (MPG): 21 (2021)
      elif ("Hwy (MPG)" in item_html):
        split_item = item.text.split(":")
        split_item = split_item[1].strip().split(" ")
        mpg = split_item[0]
        int_item = float(mpg.strip())
        car_dict[make][model][trim][year]["Highway_MPG"] = int_item
      #EPA Fuel Economy Est - City (MPG): 15 (2021)
      elif ("City (MPG)" in item_html):
        split_item = item.text.split(":")
        split_item = split_item[1].strip().split(" ")
        mpg = split_item[0]
        int_item = float(mpg.strip())
        car_dict[make][model][trim][year]["City_MPG"] = int_item
      elif ("Horsepower" in item_html):
        #Horsepower (Net @ RPM): 600 @ 6000
        split_item = item.text.split(":")
        split_item = split_item[1].strip().split(" ")
        int_item = float(split_item[0].strip())
        car_dict[make][model][trim][year]["Horsepower"] = int_item
      elif ("Engine Type" in item_html):
        #Engine Type: Twin Turbo Premium Unleaded V-8
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        car_dict[make][model][trim][year]["Engine"] = split_item
      #Passenger Capacity: 5
      elif ("Passenger Capacity" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item.strip())
        car_dict[make][model][trim][year]["Seats"] = int_item
      #8 Cylinder Engine
      # Julie you can parse the first letter for the category algorithm
      elif ("Cylinders" in item_html):
        split_item = item_html.split(" ")
        car_dict[make][model][trim][year]["Cylinders"] = split_item[0]
      elif ("Torque" in item_html):
        #Torque (Net @ RPM): 553 @ 1800
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip().split(" ")
        int_item = float(split_item[0].strip())
        car_dict[make][model][trim][year]["Torque"] = int_item
      elif ("Body Style" in item_html):
        # Body Style: Sedan
        split_item = item.text.split(":")
        car_dict[make][model][trim][year]["Type"] = split_item[1]
      elif ("All Wheel Drive" in item_html):
        car_dict[make][model][trim][year]["DriveTrain"] = "AWD"
      elif ("Front Wheel Drive" in item_html):
        car_dict[make][model][trim][year]["DriveTrain"] = "FWD"
      elif ("Rear Wheel Drive" in item_html):
        car_dict[make][model][trim][year]["DriveTrain"] = "RWD"
      #Base Curb Weight (lbs.): 2456
      elif ("Base Curb Weight (lbs.): 2222" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["Weight"] = int_item
      #TODO Trunk Volume (cu. ft.): 14 OR Cargo Volume (cu. ft.): 57.5
      elif ("Trunk Volume" in item_html or "Cargo Volume" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["CargoVolume"] = int_item
      #Transmission: Manual
      #Transmission: Automatic w/OD
      elif ("Transmission:" in item_html):
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        car_dict[make][model][trim][year]["Transmission"] = split_item
      #Height, Overall (in.): 74.6
      elif ("Height" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["Height"] = int_item
      #Length, Overall (in.): 196.4
      elif ("Length" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["Length"] = int_item
      #Width, Max w/o mirrors (in.): 74.9
      elif ("Width" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["Width"] = int_item
      #Wheelbase (in.): 117.4
      elif ("Wheelbase" in item_html):
        #Converting the item into an int.
        split_item = item.text.split(":")
        split_item = split_item[1].strip()
        int_item = float(split_item)
        car_dict[make][model][trim][year]["Wheelbase"] = int_item
      #Leather Seats
      elif ("Leather Seats" in item_html):
        doIhaveLeatherSeats = True
      # Looking for Convertible
      elif ("Convertible" in item_html):
        amIConvertible = True
      #Premium Sound System (used for luxury cars)
      elif ("Premium Sound System" in item_html):
        car_dict[make][model][trim][year]["PremiumSoundSystem"] = "True"
      #Get the engine Displacement (Displacement: 1.4L/83)
      elif ("Displacement" in item_html):
        split_item = item.text.split(":")
        split_item = split_item[1].strip().split("L")
        int_item = float(split_item[0])
        car_dict[make][model][trim][year]["Displacement"] = int_item
      else:
          continue
    # Check if the car has leather seats.
    if (doIhaveLeatherSeats == True):
      car_dict[make][model][trim][year]["LeatherSeats"] = "True"
    else:
      car_dict[make][model][trim][year]["LeatherSeats"] = "False"
    #Checking if its a convertible
    if (amIConvertible == True):
      car_dict[make][model][trim][year]["Convertible"] = "True"
    else:
      car_dict[make][model][trim][year]["Convertible"] = "False"
    
    print("Got the specs for the car, updating Current_Car_Dict.txt")
    #Adding the items to a file
    write_cardict = open("Current_Car_Dict.txt", "w")
    write_cardict.write("%r" % (car_dict))
    write_cardict.close()
    # Getting pictures again
    # Getting Vehicle Images.
    listOfImgTag = driver.find_elements_by_tag_name('img')
    downloadVehiclePic(make,model,year, listOfImgTag)
    #Closing the firefox session
    driver.close()
    print("Sleep for 1 seconds")
    time.sleep(1)
  except Exception as err:
    print("Your Vehicle had an issue while populating the Description. \n Make:%s, Model:%s, Year:%s \n error:%s" % (make,model,year,err))
    error_file = open("DescriptionError.txt", "a")
    error_file.write("Your Vehicle had an issue populating the Description.  Make:%s, Model:%s, Year:%s error:%s\n" % (make,model,year,err))
    error_file.close()
    driver.close()
  

'''
  This method is used to get the list of trims from each vehicle.
  This method will also populate the avg price and msrp price of vehicles in car_dict.
  
  This function populates the info_dict and adds the trim, year for each vehicle
  This function populates the car_dict and adds the trim of each vehicle 
  Each vehicle's trim must be checked for 
    -  '.'   = !
    -  '[ ]' = ( ) 
    -  '#'   = ''
    -  '/'   = ?
    
  This function populates the info_dict more adding the hrefs to each trim depending on the year
  This function populates the car_dict more by adding the average cost and msrp into it
   
  info_dict {make:{model:{year:{urlToWebsite}}}}
  car_dict {make:{model:{year:{cost:'price', msrp:'price' }}}}

  This function returns nothing, but modifies the car_dict, info_dict and downloads a photo for each vehilce based on make,model and year.
'''
def getTrims(make, model, year):
  try:
    makeUrl = make.replace(" ", "-")
    modelUrl = model.replace(" ", "-")
    url = "https://cars.usnews.com/cars-trucks/%s/%s/%s/specs" % (makeUrl.lower(),modelUrl.lower(),year)
    # Creating the web Driver object
    driver = webdriver.Firefox()
    #Get the page.
    # driver = webdriver.Firefox()
    driver.get(url)
    
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
    
    #There is a chance that there is only msrp, but if there is not get the avg price as well
    price_spec_html = driver.find_elements_by_class_name("card__price")
    #Index to iterate through the prices
    index = 0
    # 3. Go through the list and set the trim name and link to the trim information 
    for a_tags in element_list:
        # Get the trim and href from the tag
        trim = a_tags.get_attribute('innerHTML').strip()
        html_link = a_tags.get_attribute('href')
        
        #Due to the Firebase not allowing certain characters in keys. So we have to convert them into specific characters that work.
        #Replacing periods in trim
        trim = trim.replace(".","!")
        #Replacing # in trim with ''
        trim = trim.replace("#","")
        #Replacing [ or ] with  in trim
        trim = trim.replace("[","(")
        trim = trim.replace("]",")")
        #Replace / with ? in trim
        trim = trim.replace("/","?")   
        
        #Create the trim and the specific year that we are looking at.
        if(car_dict.get(make).get(model).get(trim) is None):
          car_dict[make][model][trim] = {}
        if (car_dict.get(make).get(model).get(trim).get(year) is None):
          car_dict[make][model][trim][year] = {}
        
        if(info_dict.get(make).get(model).get(trim) is None):
          info_dict[make][model][trim] = {}
        if (info_dict.get(make).get(model).get(trim).get(year) is None):
          info_dict[make][model][trim][year] = {}
        
        # Put them in the dict to be individually 
        info_dict[make][model][trim][year] = html_link
        
        #Adding the price to the trim
        spec_text = price_spec_html[index].text
        #Avg Paid: $20,368
        if ("Avg Paid" in spec_text or "Average Price" in spec_text):
            avg_cost = int(spec_text.split(":")[1].replace(",","").replace("$","").strip())
            car_dict[make][model][trim][year]["Cost"] = avg_cost
            
            # If the average is in the output, the msrp is also there so we have to add one to the index to get MSRP of the same car. 
            index = index +1
            spec_text = price_spec_html[index].text
            #MSRP: $33,150
        if ("MSRP" in spec_text):
            msrp = int(spec_text.split(":")[1].replace(",","").replace("$","").strip())
            car_dict[make][model][trim][year]["MSRP"] = msrp
        index = index + 1
        #debug
        print ("The trim is %s and the link is %s" % (trim, html_link))
        print ("make: %s, model: %s year: %s, trim: %s \n\n" % (make,model,year,trim))
        
        # Getting Vehicle Images.
        listOfImgTag = driver.find_elements_by_tag_name('img')
        downloadVehiclePic(make,model,year, listOfImgTag)
        
    infodict_file = open("info_dict.txt", "w")
    infodict_file.write("info_dict = %r" % (info_dict))
    infodict_file.close()
    driver.close()
    
    infodict_file = open("car_dict.txt", "w")
    infodict_file.write("car_dict = %r" % (car_dict))
    infodict_file.close()
    driver.close()
    
    print("Sleep for 3 seconds")
    time.sleep(3)
  except Exception as err:
    print("Your Vehicle had an issue while getting Trim. \n Make:%s, Model:%s, Year:%s \n error:%s" % (make,model,year,err))
    error_file = open("trimError.txt", "a")
    error_file.write("Your Vehicle had an issue while getting Trim.  Make:%s, Model:%s,  Year:%s error:%s\n" % (make,model,year,err))
    error_file.close()
    driver.close()

'''
  This method is used to get all trims from the make,model and year passed in the makeList/ModelList
  This method will go through each car and get the href and key value.
  This method will populate 
    info_dict {make:{model:{year:{urlToWebsite}}}}
    car_dict {make:{model:{year:{cost:'price', msrp:'price' }}}}
'''
def get_trims_and_pictures():
  # 0.5 Create a parser to parse through the url to get the trims.
  # Uncomment this if you want to run the whole thing.
  #for index, make in enumerate(makeList):
  makeIndex = 0
  for item in range(makeIndex, len(makeList)):
      make = makeList[makeIndex]
      print(make)
      # Look through each model to find each trim and the respective url.
      # modelList = [[model1, model2,... for make 1], [model1, model2,... for make 2], ...]
      # Uncomment this if you are running the whole thing
      #for modelIndex, model in enumerate(modelList[makeIndex]):
      modelIndex = 0
      for item2 in range(modelIndex, len(modelList[makeIndex])):
        model = modelList[makeIndex][modelIndex]
        print(model)
        #Check if the car make&model is in the car_dict already.
        car_item = info_dict.get(make).get(model)
        #Check if the vehicle is already populated
        if (car_item == None or car_item == {}):
          #For each model go through each year and find each cars trims
          #yearList = [[years for model1], [years for model2]....]
          for year in yearList[makeIndex][modelIndex]:
            if (info_dict.get(make).get(model).get(year) == None or info_dict.get(make).get(model).get(year) == {}):
              print(year)
              getTrims(make, model, year)
        modelIndex = modelIndex +1
      makeIndex = makeIndex +1

### START OF MAIN FUNCTION ###
get_trims_and_pictures()
#print(info_dict)        
# Go through each href and get all of the specs for each car
try:
  for make, models in info_dict.items():
    #debug
    print("make %s" % make)
    #print("makes %s" % models)
    for model, trims in models.items():
        #debug
        print("model %s" % model)
        for trim, years in trims.items():
            #debug
            print("trim %s" % trim)
            for year, href in years.items():
              #Check if the trim is populated for the dictionary
              check_trim = car_dict.get(make).get(model).get(trim)
              if(check_trim == {} or check_trim == None):
                # print ("make: %s, model: %s year: %s,trim: %s, \nhref:%s" % (make,model,year,trim, href))
                getVehicleDescription (make, model, trim, year, href)
                print("populated info for vehicle: %s: %s: %s: %s" % (make,model,trim, year))
                # Sleep for 10 seconds before going to the next one.
                print("Sleep for 2 seconds")
                time.sleep(2)
except Exception as err:
  print("Your Vehicle had an issue while populating description. \n Make:%s, Model:%s, Trim:%s, Year:%s \n error:%s\n" % (make,model,trim,year,err))

        





