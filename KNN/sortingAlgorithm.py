from collections import Counter
import math
from numpy import double
import csv
from mlxtend.plotting import plot_decision_regions
import matplotlib.pyplot as plt
from sklearn.svm import SVC
import numpy as np

def knn(data, query, k, distance_fn, choice_fn):
    neighbor_distances_and_indices = []

    # 3. For each example in the data
    for index,example in enumerate(data):
        # 3.1 Calculate the distance between the query example and the current
        # example from the data.
        distance = distance_fn(example[:-1], query)
        # 3.2 Add the distance and the index of the example to an ordered collection
        neighbor_distances_and_indices.append((distance, index, example[-1]))

    # 4. Sort the ordered collection of distances and indices from
    # smallest to largest (in ascending order) by the distances
    sorted_neighbor_distances_and_indices = sorted(neighbor_distances_and_indices)

    # 5. Pick the first K entries from the sorted collection
    k_nearest_distances_and_indices = sorted_neighbor_distances_and_indices[:k]

    # 6. Get the labels of the selected K entries
    k_nearest_labels = [data[i][-1] for distance, i, category in k_nearest_distances_and_indices]

    # 7. If regression (choice_fn = mean), return the average of the K labels - predicting a quantity
    # 8. If classification (choice_fn = mode), return the mode of the K labels - predicting a label
    return k_nearest_distances_and_indices , choice_fn(k_nearest_labels), sorted_neighbor_distances_and_indices

def mean(labels):
    return sum(labels) / len(labels)

def mode(labels):
    return Counter(labels).most_common(1)[0][0]

def euclidean_distance(point1, point2):
    sum_squared_distance = 0
    for i in range(len(point1)):
        sum_squared_distance += math.pow(point1[i] - point2[i], 2)
    return math.sqrt(sum_squared_distance)

def plotting(distances, testCarName):

    index = 1
    for row in distances:
        distances[index-1] = row + (index,);
        index+= 1;

    # add test data into the chart (distance, index, category, index in order)
    distances.append((0, 0, 2, 0))

    nparray = np.asarray(distances)

    X = nparray[:,(0,3)]
    intY = nparray.astype(np.int32)
    y = intY[:,2]

    # Training a classifier
    svm = SVC(C=0.5, kernel='linear')
    svm.fit(X, y)

    # Plotting decision regions
    plot_decision_regions(X, y, clf=svm)

    # Adding axes annotations
    plt.xlabel('distance')
    plt.ylabel('index')
    plt.title(testCarName)
    plt.show()

def main():

    car_trainning_data = []
    car_testing_data = []
    k = 10
    commuterCount = 0
    sportCount = 0

    with open('demoData.data', 'r') as csvfile:
        next(csvfile)
        reader = csv.reader(csvfile)
        for row in reader:
            car_trainning_data.append([double(val) for val in row])

    with open('testingData.data', 'r') as csvfile:
        next(csvfile)
        reader = csv.reader(csvfile)
        for row in reader:
            each_car = [double(val) for val in row[4:]]
            car_testing_data.append([double(val) for val in row[4:]]);

            commuterCount = 0
            sportCount = 0

            car_k_nearest_neighbors, car_prediction, list_of_distances = knn(
                car_trainning_data, each_car, k, distance_fn=euclidean_distance, choice_fn=mode
            )

            car_being_tested = row[0] + row[1] + row[2] + row[3]

            for value in list_of_distances[:k]:
                if (value[2] == 0):
                    commuterCount = commuterCount + 1
                else:
                    sportCount = sportCount + 1

            print("With k = {0}, {1} is {2}% Commuters and {3}% Sports".format(k, car_being_tested, round(commuterCount/k*100), round(sportCount/k*100)))

            plotting(list_of_distances, car_being_tested)

            if (car_prediction == 0):
                print(row[0] + " " + row[1]+ " " + row[2] + " " + row[3] + " is a Commuter car")
            else:
                print(row[0] + " " + row[1]+ " " + row[2] + " " + row[3] + " is a Sport car")

            print("\n")


    # #test one value
    # #["MPG", "Cylinders", "Engine", "Doors", "Seats", "Horsepower", "Weight", "GroundClearance", "Torque ft-lb", "EV", "Convertible"]
    # car_being_tested = ""
    # car_testing_data = [25, 6, 3, 2, 2, 382, 3400, 5, 368, 0, 0]
    # car_k_nearest_neighbors, car_prediction, list_of_distances = knn(
    #     car_trainning_data, car_testing_data, k=1, distance_fn=euclidean_distance, choice_fn=mode
    # )
    # if (car_prediction == 0):
    #     print("Commuter car")
    # else:
    #     print("Sport car")

    # plotting(list_of_distances, car_being_tested)

if __name__ == '__main__':
    main()
    
