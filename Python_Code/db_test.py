months = ["January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
months = [1,2,3,4,5,6,7,8,9,10,11,12]

years = [2015,2016,2017]
#years = [2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029]

for year in years:
  for month in months:
    copy_local = "hadoop fs -copyFromLocal ~/upload-example/FinalProject/DataSet/EditedData/%s_%s.txt" % (year,month) 
    copy_hadoop = " /finalproject/energydata/%s/%s" % (year, month)
    command = copy_local + copy_hadoop
    print(command)