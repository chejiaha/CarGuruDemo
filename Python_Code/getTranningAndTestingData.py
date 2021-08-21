#import statements
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import csv

#implement credential
cred = credentials.Certificate(r"C:\Users\mphg9\Downloads\serviceKey.json")

#initialize app with firebase_admin
firebase_admin.initialize_app(cred,{"databaseURL":"https://car-guru-demo-default-rtdb.firebaseio.com"})

#get data reference
vehicleRef = db.reference("Vehicle").get()
testingDataRef = db.reference("TestBranch").get()

#write to demoData.data file
trainningDataFile = open("demoData.data", "w")
trainningWriter = csv.writer(trainningDataFile, lineterminator='\n')

#write header
trainningWriter.writerow(("MPG", "Cylinders", "Engine", "Doors", "Seats", "Horsepower", "Weight", "GroundClearance", "Torque ft-lb", "EV", "Convertible", "Category"))

#loop through snapshot to get values
for make in vehicleRef.values():
    for model in make.values():
        for trim in model.values():
            for year in trim.values():
                #["MPG", "Cylinders", "Engine", "Doors", "Seats", "Horsepower", "Weight", "GroundClearance", "EV", "Convertible", "Category"]
                # Boolean values: Sport(1)-Commuter(0), Yes(1)-No(0)
                if (str(year["Convertible"]) == "Yes"):
                    convertible = 1
                elif (str(year["Convertible"]) == "Both"): 
                    convertible = 0.5
                else:
                    convertible = 0

                trainningWriter.writerow((str(year["MPG"]), str(year["Cylinders"]), str(year["Engine"]), str(year["Doors"]), str(year["Seats"]), str(year["Horsepower"]), str(year["Weight"]), str(year["GroundClearance"]), str(year["Torque ft-lb"]), (str(1) if str(year["EV"]) == "Yes" else str(0)), str(convertible), (str(1) if str(year["Category"]) == "Sport" else str(0))))

#close file                
trainningDataFile.close()

#write to testingData.data file
testingDataFile = open("testingData.data", "w")
testingWriter = csv.writer(testingDataFile, lineterminator='\n')

#write header
testingWriter.writerow(("Make", "Model", "Trim", "Year", "MPG", "Cylinders", "Engine", "Doors", "Seats", "Horsepower", "Weight", "GroundClearance", "Torque ft-lb", "EV", "Convertible"))

#loop through snapshot to get values
for makeKey, make in testingDataRef.items():
    for modelKey, model in make.items():
        for trimKey, trim in model.items():
            for yearKey, year in trim.items():
                testingWriter.writerow((makeKey, modelKey, trimKey, yearKey, str(year["MPG"]), str(year["Cylinders"]), str(year["Engine"]), str(year["Doors"]), str(year["Seats"]), str(year["Horsepower"]), str(year["Torque ft-lb"]), str(year["Weight"]), str(year["GroundClearance"]), (str(1) if str(year["EV"]) == "Yes" else str(0)), (str(1) if str(year["Convertible"]) == "Yes" else str(0))))

#close file 
testingDataFile.close()