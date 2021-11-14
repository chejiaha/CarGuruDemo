from requests.api import head
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time
#https://www.scrapingbee.com/blog/download-image-python/
import requests # request img from web
import shutil # save img locally

#import vehicle_scrapper_data
#Loading manually entered data for all make's, year's and models.
import car_make_year_data

# # The list of makes
# makeList = ["Acura","Alfa Romeo","Aston Martin","Audi","BMW","Buick","Cadillac","Chevrolet","Chrysler",
#             "Dodge","Ferrari","FIAT","Ford","Genesis","GMC","Honda","HUMMER","Hyundai","Infiniti","Isuzu",
#             "Jaguar","Jeep","Kia","Lamborghini","Land Rover","Lexus","Lincoln","Lotus","Maserati","Mazda",
#             "Mercedes-Benz","Mercury","MINI","Mitsubishi","Nissan","Polestar","Pontiac","Porsche","RAM",
#             "Saab","Saturn","Scion","Smart","Subaru","Suzuki","Tesla","Toyota","Volkswagen","Volvo"]

# Unmodified list of vehicles
# modelList = [car_make_year_data.AcuraModel ,car_make_year_data.AlfaRomeoModel, car_make_year_data.AstonMartinModel,car_make_year_data.AudiModel,car_make_year_data.BMWModel,car_make_year_data.BuickModel,car_make_year_data.CadillacModel,car_make_year_data.ChevroletModel,car_make_year_data.ChryslerModel,car_make_year_data.DodgeModel,car_make_year_data.FerrariModel,car_make_year_data.FIATModel,car_make_year_data.FordModel,car_make_year_data.GenesisModel,car_make_year_data.GMCModel,car_make_year_data.HondaModel,car_make_year_data.HummerModel, car_make_year_data.hyundiModel, car_make_year_data.infinityModel, car_make_year_data.isuzuModel, car_make_year_data.jaguarModel, car_make_year_data.jeepModel, car_make_year_data.kiaModel, car_make_year_data.lamboModel, car_make_year_data.landRoverModel, car_make_year_data.lexusModel, car_make_year_data.linconModel, car_make_year_data.lotusModel, car_make_year_data.mazarattiModel, car_make_year_data.mazdaModel, car_make_year_data.mercedesModel, car_make_year_data.mercuryModel, car_make_year_data.miniModel, car_make_year_data.mitsubishiModel, car_make_year_data.nissanModel, car_make_year_data.polestarModel, car_make_year_data.pontiacModel, car_make_year_data.porsheModel, car_make_year_data.ramModel, car_make_year_data.saabModel, car_make_year_data.saturnModel, car_make_year_data.scionModel, car_make_year_data.subaruModel, car_make_year_data.suzukiModel, car_make_year_data.teslaModel, car_make_year_data.toyotaModel, car_make_year_data.volwagenModel, car_make_year_data.volvoModel ]
# yearList = [car_make_year_data.AcuraYear,car_make_year_data.AlfaRomeoYear,car_make_year_data.AstonMartinYear,car_make_year_data.AudiYear,car_make_year_data.BMWYear,car_make_year_data.BuickYear,car_make_year_data.CadillacYear,car_make_year_data.ChevroletYear,car_make_year_data.ChryslerYear,car_make_year_data.DodgeYear,car_make_year_data.FerrariYear,car_make_year_data.FIATYear,car_make_year_data.FordYear,car_make_year_data.GenesisYear,car_make_year_data.GMCYear,car_make_year_data.HondaYear,car_make_year_data.HummerYear,car_make_year_data.hyundiYear, car_make_year_data.infinityYear, car_make_year_data.isuzuYear, car_make_year_data.jaguarYear, car_make_year_data.jeepYear,car_make_year_data.kiaYear, car_make_year_data.lamboYear, car_make_year_data.landRoverYear, car_make_year_data.lexusYear, car_make_year_data.linconYear, car_make_year_data.lotusYear, car_make_year_data.mazarattiYear, car_make_year_data.mazdaYear, car_make_year_data.mercedesYear, car_make_year_data.mercuryYear, car_make_year_data.miniYear, car_make_year_data.mitsubishiYear, car_make_year_data.nissanYear, car_make_year_data.polestarYear, car_make_year_data.pontiacYear, car_make_year_data.porsheYear, car_make_year_data.ramYear, car_make_year_data.saabYear, car_make_year_data.saturnYear, car_make_year_data.scionYear, car_make_year_data.subaruYear, car_make_year_data.suzukiYear, car_make_year_data.teslaYear, car_make_year_data.toyotaYear, car_make_year_data.volwageYear, car_make_year_data.volvoYear]


makeList = ["Alfa Romeo","Aston Martin",]

modelList = [car_make_year_data.AlfaRomeoModel, car_make_year_data.AstonMartinModel]
yearList = [car_make_year_data.AlfaRomeoYear,car_make_year_data.AstonMartinYear]



### START OF MAIN FUNCTION ###
# Variable to append to the name each time a new picture is added.
print(len(makeList))
print(len(modelList))
print(len(yearList))

car_dict = {}
info_dict = {}


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

car_dict = setupJson(car_dict)
info_dict = setupJson(info_dict)


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
    print("Sleep for 3 seconds")
    time.sleep(3)
  except Exception as err:
    print("Your Vehicle had an issue while getting Trim. \n Make:%s, Model:%s, Trim:%s, Year:%s \n error:%s" % (make,model,trim,year,err))
    error_file = open("trimError.txt", "a")
    error_file.write("Your Vehicle had an issue while getting Trim.  Make:%s, Model:%s, Trim:%s, Year:%s error:%s\n" % (make,model,trim,year,err))
    error_file.close()
    driver.close()

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


# 0.5 Create a parser to parse through the url to get the trims.
# # for makeIndex, make in enumerate(makeList):
# makeIndex = 17
# for item in range(makeIndex) :
#   make = makeList[makeIndex]
#   # Look through each model to find each trim and the respective url.
#   # modelList = [[model1, model2,... for make 1], [model1, model2,... for make 2], ...]
#   for modelIndex, model in enumerate(modelList[makeIndex]):
#     #For each model go through each year and find each cars specs
#     #Replace spaces with _ in all models 
    
#     print("next model")
#     #yearList = [[years for model1], [years for model2]....]
#     for modelYears in yearList[makeIndex][modelIndex]:
#         print("YEARSList Make: %s, Model:%s, year %s" % (make.lower(),model.lower(),modelYears))
#         #for year in modelYears:
#             # print("modelYEARS Make: %s, Model:%s, Year %s"% (make.lower(),model.lower(),year))
#             #Go through each page and get the make model and year
#             #picNum = getTrims(make, model, year, picNum)
#         print("sleep for 5 seconds...")
#             #time.sleep(5)
#             #debug
#             #print(car_dict)

#   makeIndex = makeIndex +1
# driver = webdriver.Firefox()

# # 4. So Now I got a list of all trims and need to open the href for 
# # each one and get all of the vehicle information based on that car
# '''
# This will populate the 'car_dict' dictionary.
# This dictionary is set up to have a dict with each item below as a dict within the car_dict
# The dictionary would have these populated Make->Model->Trim->Year:{Doors:2......}
# We will get the :
#     •	Doors ** Not in this parser.
#     •	MPG
#     •	Horsepower
#     •	Engine
#     •	Seats
#     •	Price
#     •	Cylinders
#     •	Torque
#     •	Transmission
#     •	Torque
#     •	(Car type [sedan, converable…])
#     •	TODO (Description) ** Need to go to review page to get this 
#     •	(Drivetrain) 
#     •	(Category) ** Added with 'Category Algorithm'  /Not in this parser.
#     •	(Common Problems)** Another Parser Not in this parser.
#     •	(Recalls)** Another parser /Not in this parser.

# car_dict = trim1: {Doors:"xyz", MPG: "xyz"...}, trim2: {Doors:"xyz", MPG: "xyz"...}
# '''

# car_dict = {'Acura': {'ILX': {}, 'ILX Hybrid': {}, 'MDX': {}, 'MDX Hybrid': {}, 'NSX': {}, 'RDX': {}, 'RL': {}, 'RLX': {}, 'TL': {}, 'TLX': {}, 'TSX': {}, 'TSX Sport Wagon': {}, 'ZDX': {}}, 'Alfa Romeo': {'4C': {}, 'Giulia': {}, 'Stelvio': {}}, 'Aston Martin': {'DB9': {}, 'DBS': {}, 'Vantage': {}}, 'Audi': {'A3': {}, 'A4': {}, 'A4 Allroad': {}, 'A4 Wagon': {}, 'A5': {}, 'A6': {}, 'A6 Allroad': {}, 'A6':{}, 'Wagon': {}, 'A7': {}, 'A8': {}, 'Allroad': {}, 'e-tron': {}, 'e-tron GT': {}, 'Q3': {}, 'Q5': {}, 'Q7': {}, 'Q8': {}, 'R8': {}, 'TT': {}}, 'BMW': {'1-Series': {}, '2-Series': {}, '3-Series': {}, '3-Series Hybrid': {}, '3-Series Wagon': {}, '4-Series': {}, '5-Series': {}, '5-Series Hybrid': {}, '5-Series Wagon': {}, '6-Series': {}, '7-Series': {}, '7-Series Hybrid': {}, '8-Series': {}, 'i3': {}, 'X1': {}, 'X2': {}, 'X3': {}, 'X4': {}, 'X5': {}, 'X6': {}, 'X7': {}, 'Z4': {}}, 'Buick': {'Cascada': {}, 'Enclave': {}, 'Encore': {}, 'Encore GX': {}, 'Envision': {}, 'LaCrosse': {}, 'Lucerne': {}, 'Rainier': {}, 'Regal': {}, 'Rendezvous': {}, 'Verano': {}}, 'Cadillac': {'ATS': {}, 'CT4': {}, 'CT5': {}, 'CT6': {}, 'CTS': {}, 'CTS Sport Wagon': {}, 'DTS': {}, 'ELR': {}, 'Escalade': {}, 'Escalade Hybrid': {}, 'SRX': {}, 'STS': {}, 
# 'XLR': {}, 'XT4': {}, 'XT5': {}, 'XT6': {}, 'XTS': {}}, 'Chevrolet': {'Avalanche': {}, 'Aveo': {}, 'Blazer': {}, 'Bolt': {}, 'Bolt EUV': {}, 'Camaro': {}, 'Cobalt': {}, 'Colorado': {}, 'Corvette': {}, 'Cruze': {}, 'Equinox': {}, 'Express': {}, 'HHR': {}, 'Impala': {}, 'Malibu': {}, 'Malibu Hybrid': {}, 'Malibu Maxx': {}, 'Monte Carlo': {}, 'Silverado 1500': {}, 'Silverado 1500 Hybrid': {}, 'Silverado HD': {}, 'Sonic': {}, 'Spark': {}, 'SS': {}, 'Suburban': {}, 'Tahoe': {}, 'Tahoe Hybrid': {}, 'TrailBlazer': {}, 'Traverse': {}, 'Trax': {}, 'Uplander': {}, 'Volt': {}}, 'Chrysler': {'200': {}, '300': {}, 'Aspen': {}, 'Crossfire': {}, 'Pacifica': {}, 'Pacifica Hybrid': {}, 'PT Cruiser': {}, 'Sebring': {}, 'Town & Country': {}, 'Voyager': {}}, 'Dodge': {'Avenger': {}, 'Caliber': {}, 'Caravan': {}, 'Challenger': {}, 'Charger': {}, 'Dakota': {}, 'Dart': {}, 'Durango': {}, 'Grand Caravan': {}, 'Journey': {}, 'Magnum': {}, 'Nitro': {}, 'Ram 1500': {}, 'Ram HD': {}, 'Sprinter': {}, 'SRT Viper': {}, 'Viper': {}}, 'Ferrari': {'599 GTB Fiorano': {}, '612 Scaglietti': {}, 'California': {}, 'F430': {}, 'F458 Italia': {}}, 'FIAT': {'124 Spider': {}, '500': {}, '500L': {}, '500X': {}}, 'Ford': {'Bronco': {}, 'Bronco Sport': {}, 'C-Max Energi': {}, 'C-Max Hybrid': {}, 'Crown Victoria': {}, 'E-Series': {}, 'Econoline': {}, 'EcoSport': {}, 'Edge': {}, 'Escape': {}, 'Escape Hybrid': {}, 'Expedition': {}, 'Explorer': {}, 'Explorer Hybrid': {}, 'Explorer':{}, 
# 'Sport Trac': {}, 'F-150': {}, 'Fiesta': {}, 'Flex': {}, 'Focus': {}, 'Focus Electric': {}, 'Fusion': {}, 'Fusion Energi': {}, 'Fusion Hubrid': {}, 'Maverick': {}, 'Mustang': {}, 'Mustang Mach-E': {}, 'Ranger': {}, 'Super Duty': {}, 'Taurus': {}, 'Taurus X': {}, 'Transit Connect': {}}, 'Genesis': {'G70': {}, 'G80': {}, 'G90': {}, 'GV70': {}, 'GV80': {}}, 'GMC': {'Acadia': {}, 'Canyon': {}, 'Envoy': {}, 'Savana': {}, 'Sierra 1500': {}, 'Sierra 1500 Hybrid': {}, 'Sierra HD': {}, 'Terrain': {}, 'Yukon': {}, 'Yukon Hybrid': {}}, 'Honda': {'Accord': {}, 'Accord Hybrid': {}, 'Accord Plug-in': {}, 'Civic': {}, 'Civic Hybrid': {}, 'Clarity': {}, 'CR-V': {}, 'CR-V Hybrid': {}, 'CR-Z': {}, 'Crosstour': {}, 'Element': {}, 'Fit': {}, 'HR-V': {}, 'Insight': {}, 'Odyssey': {}, 'Passport': {}, 'Pilot': {}, 'Ridgeline': {}, 'S2000': {}}, 'HUMMER': {'H2': {}, 'H2 SUT': {}, 'H3': {}, 'H3T': {}}, 'Hyundai': {'C30': {}, 'C70': {}, 'S40': {}, 'S60': {}, 'S80': {}, 'S90': {}, 'V50': {}, 'V60': {}, 'V70': {}, 'V90': {}, 'XC40': {}, 'XC60': {}, 'XC70': {}, 'XC90': {}}, 'Infiniti': {'Arteon': {}, 'Atlas': {}, 'Beetle': {}, 'CC': {}, 'Eos': {}, 'GLI': {}, 'Golf': {}, 'Golf SportWagen': {}, 'GTI': {}, 'ID.4': {}, 'Jetta': {}, 'Jetta Hybrid': {}, 'Jetta SportWagen': {}, 'New Beetle': {}, 'Passat': {}, 'Passat Wagon': {}, 'R32': {}, 'Rabbit': {}, 'Routan': {}, 'Taos': {}, 'Tiguan': {}, 'Touareg': {}, 'Touareg Hybrid': {}}, 'Isuzu': {'4Runner': {}, '86': {}, 'Avalon': {}, 'Avalon Hybrid': {}, 'C-HR': {}, 'Camry': {}, 'Camry Hybrid': {}, 'Camry Solara': {}, 'Corolla': {}, 'Corolla Cross': {}, 'Corolla Hybrid': {}, 'Corolla iM': {}, 'FJ Cruiser': {}, 'GR 86': {}, 'Highlander': {}, 'Highlander Hybrid': {}, 'Land Cruiser': {}, 'Matrix': {}, 'Prius': {}, 'Prius c': {}, 'Prius Plug-in': {}, 'Prius Prime': {}, 'Prius V': {}, 'RAV4': {}, 'RAV4 Hybrid': {}, 'RAV4 Prime': {}, 'Sienna': {}, 'Supra': {}, 'Tacoma': {}, 'Tundra': {}, 'Venza': {}, 'Yaris': {}, 'Yaris iA': {}}, 'Jaguar': {'Model 3': {}, 'Model S': {}, 'Model X': {}, 'Model Y': {}, 'Roadster': {}}, 'Jeep': {'Aerio': {}, 'Equator': {}, 'Grand Vitara': {}, 'Kizashi': {}, 'Reno': {}, 'SX4': {}, 'SX4 Wagon': {}, 'XL7': {}}, 'Kia': {'Ascent': {}, 'BRZ': {}, 'Crosstrek': {}, 'Crosstrek Hybrid': {}, 'Forester': {}, 'Impreza': {}, 'Impreza Wagon': {}, 'Legacy': {}, 'Outback': {}, 'Tribeca': {}, 'WRX': {}, 'XV Crosstrek': {}, 'XV Crosstrek Hybrid': {}}, 'Lamborghini': {'Fortwo': {}}, 'Land Rover': {'FR-S': {}, 'iA': {}, 'iM': {}, 'iQ': {}, 'tC': {}, 'xB': {}, 'xD': {}, '': {}}, 'Lexus': {'Astra': {}, 'Aura': {}, 'Aura Hybrid': {}, 'Ion': {}, 'Outlook': {}, 'Sky': {}, 'VUE': {}, 'VUE Hybrid': {}}, 'Lincoln': {'9-3': {}, '9-3 Wagon': {}, '9-4X': {}, '9-5': {}, '9-5 Wagon': {}, '9-7X': {}}, 'Lotus': {'1500': {}, 'Dakota': {}, 'HD': {}}, 'Maserati': {'911': {}, '911-GT3': {}, '911-Turbo': {}, 'Boxster': {}, 'Cayenne': {}, 'Cayenne Hybrid': {}, 'Cayman': {}, 'Macan': {}, 'Panamera': {}, 'Taycan': {}}, 'Mazda': {'G3': {}, 'G5': {}, 'G6': {}, 'G8': {}, 'G8-GXP': {}, 'Grand Prix': {}, 'Solstice': {}, 'Torrent': {}, 'Vibe': {}}, 'Mercedes-Benz': {'2': {}}, 'Mercury': {'350Z': {}, '370Z': {}, 'Altima': {}, 'Altima Hybrid': {}, 'Armada': {}, 'Cube': {}, 'Frontier': {}, 'GT-R': {}, 'Juke': {}, 'Kicks': {}, 'Leaf': {}, 'Maxima': {}, 'Murano': {}, 'NV': {}, 'Pathfinder': {}, 'Pathfinder Hybrid': {}, 'Quest': {}, 'Rogue': {}, 'Rogue Hybrid': {}, 'Rogue Sport': {}, 'Sentra': {}, 'Titan': {}, 'Versa': {}, 'Xterra': {}}, 'MINI': {'Eclipse': {}, 'Eclipse Cross': {}, 'Endeavor': {}, 'Galant': {}, 'i': {}, 'Lancer': {}, 'Mirage': {}, 'Outlander': {}, 'Outlander Sport': {}, 'Raider': {}}, 'Mitsubishi': {'Cooper': {}, 'Cooper Clubman': {}, 'Cooper Countryman': {}, 'Cooper CoupeCooper Paceman': {}, 'Cooper Roadster': {}, 'Electric Hardtop': {}}, 'Nissan': {'Grand Marquis': {}, 'Mariner': {}, 'Mariner Hybrid': {}, 'Milan': {}, 'Milan Hybrid': {}, 'Montego': {}, 'Mountaineer': {}, 'Sable': {}}, 'Polestar': {'A-Class': {}, 'C-Class': {}, 'CL-Class': {}, 'CLA-Class': {}, 'CLK-Class': {}, 'CLS-Class': {}, 'E-Class': {}, 'E-Class Coupe': {}, 'E-Class Wagon': {}, 'G-Class': {}, 'GL-Class': {}, 'GLA-Class': {}, 'GLB-Class': {}, 'GLC-Class': {}, 'GLE-Class': {}, 'GLK-Class': {}, 'GLS-Class': {}}, 'Pontiac': {'B-Series': {}, 'CX-3': {}, 'CX-30': {}, 'CX-5': {}, 'CX-7': {}, 'CX-9': {}, 'Mazda2': {}, 'Mazda3': {}, 'Mazda5': {}, 'Mazda6': {}, 'Mazda6 Wagon': {}, 'Mazdaspeed3': {}, 'MX-5 Miata': {}, 'RX-8': {}, 'Tribute': {}}, 'Porsche': {'GranTurismo': {}, 'Levante': {}, 'Quattroporte': {}}, 'RAM': {'Elise': {}, 'Exige': {}}, 'Saab': {'Aviator': {}, 'Continental': {}, 'Corsair': {}, 'Mark LT': {}, 'MKC': {}, 'MKS': {}, 'MKT': {}, 'MKX': {}, 'MKZ': {}, 'MKZ Hybrid': {}, 'Nautilus': {}, 'Navigator': {}, 'Town Car': {}}, 'Saturn': {'CT Hybrid': {}, 'ES': {}, 'ES Hybrid': {}, 'GS': {}, 'GS Hybrid': {}, 'HX': {}, 'HS': {}, 'IS': {}, 'IS-F': {}, 'LC': {}, 'LFA': {}, 'LS': {}, 'LS Hybrid': {}, 'LX': {}, 'NX': {}, 'NX Hybrid': {}, 'RC': {}, 'RX 350': {}, 'RX Hybrid': {}, 'SC': {}, 'UX': {}, 'UX Hybrid': {}}, 'Scion': {'Defender': {}, 'Discovery': {}, 'Discovery Sport': {}, 'LR2': {}, 'LR3': {}, 'LR4': {}, 'Range Rover': {}, 'Range Rover Evoque': {}, 'Range Rover Sport': {}, 'Range Rover Velar': {}}, 'Smart': {'Gallardo': {}, 'Murcielago': {}}, 'Subaru': {'Amanti': {}, 'Borrego': {}, 'Cadenza': {}, 'Carnival': {}, 'Forte': {}, 'KS': {}, 'K900': {}, 'Niro': {}, 'Optima': {}, 'Optima Hybrid': {}, 'Rio': {}, 'Rio5': {}, 'Ronda': {}, 'Sedona': {}, 'Seltos': {}, 'Sorento': {}, 'Sorento Hybrid': {}, 'Soul': {}, 'Spectra': {}, 'Spectra5': {}, 'Sportage': {}, 'Stinger': {}, 'Telluride': {}}, 'Suzuki': {'Cherokee': {}, 'Commander': {}, 'Compass': {}, 'Gladiator': {}, 'Grand Cherokee': {}, 'Grand Cherokee L': {}, 'Grand Wagoneer': {}, 'Liberty': {}, 'Patriot': {}, 'Renegade': {}, 'Wagoneer': {}, 'Wrangler': {}}, 'Tesla': {'E-Pace': {}, 'F-Pace': {}, 'F-Type': {}, 'I-Pace': {}, 'S-Type': {}, 'X-Type': {}, 'X-Type Wagon': {}, 'XE': {}, 'XF': {}, 'XJ': {}, 'XJR': {}, 'XK': {}, 'XKR': {}}, 'Toyota': {'Ascender': {}, 'i-290': {}, 'i-370': {}}, 'Volkswagen': {'EX': {}, 'FX': {}, 'G35': {}, 'G37': {}, 'JX': {}, 'M': {}, 'M Hybrid': {}, 'Q40': {}, 'Q50': {}, 'Q50 Hybrid': {}, 'Q60': {}, 'Q70': {}, 'QX30': {}, 'QX50': {}, 'QX55': {}, 'QX56': {}, 'QX60': {}, 'QX70': {}, 'QX80': {}}, 'Volvo': {'Accent': {}, 'Azera': {}, 'Elantra': {}, 'Elantra Hybrid': {}, 'Elantra Touring': {}, 'Entourage': {}, 'Equus': {}, 'Genesis': {}, 'Genesis Coupe': {}, 'Ioniq': {}, 'Kona': {}, 'Kona EV': {}, 'Palisade': {}, 'Santa Cruz': {}, 'Santa Fe': {}, 'Santa Fe Hybrid': {}, 'Sonata': {}, 'Sonata Hybrid': {},
# 'Tiburon': {}, 'Tucson': {}, 'Tucson Hybrid': {}, 'Veloster': {}, 'Venue': {}, 'Veracruz': {}}}

# info_dict = {"BMW": {"5-Series":{"4dr Sdn 528i":{"2016":{}}}}}
# car_dict = {"BMW": {"5-Series":{"4dr Sdn 528i":{"2016":{}}}}}
# make = "BMW"
# model = "3-Series"
# year = "2016"

# # Variable to add to each time a new picture is added.
# picNum = 1
# def downloadVehiclePic(make,model,year, listOfImgTag):
#   #img = driver.find_element_by_xpath('//div[@id="recaptcha_image"]/img')
#   #seperate year, make and model as a string so we can find the picture.
#   make = make.lower()
#   model = model.lower()
#   model = model.lower()
#   make = make.replace(" ","_" )
#   model = model.replace(" ","_" )
#   model = model.replace("-","_")
  
#   # Creating a custom header to allow a reply to my client
#   headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36', "Upgrade-Insecure-Requests": "1","DNT": "1","Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8","Accept-Language": "en-US,en;q=0.5","Accept-Encoding": "gzip, deflate"}
#   file_name = "vehicle_pictures/%s_%s_%s.jpg" % (year, make, model)
  
#   for imgTag in listOfImgTag:
#     url = ""
#     try:
#       url = imgTag.get_attribute('src')
#     except:
#       print("this image did not have a src")
  
#     # url = "https://cars.usnews.com/pics/size/350x262/images/Auto/izmo/i2314308/2016_bmw_3_series_angularfront.jpg"
    
#     # url = "https://smartcdn.prod.postmedia.digital/driving/wp-content/uploads/2021/05/chrome-image-414927.png"
    
        
#     if("%s_%s_%s" % (year, make, model) in url):
#     # if(make in url):
#       #urllib.request.urlretrieve(src, "%s_%s_%s.jpg" % (year, make, model))
#       res = requests.get(url, stream = True, headers=headers)
#       if res.status_code == 200:
#           with open(file_name,'wb') as f:
#               shutil.copyfileobj(res.raw, f) 
#           print('Image sucessfully Downloaded: ',file_name)
#           picNum + 1
#       else:
#           print('Image Couldn\'t be retrieved')
#       break

# # #href = "https://cars.usnews.com/cars-trucks/%s/%s/%s/specs/" % (make.lower(), model.lower(), year)
# href = "http://127.0.0.1:5500/testHtml.html"
# # #href = "http://127.0.0.1:5500/testHtml2.html"
# # #specHref = "/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379533"

# driver.get(href)

# listOfImgTag = driver.find_elements_by_tag_name('img')

# downloadVehiclePic(make,model,year, listOfImgTag)







                
                