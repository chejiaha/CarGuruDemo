import sys

#Connect to the database
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

cred = credentials.Certificate(r"C:\Users\mphg9\Downloads\serviceKey.json")
firebase_admin.initialize_app(cred,{"databaseURL":"https://car-guru-demo-default-rtdb.firebaseio.com"})

#get parent branches references 
questionRef = db.reference("Questions").get()
vehicleRef = db.reference("Vehicle").get()

#initialize
commuterCount = 0
sportCount = 0
filterList = {}
carList = []
finalList = []
#PART 1: get MAIN QUESTION branch reference
mainQuestion = questionRef.get("MainQuestion")

# Prompt questions and ask for answers:
for index, (key, question) in enumerate(mainQuestion.items()):
    #display question
    question = mainQuestion.get(key)
    questionDescription = question.get("Description")
    print("Question: %s " % ( questionDescription))

    #display answers
    questionAnswers = question.get("Answers")
    for key, answer in questionAnswers.items():
        print(str(key[4:]) + ". " + answer["Description"])

    #get user input
    answer = "mqa" + str(index + 1) + input("\n")

    #handle user choices
        # Used for questions that are String not Lists
    if (questionAnswers[answer]["Value"] == "Commuter"):
        commuterCount = commuterCount + 1
    elif (questionAnswers[answer]["Value"] == "Sport" ):
        sportCount = sportCount + 1
        # Used for questions that are Lists not String
    elif ("Sport" in questionAnswers[answer]["Value"]):
        sportCount = sportCount + 1
    elif ("Commuter" in questionAnswers[answer]["Value"]):
        commuterCount = commuterCount + 1
        #Used for question 5
    elif (questionAnswers[answer]["Value"] == str(2)):
        sportCount = sportCount + 1
        filterList["Seats"] = "2"
    else:
        commuterCount = commuterCount + 1
        filterList["Seats"] = questionAnswers[answer]["Value"]

    #Print out results
    print("Commuter: %s "  % commuterCount) 
    print("Sport: %s" % sportCount)

#PART 2: get specific CATEGORY QUESTION branch reference
if (commuterCount > sportCount):
    categoryQuestion = questionRef.get("CommuterQuestion")
    categoryChosen = "Commuter"
    prefix = "cqa"
else:
    categoryQuestion = questionRef.get("SportQuestion")
    categoryChosen = "Sport"
    prefix = "sqa"

#Prompt questions and ask for answers:
for index, (key, answer) in enumerate(categoryQuestion.items()):
    # display question
    question = categoryQuestion.get(key)
    questionDescription = question.get("Description")
    print("Question: %s " % ( questionDescription))

    # display answers
    questionAnswers = question.get("Answers")
    for key, answer in questionAnswers.items():
        print(str(key[4:]) + ". " + answer["Description"])

    answer = prefix + str(index+1) + input("\n")

    # get DBField and Value from the answer given by user
    dbField = question.get("DBField")
    answerValue = questionAnswers[answer]["Value"]

    #append filter to filterList
    filterList[dbField] = answerValue

print(filterList)

#Debug
# filterList = {'Year': '2016', 'Weight': '>2000', 'Convertible': 'Yes', 'GroundClearance': '5', 'Cylinders': '<=6'}
# categoryChosen = "Sport"

finalCarList = []

#Loop through each car with filterList, if the number of cars returned equals the number of filters 
# (also means that car matches all the conditions) then added it to the final list
for makeKey, make in vehicleRef.items():
    for modelKey, model in make.items():
        for trimKey, trim in model.items():
            for yearKey, year in trim.items():

                if (str(year["Category"]) == categoryChosen):
                    #Key/ Value being {'Year': 'All', 'Weight': '<2000'...}
                    conditionMatched = []
                    for key,val in filterList.items():
                        
                        #if condition is year
                        if (key == "Year"):
                            comparedValue = yearKey
                        else:
                            comparedValue = year[key] 

                        #if condition is <,>,<=,>=,-,All,Both,"MatchingString"
                        if ("<=" in val):
                            if(int(comparedValue) <= int(val[2:])):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        elif (">=" in val):  
                            if(int(comparedValue) >= int(val[2:])):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        elif ("<" in val):
                            if(int(comparedValue) < int(val[1:])):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        elif (">" in val):  
                            if(int(comparedValue) > int(val[1:])):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        elif ("-" in val):
                            if(int(comparedValue) >= int(val.split("-",1)[0]) and int(comparedValue) <= int(val.split("-",1)[1])):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        elif (val == "All"):
                            conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                        else:
                            if(comparedValue == val or comparedValue == "Both"):
                                conditionMatched.append("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))

                    if (len(conditionMatched) == len(filterList)):
                        print(conditionMatched[0])
                        finalCarList.append(conditionMatched[0])
print(finalCarList)