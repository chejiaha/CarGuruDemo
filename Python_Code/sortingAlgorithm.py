from collections import Counter
import math
from matplotlib import pyplot as plt
from numpy import double
import csv

def knn(data, query, k, distance_fn, choice_fn):
    neighbor_distances_and_indices = []
    
    # 3. For each example in the data
    for index,example in enumerate(data):
        # 3.1 Calculate the distance between the query example and the current
        # example from the data.
        distance = distance_fn(example[:-1], query)
        # 3.2 Add the distance and the index of the example to an ordered collection
        neighbor_distances_and_indices.append((distance, index))

    # 4. Sort the ordered collection of distances and indices from
    # smallest to largest (in ascending order) by the distances
    sorted_neighbor_distances_and_indices = sorted(neighbor_distances_and_indices)   

    # 5. Pick the first K entries from the sorted collection
    k_nearest_distances_and_indices = sorted_neighbor_distances_and_indices[:k]

    # 6. Get the labels of the selected K entries
    k_nearest_labels = [data[i][-1] for distance, i in k_nearest_distances_and_indices]  
    
    # 7. If regression (choice_fn = mean), return the average of the K labels - predicting a quantity
    # 8. If classification (choice_fn = mode), return the mode of the K labels - predicting a label
    return k_nearest_distances_and_indices , choice_fn(k_nearest_labels)

def mean(labels):
    return sum(labels) / len(labels)

def mode(labels):
    return Counter(labels).most_common(1)[0][0]

def euclidean_distance(point1, point2):
    sum_squared_distance = 0
    for i in range(len(point1)):
        sum_squared_distance += math.pow(point1[i] - point2[i], 2)
    return math.sqrt(sum_squared_distance)

def main():

    car_data = []
    with open('demoData.data', 'r') as csvfile:
        next(csvfile)
        reader = csv.reader(csvfile)
        for row in reader:
            car_data.append([double(val) for val in row])

    # print(car_data)

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
                
    # #test one value
    # #["MPG", "Cylinders", "Engine", "Doors", "Seats", "Horsepower", "Weight", "GroundClearance", "Torque ft-lb", "EV", "Convertible"]
    # car_info = [25, 6, 3, 2, 2, 382, 3400, 5, 368, 0, 0]
    # car_k_nearest_neighbors, car_prediction = knn(
    #     car_data, car_info, k=1, distance_fn=euclidean_distance, choice_fn=mode
    # )
    # if (car_prediction == 0):
    #     print("Commuter car")
    # else:
    #     print("Sport car")

if __name__ == '__main__':
    main()
    
