from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time


driver = webdriver.Firefox()

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
    •	(Drivetrain) 
    •	(Category) ** Added with 'Category Algorithm'  /Not in this parser.
    •	(Common Problems)** Another Parser Not in this parser.
    •	(Recalls)** Another parser /Not in this parser.

car_dict = trim1: {Doors:"xyz", MPG: "xyz"...}, trim2: {Doors:"xyz", MPG: "xyz"...}
'''

car_dict = {'Acura': {'ILX': {}, 'ILX Hybrid': {}, 'MDX': {}, 'MDX Hybrid': {}, 'NSX': {}, 'RDX': {}, 'RL': {}, 'RLX': {}, 'TL': {}, 'TLX': {}, 'TSX': {}, 'TSX Sport Wagon': {}, 'ZDX': {}}, 'Alfa Romeo': {'4C': {}, 'Giulia': {}, 'Stelvio': {}}, 'Aston Martin': {'DB9': {}, 'DBS': {}, 'Vantage': {}}, 'Audi': {'A3': {}, 'A4': {}, 'A4 Allroad': {}, 'A4 Wagon': {}, 'A5': {}, 'A6': {}, 'A6 Allroad': {}, 'A6':{}, 'Wagon': {}, 'A7': {}, 'A8': {}, 'Allroad': {}, 'e-tron': {}, 'e-tron GT': {}, 'Q3': {}, 'Q5': {}, 'Q7': {}, 'Q8': {}, 'R8': {}, 'TT': {}}, 'BMW': {'1-Series': {}, '2-Series': {}, '3-Series': {}, '3-Series Hybrid': {}, '3-Series Wagon': {}, '4-Series': {}, '5-Series': {}, '5-Series Hybrid': {}, '5-Series Wagon': {}, '6-Series': {}, '7-Series': {}, '7-Series Hybrid': {}, '8-Series': {}, 'i3': {}, 'X1': {}, 'X2': {}, 'X3': {}, 'X4': {}, 'X5': {}, 'X6': {}, 'X7': {}, 'Z4': {}}, 'Buick': {'Cascada': {}, 'Enclave': {}, 'Encore': {}, 'Encore GX': {}, 'Envision': {}, 'LaCrosse': {}, 'Lucerne': {}, 'Rainier': {}, 'Regal': {}, 'Rendezvous': {}, 'Verano': {}}, 'Cadillac': {'ATS': {}, 'CT4': {}, 'CT5': {}, 'CT6': {}, 'CTS': {}, 'CTS Sport Wagon': {}, 'DTS': {}, 'ELR': {}, 'Escalade': {}, 'Escalade Hybrid': {}, 'SRX': {}, 'STS': {}, 
'XLR': {}, 'XT4': {}, 'XT5': {}, 'XT6': {}, 'XTS': {}}, 'Chevrolet': {'Avalanche': {}, 'Aveo': {}, 'Blazer': {}, 'Bolt': {}, 'Bolt EUV': {}, 'Camaro': {}, 'Cobalt': {}, 'Colorado': {}, 'Corvette': {}, 'Cruze': {}, 'Equinox': {}, 'Express': {}, 'HHR': {}, 'Impala': {}, 'Malibu': {}, 'Malibu Hybrid': {}, 'Malibu Maxx': {}, 'Monte Carlo': {}, 'Silverado 1500': {}, 'Silverado 1500 Hybrid': {}, 'Silverado HD': {}, 'Sonic': {}, 'Spark': {}, 'SS': {}, 'Suburban': {}, 'Tahoe': {}, 'Tahoe Hybrid': {}, 'TrailBlazer': {}, 'Traverse': {}, 'Trax': {}, 'Uplander': {}, 'Volt': {}}, 'Chrysler': {'200': {}, '300': {}, 'Aspen': {}, 'Crossfire': {}, 'Pacifica': {}, 'Pacifica Hybrid': {}, 'PT Cruiser': {}, 'Sebring': {}, 'Town & Country': {}, 'Voyager': {}}, 'Dodge': {'Avenger': {}, 'Caliber': {}, 'Caravan': {}, 'Challenger': {}, 'Charger': {}, 'Dakota': {}, 'Dart': {}, 'Durango': {}, 'Grand Caravan': {}, 'Journey': {}, 'Magnum': {}, 'Nitro': {}, 'Ram 1500': {}, 'Ram HD': {}, 'Sprinter': {}, 'SRT Viper': {}, 'Viper': {}}, 'Ferrari': {'599 GTB Fiorano': {}, '612 Scaglietti': {}, 'California': {}, 'F430': {}, 'F458 Italia': {}}, 'FIAT': {'124 Spider': {}, '500': {}, '500L': {}, '500X': {}}, 'Ford': {'Bronco': {}, 'Bronco Sport': {}, 'C-Max Energi': {}, 'C-Max Hybrid': {}, 'Crown Victoria': {}, 'E-Series': {}, 'Econoline': {}, 'EcoSport': {}, 'Edge': {}, 'Escape': {}, 'Escape Hybrid': {}, 'Expedition': {}, 'Explorer': {}, 'Explorer Hybrid': {}, 'Explorer':{}, 
'Sport Trac': {}, 'F-150': {}, 'Fiesta': {}, 'Flex': {}, 'Focus': {}, 'Focus Electric': {}, 'Fusion': {}, 'Fusion Energi': {}, 'Fusion Hubrid': {}, 'Maverick': {}, 'Mustang': {}, 'Mustang Mach-E': {}, 'Ranger': {}, 'Super Duty': {}, 'Taurus': {}, 'Taurus X': {}, 'Transit Connect': {}}, 'Genesis': {'G70': {}, 'G80': {}, 'G90': {}, 'GV70': {}, 'GV80': {}}, 'GMC': {'Acadia': {}, 'Canyon': {}, 'Envoy': {}, 'Savana': {}, 'Sierra 1500': {}, 'Sierra 1500 Hybrid': {}, 'Sierra HD': {}, 'Terrain': {}, 'Yukon': {}, 'Yukon Hybrid': {}}, 'Honda': {'Accord': {}, 'Accord Hybrid': {}, 'Accord Plug-in': {}, 'Civic': {}, 'Civic Hybrid': {}, 'Clarity': {}, 'CR-V': {}, 'CR-V Hybrid': {}, 'CR-Z': {}, 'Crosstour': {}, 'Element': {}, 'Fit': {}, 'HR-V': {}, 'Insight': {}, 'Odyssey': {}, 'Passport': {}, 'Pilot': {}, 'Ridgeline': {}, 'S2000': {}}, 'HUMMER': {'H2': {}, 'H2 SUT': {}, 'H3': {}, 'H3T': {}}, 'Hyundai': {'C30': {}, 'C70': {}, 'S40': {}, 'S60': {}, 'S80': {}, 'S90': {}, 'V50': {}, 'V60': {}, 'V70': {}, 'V90': {}, 'XC40': {}, 'XC60': {}, 'XC70': {}, 'XC90': {}}, 'Infiniti': {'Arteon': {}, 'Atlas': {}, 'Beetle': {}, 'CC': {}, 'Eos': {}, 'GLI': {}, 'Golf': {}, 'Golf SportWagen': {}, 'GTI': {}, 'ID.4': {}, 'Jetta': {}, 'Jetta Hybrid': {}, 'Jetta SportWagen': {}, 'New Beetle': {}, 'Passat': {}, 'Passat Wagon': {}, 'R32': {}, 'Rabbit': {}, 'Routan': {}, 'Taos': {}, 'Tiguan': {}, 'Touareg': {}, 'Touareg Hybrid': {}}, 'Isuzu': {'4Runner': {}, '86': {}, 'Avalon': {}, 'Avalon Hybrid': {}, 'C-HR': {}, 'Camry': {}, 'Camry Hybrid': {}, 'Camry Solara': {}, 'Corolla': {}, 'Corolla Cross': {}, 'Corolla Hybrid': {}, 'Corolla iM': {}, 'FJ Cruiser': {}, 'GR 86': {}, 'Highlander': {}, 'Highlander Hybrid': {}, 'Land Cruiser': {}, 'Matrix': {}, 'Prius': {}, 'Prius c': {}, 'Prius Plug-in': {}, 'Prius Prime': {}, 'Prius V': {}, 'RAV4': {}, 'RAV4 Hybrid': {}, 'RAV4 Prime': {}, 'Sienna': {}, 'Supra': {}, 'Tacoma': {}, 'Tundra': {}, 'Venza': {}, 'Yaris': {}, 'Yaris iA': {}}, 'Jaguar': {'Model 3': {}, 'Model S': {}, 'Model X': {}, 'Model Y': {}, 'Roadster': {}}, 'Jeep': {'Aerio': {}, 'Equator': {}, 'Grand Vitara': {}, 'Kizashi': {}, 'Reno': {}, 'SX4': {}, 'SX4 Wagon': {}, 'XL7': {}}, 'Kia': {'Ascent': {}, 'BRZ': {}, 'Crosstrek': {}, 'Crosstrek Hybrid': {}, 'Forester': {}, 'Impreza': {}, 'Impreza Wagon': {}, 'Legacy': {}, 'Outback': {}, 'Tribeca': {}, 'WRX': {}, 'XV Crosstrek': {}, 'XV Crosstrek Hybrid': {}}, 'Lamborghini': {'Fortwo': {}}, 'Land Rover': {'FR-S': {}, 'iA': {}, 'iM': {}, 'iQ': {}, 'tC': {}, 'xB': {}, 'xD': {}, '': {}}, 'Lexus': {'Astra': {}, 'Aura': {}, 'Aura Hybrid': {}, 'Ion': {}, 'Outlook': {}, 'Sky': {}, 'VUE': {}, 'VUE Hybrid': {}}, 'Lincoln': {'9-3': {}, '9-3 Wagon': {}, '9-4X': {}, '9-5': {}, '9-5 Wagon': {}, '9-7X': {}}, 'Lotus': {'1500': {}, 'Dakota': {}, 'HD': {}}, 'Maserati': {'911': {}, '911-GT3': {}, '911-Turbo': {}, 'Boxster': {}, 'Cayenne': {}, 'Cayenne Hybrid': {}, 'Cayman': {}, 'Macan': {}, 'Panamera': {}, 'Taycan': {}}, 'Mazda': {'G3': {}, 'G5': {}, 'G6': {}, 'G8': {}, 'G8-GXP': {}, 'Grand Prix': {}, 'Solstice': {}, 'Torrent': {}, 'Vibe': {}}, 'Mercedes-Benz': {'2': {}}, 'Mercury': {'350Z': {}, '370Z': {}, 'Altima': {}, 'Altima Hybrid': {}, 'Armada': {}, 'Cube': {}, 'Frontier': {}, 'GT-R': {}, 'Juke': {}, 'Kicks': {}, 'Leaf': {}, 'Maxima': {}, 'Murano': {}, 'NV': {}, 'Pathfinder': {}, 'Pathfinder Hybrid': {}, 'Quest': {}, 'Rogue': {}, 'Rogue Hybrid': {}, 'Rogue Sport': {}, 'Sentra': {}, 'Titan': {}, 'Versa': {}, 'Xterra': {}}, 'MINI': {'Eclipse': {}, 'Eclipse Cross': {}, 'Endeavor': {}, 'Galant': {}, 'i': {}, 'Lancer': {}, 'Mirage': {}, 'Outlander': {}, 'Outlander Sport': {}, 'Raider': {}}, 'Mitsubishi': {'Cooper': {}, 'Cooper Clubman': {}, 'Cooper Countryman': {}, 'Cooper CoupeCooper Paceman': {}, 'Cooper Roadster': {}, 'Electric Hardtop': {}}, 'Nissan': {'Grand Marquis': {}, 'Mariner': {}, 'Mariner Hybrid': {}, 'Milan': {}, 'Milan Hybrid': {}, 'Montego': {}, 'Mountaineer': {}, 'Sable': {}}, 'Polestar': {'A-Class': {}, 'C-Class': {}, 'CL-Class': {}, 'CLA-Class': {}, 'CLK-Class': {}, 'CLS-Class': {}, 'E-Class': {}, 'E-Class Coupe': {}, 'E-Class Wagon': {}, 'G-Class': {}, 'GL-Class': {}, 'GLA-Class': {}, 'GLB-Class': {}, 'GLC-Class': {}, 'GLE-Class': {}, 'GLK-Class': {}, 'GLS-Class': {}}, 'Pontiac': {'B-Series': {}, 'CX-3': {}, 'CX-30': {}, 'CX-5': {}, 'CX-7': {}, 'CX-9': {}, 'Mazda2': {}, 'Mazda3': {}, 'Mazda5': {}, 'Mazda6': {}, 'Mazda6 Wagon': {}, 'Mazdaspeed3': {}, 'MX-5 Miata': {}, 'RX-8': {}, 'Tribute': {}}, 'Porsche': {'GranTurismo': {}, 'Levante': {}, 'Quattroporte': {}}, 'RAM': {'Elise': {}, 'Exige': {}}, 'Saab': {'Aviator': {}, 'Continental': {}, 'Corsair': {}, 'Mark LT': {}, 'MKC': {}, 'MKS': {}, 'MKT': {}, 'MKX': {}, 'MKZ': {}, 'MKZ Hybrid': {}, 'Nautilus': {}, 'Navigator': {}, 'Town Car': {}}, 'Saturn': {'CT Hybrid': {}, 'ES': {}, 'ES Hybrid': {}, 'GS': {}, 'GS Hybrid': {}, 'HX': {}, 'HS': {}, 'IS': {}, 'IS-F': {}, 'LC': {}, 'LFA': {}, 'LS': {}, 'LS Hybrid': {}, 'LX': {}, 'NX': {}, 'NX Hybrid': {}, 'RC': {}, 'RX 350': {}, 'RX Hybrid': {}, 'SC': {}, 'UX': {}, 'UX Hybrid': {}}, 'Scion': {'Defender': {}, 'Discovery': {}, 'Discovery Sport': {}, 'LR2': {}, 'LR3': {}, 'LR4': {}, 'Range Rover': {}, 'Range Rover Evoque': {}, 'Range Rover Sport': {}, 'Range Rover Velar': {}}, 'Smart': {'Gallardo': {}, 'Murcielago': {}}, 'Subaru': {'Amanti': {}, 'Borrego': {}, 'Cadenza': {}, 'Carnival': {}, 'Forte': {}, 'KS': {}, 'K900': {}, 'Niro': {}, 'Optima': {}, 'Optima Hybrid': {}, 'Rio': {}, 'Rio5': {}, 'Ronda': {}, 'Sedona': {}, 'Seltos': {}, 'Sorento': {}, 'Sorento Hybrid': {}, 'Soul': {}, 'Spectra': {}, 'Spectra5': {}, 'Sportage': {}, 'Stinger': {}, 'Telluride': {}}, 'Suzuki': {'Cherokee': {}, 'Commander': {}, 'Compass': {}, 'Gladiator': {}, 'Grand Cherokee': {}, 'Grand Cherokee L': {}, 'Grand Wagoneer': {}, 'Liberty': {}, 'Patriot': {}, 'Renegade': {}, 'Wagoneer': {}, 'Wrangler': {}}, 'Tesla': {'E-Pace': {}, 'F-Pace': {}, 'F-Type': {}, 'I-Pace': {}, 'S-Type': {}, 'X-Type': {}, 'X-Type Wagon': {}, 'XE': {}, 'XF': {}, 'XJ': {}, 'XJR': {}, 'XK': {}, 'XKR': {}}, 'Toyota': {'Ascender': {}, 'i-290': {}, 'i-370': {}}, 'Volkswagen': {'EX': {}, 'FX': {}, 'G35': {}, 'G37': {}, 'JX': {}, 'M': {}, 'M Hybrid': {}, 'Q40': {}, 'Q50': {}, 'Q50 Hybrid': {}, 'Q60': {}, 'Q70': {}, 'QX30': {}, 'QX50': {}, 'QX55': {}, 'QX56': {}, 'QX60': {}, 'QX70': {}, 'QX80': {}}, 'Volvo': {'Accent': {}, 'Azera': {}, 'Elantra': {}, 'Elantra Hybrid': {}, 'Elantra Touring': {}, 'Entourage': {}, 'Equus': {}, 'Genesis': {}, 'Genesis Coupe': {}, 'Ioniq': {}, 'Kona': {}, 'Kona EV': {}, 'Palisade': {}, 'Santa Cruz': {}, 'Santa Fe': {}, 'Santa Fe Hybrid': {}, 'Sonata': {}, 'Sonata Hybrid': {},
'Tiburon': {}, 'Tucson': {}, 'Tucson Hybrid': {}, 'Veloster': {}, 'Venue': {}, 'Veracruz': {}}}

info_dict = {"BMW": {"5-Series":{"4dr Sdn 528i":{"2016":{}}}}}
car_dict = {"BMW": {"5-Series":{"4dr Sdn 528i":{"2016":{}}}}}
make = "BMW"
model = "5-Series"
year = "2016"

# #href = "https://cars.usnews.com/cars-trucks/%s/%s/%s/specs/" % (make.lower(), model.lower(), year)
# href = "http://127.0.0.1:5500/testHtml.html"
# #href = "http://127.0.0.1:5500/testHtml2.html"
# #specHref = "/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379533"

# driver.get(href)
#  #Once you get the specific make, model and year
# # 2. Find the trim for the make and year 
# # The Links are in <h4> elements
# #Test: All L4 Headers
# all_headers = driver.find_elements_by_tag_name('h4')

# # element_list is a list to hold all elements for easy extraction of name of trim and href
# element_list = []
# #Going through each link and checking for class
# for element in all_headers:
#     element_code = element.get_attribute('innerHTML')
#     #If there is an a tag, put the a tag in the element list for later use.
#     if ("<a" in element_code):
#         # Get the a tag element
#         a_tag_element = element.find_element_by_tag_name('a')
#         element_list.append(a_tag_element)

# #There is a chance that there is only msrp, so we will only use the msrp car prices to keep consistancy
# price_spec_html = driver.find_elements_by_class_name("card__price")

# #Index to iterate through the prices
# index = 0
# # 3. Go through the list and set the trim name and link to the trim information 
# for a_tags in element_list:
#     # Get the trim and href from the tag
#     trim = a_tags.get_attribute('innerHTML').strip()
#     html_link = a_tags.get_attribute('href')
    
#     #Create the trim and the specific year that we are looking at.
#     car_dict[make][model][trim] = {}
#     car_dict[make][model][trim][year] = {}
    
#     info_dict[make][model][trim] = {}
#     info_dict[make][model][trim][year] = {}
    
#     # Put them in the dict to be individually 
#     info_dict[make][model][trim][year] = html_link
    
#     #Adding the price to the trim
#     spec_text = price_spec_html[index].text
#     #Avg Paid: $20,368
#     if ("Avg Paid" in spec_text):
#         avg_cost = spec_text[10:]
#         car_dict[make][model][trim][year]["Cost"] = avg_cost
#         # If the average is in the output, the msrp is also there so we have to add one to the index to get MSRP of the same car. 
#         index = index +1
#         spec_text = price_spec_html[index].text
#         #MSRP: $33,150
#     if ("MSRP" in spec_text):
#         msrp = spec_text[6:]
#         car_dict[make][model][trim][year]["MSRP"] = msrp
    
#     index = index + 1
#     #debug
#     print ("The trim is %s and the link is %s" % (trim, html_link))
#     print ("make: %s, model: %s year: %s, trim: %s" % (make,model,year,trim))
#         #debug
#         #print(car_dict)
# print(info_dict)

info_dict = {'BMW': {'5-Series': {'4dr Sdn 528i': {'2016': {}}, '4dr Sdn 320i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379533'}, '4dr Sdn 320i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379532'}, '4dr Sdn 328i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379804'}, '4dr Sdn 328d RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379808'}, '4dr Sdn 328d xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379809'}, '4dr Sports Wgn 328i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sports-wagon-379807'}, '4dr Sdn 330e Plug-In Hybrid RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-382983'}, '4dr Sports Wgn 328d xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sports-wagon-379810'}, '4dr Sdn 340i RWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379811'}, '4dr Sdn 340i xDrive AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-sedan-379813'}, '5dr 335i xDrive Gran Turismo AWD': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-wagon-379766'}, 'M3 4dr Sdn': {'2016': 'http://127.0.0.1:5500/cars-trucks/bmw/3-series/2016/specs/3-series-m3-sedan-379326'}}}, 
 'Audi':{'A4': {'a41': {'2016': {},'2017': {},'2018': {}},'a42':{'2016': {},'2017': {},'2018': {}}},'A5':{'a51': {'2016': {},'2017': {},'2018': {}},'a52':{'2016': {},'2017': {},'2018': {}}}},
 }

#After getting all of the trim and year information, as well as storing the href's of each datasheet, go through each datasheet and pull the description information needed
for make, models in info_dict.items():
    #debug
    print("make %s" % make)
    print("makes %s" % models)
    for model, trims in models.items():
        #debug
        print("model %s" % model)
        for trim, years in trims.items():
            #debug
            print("trim %s" % trim)
            i = 0
            for year, html_link in years.items():
                if html_link and i < 4:
                    driver.get(html_link)
                    i = i+1
                print ("make: %s, model: %s year: %s,trim: %s, \nhref:%s" % (make,model,year,trim, html_link))
                
                
                