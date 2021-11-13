import os
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
import requests
import shutil # save img locally

car_dict = {'Volkswagen':{'Eos': {}, 'GLI': {}, 'Golf': {}, 'Golf SportWagen': {}, 'GTI': {}, 'ID.4': {}, 'Jetta': {}, 'Jetta Hybrid': {}, 'Jetta SportWagen': {}, 'New Beetle': {}, 'Passat': {}, 'Passat Wagon': {}, 'R32': {}, 'Rabbit': {}, 'Routan': {}, 'Taos': {}, 'Tiguan': {}, 'Touareg': {}, 'Touareg Hybrid': {}}, 
            "Toyota":{ "C-HR": {}, "Camry": {}, "Camry Hybrid": {}, "Camry Solara": {}, "Corolla": {}, "Corolla Cross": {}, "Corolla Hybrid": {}, "Corolla iM": {}, "FJ Cruiser": {}, "GR 86": {}, "Highlander": {}, "Highlander Hybrid": {}, "Land Cruiser": {}, "Matrix": {},  "Prius Prime": {}},
            "Volvo":{"S80": {}, "S90": {}, "V50": {}, "V60": {}, "V70": {}, "V90": {}, "XC40": {}, "XC60": {}, "XC70": {}, "XC90": {}},
            "Infinity":{"ex": {}, "fx": {}, "g35": {}, "g37": {}, "jx": {}, "m": {}, "m-hybrid": {}, "m40": {}, "q50": {}, "q50 hybrid": {}, "Q60": {}, "Q70": {}, "qx30": {}, "qx50": {}, "qx55": {}, "qx56": {}, "qx60": {}, "qx70": {}, "qx80": {}},
            'Smart': {'Fortwo': {}}}
info_dict = {'Volkswagen':{'Eos': {}, 'GLI': {}, 'Golf': {}, 'Golf SportWagen': {}, 'GTI': {}, 'ID.4': {}, 'Jetta': {}, 'Jetta Hybrid': {}, 'Jetta SportWagen': {}, 'New Beetle': {}, 'Passat': {}, 'Passat Wagon': {}, 'R32': {}, 'Rabbit': {}, 'Routan': {}, 'Taos': {}, 'Tiguan': {}, 'Touareg': {}, 'Touareg Hybrid': {}}, 
            "Toyota":{ "C-HR": {}, "Camry": {}, "Camry Hybrid": {}, "Camry Solara": {}, "Corolla": {}, "Corolla Cross": {}, "Corolla Hybrid": {}, "Corolla iM": {}, "FJ Cruiser": {}, "GR 86": {}, "Highlander": {}, "Highlander Hybrid": {}, "Land Cruiser": {}, "Matrix": {},  "Prius Prime": {}},
            "Volvo":{"S80": {}, "S90": {}, "V50": {}, "V60": {}, "V70": {}, "V90": {}, "XC40": {}, "XC60": {}, "XC70": {}, "XC90": {}},
            "Infinity":{"ex": {}, "fx": {}, "g35": {}, "g37": {}, "jx": {}, "m": {}, "m-hybrid": {}, "m40": {}, "q50": {}, "q50 hybrid": {}, "Q60": {}, "Q70": {}, "qx30": {}, "qx50": {}, "qx55": {}, "qx56": {}, "qx60": {}, "qx70": {}, "qx80": {}},
            'Smart': {'Fortwo': {}}}

volwagenModel = ["Eos", "GLI", "Golf", "Golf SportWagen", "GTI", "ID.4", "Jetta", "Jetta Hybrid", "Jetta SportWagen", "New Beetle", "Passat", "Passat Wagon", "R32", "Rabbit", "Routan", "Taos", "Tiguan", "Touareg", "Touareg Hybrid"]
volwageYear = [[2015,2014,2013,2012,2011,2010,2009,2008,2007], [2009], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010],  [2019,2018,2017,2016,2015], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2014,2013], [2014,2013,2012,2011,2010,2009], [2010,2009,2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2010,2009,2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2008],  [2009,2008,2007], [2012,2011,2010,2009,2008,2007], [2022], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009], [2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2014,2013,2012]]

toyotaModel = [ "C-HR", "Camry", "Camry Hybrid", "Camry Solara", "Corolla", "Corolla Cross", "Corolla Hybrid", "Corolla iM", "FJ Cruiser", "GR 86", "Highlander", "Highlander Hybrid", "Land Cruiser", "Matrix", "Prius Prime"]
toyotaYear = [[2021,2020,2019,2018], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2022], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021,2020], [2018,2017], [2014,2013,2012,2011,2010,2009,2008,2007], [2022], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2013,2012,2011,2010,2009,2008,2007], 
               [2021,2020,2019,2018,2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007], [2021,2020,2019,2018,2017]]

infinityModel = ["EX", "FX", "G35", "G37", "JX", "M", "M Hybrid", "Q40", "Q50", "Q50 Hybrid", "Q60", "Q70", "QX30", "QX50", "QX55", "QX56", "QX60", "QX70", "QX80"]
infinityYear = [[2013,2012,2011,2010,2009,2008], [2013,2012,2011,2010,2009,2008,2007], [2008,2007],  [2013,2012,2011,2010,2009,2008], [2013],  [2013,2012,2011,2010,2009,2008,2007], [2013,2012], [2015],  [2021,2020,2019,2018,2017,2016,2015,2014], [2015,2014], [2021,2020,2019,2018,2017,2016,2015,2014],  [2019,2018,2017,2016,2015,2014], [2019,2018,2017], [2021,2020,2019,2018,2017,2016,2015,2014], [2022], [2013,2012,2011,2010,2009,2008,2007], [2020,2019,2018,2017,2016,2015,2014], [2017,2016,2015,2014], [2021,2020,2019,2018,2017,2016,2015,2014], ]

smartModel = ["Fortwo"]
smartYear = [[2017,2016,2015,2014,2013,2012,2011,2010,2009,2008]]

makeList = ["Volkswagen","Volvo","Toyota","Smart","Infiniti"]

modelList = [volwagenModel,toyotaModel, infinityModel, smartModel]
yearList = [volwageYear, toyotaYear, infinityYear, smartYear]




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
      print("Your Vehicle had an issue while getting Image. \n Make:%s, Model:%s, Year:%s \n error:%s" % (make,model,year,err))
      error_file = open("ImageError.txt", "a")
      error_file.write("Your Vehicle had an issue populating the Description.  Make:%s, Model:%s, Year:%s error:%s\n" % (make,model,year,err))
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
          split_mpg = item.text.split("/")
          # Splitting the city and higway mpg
          city_mpg = split_mpg[0][5:7]
          highway_mpg = split_mpg[1][1:3]
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
      elif ("All Wheel Drive" in item_html):
          car_dict[make][model][trim][year]["DriveTrain"] = "AWD"
      elif ("Front Wheel Drive" in item_html):
          car_dict[make][model][trim][year]["DriveTrain"] = "FWD"
      elif ("Rear Wheel Drive" in item_html):
          car_dict[make][model][trim][year]["DriveTrain"] = "RWD"
      #Base Curb Weight (lbs.): 2456
      elif ("Base Curb Weight (lbs.):" in item_html):
          car_dict[make][model][trim][year]["Weight"] = item.text[25:]
      #TODO Trunk Volume (cu. ft.): 14 OR Cargo Volume (cu. ft.): 57.5
      elif ("Trunk Volume" in item_html or "Cargo Volume" in item_html):
          car_dict[make][model][trim][year]["CargoVolume"] = item.text[24:]
      #Transmission: Manual
      #Transmission: Automatic w/OD
      elif ("Transmission:" in item_html):
          car_dict[make][model][trim][year]["Transmission"] = item.text[14:]
      #Height, Overall (in.): 74.6
      elif ("Height" in item_html):
          car_dict[make][model][trim][year]["Height"] = item.text[23:]
      #Length, Overall (in.): 196.4
      elif ("Length" in item_html):
          car_dict[make][model][trim][year]["Length"] = item.text[23:]
      #Width, Max w/o mirrors (in.): 74.9
      elif ("Width" in item_html):
          car_dict[make][model][trim][year]["Width"] = item.text[30:]
      #Wheelbase (in.): 117.4
      elif ("Wheelbase" in item_html):
        car_dict[make][model][trim][year]["Wheelbase"] = item.text[17:]
      #Leather Seats
      elif ("Leather Seats" in item_html):
        doIhaveLeatherSeats = True
      # Looking for Convertible
      elif ("Convertible" in item_html):
        amIConvertible = True
      #Premium Sound System (used for luxury cars)
      elif ("Premium Sound System" in item_html):
        car_dict[make][model][trim][year]["PremiumSoundSystem"] = "True"
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
    print("Your Vehicle had an issue while populating the Description. \n Make:%s, Model:%s, Trim:%s, Year:%s \n error:%s" % (make,model,trim,year,err))
    error_file = open("DescriptionError.txt", "a")
    error_file.write("Your Vehicle had an issue populating the Description.  Make:%s, Model:%s, Trim:%s, Year:%s error:%s\n" % (make,model,trim,year,err))
    error_file.close()
    driver.close()

  
  

'''
  This method is used to get the list of trims from each vehicle.
  This method will also populate the avg price and msrp price of vehicles in car_dict.
  
  This function populates the car_dict and adds the trim of each vehicle
  This function populates the info_dict and adds the hrefs to each trim.
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
        
        #Create the trim and the specific year that we are looking at.
        car_dict[make][model][trim] = {}
        car_dict[make][model][trim][year] = {}
        
        info_dict[make][model][trim] = {}
        info_dict[make][model][trim][year] = {}
        
        # Put them in the dict to be individually 
        info_dict[make][model][trim][year] = html_link
        
        #Adding the price to the trim
        spec_text = price_spec_html[index].text
        #Avg Paid: $20,368
        if ("Avg Paid" in spec_text):
            avg_cost = spec_text[10:]
            car_dict[make][model][trim][year]["Cost"] = avg_cost
            # If the average is in the output, the msrp is also there so we have to add one to the index to get MSRP of the same car. 
            index = index +1
            spec_text = price_spec_html[index].text
            #MSRP: $33,150
        if ("MSRP" in spec_text):
            msrp = spec_text[6:]
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
    print("Sleep for 3 seconds")
    time.sleep(3)
  except Exception as err:
    print("Your Vehicle had an issue while getting Trim. \n Make:%s, Model:%s, Trim:%s, Year:%s \n error:%s" % (make,model,trim,year,err))
    error_file = open("trimError.txt", "a")
    error_file.write("Your Vehicle had an issue while getting Trim.  Make:%s, Model:%s, Trim:%s, Year:%s error:%s\n" % (make,model,trim,year,err))
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
        car_item = car_dict.get(make).get(model)
        #Check if the vehicle is already populated
        if (car_item == None or car_item == {}):
          #For each model go through each year and find each cars specs
          #yearList = [[years for model1], [years for model2]....]
          # Uncomment this if you are running the whole thing
          #for modelYears in yearList[index]:
          for year in yearList[makeIndex][modelIndex]:
            # if (car_dict.get(make).get(model).get(year) == None or car_dict.get(make).get(model).get(year) == {}):
              print(year)
              #Go through each page and get the make model and year
              getTrims(make, model, year)
              time.sleep(2)
              #debug
              #print(car_dict)
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
