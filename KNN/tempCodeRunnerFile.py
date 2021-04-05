with open('testingData.data', 'r') as csvfile:
        next(csvfile)
        reader = csv.reader(csvfile)
        for row in reader:
            car_info = [double(val) for val in row[4:]]

            car_k_nearest_neighbors, car_prediction = knn(
                car_data, car_info, k=1, distance_fn=euclidean_distance, choice_fn=mode
            )
            if (car_prediction == 0):
                print(row[0] + " " + row[1]+ " " + row[2] + " " + row[3] + " is a Commuter car")
            else:
                print(row[0] + " " + row[1]+ " " + row[2] + " " + row[3] + " is a Sport car")