months = ["January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
months = [1,2,3,4,5,6,7,8,9,10,11,12]

years = [2015,2016,2017,2018]
#years = [2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029]

# ''' Adding the files by the year they are in'''
# for year in years:
#   for month in months:
#     copy_local = "hadoop fs -copyFromLocal ~/upload-example/FinalProject/DataSet/EditedData/%s_%s.txt" % (year,month) 
#     copy_hadoop = " /finalproject/energydata/%s/%s" % (year, month)
#     command = copy_local + copy_hadoop
#     print(command)
    
# ''' remove the current files'''
# for year in years:
#   for month in months:
   
#     copy_local = " hadoop fs -rm /finalproject/energydata/%s/%s/%s_%s.txt" % (year,month,year,month) 
#     print(copy_local)

months = ["January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
months = [1,2,3,4,5,6,7,8,9,10,11,12]

years = [2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029]

# ''' Populating the weather folders'''
# for year in years:
#   mkdir_year = "hadoop fs -mkdir /finalproject/weatherdata/%s" % (year)
#   print(mkdir_year)
#   for month in months:
#      mkdir_month = "hadoop fs -mkdir /finalproject/weatherdata/%s/%s" % (year, month)
#      print(mkdir_month)

'''Populating folder data with weather files'''
for year in years:
  for month in months:
    mkdir_start = "hadoop fs -copyFromLocal ~/upload-example/FinalProject/DataSet/Weather/weatherdata/%s/%s/%s_%s.txt" % (year,month,year,month) 
    mkdir_end = " /finalproject/weatherdata/%s/%s/%s_%s.txt" % (year,month,year,month) 
    command = mkdir_start + mkdir_end
    print(command)


    