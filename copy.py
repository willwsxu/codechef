#copy file content n times, line by line
def copyFile(file, n):
    with open(file+".out", "w") as out:
        with open(file, "r") as fr:
            for line in fr:
                for i in range(n):
                    out.write(line);

copyFile("..\\power-l.txt",550)
