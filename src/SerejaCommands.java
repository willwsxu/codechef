
class SerejaCommands {
    int cmdsFr[];  // 
    int cmdsTo[];
    int cmdsType[];
    int cmdsCount[];
    
    void parse(int cmds[])
    {
        int m=cmds.length/3;
        cmdsFr = new int[m];
        cmdsTo = new int[m];
        cmdsType = new int[m];
        cmdsCount = new int[m];
        for (int i=0; i<m; i++) {
            cmdsType[i]=cmds[3*i];
            cmdsFr[i]=cmds[3*i+1];
            cmdsTo[i]=cmds[3*i+2];
        }
        cmdsCount[m-1]++;
        for (int i=m-1; i>=0; i--) {
            
        }
    }
    
    void bruteforce()
    {
        
    }
}
