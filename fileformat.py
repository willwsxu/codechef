
def unitToWindows():
    try:
        f = open("cliqueDistlargeEWD.txt", "r");
        #f = open("test.txt", "r")
        fout = open("winout.txt", "w");
        for line in f:
            #print(line)
            if (len(line)>1):
                line = line.replace ("0.", "")
                fout.write(line)
        f.close()
        fout.close()
    except IOError as e:
        print (e)
        return

unitToWindows()
