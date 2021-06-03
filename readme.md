Алгоритм начинает работу с выбора минимального столбца в матрице A1
(если таких столбцов несколько, то можно взять, например, самый левый). 
Среди строк матрицы , содержащих единицу в выбранном столбце, находится максимальная строка 
(если таких строк несколько, то для определенности можно взять самую верхнюю). 
После этого матрица заменяется матрицей A2, которая получается из путем удаления строки и всех столбцов, 
имеющих единичные значения на пересечении с данной строкой. 
Затем ищется минимальный столбец в матрице A2 и т. д., до тех пор, 
пока множество еще непокрытых столбцов на некотором шаге не окажется пустым. 
Совокупность выбранных к данному моменту строк и представляет искомое решение.