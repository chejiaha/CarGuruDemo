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
filterList = {}
carList = []
finalList = []

#category key-value dict
categoriesNumberDict = {0: "Commuter", 1: "Sport", 2: "Utility"}

categoriesInfoDict = {  0 : {"Name": "Commuter", "QuestionBranchName": "CommuterQuestion", "Prefix" : "cqa", "Count" : 0},
                        1 : {"Name": "Sport", "QuestionBranchName": "SportQuestion", "Prefix" : "sqa", "Count" : 0}     }

def getQuestionsAndAnswersToDisplay(typeOfQuestions, prefix, main):
    
    #get reference from database
    questionListsRef = questionRef.get(typeOfQuestions)
    
    # Prompt questions and ask for answers:
    for index, (key, question) in enumerate(questionListsRef.items()):
        #display question
        question = questionListsRef.get(key)
        questionDescription = question.get("Description")
        print("Question: %s " % ( questionDescription))

        #display answers
        questionAnswers = question.get("Answers")
        for key, answer in questionAnswers.items():
            print(str(key[4:]) + ". " + answer["Description"])

        #get user input
        answer = prefix + str(index + 1) + input("\n")
        
        if (main):
            handleMainQuestionsInputs(questionAnswers, answer)
        else:
            handleOtherTypesInputs(questionAnswers, answer, question)
    
    #reset
    maxCount = 0
    returnList = {}
    
    # return category with the most count
    if (main):
        for key, value in categoriesInfoDict.items():
            if (categoriesInfoDict[key]["Count"] > maxCount):
                returnList = categoriesInfoDict[key]
    
    return returnList
        
def handleMainQuestionsInputs(questionAnswers, answer):
    
    for key, value in categoriesInfoDict.items():
        if (questionAnswers[answer]["Value"] == categoriesInfoDict[key]["Name"] or categoriesInfoDict[key]["Name"] in questionAnswers[answer]["Value"]):
            categoriesInfoDict[key]["Count"] +=1  

    #Print out results
    for key, value in categoriesInfoDict.items():
        print(categoriesInfoDict[key]["Name"] + " : %s "  % categoriesInfoDict[key]["Count"])
        
def handleOtherTypesInputs(questionAnswers, answer, question):
    # get DBField and Value from the answer given by user
    dbField = question.get("DBField")
    answerValue = questionAnswers[answer]["Value"]

    #append filter to filterList
    filterList[dbField] = answerValue

def sortThroughCarList(categoryChosen):
    #Creating Lists for adding and removing 
    smallerList = set()
    greaterList = set()
    smallerThanList = set()
    greaterThanList = set()
    allList = set()
    normalList = set()
    yearList = set()

    for makeKey, make in vehicleRef.items():
        for modelKey, model in make.items():
            for trimKey, trim in model.items():
                for yearKey, year in trim.items():
                    if (str(year["Category"]) == categoryChosen):
                        #Key/ Value being {'Year': 'All', 'Weight': '<2000'...}
                        for key,val in filterList.items():
                            #Import printable to allow this.
                            #Checking if there are special characters
                            if (key == "Year"):
                                comparedValue = yearKey
                            else:
                                comparedValue = year[key] 

                            if ("<=" in val):
                                if(int(comparedValue) <= int(val[2:])):
                                    smallerThanList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            elif (">=" in val):  
                                if(int(comparedValue) >= int(val[2:])):
                                    greaterThanList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            elif ("<" in val):
                                if(int(comparedValue) < int(val[1:])):
                                    smallerList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            elif (">" in val):  
                                if(int(comparedValue) > int(val[1:])):
                                    greaterList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            elif ("-" in val):
                                if(int(comparedValue) >= int(val.split("-",1)[0]) and int(comparedValue) <= int(val.split("-",1)[1])):
                                    allList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            elif (val == "All"):
                                allList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
                            else:
                                if(comparedValue == val or comparedValue == "Both"):
                                    normalList.add("%s %s %s %s" %(makeKey, modelKey, trimKey, yearKey))
    
    list_of_sets = [smallerList, greaterList, smallerThanList, greaterThanList, allList, normalList, yearList]
    return list_of_sets

def printOutListOfCarsReturned(list_of_sets):
    # Empty sets evaluate to false,
    # so will be excluded from list comp.
    non_empties = [x for x in list_of_sets if x]
    solution_set = set.intersection(*non_empties)                   
    #print(list(solution_set))
    for car in list(solution_set):
        print(car)
        
def main():
    
    for key, value in categoriesInfoDict.items():
        categoriesInfoDict[key]["Count"] = 0
    
    #PART 1: get MAIN QUESTION branch reference
    categoryChosen = getQuestionsAndAnswersToDisplay("MainQuestion", "mqa", main=True)
    
    #PART 2: get specific CATEGORY QUESTION branch reference
    getQuestionsAndAnswersToDisplay(categoryChosen["QuestionBranchName"], categoryChosen["Prefix"], main=False)
    
    print(filterList)
    
    listOfSets = sortThroughCarList(categoryChosen["Name"])
    
    printOutListOfCarsReturned(listOfSets)

if __name__ == '__main__':
    main()

