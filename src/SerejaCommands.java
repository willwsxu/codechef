
class SerejaCommands {
    int cmdsFr[];  // 
    int cmdsTo[];
    int cmdsCount[];
    
    void parse(int cmds[])
    {
        int m=cmds.length/3;
        for (int i=0; i<m; i++) {
            if (cmds[3*i]==1) {
                cmdsFr[i]=cmds[3*i+1];
                cmdsTo[i]=cmds[3*i+2];
                cmdsCount[i]++;
            } else {
                
            }
        }
    }
    
    void bruteforce()
    {
        
    }
}
